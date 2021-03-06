package org.usfirst.frc.team4488.robot.systems;

import JavaRoboticsLib.ControlSystems.SimPID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;

public class SmartDrive {

	private Drive m_drive;
	private SimPID m_turnController;
	private SimPID m_driveController;
	private SimPID m_driveSpeedController;
	private SimPID m_straightController;
	private Preferences prefs;

	private double m_driveTurnCrawlRangeMin;
	private double m_driveTurnCrawlPower; // Used to break static friction and
										// help PID converge on angle

	public SmartDrive(Drive drive) {
		m_drive = drive;
		try {
			prefs = Preferences.getInstance();
			m_turnController = new SimPID(prefs.getDouble("DriveTurnP", 0), prefs.getDouble("DriveTurnI", 0),
					prefs.getDouble("DriveTurnD", 0), prefs.getDouble("DriveTurnEps", 0));
			m_turnController.setMaxOutput(0.6);
			m_turnController.setDoneRange(prefs.getDouble("DriveTurnDoneRange", 0));
			m_driveTurnCrawlRangeMin = prefs.getDouble("DriveTurnCrawlRangeMin", 0);
			m_driveController = new SimPID(prefs.getDouble("DriveP", 0), prefs.getDouble("DriveI", 0),
					prefs.getDouble("DriveD", 0), prefs.getDouble("DriveEps", 0));
			m_driveController.setMaxOutput(.5);
			m_driveController.setDoneRange(2);
			m_driveController.setMinDoneCycles(1);
			m_driveSpeedController = new SimPID(prefs.getDouble("DriveSpeedP", 0), prefs.getDouble("DriveSpeedI", 0),
					prefs.getDouble("DriveSpeedD", 0), prefs.getDouble("DriveSpeedEps", 0));
			m_driveSpeedController.setMaxOutput(1);
			m_straightController = new SimPID(prefs.getDouble("DriveStraightP", 0), prefs.getDouble("DriveStraightI", 0),
					prefs.getDouble("DriveStraightD", 0), prefs.getDouble("DriveStraightEps", 0));
			m_straightController.setMaxOutput(0.10);
			m_driveTurnCrawlPower = prefs.getDouble("DriveTurnCrawlPower", 0);
		} catch (Exception e) {
			System.out.println("Oops");
			e.printStackTrace();
		}
	}

	public Drive getDrive() {
		return m_drive;
	}

	public void batterBrake(double in) {
		double power = in > 0.2 ? in : 0.2;
	}

	public void turnToCamera() {
		turnToCamera(0);
	}

	public void turnToCamera(double linearPower) {
		double offset = Math.asin(-(13.25) / SmartDashboard.getNumber("Range", 1)) * (180.0 / Math.PI)
				+ prefs.getDouble("TurnToCamConstant", 0); // correction for parallax
		m_turnController.setDesiredValue(SmartDashboard.getNumber("AzimuthX", m_drive.getAngle()) + offset);

		double power = m_turnController.calcPID(m_drive.getAngle());

		if (m_turnController.isDone()) {
			power = 0;
		}
			
		// maintain a minimum speed in either direction to aim
		if (Math.abs(linearPower) < m_driveTurnCrawlPower && !m_turnController.isDone()) {
			double range = SmartDashboard.getNumber("Range", 0);
			if (range < m_driveTurnCrawlRangeMin) {
				// if too close, crawl away from target to aim
				linearPower = -m_driveTurnCrawlPower;
			}else{
				linearPower = m_driveTurnCrawlPower;
			}
		}

		m_drive.setPowers(linearPower + power, linearPower - power);
	}

	public double getTurnSetpoint() {
		return m_turnController.getDesiredVal();
	}

	public void driveToDistance(double distance) {
		m_driveController.setDesiredValue(distance);
		double power = m_driveController.calcPID(m_drive.getLinearDistance());
		m_drive.setPowers(power, power);
	}

	public void driveToDistance(double distance, double heading) {
		m_driveController.setDesiredValue(distance);
		m_straightController.setDesiredValue(heading);
		double power = m_driveController.calcPID(m_drive.getLinearDistance());
		double angleCorrection = m_straightController.calcPID(m_drive.getAngle());
		m_drive.setPowers(power + angleCorrection, power - angleCorrection);
	}
	
	public void driveToSpeed(double speed, double heading) {
		m_driveSpeedController.setDesiredValue(speed);
		m_straightController.setDesiredValue(heading);
		double power = m_driveSpeedController.calcPID(m_drive.getLinearSpeed());
		double angleCorrection = m_straightController.calcPID(m_drive.getAngle());
		m_drive.setPowers(power + angleCorrection, power - angleCorrection);
	}

	public void turnToAngle(double angle) {
		m_turnController.setDesiredValue(angle);
		double power = m_turnController.calcPID(m_drive.getAngle());
		m_drive.setPowers(power + m_driveTurnCrawlPower, -power + m_driveTurnCrawlPower);
	}

	public void stop() {
		m_drive.setPowers(0, 0);
	}

	public void resetAll() {
		m_drive.resetAngle();
		m_drive.resetEncoders();
	}

	public boolean isDriveDistanceDone() {
		return m_driveController.isDone();
	}

	public boolean isTurnDone() {
		return m_turnController.isDone();
	}

	public void setDriveMaxOutput(double max) {
		m_driveController.setMaxOutput(max);
	}

	public double getDriveMaxOutput() {
		return m_driveController.getMaxOutputVal();
	}

	public void setTurnDoneRange(double range) {
		m_turnController.setDoneRange(range);
	}

	public double getTurnDoneRange() {
		return m_turnController.getDoneRangeVal();
	}

	public void setDriveDoneRange(double range) {
		m_driveController.setDoneRange(range);
	}

	public double getDriveDoneRange() {
		return m_driveController.getDoneRangeVal();
	}

	public void setTurnMinDoneCycles(int cycles) {
		m_turnController.setMinDoneCycles(cycles);
	}

	public int getTurnMinDoneCycles() {
		return m_turnController.getMinDoneCycles();
	}

	public void setDriveMinDoneCycles(int cycles) {
		m_driveController.setMinDoneCycles(cycles);
	}

	public int getDriveMinDoneCycles() {
		return m_driveController.getMinDoneCycles();
	}
}
