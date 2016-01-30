package org.usfirst.frc.team4488.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Talon;
import JavaRoboticsLib.Utility.*;

public class ShooterWheel {
	private Talon m_shooterWheel;
    private Counter m_shooterCounter;
    private Derivative Acceleration;
    private InputFilter m_filter;
    private double AccelerationThreshold;
    private Object LockObject = new Object();
    public double Tolerance()
    {
    	return ShooterSpeed() * .95;
    }


    /// <summary>
    /// Target Rotations Per Minute for shooting.
    /// </summary>
    private double m_shooterSpeed;


    public double GetRate()
    {
       return m_shooterCounter.getRate()    
    }


    public double ShooterSpeed()
    {
       return m_shooterSpeed;
    }
        
    public void SetShooterSpeed(double speed)
    {	
    	m_shooterSpeed = speed;
    }
    

   
    private Boolean load;

    public Boolean Load()
    {
    	return load;
    }
    
    public void SetLoad(Boolean thing)
    {
    	load = thing;
    }
    



    private Boolean spin;

    public Boolean Spin()
    {
       return spin;
    }
    
    public void SetSpin(Boolean thingy)
        {
           spin = thingy;
        }
    




    /// <summary>
    /// Initializes ShooterWheel member variables using input channels
    /// </summary>
    public ShooterWheel()
    {
        m_shooterWheel = new Talon();
        m_shooterCounter = new Counter();

    }

    /// <summary>
    /// Spins shooter wheel
    /// </summary>
    public void SpinWheel()
    {
        if (Spin())
        {
            m_shooterWheel.set(GetRate() < ShooterSpeed() ? 1 : 0);
        }
        if (!Spin())
        {
            m_shooterWheel.set(0);
        }
        if (Load())
        {
            m_shooterWheel.set(-1);

        }
    }

  
    /// <summary>
    /// Checks if wheel rate is within tolerance of 95% of desired RPM
    /// </summary>
    /// <returns>True if within tolerance, false otherwise</returns>
    public Boolean atRate()
    {return (GetRate() > Tolerance() ? true : false);}
   

    public Boolean IsBallShot()
    {
        if ((m_filter.get(Acceleration.get(GetRate())) < AccelerationThreshold) && (GetRate() < Tolerance()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
