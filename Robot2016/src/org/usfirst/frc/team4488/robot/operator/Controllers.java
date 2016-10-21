package org.usfirst.frc.team4488.robot.operator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

public class Controllers {
	private Joystick m_primary;
	private Joystick m_secondary;
	
	
	/*
	 * This is the Controllers class, which grabs raw booleans and number values from an Xbox controller and turns them into more obviously named functions,
	 * which then are assigned to control different functions of the robot. 
	 */
	
	public Controllers(){
		m_primary = new Joystick(0);
		m_secondary = new Joystick(1);
	}
	
	//----------------------------------------
	//-----------GENERAL FUNCTIONS------------
	//----------------------------------------
	
	//This function gets the A button boolean from the controller. 
	public boolean getA(Joystick controller){
		return controller.getRawButton(1);
	}
	
	//This function gets the B button boolean from the controller. 
	public boolean getB(Joystick controller){
		return controller.getRawButton(2);
	}
	
	//This function gets the X button boolean from the controller. 
	public boolean getX(Joystick controller){
		return controller.getRawButton(3);
	}

	//This function gets the Y button boolean from the controller. 
	public boolean getY(Joystick controller){
		return controller.getRawButton(4);
	}
	
	//This function gets the Left Bumper boolean from the controller. 
	public boolean getLeftBumper(Joystick controller){
		return controller.getRawButton(5);
	}

	//This function gets the Right Bumper boolean from the controller. 
	public boolean getRightBumper(Joystick controller){
		return controller.getRawButton(6);
	}
	
	//This function gets the Select button boolean from the controller. 
	public boolean getSelect(Joystick controller){
		return controller.getRawButton(7);
	}
	
	//This function gets the Start button boolean from the controller. 
	public boolean getStart(Joystick controller){
		return controller.getRawButton(8);
	}
	
	//This function gets the Left Stick button boolean from the controller. 
	public boolean getLeftStickPress(Joystick controller){
		return controller.getRawButton(9);
	}
	
	//This function gets the Right Stick button boolean from the controller. 
	public boolean getRightStickPress(Joystick controller){
		return controller.getRawButton(10);
	}
	
	//Creates a numerical threshold for the Right Trigger to be considered pressed, and returns a boolean based on the raw numerical value compared to the threshold.
	public boolean getLeftTrigger(Joystick controller){
		return controller.getRawAxis(2) > 0.75;
	}
	
	//Creates a numerical threshold for the Left Trigger to be considered pressed, and returns a boolean based on the raw numerical value compared to the threshold.
	public boolean getRightTrigger(Joystick controller){
		return controller.getRawAxis(3) > 0.75;
	}
	
	//Returns the raw numerical value of the X-axis of the Right Stick, with left returning negative values, and right returning positive values.
	public double getRightStickX(Joystick controller){
		return controller.getRawAxis(0);
	}
	
	//Returns the raw numerical value of the Y-axis of the Right Stick, with down returning negative values, and up returning positive values.
	public double getRightStickY(Joystick controller){
		return controller.getRawAxis(1) * -1.0;
	}
	
	//Returns the raw numerical value of the X-axis of the Left Stick, with left returning negative values, and right returning positive values.
	public double getLeftStickX(Joystick controller){
		return controller.getRawAxis(4);
	}
	
	//Returns the raw numerical value of the Y-axis of the Left Stick, with down returning negative values, and up returning positive values.
	public double getLeftStickY(Joystick controller){
		return controller.getRawAxis(5) * -1.0;
	}
	
	//Returns the raw numerical value of the Directional Pad. Will return -1 if not pressed. 
	//Will return a value between 0 and 360 based on the location of where the D Pad is being pressed.
	public double getDPad(Joystick controller){
		return controller.getPOV(0);
	}
	
	//----------------------------------------
	//--------ROBOT SPECIFIC FUNTIONS---------
	//----------------------------------------
	
	//Gets a numerical value from the Y-axis of the Left Stick on the primary controller that is used to set the ground speed of the drive train. 
	public double getSpeed(){
		return getLeftStickY(m_primary);
	}
	
