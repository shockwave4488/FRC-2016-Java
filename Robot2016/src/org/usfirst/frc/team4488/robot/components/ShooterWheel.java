package org.usfirst.frc.team4488.robot.components;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4488.robot.RobotMap;

import JavaRoboticsLib.Utility.*;
import JavaRoboticsLib.ControlSystems.SimplePID;

public class ShooterWheel {
	private Talon m_shooterWheel;
    private Counter m_shooterCounter;
    private SimplePID m_pid;
    private final double m_accelerationThreshold = 100;
    private final double m_tolerance = 0.025;
    private double m_oldPosition;
    private double m_oldTime;
    private double m_rateBuffer;
    
    private final Object lockObject = new Object();
    
    private double m_shooterSpeed;
    private boolean m_load;
    private boolean m_spin;
        
    private double getLowTolerance(){
    	return getShooterSpeed() * (1.0 - m_tolerance);
    }
    
    private double getHighTolerance(){
    	return getShooterSpeed() * (1.0 + m_tolerance);
    }

    public double getRate(){
       synchronized(lockObject){
    	   return m_rateBuffer;
       }
    }
    
    private void updateRate(){
    	synchronized(lockObject){
    		double dx = m_shooterCounter.getDistance() - m_oldPosition;
    		double dt = ((double)Utility.getFPGATime() / 1000000.0) - m_oldTime;
    		//System.out.println("DT " + dt);
    		m_oldTime = (double)Utility.getFPGATime() / 1000000.0;
    		m_oldPosition = m_shooterCounter.getDistance();
    		double rate = dx / dt; //rotations per second
    		m_rateBuffer = rate * 60;
    	}
    }

    public double getShooterSpeed(){
       synchronized(lockObject){
    	return m_shooterSpeed;
       }
    }
        
    public void setShooterSpeed(double speed){
    	synchronized(lockObject){
    	m_shooterSpeed = speed;
    	m_pid.setSetPoint(speed);
    	}
    }    

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
    
    public ShooterWheel(int motorChannel, int counterChannel){
        m_shooterWheel = new Talon(motorChannel);
        m_shooterCounter = new Counter(counterChannel);
        m_shooterCounter.setDistancePerPulse(1.0 /1024.0);
        try {
			m_pid = new SimplePID(0.005, 0, 0);
		} catch (Exception e) {}
        m_oldTime = Utility.getFPGATime();
        if(motorChannel == RobotMap.ShooterMotorRight){
        	m_shooterWheel.setInverted(true);
        }
    }
    
    public void SpinWheel(){    	
    	if(m_shooterWheel.getChannel() == RobotMap.ShooterMotorRight){
        	//System.out.println("RIGHTY " + getRate());
    	}
    	else{
    		//System.out.println("LEFTY " + getRate());
    	}
 		SmartDashboard.putNumber("Current Rate", getRate() );
 		
 		
 		double speed = m_pid.get(getRate()) + feedForward();
 		SmartDashboard.putNumber("Power", speed);
 		
        if (getLoad()){
            m_shooterWheel.set(-0.3);
        }
        else if (getSpin()){
        	m_shooterWheel.set(speed);
        	//if(getRate() != 0) System.out.println(getRate());
        }
        else if (!getSpin()){
            m_shooterWheel.set(0);
        }
        updateRate();
    }
  
    public boolean atRate(){
    	synchronized(lockObject){
    	return (getRate() > getLowTolerance() && getRate() < getHighTolerance());
    	}
    }
    
    private double feedForward(){
    	//6000 for practice
    	//6500 for competition
    	final double maxRPM = 6400;
    	return getShooterSpeed() / maxRPM;
    	/*
    	double rate = 0;
    	if(getShooterSpeed() > 4500)
    		rate = getShooterSpeed() / 6000;
    	else
    		rate = getShooterSpeed() / 7000;
    	SmartDashboard.putNumber("High", rate);
    	return rate;
    	*/
    }
}
