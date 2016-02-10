package org.usfirst.frc.team4488.robot.components;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4488.robot.RobotMap;

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
    private double m_oldPosition;
    private double m_dt = 0.01;
    
    private Object lockObject = new Object();
    
    private double m_shooterSpeed;
    private boolean m_load;
    private boolean m_spin;
        
    public double getTolerance(){
    	synchronized(lockObject){
    	return getShooterSpeed() * .90;
    	}
    }

    /// <summary>
    /// Gets current rate from counter of shooter wheels
    /// </summary>
    public double getRate(){
       synchronized(lockObject){
    	double delta = delta();
       	delta /= m_dt; // dX / dT
       	return delta * 60;// RPS -> RPM
       }
    }
    
    public double delta(){
    	synchronized(lockObject){
    		return  m_shooterCounter.getDistance() - m_oldPosition; //dX
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
    	SmartDashboard.putNumber("Target Rate", speed);
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
        m_shooterWheel = new Talon(motorChannel);
        m_shooterCounter = new Counter(counterChannel);
        m_shooterCounter.setSamplesToAverage(50);
        m_shooterCounter.setDistancePerPulse(1.0 / 1024.0);
        m_acceleration = new Derivative();
        m_filter = new InputFilter();
        if(motorChannel == RobotMap.ShooterMotorRight)
        	m_shooterWheel.setInverted(true);
    }

    /// <summary>
    /// Spins shooter wheel
    /// </summary>
    public void SpinWheel(){
 		SmartDashboard.putNumber("Current Rate", getRate() );
        if (getLoad()){
            m_shooterWheel.set(-0.3);
        }
        else if (getSpin()){
        	m_shooterWheel.set(getRate() < getShooterSpeed() ? getShooterSpeed() / 5000 : 0);
        	if(getRate() != 0) System.out.println(getRate());
        }
        else if (!getSpin()){
            m_shooterWheel.set(0);
        }
        m_oldPosition = m_shooterCounter.getDistance();
    }
  
    /// <summary>
    /// Checks if wheel rate is within tolerance of 95% of desired RPM
    /// </summary>
    /// <returns>True if within tolerance, false otherwise</returns>
    public boolean atRate(){
    	synchronized(lockObject){
    	return true;//(getRate() > getTolerance());
    	}
    }
    
   
    /// <summary>
    /// Checks change in acceleration and RPM to see if ball was shot
    /// </summary>
    /// <returns>True if shoot, false if not</returns>
    public boolean ballShot(){
        return m_filter.get(m_acceleration.get(getRate())) < m_accelerationThreshold && getRate() < getTolerance();
    }
}
