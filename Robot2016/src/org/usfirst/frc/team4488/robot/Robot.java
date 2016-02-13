
package org.usfirst.frc.team4488.robot;

import org.usfirst.frc.team4488.robot.autonomous.AutonAction;
import org.usfirst.frc.team4488.robot.autonomous.AutonDefense;
import org.usfirst.frc.team4488.robot.autonomous.AutonomousManager;
import org.usfirst.frc.team4488.robot.operator.*;
import org.usfirst.frc.team4488.robot.systems.*;
import com.kauailabs.navx.frc.AHRS;
import JavaRoboticsLib.Drive.*;
import JavaRoboticsLib.Utility.Logger;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
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
	private AutonomousManager autonManager;
	private Shooter shooter;
	private Manipulator manipulator;
	private SystemsManagement systems;
	
	private SendableChooser m_position, m_defense, m_action;

	private AHRS m_navx; 
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
    	m_navx = new AHRS(SPI.Port.kMXP);
    	driveHelper = new DriveHelper(drive, 0.05, 0.05, 0.6, 0, 0.75, 0.2);
    	//autonManager = new AutonomousManager(drive, shooter, manipulator);
    	Logger.setPrintToConsole(true);
    	
    	SmartDashboard.putNumber("P", 0.005);
    	SmartDashboard.putNumber("I", 0);
    	SmartDashboard.putNumber("D", 0);
    }
    
    private void allPeriodic(){
    	SmartDashboard.putNumber("Gyro", m_navx.getFusedHeading());
    	SmartDashboard.putBoolean("At Rate?", shooter.AtRate());
    	SmartDashboard.putBoolean("Has Ball?", shooter.hasBall());
    	SmartDashboard.putBoolean("Ball Shot?", shooter.ShotBall());
    	SmartDashboard.putNumber("TurretPot", shooter.TurretAngle());
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
    }
    
    /**
     * This function is called periodically during operator control
     */
    @Override
	public void teleopPeriodic() {  	
    	allPeriodic();
    	
    	shooter.setShooterRPM(SmartDashboard.getNumber("Target Rate", 0));
    	systems.setChargeButton(c.getChargeButton());
    	
    	systems.setLoadButton(c.getLoadButton());
    	systems.setShootButton(c.getShootButton());
    	manipulator.setArmManualPower(c.getArmManual());
    	manipulator.setIntakeManualPower(c.getIntakeManual());
    	systems.Update();
    	
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
    
}
