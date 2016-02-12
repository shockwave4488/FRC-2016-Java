

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
    
    /*
     *Returns the result of the beam break in the arm to determine whether there is a ball in the intake 
     */
    public boolean ballInIntake(){
    	return !m_armBallSensor.get();
    }
    
    /*
     * Spins the intake only if there is no ball in the intake. If there is, the intake is not spun.
     */
    public void intakeBall(){
    	if(m_armBallSensor.get()){
    		m_intakeMotor.set(0);
    	}
    	else{
    		m_intakeMotor.set(1);
    	}
    }
    
    /*
     * Turns off the intake motors.
     */
    public void off(){
    	m_intakeMotor.set(0);
    }
    
    /*
     * Turns off the intake motors for loading specifically.
     */
    public void load(){
    	m_intakeMotor.set(0);
    }
    
    /*
     * If the ball needs to be spat out for any reason, this function reverses the intake.
     */
    public void output(){
    	m_intakeMotor.set(-1);
    }
}
