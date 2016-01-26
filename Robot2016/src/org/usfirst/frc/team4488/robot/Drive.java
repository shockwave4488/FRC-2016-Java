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
			m_left = new SpeedControllerGroup(Talon.class, new int[]{});
			m_right = new SpeedControllerGroup(Talon.class, new int[]{});
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
