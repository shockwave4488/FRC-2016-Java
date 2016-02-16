package org.usfirst.frc.team4488.robot.systems;

import JavaRoboticsLib.ControlSystems.SimplePID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDrive {
	
	private Drive m_drive;
	private SimplePID m_turnController;
	
	public SmartDrive(Drive drive){
		m_drive = drive;
		try {
			m_turnController = new SimplePID(0.25, 0, 0, -0.35, 0.35);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void turnToCamera(){
		m_turnController.setP(SmartDashboard.getNumber("DriveP", 0.25));
		m_turnController.setSetPoint(SmartDashboard.getNumber("AzimuthX", m_drive.getAngle()));
		double power = m_turnController.get(m_drive.getAngle());
		m_drive.setPowers(power, -power);
	}
	
	public void forwardToRamp(){
		double pitch = m_drive.getGyroscope().getPitch();
		double roll = m_drive.getGyroscope().getRoll();
		if(Math.abs(pitch) < 1)
			m_drive.setPowers(0.25, 0.25);
		else
			m_drive.setPowers(-0.1, -0.1);
	}
	
	public void backwardsToRamp(){
		double pitch = m_drive.getGyroscope().getPitch();
		double roll = m_drive.getGyroscope().getRoll();
		if(Math.abs(pitch) < 1)
			m_drive.setPowers(-0.25, -0.25);
		else
			m_drive.setPowers(0.1, 0.1);
	}
}
