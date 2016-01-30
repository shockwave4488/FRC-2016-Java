

package org.usfirst.frc.team4488.robot.components;


import org.usfirst.frc.team4488.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

public class Intake
{
    private Talon m_intakeMotor;
    private DigitalInput m_armBallSensor;
    
    public Intake() {
        m_intakeMotor = new Talon(RobotMap.IntakeMotor);
        m_armBallSensor = new DigitalInput(RobotMap.IntakeBeamBreak);
    }
    
    public boolean ballInIntake(){
    	return m_armBallSensor.get();
    }
    
    public void intakeBall(){
    	if(m_armBallSensor.get()){
    		m_intakeMotor.set(0);
    	}
    	else{
    		m_intakeMotor.set(1);
    	}
    }
    
    public void off(){
    	m_intakeMotor.set(0);
    }
    
    public void load(){
    	m_intakeMotor.set(0);
    }
    
    public void output(){
    	m_intakeMotor.set(-1);
    }
}
