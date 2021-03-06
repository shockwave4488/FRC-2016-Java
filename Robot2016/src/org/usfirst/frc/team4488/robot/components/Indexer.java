package org.usfirst.frc.team4488.robot.components;

import org.usfirst.frc.team4488.robot.RobotMap;
import JavaRoboticsLib.Utility.Logger;
import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Indexer {
	private Talon m_leftIndexWheel;
	private Talon m_rightIndexWheel;
    private DigitalInput m_shooterBallSensor;
    private Object lockObject = new Object();
    
    /// <summary>
    /// Constructor for Indexer
    /// </summary>
    public Indexer(){
        m_leftIndexWheel = new Talon(RobotMap.IndexMotorLeft);
        m_rightIndexWheel = new Talon(RobotMap.IndexMotorRight);
        m_leftIndexWheel.setInverted(false);
        m_rightIndexWheel.setInverted(true);
        m_shooterBallSensor = new DigitalInput(RobotMap.IndexerBeamBreak);
        Logger.addMessage("Indexer Initialized", 1); 
    }

    /// <summary>
    /// Gets whether ball is in the shooter or not
    /// </summary>
    public boolean ballInShooter(){
    	synchronized(lockObject){
    	return !m_shooterBallSensor.get();
    	}
    }

    /// <summary>
    /// Sets index wheel to push ball into shooter wheels
    /// </summary>
    public void shoot(){
    	double power = 1;//12.5 / ControllerPower.getInputVoltage();
        m_leftIndexWheel.set(power);
        m_rightIndexWheel.set(power);
    }

    /// <summary>
    /// Runs index wheels to load if there is no ball in shooter, stops otherwise
    /// </summary>
    public void load(){
    	if(!ballInShooter()){
            m_leftIndexWheel.set(-0.3);
            m_rightIndexWheel.set(-0.3);
    	}
    }

    /// <summary>
    /// Stops index wheels
    /// </summary>
    public void stop(){
        m_leftIndexWheel.set(0);
        m_rightIndexWheel.set(0);
    }
    
    public void spin(){
    	m_leftIndexWheel.set(-0.1);
    	m_rightIndexWheel.set(0.1);
    }
}


