package org.usfirst.frc.team4488.robot.components;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import JavaRoboticsLib.Utility.*;

/// <summary>
/// Shooter Wheel class, consisting of one shooter wheel and accompanying encoder
/// </summary>
public class ShooterWheel {
	private CANTalon m_shooterWheel;
    private Counter m_shooterCounter;
    private Derivative m_acceleration;
    private InputFilter m_filter;
    private double m_accelerationThreshold;
    
    private Object lockObject = new Object();
    
    private double m_shooterSpeed;
    private boolean m_load;
    private boolean m_spin;
    
    public double getTolerance(){
    	synchronized(lockObject){
    	return getShooterSpeed() * .95;
    	}
    }

    /// <summary>
    /// Gets current rate from counter of shooter wheels
    /// </summary>
    public double getRate(){
       synchronized(lockObject){
    	return m_shooterCounter.getRate();
       }
    }
    
    /// <summary>
    /// Gets and sets m_shooterSpeed
    /// </summary>
    public double getShooterSpeed(){
       synchronized(lockObject){
    	return m_shooterSpeed;
       }
    }
        
    public void setShooterSpeed(double speed){
    	synchronized(lockObject){
    	m_shooterSpeed = speed;
    	SmartDashboard.putNumber("Target RPM", speed);
    	}
    }    

    /// <summary>
    /// Gets and sets Load boolean
    /// </summary>
    public boolean getLoad(){
    	synchronized(lockObject){
    	return m_load;
    	}
    }
    
    public void setLoad(boolean thing){
    	synchronized(lockObject){
    	m_load = thing;
    	SmartDashboard.putBoolean("m_load", m_load);
    	}
    }
    
    /// <summary>
    /// Gets and sets Spin boolean
    /// </summary>
    public boolean getSpin(){
       synchronized(lockObject){
    	return m_spin;
       }
    }
    
    public void setSpin(boolean value){
        synchronized(lockObject){   
    	m_spin = value;
        }
    }
    
    /// <summary>
    /// Initializes ShooterWheel member variables using input channels
    /// </summary>
    public ShooterWheel(int motorChannel, int counterChannel){
        m_shooterWheel = new CANTalon(motorChannel);
        m_shooterCounter = new Counter(counterChannel);
        m_shooterCounter.setSamplesToAverage(100);
        m_acceleration = new Derivative();
        m_filter = new InputFilter();
    }

    /// <summary>
    /// Spins shooter wheel
    /// </summary>
    public void SpinWheel(){
		double rate = ((1.0 / m_shooterCounter.getPeriod())/1024.0)*60.0;
		SmartDashboard.putNumber("Shooter Rate 2", rate );
		if(rate != 0)System.out.println(rate);
        if (getLoad()){
            //m_shooterWheel.set(-0.3);
        }
        if (getSpin()){
        	//double rate = m_shooterCounter.getRate();
        	//System.out.println(m_shooterWheel.get());
            m_shooterWheel.set(rate < getShooterSpeed() ? 1 : 0.2);
        }
        if (!getSpin()){
            //m_shooterWheel.set(0);
        }
    }
  
    /// <summary>
    /// Checks if wheel rate is within tolerance of 95% of desired RPM
    /// </summary>
    /// <returns>True if within tolerance, false otherwise</returns>
    public boolean atRate(){
    	synchronized(lockObject){
    	return (getRate() > getTolerance() ? true : false);
    	}
    }
    
    public double currentRate(){
    	return m_shooterCounter.getRate();
    }
   
    /// <summary>
    /// Checks change in acceleration and RPM to see if ball was shot
    /// </summary>
    /// <returns>True if shoot, false if not</returns>
    public boolean ballShot(){
        return m_filter.get(m_acceleration.get(getRate())) < m_accelerationThreshold && getRate() < getTolerance();
    }
    
    public void MoveWheel(double trigger){
    	//m_shooterWheel.set(trigger);
    }

}
