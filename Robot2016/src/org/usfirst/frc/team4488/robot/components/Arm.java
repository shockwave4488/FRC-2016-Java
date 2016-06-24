

package org.usfirst.frc.team4488.robot.components;
import org.usfirst.frc.team4488.robot.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import JavaRoboticsLib.Utility.Logger;
import JavaRoboticsLib.ControlSystems.MotionControlledSystem;
import JavaRoboticsLib.ControlSystems.SimplePID;
import JavaRoboticsLib.FlowControl.EdgeTrigger;

public class Arm extends MotionControlledSystem {
    private SimplePID m_armPID;
    private Talon m_armMotor;
    private DigitalInput m_backLimit;
    private ArmPosition m_position;
    private double m_semiManualPosition;
    private boolean m_limitFound;
    private Timer m_findLimitWatchdog;
    private ArmEncoder m_encoder;
    /**
     * Constructor for the Arm class
     */
    public Arm() {
        m_armMotor = new Talon(RobotMap.ArmMotor);
        m_encoder = new ArmEncoder(RobotMap.ArmEncoderB, RobotMap.ArmEncoderA);
        double reduction = 2;
        m_encoder.setDistancePerPulse(1.0 / (1024 * reduction / 360));
        m_encoder.setReverseDirection(false);
        m_backLimit = new DigitalInput(RobotMap.ArmBackLimit);
        m_findLimitWatchdog = new Timer();
        m_findLimitWatchdog.start();
        try {
			m_armPID = new SimplePID(SmartDashboard.getNumber("Arm P",0), SmartDashboard.getNumber("Arm I",0), SmartDashboard.getNumber("Arm D",0), -0.35, 0.35);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        super.periodic = new Notifier(this::Update);
        super.Controller = m_armPID;
        super.Motor = m_armMotor;
        super.Sensor = m_encoder;
        super.SetpointTolerance = 2;
        m_limitFound = false;
        lowLimit = -20;
        highLimit = SmartDashboard.getNumber("ArmOffset", 120);
        setPosition(ArmPosition.High);
        Logger.addMessage("Arm Initialized", 1);
    }		
    /**
     * This moves the arm to the position requested as the input of the program.
     */
    public void setPosition(ArmPosition value){
    	SmartDashboard.putString("Arm Posiition", value.toString());
    	m_position = value;
        switch (value)
        {
        case High:
            super.setSetPoint(SmartDashboard.getNumber("ArmOffset", 120));
            break;

        case Load:
            super.setSetPoint(SmartDashboard.getNumber("ArmOffset", 120));
            break;
            
        case SemiManual:
            super.setSetPoint(m_semiManualPosition);            
            break;
            
        case Intake:
            super.setSetPoint(22);
            break;
            
        case Shoot:
            super.setSetPoint(70);
            break;
            
        case BatterShoot:
        	super.setSetPoint(33);
        	break;
            
        case Low:
        	super.setSetPoint(-10);
        	break;
        	
        }
    }
    /**
     * Returns the position to which the arm was set to
     */
    public ArmPosition getPosition(){
    	return m_position;
    }
    /**
     * Returns the present arm angle. Based on the encoder value
     * 
     * @return arm angle
     */
    public double armAngle(){
    	return m_encoder.pidGet();
    }
    /**
    * Set a manual value to take the arm. For some reason was called semi manual...
    * @param value
    */
    public void setSemiManualPosition(double value){
    	m_semiManualPosition = value;
    }
    /**
     * Set the value of the arm encoder to a given parameter. 
     * @param offset
     */
    public void resetEncoder(double offset){
    	m_encoder.reset(offset);
    }
    /**
     * Check the Hal Effect sensor that detects the arm at the top of its position
     * @return true when arm is close to the sensor
     */
    public boolean atBackLimit(){
    	return !m_backLimit.get();
    }
    /**
     * Read the variable that stores the limit sensor status. Once the sensor detected the arm, it is set to true.
     * @return status of the sensor 'found' state
     */
    public boolean getLimitFound(){
    	return m_limitFound;
    }
    /**
     * This method is responsible for finding the limit switch when the arm class starts. 
     */
    @Override
    public void Update(){
    	SmartDashboard.putBoolean("Limit", atBackLimit());
    	SmartDashboard.putBoolean("Arm Limit Found", m_limitFound);
    	/* If the limit switch was not found yet, and the robot is enabled, the arm is 
    	 * moved towards the upright position until it reaches the limit switch. 
    	 */
    	if (!m_limitFound){
    		if(!DriverStation.getInstance().isEnabled()){
    			m_findLimitWatchdog.reset();
    		}
    		if(!atBackLimit() && m_findLimitWatchdog.get() < 1.5) {
    			super.setManual(true);
    			super.setManualPower(0.1);
     		}
    		else{
    			super.setManual(false);
    			m_limitFound = true;
    			Logger.addMessage("Arm found limit");
    		}
    	}
    	
    	if(atBackLimit())
    		resetEncoder(SmartDashboard.getNumber("ArmOffset", 120));
    	
    	/*
    	if(super.Sensor.pidGet() > 50)
    		m_armPID.setGains(SmartDashboard.getNumber("Arm P",0) / 10, SmartDashboard.getNumber("Arm I",0) / 5, SmartDashboard.getNumber("Arm D",0) / 5);
    	    	else
    		m_armPID.setGains(SmartDashboard.getNumber("Arm P",0), SmartDashboard.getNumber("Arm I",0), SmartDashboard.getNumber("Arm D",0));
    		*/
    	    	
    	super.Update();
    }
    /**
     * Reset all the arm parameters
     */
    public void resetArm(){
    	Logger.addMessage("Arm Reset");
    	m_limitFound = false;
    	m_findLimitWatchdog.reset();
    }
}
