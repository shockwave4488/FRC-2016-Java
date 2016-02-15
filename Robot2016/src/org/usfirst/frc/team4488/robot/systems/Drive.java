package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.RobotMap;
import com.kauailabs.navx.frc.AHRS;
import JavaRoboticsLib.Drive.Interfaces.*;
import JavaRoboticsLib.WPIExtensions.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive implements TankDrive{
	
	//private Talon m_rightWheel;
	//private Talon m_leftWheel;

	private static final double RAMPRATE = 60; //Volts per second
	private static final int COUNTSPERREV = 360; //Encoder counts per revolution
	
	private CANTalon m_left;
	private SpeedControllerGroup m_leftFollowers;
	private CANTalon m_right;
	private SpeedControllerGroup m_rightFollowers;
	
	private AHRS m_navx;
	
	public Drive() {
		try {

			m_left = new CANTalon(RobotMap.DriveMotorLeft1);
			CANTalon leftSlave1 = new CANTalon(RobotMap.DriveMotorLeft2);
            CANTalon leftSlave2 = new CANTalon(RobotMap.DriveMotorLeft3);
            leftSlave1.changeControlMode(TalonControlMode.Follower);
            leftSlave1.set(RobotMap.DriveMotorLeft1);
            leftSlave2.changeControlMode(TalonControlMode.Follower);
            leftSlave2.set(RobotMap.DriveMotorLeft1);
            m_leftFollowers = new SpeedControllerGroup(new SpeedController[]{leftSlave1, leftSlave2});
                        
            m_right = new CANTalon(RobotMap.DriveMotorRight1);
            CANTalon rightSlave1 = new CANTalon(RobotMap.DriveMotorRight2);
            CANTalon rightSlave2 = new CANTalon(RobotMap.DriveMotorRight3);
            rightSlave1.changeControlMode(TalonControlMode.Follower);
            rightSlave1.set(RobotMap.DriveMotorRight1);
            rightSlave2.changeControlMode(TalonControlMode.Follower);
            rightSlave2.set(RobotMap.DriveMotorRight1);
            m_rightFollowers = new SpeedControllerGroup(new SpeedController[]{rightSlave1, rightSlave2});
            
            m_left.enableBrakeMode(false);
            m_right.enableBrakeMode(false);
            m_left.setVoltageRampRate(RAMPRATE);
            m_right.setVoltageRampRate(RAMPRATE);
            m_left.configEncoderCodesPerRev(COUNTSPERREV);
            m_right.configEncoderCodesPerRev(COUNTSPERREV);
            m_right.setInverted(true);
            m_navx = new AHRS(SPI.Port.kMXP);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	/**
	 * Sets the CIM motors on the West Coast Drive train to the leftPower and rightPower. Essential for the TankDrive interface
	 */
	public void setPowers(double leftPower, double rightPower) {
		m_left.set(leftPower);
		m_right.set(rightPower);
		SmartDashboard.putNumber("Drive Speed Left", m_left.getSpeed());
		SmartDashboard.putNumber("Drive Power Left", leftPower);
		SmartDashboard.putNumber("Drive Speed Right", m_right.getSpeed());
		SmartDashboard.putNumber("Drive Power Right", rightPower);
	}
	
	public double getAngle(){
		return m_navx.getFusedHeading();
	}
	
	public void resetAngle(){
		m_navx.reset();
	}
	
	public double getLeftDistance(){
		return m_left.getPosition() * 8.0 * Math.PI;
	}
	
	public double getRightDistance(){
		return m_right.getPosition() * 8.0 * Math.PI;
	}
	
	public double getLeftSpeed(){
		return m_left.getSpeed() * 8.0 * Math.PI;
	}
	
	public double getRightSpeed(){
		return m_right.getSpeed() * 8.0 * Math.PI;
	}
	
	public double getLinearDistance() {return (getLeftDistance() + getRightDistance()) / 2;}
	public double getTurnDistance() {return (getLeftDistance() - getRightDistance()) / 2;}
	public double getLinearSpeed() {return (getLeftSpeed() + getRightSpeed()) / 2;}
	public double getTurnSpeed() {return (getLeftSpeed() - getRightSpeed()) / 2;}
	
	public void resetEncoders(){
		m_left.setEncPosition(0);
		m_right.setEncPosition(0);
		}
}
