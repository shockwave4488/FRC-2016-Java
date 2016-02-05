package org.usfirst.frc.team4488.robot.components;

import org.usfirst.frc.team4488.robot.RobotMap;
import JavaRoboticsLib.Utility.Logger;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Indexer {
	private Talon m_indexWheel;
    private DigitalInput m_shooterBallSensor;
    
    /// <summary>
    /// Constructor for Indexer
    /// </summary>
    public Indexer(){
        m_indexWheel = new Talon(RobotMap.IndexMotorLeft);
        m_shooterBallSensor = new DigitalInput(RobotMap.IndexerBeamBreak);
        Logger.addMessage("Indexer Initialized", 1);
    }

    /// <summary>
    /// Gets whether ball is in the shooter or not
    /// </summary>
    public boolean ballInShooter(){
    	return m_shooterBallSensor.get();
    }

    /// <summary>
    /// Sets index wheel to push ball into shooter wheels
    /// </summary>
    public void shoot(){
        m_indexWheel.set(1);
    }

    /// <summary>
    /// Runs index wheels to load if there is no ball in shooter, stops otherwise
    /// </summary>
    public void load(){
        if (!ballInShooter()){
            m_indexWheel.set(-1);
        }
        else{
            m_indexWheel.set(0);
        }
    }

    /// <summary>
    /// Stops index wheels
    /// </summary>
    public void stop(){
        m_indexWheel.set(0);
    }
}


