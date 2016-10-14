package org.usfirst.frc.team4488.robot.operator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

public class Controllers {
	private Joystick m_primary;
	private Joystick m_secondary;
	
	
	public Controllers(){
		m_primary = new Joystick(0);
		m_secondary = new Joystick(1);
	}
	
	//----------------------------------------
	//-----------GENERAL FUNCTIONS------------
	//----------------------------------------
	
	public boolean getA(Joystick controller){
		return controller.getRawButton(1);
	}
	
	public boolean getB(Joystick controller){
		return controller.getRawButton(2);
	}
	
	public boolean getX(Joystick controller){
		return controller.getRawButton(3);
	}

	public boolean getY(Joystick controller){
		return controller.getRawButton(4);
	}
	
	public boolean getLeftBumper(Joystick controller){
		return controller.getRawButton(5);
	}
	
	public boolean getRightBumper(Joystick controller){
		return controller.getRawButton(6);
	}
	
	public boolean getSelect(Joystick controller){
		return controller.getRawButton(7);
	}
	
	public boolean getStart(Joystick controller){
		return controller.getRawButton(8);
	}
	
	public boolean getLeftStickPress(Joystick controller){
		return controller.getRawButton(9);
	}
	
	public boolean getRightStickPress(Joystick controller){
		return controller.getRawButton(10);
	}
	
	public boolean getLeftTrigger(Joystick controller){
		return controller.getRawAxis(2) > 0.75;
	}
	
	public boolean getRightTrigger(Joystick controller){
		return controller.getRawAxis(3) > 0.75;
	}
	
	public double getRightStickX(Joystick controller){
		return controller.getRawAxis(0);
	}
	
	public double getRightStickY(Joystick controller){
		return controller.getRawAxis(1) * -1.0;
	}
	
	public double getLeftStickX(Joystick controller){
		return controller.getRawAxis(4);
	}
	
	public double getLeftStickY(Joystick controller){
		return controller.getRawAxis(5) * -1.0;
	}
	
	public double getDPad(Joystick controller){
		return controller.getPOV(0);
	}
	
	//----------------------------------------
	//--------ROBOT SPECIFIC FUNTIONS---------
	//----------------------------------------
		
	
	public double getSpeed(){
		return getRightStickY(m_primary);
	}
	
	public double getTurn(){
		return getLeftStickY(m_primary);
	}
	
	public boolean getChargeButton(){
		return getLeftBumper(m_secondary);
	}
	
	public boolean getBatterChargeButton(){
		return getLeftTrigger(m_secondary);
	}
	
	public boolean getShootButton(){
		return getRightBumper(m_primary) || getRightBumper(m_secondary);
	}
	
	public boolean getIntakeButton(){
		return getA(m_secondary);
	}
	
	public boolean getBatterBrakeButton(){
		return getRightStickPress(m_primary);
	}
	
	public boolean getShootAlignButton(){
		return getLeftBumper(m_primary);
	}
	
	public boolean getReset(){
		return getStart(m_secondary);
	}
	
	public boolean getArmReset(){
		return getSelect(m_secondary);
	}
	
	public boolean getLowDefenseButton(){
		return getY(m_secondary);
	}
	
	public double getSemiManualPosition(){
		//Uses raw value of Right Trigger, 
		//rather than reading it like a button.
		return m_secondary.getRawAxis(3);
	}
	
	public boolean getLowGoalIntakeButton(){
		return getB(m_secondary);
	}
	
	public boolean getPurgeButton(){
		return getSelect(m_secondary);
	}
	
	public boolean getTestSuccess_NextButton(){
		return getA(m_primary);
	}
	public boolean getTestFail_ReplayButton(){
		return getX(m_primary);
	}
	public boolean getTestAbortButton(){
		return getY(m_primary);
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
