

package org.usfirst.frc.team4488.robot.components;


import org.usfirst.frc.team4488.robot.RobotMap;

import JavaRoboticsLib.ControlSystems.SimplePID;
import JavaRoboticsLib.FlowControl.EdgeTrigger;
import edu.wpi.first.wpilibj.*;

public class Intake
{
	private EdgeTrigger m_sensorTrigger;
    private Talon m_intakeMotor;
    private DigitalInput m_armBallSensor;
    private boolean m_manual;
    private SimplePID m_holdingPID;
    private Timer m_waitTimer;
    
    private double m_manualPower;
    
    public Intake() {
        m_intakeMotor = new Talon(RobotMap.IntakeMotor);
        m_manual = false;
        m_manualPower = 0;
        m_sensorTrigger = new EdgeTrigger();
        m_waitTimer = new Timer();
        m_armBallSensor = new DigitalInput(RobotMap.IntakeBeamBreak);
        try {
			m_holdingPID = new SimplePID(0.25, 0, 0, -0.25, 0.25);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        m_holdingPID.setSetPoint(1);
    }
    
    /**
     *Returns the result of the beam break in the arm to determine whether there is a ball in the intake 
     */
    public boolean ballInIntake(){
    	return !m_armBallSensor.get();
    }
    
    /**
     * Spins the intake only if there is no ball in the intake. If there is, the intake is not spun.
     */
    public void intakeBall(){
    	if(m_sensorTrigger.getRisingUpdate(!m_armBallSensor.get()))
    		m_waitTimer.reset();
    	
    	if(ballInIntake()){
    		hold();
    	}
    	else{
    		m_intakeMotor.set(0.8);
    	}
    }
       
    /**
     * Turns off the intake motors.
     */
    public void off(){
    	m_intakeMotor.set(0);
    }
    
    /**
     * Pushes the ball into the shooter (Kiss pass)
     */
    public void load(){
    	m_intakeMotor.set(0.5);
    }
    
    /**
     * If the ball needs to be spat out for any reason, this function reverses the intake.
     */
    public void output(){
    	m_intakeMotor.set(-1);
    }
    
    public void hold(){
    	m_intakeMotor.set(m_waitTimer.get() > 0.10 ? 0 : 0.8);
    }
}
