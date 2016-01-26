package org.usfirst.frc.team4488.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Indexer {
	private Talon m_indexWheel;
    private DigitalInput m_shooterBallSensor;
    
    public Indexer()
    {
        Talon m_indexWheel = new Talon();
        DigitalInput m_shooterBallSensor = new DigitalInput();
    }

    public Boolean IsBallInShooter()
    {return m_shooterBallSensor.get();}

    public void Shoot()
    {
        m_indexWheel.set(1);
    }

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

    public void Stop()
    {
        m_indexWheel.set(0);
    }


}