	//Gets a numerical value from the X-axis of the Right Stick on the primary controller that is used to set the turning speed of the drive train. 
	public double getTurn(){
		return getRightStickX(m_primary);
	}
	
	//Gets a boolean from the Left Bumper of the secondary controller that activates the Charging state, which drives the turret upwards and spins the shooter wheels in preparation to shoot a normal shot.
	public boolean getChargeButton(){
		return getLeftBumper(m_secondary);
	}
	
	//Gets a boolean from the Left Trigger of the secondary controller that activates the BatterCharging state, which drives the turret upwards to a position to shoot a batter shot.
	public boolean getBatterChargeButton(){
		return getLeftTrigger(m_secondary);
	}
	
	//Gets a boolean from the Right Bumper of either the primary or secondary controller that is used to shoot the ball from the Charging state. 
	public boolean getShootButton(){
		return getRightBumper(m_primary) || getRightBumper(m_secondary);
	}
	
	//Gets a boolean from the A button of the secondary controller that moves the arm and spins the intake in order to intake a ball into the shooter.
	public boolean getIntakeButton(){
		return getA(m_secondary);
	}
	
	//Gets a boolean from the Right Stick of the primary controller that is used to put all motors in brake mode when the button is pressed.
	public boolean getBatterBrakeButton(){
		return getRightStickPress(m_primary);
	}
	
	//Gets a boolean from the Left Bumper on the primary controller that is used to align the robot with the target using values that are gotten from the camera. 
	public boolean getShootAlignButton(){
		return getLeftBumper(m_primary);
	}
	
	//Gets a boolean from the Start button on the secondary controller that is used to reset that state machine of the entire system on the robot.
	public boolean getReset(){
		return getStart(m_secondary);
	}
	
	//Gets a boolean from the Select button on the secondary controller that is used to reset the endcoder of the arm back to zero.
	public boolean getArmReset(){
		return getSelect(m_secondary);
	}
	
	//Gets a boolean from the Y button on the secondary controller that is used to move the arm down in order to get across defenses that require a low profile
	//or lifting of some sort.
	public boolean getLowDefenseButton(){
		return getY(m_secondary);
	}
	
	//Gets a numerical value from the Right Trigger on the secondary controller that is used to move the arm to a position based on the value from the controller.
	public double getSemiManualPosition(){
		//Uses raw value of Right Trigger, 
		//rather than reading it like a button.
		return m_secondary.getRawAxis(3);
	}
	
	//Gets a boolean from the B button on the secondary controller that is used to move the arm down and spin the intake wheel 
	//in order to intake a ball and keep it in the arm.  
	public boolean getLowGoalIntakeButton(){
		return getB(m_secondary);
	}
	
	//Gets a boolean from the Select button on the secondary controller that is used to purge a ball in an unusable position from the shooter.
	public boolean getPurgeButton(){
		return getSelect(m_secondary);
	}
	
	//Gets a boolean from the A button on the primary controller that is used to signal a successful test in testing mode.
	public boolean getTestSuccess_NextButton(){
		return getA(m_primary);
	}
	
	//Gets a boolean from the X button on the primary controller that is used to signal an unsuccessful test in testing mode.
	public boolean getTestFail_ReplayButton(){
		return getX(m_primary);
	}
	
	//Gets a boolean from the Y button on the primary controller that is used to abort a test in testing mode.
	public boolean getTestAbortButton(){
		return getY(m_primary);
	}

	/**
	 * This function vibrates the primary controller.
	 */
	public void vibratePrimary(double amnt){
		m_primary.setRumble(RumbleType.kLeftRumble, (float)amnt);
		m_primary.setRumble(RumbleType.kRightRumble, (float)amnt);
	}
	/**
	 * This function vibrates the secondary controller.
	 */
	public void vibrateSecondary(double amnt){
		m_secondary.setRumble(RumbleType.kLeftRumble, (float)amnt);
		m_secondary.setRumble(RumbleType.kRightRumble, (float)amnt);
	}
}
