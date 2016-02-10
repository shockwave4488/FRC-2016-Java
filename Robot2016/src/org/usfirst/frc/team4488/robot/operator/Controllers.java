package org.usfirst.frc.team4488.robot.operator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

public class Controllers {
	private Joystick m_primary;
	private Joystick m_secondary;
	
	public Controllers(){
		m_primary = new Joystick(0);
	}
	
	public double getSpeed(){
		return m_primary.getRawAxis(1);
	}
	
	public double getTurn(){
		return m_primary.getRawAxis(4);
	}
	
	public boolean getChargeButton(){
		return m_primary.getRawButton(5);
	}
	
	public double getShooterRight(){
		return m_primary.getRawAxis(3);
	}
	
	public boolean getLoadButton(){
		return m_primary.getRawButton(1);
	}
	
	//public boolean getSpinButton(){
		//return m_primary.getRawButton(1);
	//}
	
	public boolean getShootButton(){
		return m_primary.getRawButton(3);
	}
	
	public double getIntakeArmManual(){
		return m_primary.getRawAxis(1);
	}
		
		
	public double getArmManual(){
		return m_primary.getRawAxis(1)*-1.0;
		
	}
	/*
	 * This function vibrates the primary controller
	 */
	public void vibratePrimary(double amnt,RumbleType vibrate){
		m_primary.setRumble(RumbleType.kLeftRumble, (float)amnt);
		m_primary.setRumble(RumbleType.kRightRumble, (float)amnt);
	}
	public void vibrateSecondary(double amnt,RumbleType vibrate){
		m_secondary.setRumble(RumbleType.kLeftRumble, (float)amnt);
		m_secondary.setRumble(RumbleType.kRightRumble, (float)amnt);
	}
}
