

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
    
    public Arm() {
        m_armMotor = new Talon(RobotMap.ArmMotor);
        m_encoder = new ArmEncoder(RobotMap.ArmEncoderA, RobotMap.ArmEncoderB);
        m_encoder.setDistancePerPulse(1.0 / (1024 * 4 / 360));
        m_encoder.setReverseDirection(false);
        m_backLimit = new DigitalInput(RobotMap.ArmBackLimit);
        m_findLimitWatchdog = new Timer();
        m_findLimitWatchdog.start();
        try {
			m_armPID = new SimplePID(SmartDashboard.getNumber("Arm P",0), SmartDashboard.getNumber("Arm I",0), SmartDashboard.getNumber("Arm D",0), -0.75, 0.75);
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
        highLimit = 110;
        Motor.setInverted(true);
        Logger.addMessage("Arm Initialized", 1);
    }
    		
    /*
     * This moves the arm to the position requested as the input of the program.
     */
    public void setPosition(ArmPosition value){
    	SmartDashboard.putString("Arm Posiition", value.toString());
    	m_position = value;
        switch (value)
        {
        case High:
            super.setSetPoint(110);
            break;

        case Load:
            super.setSetPoint(110);
            break;
            
        case SemiManual:
            super.setSetPoint(m_semiManualPosition);            
            break;
            
        case Intake:
            super.setSetPoint(18);
            break;
            
        case Shoot:
            super.setSetPoint(70);
            break;
            
        case Low:
        	super.setSetPoint(-10);
        	break;
        	
        }
    }
    
    /*
     * Returns the current position of the arm
     */
    public ArmPosition getPosition(){
    	return m_position;
    }
    
    public double armAngle(){
    	return m_encoder.pidGet();
    }

    public void setSemiManualPosition(double value){
    	m_semiManualPosition = value;
    }
    
    public void resetEncoder(double offset){
    	m_encoder.reset(offset);
    }
    
    public boolean atBackLimit(){
    	return !m_backLimit.get();
    }
    
    public boolean getLimitFound(){
    	return m_limitFound;
    }
    
    @Override
    public void Update(){
    	m_armPID.setGains(SmartDashboard.getNumber("Arm P",0), SmartDashboard.getNumber("Arm I",0), SmartDashboard.getNumber("Arm D",0));
    	SmartDashboard.putBoolean("Limit", atBackLimit());
    	SmartDashboard.putBoolean("Arm Limit Found", m_limitFound);
    	if(!m_limitFound){
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
    	super.Update();
    }
    
    public void resetArm(){
    	Logger.addMessage("Arm Reset");
    	m_limitFound = false;
    	m_findLimitWatchdog.reset();
    }
}
