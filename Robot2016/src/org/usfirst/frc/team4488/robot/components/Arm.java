

package org.usfirst.frc.team4488.robot.components;
import org.usfirst.frc.team4488.robot.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import JavaRoboticsLib.Utility.Logger;
import JavaRoboticsLib.ControlSystems.MotionControlledSystem;
import JavaRoboticsLib.ControlSystems.SimplePID;

public class Arm extends MotionControlledSystem {
    private SimplePID m_armPID;
    private Talon m_armMotor;
    private AnalogPotentiometer m_armPotentiometer;
    private ArmPosition m_position;
    
    public Arm() {
        m_armMotor = new Talon(RobotMap.ArmMotor);
        m_armPotentiometer = new AnalogPotentiometer(RobotMap.ArmPotentiometer, 360, -178);
        try {
			m_armPID = new SimplePID(SmartDashboard.getNumber("Arm P",0), SmartDashboard.getNumber("Arm I",0), SmartDashboard.getNumber("Arm D",0), -0.75, 0.75);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        super.Controller = m_armPID;
        super.Motor = m_armMotor;
        super.Sensor = m_armPotentiometer;
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
            
        case Low:
            super.setSetPoint(-20);            
            break;
            
        case Intake:
            super.setSetPoint(18);
            break;
            
        case Shoot:
            super.setSetPoint(50);
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
    	return m_armPotentiometer.pidGet();
    }

    @Override
    public void Update(){
    	m_armPID.setGains(SmartDashboard.getNumber("Arm P",0), SmartDashboard.getNumber("Arm I",0), SmartDashboard.getNumber("Arm D",0));
    	super.Update();
    }
}
