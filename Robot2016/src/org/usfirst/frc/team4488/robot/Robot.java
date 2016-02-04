
package org.usfirst.frc.team4488.robot;

import org.usfirst.frc.team4488.robot.autonomous.AutonomousManager;
import org.usfirst.frc.team4488.robot.operator.*;
import org.usfirst.frc.team4488.robot.systems.*;

import JavaRoboticsLib.Drive.*;
import edu.wpi.first.wpilibj.IterativeRobot;

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
	
        /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
    	c = new Controllers();
    	drive = new Drive();
    	//shooter = new Shooter();
    	//manipulator = new Manipulator();
    	//driveHelper = new DriveHelper(drive, 0.2, 0.2, 1, 0, 1, 0.2);
    	//autonManager = new AutonomousManager(drive, shooter, manipulator);
    }
    
    
    @Override
	public void autonomousInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
	public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    @Override
	public void teleopPeriodic() {
    	//driveHelper.Drive(c.getSpeed(), c.getTurn(), true, false);
        drive.setPowers(c.getSpeed(), c.getTurn());
    }
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {
    
    }
    
}
