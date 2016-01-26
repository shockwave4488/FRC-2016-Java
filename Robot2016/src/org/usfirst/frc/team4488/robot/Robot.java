
package org.usfirst.frc.team4488.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import JavaRoboticsLib.Drive.*;

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
	
        /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	c = new Controllers();
    	drive = new Drive();
    	driveHelper = new DriveHelper(drive, 1, 1, 1, 1, 1, 1);
    }
    
    
    public void autonomousInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
