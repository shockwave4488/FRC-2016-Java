package org.usfirst.frc.team4488.robot.systems;

import JavaRoboticsLib.ControlSystems.SimplePID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDrive {
	
	private Drive m_drive;
	private SimplePID m_turnController;
	private SimplePID m_driveController;
	private SimplePID m_straightController;
	
	public SmartDrive(Drive drive){
		m_drive = drive;
		try {
			m_turnController = new SimplePID(0.25, 0, 0, -0.15, 0.15);
			m_turnController.setContinuous(true);
			m_turnController.setMaxInput(360);
			m_turnController.setMinInput(0);
			m_driveController = new SimplePID(0.2, 0, 0, -0.5, 0.5);
			m_straightController = new SimplePID(0, 0, 0, -0.1, 0.1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Drive getDrive(){
		return m_drive;
	}
	
	public void turnToCamera(){
		m_turnController.setP(SmartDashboard.getNumber("DriveP", 0.25));
		m_turnController.setSetPoint(SmartDashboard.getNumber("AzimuthX", m_drive.getAngle()));
		double power = m_turnController.get(m_drive.getAngle());
		m_drive.setPowers(power, -power);
	}
	
	public void driveToDistance(double distance, double heading){
		m_driveController.setSetPoint(distance);
		m_straightController.setSetPoint(heading);
		double power = m_driveController.get(m_drive.getLinearDistance());
		double correction = m_straightController.get(m_drive.getAngle());
		m_drive.setPowers(power + correction, power - correction);
	}
	
	public void turnToAngle(double angle){
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
