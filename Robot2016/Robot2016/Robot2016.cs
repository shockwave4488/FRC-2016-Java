using System;
using System.Collections.Generic;
using System.Linq;
using WPILib;
using WPILib.Extras;
using CSharpRoboticsLib.Drive;
using Robot2016.Operator;
using Robot2016.Systems;

namespace Robot2016
{
    /// <summary>
    /// The VM is configured to automatically run this class, and to call the
    /// functions corresponding to each mode, as described in the IterativeRobot
    /// documentation. 
    /// </summary>
    public class Robot2016 : IterativeRobot
    {
        private Controllers c;
        private Drive drive;

        private DriveHelper driveHelper;
        private FieldCentricDrive centricDrive;
        private Shooter shooter;
        private Intake intake;
        private Manipulator m_manipulator;

        private XboxController j;

        /// <summary>
        /// This function is run when the robot is first started up and should be
        /// used for any initialization code.
        /// </summary>
        public override void RobotInit()
        {
            //intake = new Intake();
            drive = new Drive();
            driveHelper = new DriveHelper(drive,1,1,1,1,1,1);
            centricDrive = new FieldCentricDrive(drive);
            shooter = new Shooter();
            drive.SetPowers(c.GetSpeed, c.GetSpeed);
            c = new Controllers();
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
            driveHelper.Drive(c.GetSpeed,c.GetTurn,true,false);
            shooter.Spin(c.GetSpinButton);
            shooter.Shoot(c.GetShootButton);
            intake.SetPosition(false, false);
            //m_manipulator.Update();
        }

        /// <summary>
        /// This function is called periodically during test mode
        /// </summary>
        public override void TestPeriodic()
        {

        }
    }
}
