package org.usfirst.frc.team4488.robot.components;

import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4488.robot.RobotMap;

import JavaRoboticsLib.Utility.*;
import JavaRoboticsLib.ControlSystems.SimPID;

public class ShooterWheel {
	private Talon m_shooterWheel;
    private Counter m_shooterCounter;
    private SimPID m_pid;
    
    private double m_oldPosition;
    private double m_oldTime;
    private double m_rateBuffer;
    
    private final Object lockObject = new Object();
    
    private double m_shooterSpeed;   
    private double m_power;
    private final double m_tolerance;
    private int cycleCount;
    private int minCycleCount;
    
    private boolean m_purge;
    private boolean m_load;
    private boolean m_spin;
    
    public double getSpeed(){
       synchronized(lockObject){
    	   return m_rateBuffer;
       }
    }
    
    private void updateSpeed(){
    	synchronized(lockObject){
    		double distance = m_shooterCounter.getDistance();
    		double time = (double)Utility.getFPGATime() / 1000000.0;
    		double dx = distance - m_oldPosition;
    		double dt = time - m_oldTime;
    		//System.out.println("DT " + dt);
    		m_oldTime = time;
    		m_oldPosition = distance;
    		double rate = dx / dt; //rotations per second
    		m_rateBuffer = rate * 60;
    	}
    }

    public void setDeJam(boolean val){
    	synchronized(lockObject){
    		m_purge = val;
    	}
    }
    
    public boolean getPurge(){
    	synchronized(lockObject){
    		return m_purge;
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
    	m_pid.setDesiredValue(speed);
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
    
    public ShooterWheel(int motorChannel, int counterChannel, double ShooterP, double ShooterI, double ShooterD, double ShooterEps, double ShooterPIDDoneRange){
        m_shooterWheel = new Talon(motorChannel);
        m_shooterCounter = new Counter(counterChannel);
        m_shooterCounter.setDistancePerPulse(1.0 /1024.0);
        m_power = 0.5; //starting power value for shooter wheel
        cycleCount = 0; //amount of debounce cycles
        minCycleCount = 20; //minimum amount of debounce cycles
        m_tolerance = ShooterPIDDoneRange; //+- RPM tolerance
        try {
        	m_pid = new SimPID(ShooterP, ShooterI, ShooterD, ShooterEps);
			m_pid.setDoneRange(ShooterPIDDoneRange);
		} catch (Exception e) {
			System.out.println("Warning: ShooterWheel PID init failed");
		}
        m_oldTime = Utility.getFPGATime();
        if(motorChannel == RobotMap.ShooterMotorRight){
        	m_shooterWheel.setInverted(true);
        }
    }
    
    public void SpinWheel(){    	
 		if(getPurge()){
 			m_shooterWheel.set(-1.0);
 		}
 		else if (getLoad()){
            m_shooterWheel.set(-0.3);
        }
        else if (getSpin()){
        	updateSpeed();
     		double power = m_pid.calcPID(getSpeed());
     		if (power > 0) {
     			m_power = m_power + .015*power;
     		}else if (power < 0) {
     			m_power = m_power + .015*power;
     		}
     		
     		if (m_power > 1){
     			m_power = 1;
     		}
     		
        	m_shooterWheel.set(m_power);
        }
        else if (!getSpin()){
            m_shooterWheel.set(0);
        }
    }
  
    public boolean atSpeed(){
    	synchronized(lockObject){
    		double currSpeed = getSpeed();
    		
    		//check if close enough to target
	        if((currSpeed <= this.m_shooterSpeed + this.m_tolerance) && (currSpeed >= this.m_shooterSpeed - this.m_tolerance)) {
	        	if (this.cycleCount <= this.minCycleCount){
	        		this.cycleCount++;
	        	}
	        }
	        //not close enough to target
	        else {
	            this.cycleCount = 0;
	        }
	        	        
	        return this.cycleCount > this.minCycleCount;
    	}
    }
}
