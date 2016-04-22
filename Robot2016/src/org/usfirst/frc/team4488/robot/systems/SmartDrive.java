package org.usfirst.frc.team4488.robot.systems;

import JavaRoboticsLib.ControlSystems.SimplePID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDrive {
	
	private Drive m_drive;
	private SimplePID m_turnController;
	private SimplePID m_driveController;
	private SimplePID m_straightController;
	
	private double accumulation;
	
	public SmartDrive(Drive drive){
		m_drive = drive;
		try {
			m_turnController = new SimplePID(SmartDashboard.getNumber("DriveP", 0.045), SmartDashboard.getNumber("DriveI",0), SmartDashboard.getNumber("DriveD", 0), -0.5, 0.5);
			m_turnController.setContinuous(true);
			m_turnController.setMaxInput(360);
			m_turnController.setMinInput(0);
			m_turnController.setDt(1);
			m_driveController = new SimplePID(0.225, 0, 0, -0.5, 0.5);
			//m_straightController = new SimplePID(0.08, 0, 0, -0.1, 0.1); //PRACTICE
			m_straightController = new SimplePID(0.03, 0, 0, -0.1, 0.1);
			m_straightController.setContinuous(false);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Drive getDrive(){
		return m_drive;
	}
	
	public void batterBrake(double in){
		double power = in > 0.2 ? in : 0.2;
	}
	
	public void turnToCamera(){
		turnToCamera(0);
	}
	
	public void resetTurnIntegral(){
		System.out.println("resetting");
		m_turnController.resetIntegral();
	}
	
	public void turnToCamera(double linearPower){
		SmartDashboard.putNumber("Gyro", m_drive.getAngle());
		//if(SmartDashboard.getBoolean("TargetFound",false)){
		m_turnController.setGains(SmartDashboard.getNumber("DriveP", 0.040), SmartDashboard.getNumber("DriveI",0), SmartDashboard.getNumber("DriveD", .175));
		double offset = Math.asin(-(13.25 / 12.0) / SmartDashboard.getNumber("Range", 8)) * (180.0 / Math.PI) + SmartDashboard.getNumber("TurnToCam Constant", 1.5);
		m_turnController.setSetPoint(SmartDashboard.getNumber("AzimuthX", m_drive.getAngle()) + offset);
		accumulation += 0.02 * (m_turnController.getSetPoint() - m_drive.getAngle() + offset) * SmartDashboard.getNumber("DriveI",0);
		double power = m_turnController.get(m_drive.getAngle());
		System.out.println(power);
		m_drive.setPowers(linearPower + power, linearPower - power);
	}
	
	public double getTurnSetpoint(){
		return m_turnController.getSetPoint();
	}
		
	public boolean atCamera(double tolerance){
		//SmartDashboard.putNumber("Gyro", m_drive.getAngle());
		double setpoint = m_turnController.getSetPoint();
		return m_drive.getAngle() > (setpoint - tolerance) && m_drive.getAngle() < (setpoint + tolerance);
	}
	
	public void driveToDistance(double distance){
		m_driveController.setSetPoint(distance);
		double power = m_driveController.get(m_drive.getLinearDistance());
		m_drive.setPowers(power, power);
		
	}
	
	public void driveToDistance(double distance, double heading){
		m_driveController.setSetPoint(distance);
		m_straightController.setSetPoint(heading);
		double power = m_driveController.get(m_drive.getLinearDistance());
		double correction = m_straightController.get(m_drive.getAngle());
		m_drive.setPowers(power + correction, power - correction);
		
	}
	
	public void turnToAngle(double angle){
		if(m_turnController.getSetPoint() != angle)
			m_turnController.resetIntegral();
		m_turnController.setGains(SmartDashboard.getNumber("DriveP", 0.045), 0, SmartDashboard.getNumber("DriveD", 0));
		m_turnController.setSetPoint(angle);
		double power = m_turnController.get(m_drive.getAngle());
		m_drive.setPowers(power,  -power);
	}
	
	public void stop(){
		m_drive.setPowers(0,  0);
	}
	
	public void resetAll(){
		m_drive.resetAngle();
		m_drive.resetEncoders();
	}
	
}
