package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.RobotMap;

import JavaRoboticsLib.Drive.Interfaces.*;
import JavaRoboticsLib.WPIExtensions.*;
import edu.wpi.first.wpilibj.*;

public class Drive implements TankDrive{
	
	private SpeedControllerGroup m_left;
	private SpeedControllerGroup m_right;
	
	private Encoder m_leftEncoder;
	private Encoder m_rightEncoder;	
	
	public Drive() {
		try {
			m_left = new SpeedControllerGroup(CANTalon.class, new int[]{RobotMap.DriveMotorLeft1,RobotMap.DriveMotorLeft2,RobotMap.DriveMotorLeft3});
			m_right = new SpeedControllerGroup(CANTalon.class, new int[]{RobotMap.DriveMotorRight1,RobotMap.DriveMotorRight2,RobotMap.DriveMotorRight3});
			m_leftEncoder = new Encoder(RobotMap.DriveLeftEncoderA, RobotMap.DriveLeftEncoderB);
			m_rightEncoder = new Encoder(RobotMap.DriveRightEncoderA,RobotMap.DriveRightEncoderB);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setPowers(double leftPower, double rightPower) {
		m_left.set(leftPower);
		m_right.set(rightPower);		
	}

}
