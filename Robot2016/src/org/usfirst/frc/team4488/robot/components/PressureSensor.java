package org.usfirst.frc.team4488.robot.components;

import org.usfirst.frc.team4488.robot.RobotMap;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PressureSensor {
	private final Object m_lock = new Object();
	private Notifier m_catchLoop;
	private AnalogInput m_sensor;
	private Timer m_snapshotTimer;
	private double m_snapshot;
	private double m_normal;
	
	public PressureSensor(){
		m_catchLoop = new Notifier(this::checkSnapshot);
		m_catchLoop.startPeriodic(0.001);
		m_sensor = new AnalogInput(RobotMap.PressureSensor);
		m_snapshotTimer = new Timer();
		m_snapshotTimer.start();
		m_snapshot = 0;
	}
	
	public void resetSnapshot(){
		m_snapshotTimer.reset();
		synchronized(m_lock){
		m_snapshot = 0;
		}
	}
	
	public void checkSnapshot(){
		
		if(m_snapshotTimer.get() > 3 && m_snapshot == 0){
			m_snapshot = m_sensor.getVoltage();// - m_normal;
			//SmartDashboard.putBoolean("Ball Shot?", m_snapshot > 3.25);
			//SmartDashboard.putNumber("Pressure Sensor Normal", m_normal);
			//SmartDashboard.putNumber("Pressure Sensor", m_snapshot);
		}
		
		/*
		double reading = m_sensor.getVoltage();
		if(reading > m_snapshot){
			synchronized(m_lock){
			m_snapshot = reading;// - m_normal;
			}
			SmartDashboard.putNumber("Pressure Sensor Normal", m_normal);
			SmartDashboard.putNumber("Pressure Sensor", getSnapshot());
		}
		*/
	}
	
	public double getSnapshot(){
		synchronized(m_lock){
			return m_snapshot;
		}
	}
	
	public void setNormal(){
		m_normal = m_sensor.getAverageVoltage();
	}
	
	public double getScalar(){
		double dif = 3.0 - getSnapshot();
		dif *= 0.5;
		return dif;
	}
	
	public double getOffset(){
		double dif = 3.0 - getSnapshot();
		dif *= 750;
		return dif;
	}
	
	public double getRPMAdjust(){
		if(m_snapshot > 3.25){ //good ball
			return 0;
		}
		else {//bad ball
			return 700;
		}
	}
	
	public double getAngleAdjust(){
		if(m_snapshot > 3.25){
			return 0;
		}
		else {
			return 1.5;
		}
	}
		
	private double getAdjustedVoltage(){
		return m_sensor.getVoltage() * 5 / ControllerPower.getVoltage5V();
	}
}
