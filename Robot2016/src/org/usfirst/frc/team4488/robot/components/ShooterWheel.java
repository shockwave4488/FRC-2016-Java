package org.usfirst.frc.team4488.robot.components;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Talon;
import JavaRoboticsLib.Utility.*;

/// <summary>
/// Shooter Wheel class, consisting of one shooter wheel and accompanying encoder
/// </summary>
public class ShooterWheel {
	private Talon m_shooterWheel;
    private Counter m_shooterCounter;
    private Derivative m_acceleration;
    private InputFilter m_filter;
    private double m_accelerationThreshold;
    
    private double m_shooterSpeed;
    private boolean m_load;
    private boolean m_spin;
    
    public double getTolerance(){
    	return getShooterSpeed() * .95;
    }

    /// <summary>
    /// Gets current rate from counter of shooter wheels
    /// </summary>
    public double getRate(){
       return m_shooterCounter.getRate();    
    }
    
    /// <summary>
    /// Gets and sets m_shooterSpeed
    /// </summary>
    public double getShooterSpeed(){
       return m_shooterSpeed;
    }
        
    public void setShooterSpeed(double speed){	
    	m_shooterSpeed = speed;
    }    

    /// <summary>
    /// Gets and sets Load boolean
    /// </summary>
    public boolean getLoad(){
    	return m_load;
    }
    
    public void setLoad(boolean thing){
    	m_load = thing;
    }
    
    /// <summary>
    /// Gets and sets Spin boolean
    /// </summary>
    public boolean getSpin(){
       return m_spin;
    }
    
    public void setSpin(boolean value){
           m_spin = value;
    }
    
    /// <summary>
    /// Initializes ShooterWheel member variables using input channels
    /// </summary>
    public ShooterWheel(int motorChannel, int counterChannel){
        m_shooterWheel = new Talon(motorChannel);
        m_shooterCounter = new Counter(counterChannel);
        m_acceleration = new Derivative();
        m_filter = new InputFilter();
    }

    /// <summary>
    /// Spins shooter wheel
    /// </summary>
    public void SpinWheel(){
        if (getLoad()){
            m_shooterWheel.set(-0.5);
        }
        if (getSpin()){
            m_shooterWheel.set(getRate() < getShooterSpeed() ? 1 : 0);
        }
        if (!getSpin()){
            m_shooterWheel.set(0);
        }
    }
  
    /// <summary>
    /// Checks if wheel rate is within tolerance of 95% of desired RPM
    /// </summary>
    /// <returns>True if within tolerance, false otherwise</returns>
    public boolean atRate(){
    	return (getRate() > getTolerance() ? true : false);
    }
   
    /// <summary>
    /// Checks change in acceleration and RPM to see if ball was shot
    /// </summary>
    /// <returns>True if shoot, false if not</returns>
    public boolean ballShot(){
        return m_filter.get(m_acceleration.get(getRate())) < m_accelerationThreshold && getRate() < getTolerance();
    }

}
