package org.usfirst.frc.team4488.robot.operator;

import JavaRoboticsLib.FlowControl.EdgeTrigger;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

public class Controllers {
	private Joystick m_primary;
	private Joystick m_secondary;
	
	private EdgeTrigger m_turnResetTrigger;
	
	public Controllers(){
		m_primary = new Joystick(0);
		m_secondary = new Joystick(1);
		m_turnResetTrigger = new EdgeTrigger();
	}
	
	public double getSpeed(){
		return m_primary.getRawAxis(1) * -1.0;
	}
	
	public double getTurn(){
		return m_primary.getRawAxis(4);
	}
	
	public boolean getChargeButton(){
		return m_secondary.getRawButton(5);
	}
	
	public boolean getBatterChargeButton(){
		return m_secondary.getRawAxis(2) > 0.75;
	}
	
	public boolean getShootButton(){
		return m_secondary.getRawButton(6) || m_primary.getRawButton(6);
	}
	
	public boolean getIntakeButton(){
		return m_secondary.getRawButton(1);
	}
	
	public boolean getBatterBrakeButton(){
		return m_primary.getRawButton(9);
	}
	
	public boolean getShootAlignButton(){
		return m_primary.getRawButton(5);
	}
	
	public boolean getQuickturn() {
		return m_primary.getRawButton(5);
	}
	
	public boolean getReset(){
		return m_secondary.getRawButton(8);
	}
	
	public boolean getArmReset(){
		return m_secondary.getRawButton(7);
	}
	
	public boolean getPortculisDefenseButton(){
		return m_secondary.getRawButton(2);
	}
	
	public boolean getLowDefenseButton(){
		return m_secondary.getRawButton(4);
	}
	
	public double getSemiManualPosition(){
		return m_secondary.getRawAxis(3);
	}
	
	public boolean getShooterLightButton(){
		return m_primary.getRawButton(1);
	}
	
	public boolean getShooterManualButton(){
		return m_primary.getRawButton(2);
	}
	
	public boolean getLowGoalIntakeButton(){
		return m_secondary.getRawButton(2);
	}
	
	public boolean getPurgeButton(){
		return m_secondary.getRawButton(7);
	}
	
	public boolean getTurnResetButton(){
		return m_turnResetTrigger.getRisingUpdate(m_primary.getRawButton(10));
	}
	
	/**
	 * This function vibrates the primary controller
	 */
	public void vibratePrimary(double amnt){
		m_primary.setRumble(RumbleType.kLeftRumble, (float)amnt);
		m_primary.setRumble(RumbleType.kRightRumble, (float)amnt);
	}
	public void vibrateSecondary(double amnt){
		m_secondary.setRumble(RumbleType.kLeftRumble, (float)amnt);
		m_secondary.setRumble(RumbleType.kRightRumble, (float)amnt);
	}
}
