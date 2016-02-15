
package org.usfirst.frc.team4488.robot;

import org.usfirst.frc.team4488.robot.autonomous.AutonAction;
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
	
	private Controllers c;
	private Drive drive;
	private DriveHelper driveHelper;
	private SmartDrive smartDrive;
	
	private AutonomousManager autonManager;
	private Shooter shooter;
	private Manipulator manipulator;
	private SystemsManagement systems;
	private CameraLights camlights;
	
	private SendableChooser m_position, m_defense, m_action;
        /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
    	c = new Controllers();
    	drive = new Drive();
    	shooter = new Shooter();
    	manipulator = new Manipulator();
    	systems = new SystemsManagement(shooter, manipulator);
    	driveHelper = new DriveHelper(drive, 0.05, 0.05, 0.6, 0.6, 0.75, 0.2);
    	smartDrive = new SmartDrive(drive);
    	camlights = new CameraLights();
    	//autonManager = new AutonomousManager(drive, shooter, manipulator);
    	Logger.setPrintToConsole(true);

    	SmartDashboard.putNumber("DriveP", 0.15);
    	SmartDashboard.putNumber("Cam Light Brightness", .5);
    	SmartDashboard.putNumber("Distance", 4);
    	SmartDashboard.putNumber("Shooting Scalar", 3);
    	SmartDashboard.putNumber("Angle Setpoint", 60);
    }
    
    private void allPeriodic(){
    	SmartDashboard.putNumber("Target RPM", shooter.getShooterRPM());
    	SmartDashboard.putNumber("Gyro", drive.getAngle());
    	SmartDashboard.putBoolean("At Rate?", shooter.AtRate());
    	SmartDashboard.putBoolean("Intake Has Ball?", manipulator.HasBall());
    	SmartDashboard.putBoolean("Has Ball?", shooter.hasBall());
    	SmartDashboard.putBoolean("Ball Shot?", shooter.ShotBall());
    	SmartDashboard.putNumber("TurretPot", shooter.TurretAngle());
    	SmartDashboard.putNumber("ArmPot", manipulator.getArmAngle());
    	SmartDashboard.putNumber("Drive Distance", drive.getLinearDistance());
    	SmartDashboard.putNumber("Turn Distance", drive.getTurnDistance());
    	SmartDashboard.putNumber("Drive Speed", drive.getLinearSpeed());
    	SmartDashboard.putNumber("Turn Speed", drive.getTurnSpeed());
    	SmartDashboard.putNumber("Intake Position", manipulator.getIntakePosition());
    }
    
    @Override
    /**
     * This program is run when the autonomous period of the game begins.
     */
	public void autonomousInit() {
    	Logger.resetTimer();
    	autonManager.run();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
	public void autonomousPeriodic() {
    	allPeriodic();

    }

    @Override
    /**
     * This function is called when Tele-op first begins.
     */
    public void teleopInit(){
    	Logger.addMessage("Starting Teleop");
    	shooter.setTurretManual(false);
    	manipulator.setArmManual(true);
    }
    
    /**
     * This function is called periodically during operator control
     */
    @Override
	public void teleopPeriodic() {  	
    	allPeriodic();
    	
    	shooter.setDistance(SmartDashboard.getNumber("Distance", 0));

    	systems.setChargeButton(c.getChargeButton());    	
    	systems.setLoadButton(c.getLoadButton());
    	systems.setShootButton(c.getShootButton());
    	systems.setIntakeButton(c.getIntakeButton());

    	camlights.setLights(Relay.Value.kForward, SmartDashboard.getNumber("Cam Light Brightness", .5));
    	manipulator.setArmManualPower(c.getArmManual());
    	shooter.setTurretManualPower(c.getTurretManual());

    	systems.Update();
    	if(c.getShootAlignButton())
    		smartDrive.turnToCamera();
    	else
    		driveHelper.Drive(c.getSpeed(), c.getTurn(), false, true);
    	//System.out.println(Utility.getFPGATime());
    }
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {
    	allPeriodic();
    }
    
    @Override
    public void disabledPeriodic(){
    	allPeriodic();
    }
}
