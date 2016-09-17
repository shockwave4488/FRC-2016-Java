
package org.usfirst.frc.team4488.robot;

import org.usfirst.frc.team4488.robot.autonomous.AutonAction;
import org.usfirst.frc.team4488.robot.autonomous.AutonDecoder;
import org.usfirst.frc.team4488.robot.autonomous.AutonDefense;
import org.usfirst.frc.team4488.robot.autonomous.AutonomousManager;
import org.usfirst.frc.team4488.robot.components.CameraLights;
import org.usfirst.frc.team4488.robot.operator.*;
import org.usfirst.frc.team4488.robot.systems.*;
import org.usfirst.frc.team4488.robot.testing.TestingManager;

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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
	private TestingManager testManager;

	
	private Timer m_endgameTimer;
	private boolean m_endgameState;
		
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
    	smartDrive = new SmartDrive(drive);
    	systems = new SystemsManagement(shooter, manipulator, smartDrive);
    	driveHelper = new DriveHelper(drive, 0.3, 0.15); //Xbox 360
    	//driveHelper = new DriveHelper(drive, 0.125, 0.075); //Xbox One
    	autonManager = new AutonomousManager(smartDrive, shooter, manipulator, systems);
    	testManager = new TestingManager(smartDrive, shooter, manipulator, systems, controllers);
    	shooter.setTurretManual(false);
    	manipulator.setArmManual(false);
    	m_endgameTimer = new Timer();
    	m_endgameState = true;
    	SmartDashboard.putBoolean("EndgameWarning", m_endgameState);
    	//SmartDashboard.putNumber("HSVLow",0);//, [50.0, 44.0, 193.0]);
    	//SmartDashboard.putNumber("HSVHigh",0);//HSVHigh  [90.0, 255.0, 255.0]
    }
    
    private void allPeriodic(){
    	SmartDashboard.putNumber("Target RPM", shooter.getShooterRPM());
    	SmartDashboard.putNumber("Gyro", drive.getAngle());
    	SmartDashboard.putNumber("Pitch", drive.getGyroscope().getPitch());
    	//SmartDashboard.putNumber("Roll", drive.getGyroscope().getRoll());
    	SmartDashboard.putBoolean("At Rate?", shooter.atSpeed());
    	SmartDashboard.putBoolean("Intake Has Ball?", manipulator.HasBall());
    	SmartDashboard.putBoolean("Has Ball?", shooter.hasBall());
    	SmartDashboard.putNumber("TurretPot", shooter.TurretAngle());
    	SmartDashboard.putNumber("ArmPot", manipulator.getArmAngle());
    	//SmartDashboard.putNumber("Drive Distance", drive.getLinearDistance());
    	SmartDashboard.putNumber("Left Encoder", drive.getLeftDistance());
    	SmartDashboard.putNumber("Right Encoder", drive.getRightDistance());
    	//SmartDashboard.putNumber("Turn Distance", drive.getTurnDistance());
    	//SmartDashboard.putNumber("Drive Speed", drive.getLinearSpeed());
    	//SmartDashboard.putNumber("Turn Speed", drive.getTurnSpeed()); 	
    	//SmartDashboard.putNumber("Compass", drive.getCompass());
    	SmartDashboard.putNumber("AlignError", smartDrive.getTurnSetpoint() - drive.getAngle());
    	SmartDashboard.putNumber("Auton Position",autonManager.getPosition());
    	SmartDashboard.putString("Auton Defense", autonManager.getDefense());
    }
    
    private void testValues(){
    	SmartDashboard.putBoolean("Intake Has Ball?", manipulator.HasBall());
    	SmartDashboard.putBoolean("Has Ball?", shooter.hasBall());
    	SmartDashboard.putNumber("TurretPot", shooter.TurretAngle());
    	SmartDashboard.putNumber("ArmPot", manipulator.getArmAngle());
     	SmartDashboard.putNumber("Left Encoder", drive.getLeftDistance());
    	SmartDashboard.putNumber("Right Encoder", drive.getRightDistance());
       	SmartDashboard.putNumber("Drive Speed", drive.getLinearSpeed());
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
    	m_endgameTimer.start();
    }
    
    /**
     * This function is called periodically during operator control
     */
    @Override
	public void teleopPeriodic() {  
    	allPeriodic();
    	if (m_endgameTimer.get() > 105) {
    		m_endgameState = !m_endgameState;
    		SmartDashboard.putBoolean("EndgameWarning", m_endgameState);
    	}
    	
    	systems.setPurgeButton(controllers.getPurgeButton());
    	systems.setBatterChargeButton(controllers.getBatterChargeButton());
    	systems.setChargeButton(controllers.getChargeButton()); 
    	systems.setShootButton(controllers.getShootButton()); //&& (controllers.getBatterChargeButton() || smartDrive.isTurnDone()));
    	systems.setIntakeButton(controllers.getIntakeButton() || controllers.getLowGoalIntakeButton());
    	systems.setLowGoalIntake(controllers.getLowGoalIntakeButton());
    	systems.setDefenseLowButton(controllers.getLowDefenseButton());
    	systems.setSemiManualPosition(controllers.getSemiManualPosition());
    	
    	
    	if (shooter.readyToShoot() && smartDrive.isTurnDone() && controllers.getShootAlignButton() && shooter.getTargetFound()){
    		controllers.vibratePrimary(0.5);
    		controllers.vibrateSecondary(0.5);
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
    	
    	if(controllers.getShootAlignButton() && controllers.getChargeButton() && shooter.getTargetFound()){
    		drive.BreakModeAll();
    		smartDrive.turnToCamera(Math.abs(controllers.getSpeed()) > 0.2 && !controllers.getShootButton() ? controllers.getSpeed() : 0); // This will send the drive powers until the shooter button is pressed
    	}
    	else if(controllers.getBatterBrakeButton()){
    		drive.BreakModeAll();
    	}
    	else{
    		drive.UnBreakModeAll();
    		driveHelper.Drive(controllers.getSpeed(), controllers.getTurn());
    	}
    	
    }
    
    /**
     * This function is called at the beginning of test mode.
     */
    @Override
    public void testInit(){
    	LiveWindow.setEnabled(false);
    	//Create new instance of testing class and let the init thread do the rest.
    	testManager.start();
    }
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {
    	testValues();
    	
    	//IS THIS CALLED EVEN IF DISABLED?
    	//Check for things like brownout
    	
    	//Check for y button -- abort
    	//wait(controllers.getTestAbortButton(), ()->{});
    	
    	if(controllers.getTestAbortButton()){
    		//Disable the robot
    		smartDrive.stop();
    		shooter.StopWheels(); // doesn't stop turret
    		manipulator.stopIntake();//doesn't stop arm moving
    		//SmartDashboard.disable...
    	}
    	
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
