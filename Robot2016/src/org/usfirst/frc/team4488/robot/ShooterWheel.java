package org.usfirst.frc.team4488.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Talon;
import JavaRoboticsLib.Utility.*;

/// <summary>
/// Shooter Wheel class, consisting of one shooter wheel and accompanying encoder
/// </summary>
public class ShooterWheel {
	private Talon m_shooterWheel;
    private Counter m_shooterCounter;
    private Derivative Acceleration;
    private InputFilter m_filter;
    private double AccelerationThreshold;
    public double Tolerance()
    {
    	return ShooterSpeed() * .95;
    }


    /// <summary>
    /// Target Rotations Per Minute for shooting.
    /// </summary>
    private double m_shooterSpeed;

    /// <summary>
    /// Gets current rate from counter of shooter wheels
    /// </summary>
    public double GetRate()
    {
       return m_shooterCounter.getRate();    
    }

    /// <summary>
    /// Gets and sets m_shooterSpeed
    /// </summary>
    public double ShooterSpeed()
    {
       return m_shooterSpeed;
    }
        
    public void SetShooterSpeed(double speed)
    {	
    	m_shooterSpeed = speed;
    }
    

   
    private Boolean load;
    /// <summary>
    /// Gets and sets Load boolean
    /// </summary>
    public Boolean Load()
    {
    	return load;
    }
    
    public void SetLoad(Boolean thing)
    {
    	load = thing;
    }
    



    private Boolean spin;
    /// <summary>
    /// Gets and sets Spin boolean
    /// </summary>
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
    public ShooterWheel(int motorChannel, int counterChannel)
    {
        m_shooterWheel = new Talon(motorChannel);
        m_shooterCounter = new Counter(counterChannel);

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
   
    /// <summary>
    /// Checks change in acceleration and RPM to see if ball was shot
    /// </summary>
    /// <returns>True if shoot, false if not</returns>
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
