using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WPILib;
using CSharpRoboticsLib.WPIExtensions;

namespace Robot2016
{
/// <summary>
/// Ball shooter class.
/// </summary>
    class Shooter
    {

        private ShooterWheel m_shooterWELeft;
        private ShooterWheel m_shooterWERight;
        private DigitalInput m_ballSensor;
        private Talon m_indexerWheelLeft;
        private Talon m_indexerWheelRight;

        /// <summary>
        /// Initializes Shooter member variables.
        /// </summary>
        public Shooter()
        {
            m_shooterWELeft = new ShooterWheel(1, 3, 4);
            m_shooterWERight = new ShooterWheel(2, 5, 6);
            m_ballSensor = new DigitalInput(3);
            m_indexerWheelLeft = new Talon(6);
            m_indexerWheelRight = new Talon(7);
        }

        /// <summary>
        /// Spins the shooter wheel if the spin button is pressed.
        /// </summary>
        public void Spin(bool SpinButton)
        {
            if(SpinButton)
            {
                m_shooterWELeft.Spin();
                m_shooterWERight.Spin();

            }
            else {
                m_shooterWELeft.SetZero();
                m_shooterWERight.SetZero();
            }
        }

        /// <summary>
        /// Spins indexer wheels if the shooter is holding a ball, the shooter button is pressed,
        /// and shooter wheel speed is more than 95% RPM.
        /// </summary>
        public void Shoot(bool ShootButton)
        {
            if (m_ballSensor.Get() && ShootButton && m_shooterWELeft.atRate()&&m_shooterWERight.atRate())
            {
                m_indexerWheelLeft.Set(1);
                m_indexerWheelRight.Set(1);

            }
            else
            {
                m_indexerWheelLeft.Set(0);
                m_indexerWheelRight.Set(0);
            }
        }

    }
}
