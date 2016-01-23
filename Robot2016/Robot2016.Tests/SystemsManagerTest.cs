using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NUnit.Framework;

namespace Robot2016.Tests
{
    [TestFixture]
    public class SystemsManagerTest
    {
        private SystemsManagement m_manager = new SystemsManagement();


        [Test]
        public void TransitionTests()
        {   m_manager.IntakeButton = true;
            m_manager.ShooterHasBall = false;
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ArmState.Intake, m_manager.armState);
            m_manager.IntakeButton = false;
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ArmState.Idle, m_manager.armState);
            m_manager.IntakeButton = true;
            m_manager.IntakeHasBall = true;
            m_manager.Update();
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ArmState.Store, m_manager.armState);
            m_manager.LoadButton = true;
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ArmState.Load, m_manager.armState);
            Assert.AreEqual(SystemsManagement.ShooterState.Load, m_manager.shooterState);
            m_manager.IntakeHasBall = false;
            m_manager.ShooterHasBall = true;
            m_manager.ChargeButton = true;
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ArmState.Idle, m_manager.armState);
            Assert.AreEqual(SystemsManagement.ShooterState.Charge, m_manager.shooterState);
            m_manager.ChargeButton = false;
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ShooterState.Load, m_manager.shooterState);
            m_manager.ChargeButton = true;
            m_manager.ShootButton = true;
            m_manager.Update();
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ArmState.Shoot, m_manager.armState);
            Assert.AreEqual(SystemsManagement.ShooterState.Shoot, m_manager.shooterState);
            m_manager.ShooterShotBall = true;
            m_manager.ShooterHasBall = false;
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ArmState.Idle, m_manager.armState);
            Assert.AreEqual(SystemsManagement.ShooterState.Idle, m_manager.shooterState);
            m_manager.DefenseHighButton = true;
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ArmState.DefenseHigh, m_manager.armState);
            m_manager.DefenseHighButton = false;
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ArmState.Idle, m_manager.armState);
            m_manager.DefenseLowButton = true;
            m_manager.Update();
            Assert.AreEqual(SystemsManagement.ArmState.DefenseLow, m_manager.armState);






        }
    }
}
