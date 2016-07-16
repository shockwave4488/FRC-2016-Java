package org.usfirst.frc.team4488.robot.systems;

import JavaRoboticsLib.ControlSystems.SimPID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;

public class SmartDrive {
	
	private Drive m_drive;
	private SimPID m_turnController;
	private SimPID m_driveController;
	private SimPID m_straightController;
	private Preferences prefs;
	
	private final double crawlForwardAssist = .1; // Used to break static friction and help PID converge on angle
	
	public SmartDrive(Drive drive){
		m_drive = drive;
		try {
			prefs = Preferences.getInstance();
			m_turnController = new SimPID(prefs.getDouble("DriveTurnP", 0.05), prefs.getDouble("DriveTurnI",0.005), prefs.getDouble("DriveTurnD", 0), prefs.getDouble("DriveTurnEps", 0.0));
			m_turnController.setMaxOutput(0.6);
			m_turnController.setDoneRange(prefs.getDouble("DriveTurnDoneRange",0.25));
			m_driveController = new SimPID(prefs.getDouble("DriveP", 0.25), prefs.getDouble("DriveI",0.002), prefs.getDouble("DriveD", 0.001), prefs.getDouble("DriveEps", 0.5));
			m_driveController.setMaxOutput(0.5);
			m_straightController = new SimPID(0.08, 0, 0.001, 0.1);
			m_straightController.setMaxOutput(0.25);
		} catch (Exception e) {
			System.out.println("Oops");
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
	
	public void turnToCamera(double linearPower){
		double offset = Math.asin(-(13.25 / 12.0) / SmartDashboard.getNumber("Range", 8)) * (180.0 / Math.PI) + SmartDashboard.getNumber("TurnToCam Constant", 1.5); // correction for parallax
		m_turnController.setDesiredValue(SmartDashboard.getNumber("AzimuthX", m_drive.getAngle()) + offset);
		
		double power = m_turnController.calcPID(m_drive.getAngle());
	
		if(m_turnController.isDone()){
			power = 0; 
		}
		
		//maintain a minimum speed in either direction to aim
		if(Math.abs(linearPower) < crawlForwardAssist && !m_turnController.isDone()){
			linearPower = crawlForwardAssist;
		}
				
		m_drive.setPowers(linearPower + power, linearPower - power);
	}
	
	public double getTurnSetpoint(){
		return m_turnController.getDesiredVal();
	}
	
	public void driveToDistance(double distance){
		m_driveController.setDesiredValue(distance);
		double power = m_driveController.calcPID(m_drive.getLinearDistance());
		m_drive.setPowers(power, power);
	}
	
	public void driveToDistance(double distance, double heading){
		m_driveController.setDesiredValue(distance);
		m_straightController.setDesiredValue(heading);
		double power = m_driveController.calcPID(m_drive.getLinearDistance());
		double correction = m_straightController.calcPID(m_drive.getAngle());
		m_drive.setPowers(power + correction, power - correction);
	}
	
	public void turnToAngle(double angle){
		m_turnController.setDesiredValue(angle);
		double power = m_turnController.calcPID(m_drive.getAngle());
		System.out.println("Current angle: " + m_drive.getAngle() + " Power: " + power);
		m_drive.setPowers(power + crawlForwardAssist,  -power + crawlForwardAssist);
	}
	
	public void stop(){
		m_drive.setPowers(0,  0);
	}
	
	public void resetAll(){
		m_drive.resetAngle();
		m_drive.resetEncoders();
	}
	
	public boolean isDriveDone(){
		return m_driveController.isDone();
	}
	
	public boolean isTurnDone(){
		return m_turnController.isDone();
	}
	
	public void setDriveMaxOutput(double max){
		m_driveController.setMaxOutput(max);
	}
	
	public double getDriveMaxOutput(){
		return m_driveController.getMaxOutputVal();
	}
	
	public void setTurnDoneRange(double range){
		m_turnController.setDoneRange(range);
	}
	
	public double getTurnDoneRange(){
		return m_turnController.getDoneRangeVal();
	}
	
}
