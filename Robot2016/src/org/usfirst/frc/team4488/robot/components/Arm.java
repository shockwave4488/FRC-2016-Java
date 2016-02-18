

package org.usfirst.frc.team4488.robot.components;
import org.usfirst.frc.team4488.robot.RobotMap;
import edu.wpi.first.wpilibj.*;
import JavaRoboticsLib.Utility.Logger;
import JavaRoboticsLib.ControlSystems.MotionControlledSystem;
import JavaRoboticsLib.ControlSystems.SimplePID;

public class Arm extends MotionControlledSystem {
    private SimplePID m_armPID;
    private Talon m_armMotor;
    private AnalogPotentiometer m_armPotentiometer;
    private ArmPosition m_position;
    private double m_desiredArmPosition=0;
    
    public Arm() {
        m_armMotor = new Talon(RobotMap.ArmMotor);
        m_armPotentiometer = new AnalogPotentiometer(RobotMap.ArmPotentiometer);
        try {
			m_armPID = new SimplePID(1, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        super.Controller = m_armPID;
        super.Motor = m_armMotor;
        super.Sensor = m_armPotentiometer;
        Logger.addMessage("Arm Initialized", 1);
    }
    
    /*
     * This moves the arm to the position requested as the input of the program.
     */
    public void setPosition(ArmPosition value){
    	m_position = value;
        switch (value)
        {
        case High:
            super.setSetPoint(0);
            m_desiredArmPosition=0; //Whatever value is used above.
            break;

        case Load:
            super.setSetPoint(0);
            m_desiredArmPosition=0; //Whatever value is used above.
            break;
            
        case Low:
            super.setSetPoint(0);
            m_desiredArmPosition=0; //Whatever value is used above.
            break;
            
        case Intake:
            super.setSetPoint(0);
            m_desiredArmPosition=0; //Whatever value is used above.
            break;
            
        case Lower:
            super.setSetPoint(0);
            m_desiredArmPosition=0; //Whatever value is used above.
            break;
        }
    }
    
    /*
     * Returns the current position of the arm
     */
    public ArmPosition getPosition(){
    	return m_position;
    }
    
    public double getDesiredPosition(){
    	return m_desiredArmPosition;
    }
    
    /**
     * returns the current value of the arm's potentiometer
     * @return
     */
    public double getPotentiometer(){
    	return m_armPotentiometer.get();
    }

}
