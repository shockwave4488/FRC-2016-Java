using System;
using System.Collections.Generic;
using System.Linq;
using WPILib;
using WPILib.SmartDashboard;
using CSharpRoboticsLib.Drive;
using _2015_Pre_build_week_project.Team_Code.Drive_Code;

namespace Robot2016
{
    /// <summary>
    /// The VM is configured to automatically run this class, and to call the
    /// functions corresponding to each mode, as described in the IterativeRobot
    /// documentation. 
    /// </summary>
    public class Robot2016 : IterativeRobot
    {
        private Drive drive;
        private DriveHelper driveHelper;
        private Intake intake;
        /// <summary>
        /// This function is run when the robot is first started up and should be
        /// used for any initialization code.
        /// </summary>
        public override void RobotInit()
        {
            intake = new Intake();
            drive = new Drive();
            driveHelper = new DriveHelper(drive,1,1,1,1,1,1);
        }

        
        /// <summary>
        /// This function is run when the robot starts autonomous
        /// </summary>
        public override void AutonomousInit()
        {
        }

        /// <summary>
        /// This function is called periodically during autonomous
        /// </summary>
        public override void AutonomousPeriodic()
        {
        }

        /// <summary>
        /// This function is called periodically during operator control
        /// </summary>
        public override void TeleopPeriodic()
        {
            intake.SetPosition(false, false);
            driveHelper.Drive(,,true,);
        }

        /// <summary>
        /// This function is called periodically during test mode
        /// </summary>
        public override void TestPeriodic()
        {

        }
    }
}
