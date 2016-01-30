

package org.usfirst.frc.team4488.robot.components;


import org.usfirst.frc.team4488.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

public class Intake
{
    private Talon m_intakeMotor;
    private DigitalInput m_armBallSensor;
    private Boolean SpinIntake;
    private Boolean StopIntake;
    private Boolean OutputIntake;
    public Intake() {
        m_intakeMotor = new Talon(RobotMap.IntakeMotor);
        m_armBallSensor = new DigitalInput(RobotMap.IntakeBeamBreak);
    }
    
    public boolean BallInIntake(){
    	return m_armBallSensor.get();
    }
    
    public void setSpinIntake(Boolean thing){
    	SpinIntake = thing;
    }
    
    public void setStopIntake(Boolean thing){
    	StopIntake = thing;
    }
    public void setOutputIntake(Boolean thing){
    	OutputIntake = thing;
    }

    public void UseIntake(){
        if (SpinIntake)
        {
            m_intakeMotor.set(1);
        }
        if (StopIntake)
        {
            m_intakeMotor.set(0);
        }
        if (OutputIntake)
        {
            m_intakeMotor.set(-1);
        }
    }
    }
