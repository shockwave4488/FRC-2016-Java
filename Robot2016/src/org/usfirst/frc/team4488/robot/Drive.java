package org.usfirst.frc.team4488.robot;

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
			m_left = new SpeedControllerGroup(Talon.class, new int[]{RobotMap.Drive_L1Channel,RobotMap.Drive_L2Channel,RobotMap.Drive_L3Channel});
			m_right = new SpeedControllerGroup(Talon.class, new int[]{RobotMap.Drive_R1Channel,RobotMap.Drive_R2Channel,RobotMap.Drive_R3Channel});
			m_leftEncoder = new Encoder(RobotMap.Drive_LEncoderAChannel, RobotMap.Drive_LEncoderBChannel);
			m_rightEncoder = new Encoder(RobotMap.Drive_REncoderAChannel,RobotMap.Drive_REncoderBChannel);		
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
