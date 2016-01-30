package org.usfirst.frc.team4488.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Indexer {
	private Talon m_indexWheel;
    private DigitalInput m_shooterBallSensor;
    
    /// <summary>
    /// Constructor for Indexer
    /// </summary>
    public Indexer()
    {
        Talon m_indexWheel = new Talon(RobotMap.indexerWheelLeft);
        DigitalInput m_shooterBallSensor = new DigitalInput(RobotMap.IndexerBeamBreak);
    }

    /// <summary>
    /// Gets whether ball is in the shooter or not
    /// </summary>
    public Boolean IsBallInShooter()
    {return m_shooterBallSensor.get();}

    /// <summary>
    /// Sets index wheel to push ball into shooter wheels
    /// </summary>
    public void Shoot()
    {
        m_indexWheel.set(1);
    }

    /// <summary>
    /// Runs index wheels to load if there is no ball in shooter, stops otherwise
    /// </summary>
    public void Load()
    {
        if (!IsBallInShooter())
        {
            m_indexWheel.set(-1);
        }
        else
        {
            m_indexWheel.set(0);
        }
    }

    /// <summary>
    /// Stops index wheels
    /// </summary>
    public void Stop()
    {
        m_indexWheel.set(0);
    }


}


