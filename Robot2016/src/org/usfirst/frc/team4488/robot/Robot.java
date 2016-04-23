
package org.usfirst.frc.team4488.robot;

import org.usfirst.frc.team4488.robot.autonomous.AutonAction;
import org.usfirst.frc.team4488.robot.autonomous.AutonDecoder;
import org.usfirst.frc.team4488.robot.autonomous.AutonDefense;
import org.usfirst.frc.team4488.robot.autonomous.AutonomousManager;
import org.usfirst.frc.team4488.robot.components.CameraLights;
import org.usfirst.frc.team4488.robot.operator.*;
import org.usfirst.frc.team4488.robot.systems.*;
import com.kauailabs.navx.frc.AHRS;
import JavaRoboticsLib.Drive.*;
import JavaRoboticsLib.Utility.Logger;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	private Controllers controllers;
	private Drive drive;
	private DriveHelper driveHelper;
	private SmartDrive smartDrive;
	
	private AutonomousManager autonManager;
	private Shooter shooter;
	private Manipulator manipulator;
	private SystemsManagement systems;
		
	private SendableChooser m_position, m_defense, m_action;
     /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
    	Logger.setPrintToConsole(true);
    	Logger.setSmartDashboardName("Logger");
    	controllers = new Controllers();
    	drive = new Drive();
    	shooter = new Shooter();
    	manipulator = new Manipulator();
    	systems = new SystemsManagement(shooter, manipulator);
    	driveHelper = new DriveHelper(drive, 0.2, 0.2, 0.6, 0.6, 0.75, 0.2); //Xbox 360
    	//driveHelper = new DriveHelper(drive, 0.125, 0.075, 0.6, 0.6, 0.75, 0.2); //Xbox One
    	smartDrive = new SmartDrive(drive);
    	autonManager = new AutonomousManager(smartDrive, shooter, manipulator, systems);
    	shooter.setTurretManual(false);
    	manipulator.setArmManual(false);

    }
    
    private void allPeriodic(){
    	SmartDashboard.putNumber("Target RPM", shooter.getShooterRPM());
    	SmartDashboard.putNumber("Gyro", drive.getAngle());
    	SmartDashboard.putNumber("Pitch", drive.getGyroscope().getRoll());
    	SmartDashboard.putNumber("Roll", drive.getGyroscope().getPitch());
    	SmartDashboard.putBoolean("At Rate?", shooter.AtRate());
    	SmartDashboard.putBoolean("Intake Has Ball?", manipulator.HasBall());
    	SmartDashboard.putBoolean("Has Ball?", shooter.hasBall());
    	SmartDashboard.putNumber("TurretPot", shooter.TurretAngle());
    	SmartDashboard.putNumber("ArmPot", manipulator.getArmAngle());
    	SmartDashboard.putNumber("Drive Distance", drive.getLinearDistance());
    	SmartDashboard.putNumber("Left Encoder", drive.getLeftDistance());
    	SmartDashboard.putNumber("Right Encoder", drive.getRightDistance());
    	SmartDashboard.putNumber("Turn Distance", drive.getTurnDistance());
    	SmartDashboard.putNumber("Drive Speed", drive.getLinearSpeed());
    	SmartDashboard.putNumber("Turn Speed", drive.getTurnSpeed()); 	
    	SmartDashboard.putNumber("Compass", drive.getCompass());
    	SmartDashboard.putNumber("AlignError", /*SmartDashboard.getNumber("AzimuthX", 0)*/smartDrive.getTurnSetpoint() - drive.getAngle());
    	SmartDashboard.putNumber("Auton Position",autonManager.getPosition());
    	SmartDashboard.putString("Auton Defense", autonManager.getDefense());
    }
    
    @Override
    /**
     * This program is run when the autonomous period of the game begins.
     */
	public void autonomousInit() {
    	Logger.resetTimer();
    	drive.BreakModeAll();
    	autonManager.start();    	
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
	public void autonomousPeriodic() {
    	allPeriodic();
    	autonManager.check();
    }

    @Override
    /**
     * This function is called when Tele-op first begins.
     */
    public void teleopInit(){
    	Logger.addMessage("Starting Teleop");
    	autonManager.kill();
    	shooter.setTurretManual(false);
    	manipulator.setArmManual(false);
        drive.UnBreakModeAll();
    }
    
    /**
     * This function is called periodically during operator control
     */
    @Override
	public void teleopPeriodic() {  	
    	allPeriodic();
    	
    	systems.setPurgeButton(controllers.getPurgeButton());
    	systems.setBatterChargeButton(controllers.getBatterChargeButton());
    	systems.setChargeButton(controllers.getChargeButton()); 
    	systems.setShootButton(controllers.getShootButton()); //&& (controllers.getBatterChargeButton() || smartDrive.atCamera(1)));
    	systems.setIntakeButton(controllers.getIntakeButton() || controllers.getLowGoalIntakeButton());
    	systems.setLowGoalIntake(controllers.getLowGoalIntakeButton());
    	systems.setDefenseLowButton(controllers.getLowDefenseButton());
    	systems.setSemiManualPosition(controllers.getSemiManualPosition());
  	
    	if (shooter.readyToShoot() && smartDrive.atCamera(0.75)){
    		controllers.vibratePrimary(0.7);
    		controllers.vibrateSecondary(0.7);
    	}
    	else{
    		controllers.vibratePrimary(0);
    		controllers.vibrateSecondary(0);
    	}
    	
    	if(controllers.getArmReset())
    		manipulator.resetArm();
    	
    	if(controllers.getReset())
    		systems.Reset();
    	
    	systems.Update();
    	
    	if(controllers.getShootAlignButton()){
    		drive.BreakModeAll();
    		smartDrive.turnToCamera(Math.abs(controllers.getSpeed()) > 0.2 ? controllers.getSpeed() : 0);
    	}
    	else if(controllers.getBatterBrakeButton()){
    		drive.BreakModeAll();
    		//smartDrive.batterBrake(controllers.getSpeed());
    	}
    	else{
    		drive.UnBreakModeAll();
    		driveHelper.Drive(controllers.getSpeed(), controllers.getTurn(), controllers.getQuickturn(), true);
    	}    	
    	
    	if(controllers.getTurnResetButton())
    		smartDrive.resetTurnIntegral();
    }
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {
    	allPeriodic();
    	if(controllers.getShooterLightButton())
    		shooter.startShootTest();
    	else
    		shooter.stopShootTest();
    }
    
    @Override
    public void disabledPeriodic(){
    	allPeriodic();

		SmartDashboard.putBoolean("LightsOn", false);
    	drive.BreakModeAll();
    }
    @Override
    public void disabledInit(){
     systems.disabledInit();
    }
}
