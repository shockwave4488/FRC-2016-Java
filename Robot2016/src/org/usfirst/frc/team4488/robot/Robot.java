
package org.usfirst.frc.team4488.robot;

import org.usfirst.frc.team4488.robot.autonomous.AutonomousManager;
import org.usfirst.frc.team4488.robot.operator.*;
import org.usfirst.frc.team4488.robot.systems.*;
import org.usfirst.frc.team4488.robot.testing.improvisedSimulator;

import JavaRoboticsLib.Drive.*;
import JavaRoboticsLib.Utility.Logger;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
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
	private DigitalInput shooterSensor;
	private Command autonCommand1;
	private SendableChooser autonChooser;
	public improvisedSimulator simulator = new improvisedSimulator();
        /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
    	c = new Controllers();
    	autonChooser = new SendableChooser();
    	SmartDashboard.putBoolean("Hullo",true);
    	//drive = new Drive();
    	shooter = new Shooter();
    	simulator.rdbtnTest.setSelected(false);
    	//systems = new SystemsManagement(shooter);
    	//manipulator = new Manipulator();
    	//driveHelper = new DriveHelper(drive, 0.2, 0.2, 1, 0, 1, 0.2);
    	//autonManager = new AutonomousManager(drive, shooter, manipulator);
    }
    
    
    @Override
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
    public void teleopInit(){
    	Logger.addMessage("Starting Teleop");
    }
    
    /**
     * This function is called periodically during operator control
     */
    @Override
	public void teleopPeriodic() {
    	//SmartDashboard.putBoolean("Shooter Sensor", shooter.HasNoBall());
    	//SmartDashboard.putBoolean("Load Button", c.getLoadButton());
    	SmartDashboard.putNumber("Current Rate", shooter.Rate());
    	SmartDashboard.putBoolean("At Rate?", shooter.AtRate());
    	SmartDashboard.putBoolean("Hullo",true);
    	shooter.setShooterRPM(c.getShooterRight()*6000);
    	shooter.Spin();
    	//driveHelper.Drive(c.getSpeed(), c.getTurn(), true, false);
        //drive.setPowers(c.getSpeed(), c.getTurn());
        //shooter.MoveShooterWheels(c.getShooterLeft(), c.getShooterRight());
        //shooter.Test(c.getLoadButton(), c.getShootButton(), c.getShooterLeft(),c.getShooterLeft());
    }
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {
    	SmartDashboard.putBoolean("Hullo",true);
    }
    
}
