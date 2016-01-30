package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.components.Indexer;
import org.usfirst.frc.team4488.robot.components.ShooterPosition;
import org.usfirst.frc.team4488.robot.components.ShooterWheels;
import org.usfirst.frc.team4488.robot.components.Turret;

public class Shooter {
	private ShooterWheels m_shooterWheels;
    private Indexer m_indexer;
    private Turret m_turret;

    /// <summary>
    /// Initializes Shooter member variables.
    /// </summary>
    public Shooter()
    {
        m_shooterWheels = new ShooterWheels();
        m_indexer = new Indexer();    
    }

    /// <summary>
    /// Spins the shooter wheels 
    /// </summary>
    public void Spin()
    {
        m_shooterWheels.spin();
        m_indexer.stop();
    }

    /// <summary>
    /// Shoots ball if the shooter wheels are at the correct rate
    /// </summary>
    public void Shoot()
    {
        if (m_shooterWheels.atRate())
    	{
        	m_indexer.shoot();
    	}
        else 
        {
        	m_indexer.stop();
        }
    }
    
    /// <summary>
    /// Stops shooter and indexer wheels
    /// </summary>
    public void StopWheels()
    {
    	m_shooterWheels.stop();
    	m_indexer.stop();
    }

    /// <summary>
    /// Sets both shooter and indexer wheels to load
    /// </summary>
    public void Load()
    {
        m_shooterWheels.load();
        m_indexer.load();
    }

    /// <summary>
    /// Sets turret position
    /// </summary>
    /// <param name="position">Turret position@param
    public void MovePosition(ShooterPosition position)
    {
    	if(position == ShooterPosition.Aiming)
        {
            m_turret.setPosition(ShooterPosition.Aiming);
        }
        if (position == ShooterPosition.Stored)
        {
            m_turret.setPosition(ShooterPosition.Stored);
        }
        if (position == ShooterPosition.Load)
        {
            m_turret.setPosition(ShooterPosition.Load);
        }
    }

}
