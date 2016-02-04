package org.usfirst.frc.team4488.robot.operator;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {
	private Joystick m_primary;
	private Joystick m_secondary;
	
	public Controllers(){
		m_primary = new Joystick(0);
	}
	
	public double getSpeed(){
		return m_primary.getRawAxis(2);
	}
	
	public double getTurn(){
		return m_primary.getRawAxis(3);
	}
	
	public boolean getSpinButton(){
		return m_primary.getRawButton(1);
	}
	
	public boolean getShootButton(){
		return m_primary.getRawButton(2);
	}
	
	public double getIntakeArmManual(){
		return m_secondary.getRawAxis(1);
	}
}
