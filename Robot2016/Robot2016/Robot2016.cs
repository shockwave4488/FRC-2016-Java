﻿using System;
using System.Collections.Generic;
using System.Linq;
using WPILib;
using WPILib.Extras;
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

        /*
        private DriveHelper driveHelper;
        private FieldCentricDrive centricDrive;
        private Shooter shooter;
        private Intake intake;
        private Manipulator m_manipulator;
        */

        private XboxController j;

        /// <summary>
        /// This function is run when the robot is first started up and should be
        /// used for any initialization code.
        /// </summary>
        public override void RobotInit()
        {
            //intake = new Intake();
            drive = new Drive();
            //driveHelper = new DriveHelper(drive,1,1,1,1,1,1);
           // centricDrive = new FieldCentricDrive(drive);
            j = new XboxController(0);
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
           // driveHelper.Drive(1,1,true,false);
            //shooter.Spin();
            //shooter.Shoot();
            //intake.SetPosition(false, false);
            //m_manipulator.Update();
            drive.SetPowers(-j.GetLeftYAxis(), -j.GetLeftYAxis());
        }

        /// <summary>
        /// This function is called periodically during test mode
        /// </summary>
        public override void TestPeriodic()
        {

        }
    }
}
