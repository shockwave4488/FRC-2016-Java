
package org.usfirst.frc.team4488.robot;

import org.usfirst.frc.team4488.robot.autonomous.AutonAction;
import org.usfirst.frc.team4488.robot.autonomous.AutonDefense;
import org.usfirst.frc.team4488.robot.autonomous.AutonomousManager;
import org.usfirst.frc.team4488.robot.operator.*;
import org.usfirst.frc.team4488.robot.systems.*;
import org.usfirst.frc.team4488.robot.testing.improvisedSimulator;
import JavaRoboticsLib.Drive.*;
import JavaRoboticsLib.Utility.Logger;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.Command;

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
	private Talon Arm;
	private Talon Intake;
	private SendableChooser m_position, m_defense, m_action;

	private AHRS m_navx;
	private AnalogPotentiometer potentiometer; 
        /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
    	c = new Controllers();
    	drive = new Drive();
    	shooter = new Shooter();
    	systems = new SystemsManagement(shooter);
    	m_navx = new AHRS(SPI.Port.kMXP);
    	potentiometer = new AnalogPotentiometer(RobotMap.TurretPontentiometer);
    	//manipulator = new Manipulator();
    	driveHelper = new DriveHelper(drive, 0.2, 0.2, 1, 0, 1, 0.2);
    	Arm = new Talon(RobotMap.ArmMotor);
    	Intake = new Talon(RobotMap.IntakeMotor);
    	//autonManager = new AutonomousManager(drive, shooter, manipulator);
    	Logger.setPrintToConsole(true);
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
    	//SmartDashboard.putNumber("Gyro", m_navx.getFusedHeading());
    	SmartDashboard.putNumber("Potentiometer", potentiometer.pidGet());
    	//SmartDashboard.putBoolean("At Rate?", shooter.AtRate());
    	//shooter.setShooterRPM(c.getShooterRight() * 6250);
    	//systems.setChargeButton(c.getChargeButton());
    	//systems.setLoadButton(c.getLoadButton());
    	//systems.setShootButton(c.getShootButton());
    	//systems.Update();
    	//driveHelper.Drive(c.getSpeed(), c.getTurn(), true, false);
    	Arm.set(c.getArmManual());
    	Intake.set(c.getIntakeArmManual());
    }
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {



    	SmartDashboard.putBoolean("Hullo",true);

    }
    
}
