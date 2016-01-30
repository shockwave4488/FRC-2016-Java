package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.RobotMap;

import JavaRoboticsLib.Drive.Interfaces.*;
import JavaRoboticsLib.WPIExtensions.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Drive implements TankDrive{
	
	private CANTalon m_left;
	private SpeedControllerGroup m_leftFollowers;
	private CANTalon m_right;
	private SpeedControllerGroup m_rightFollowers;
	
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
