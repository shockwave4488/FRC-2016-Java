package org.usfirst.frc.team4488.robot;

public class Shooter {
	private ShooterWheels m_shooterWheels;
    private Indexer m_indexer;

    /// <summary>
    /// Initializes Shooter member variables.
    /// </summary>
    public Shooter()
    {
        ShooterWheels m_shooterWheels = new ShooterWheels();
        Indexer m_indexer = new Indexer();    
    }

    /// <summary>
    /// Spins the shooter wheel if the spin button is pressed.
    /// </summary>
    public void Spin()
    {
        m_shooterWheels.Spin();
        m_indexer.Stop();
    }

    /// <summary>
    /// Spins indexer wheels if the shooter is holding a ball, the shooter button is pressed,
    /// and shooter wheel speed is more than 95% RPM.
    /// </summary>
    public void Shoot()
    {
        m_indexer.Shoot();
    }

    public void Load()
    {
        m_shooterWheels.Load();
        m_indexer.Load();
    }

    public void MovePosistion()
    {

    }

}
