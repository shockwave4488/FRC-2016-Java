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

        private Talon m_shooterWheel1;
        private Talon m_shooterWheel2;
        private Encoder m_shooterEncoder2;
        private Encoder m_shooterEncoder1;
        private DigitalInput m_ballSensor;
        private Talon m_indexerWheel1;
        private Talon m_indexerWheel2;

        /// <summary>
        /// Target Rotations Per Minute for shooting.
        /// </summary>
        public double Rpm {get; set; }

        /// <summary>
        /// Initializes Shooter member variables.
        /// </summary>
        public Shooter()
        {
            m_shooterWheel1 = new Talon(4);
            m_shooterWheel2 = new Talon(5);
            m_shooterEncoder1 = new WPILib.Encoder(5, 6);
            m_shooterEncoder2 = new WPILib.Encoder(7, 8);
            m_ballSensor = new DigitalInput(3);
            m_indexerWheel1 = new Talon(6);
            m_indexerWheel2 = new Talon(7);
        }

        /// <summary>
        /// Spins the shooter wheel if the spin button is pressed.
        /// </summary>
        public void Spin(bool SpinButton)
        {
            if(SpinButton)
            {
                m_shooterWheel1.Set(m_shooterEncoder1.GetRate() < Rpm  ? 1 : 0);
                m_shooterWheel2.Set(m_shooterEncoder2.GetRate() < Rpm ? 1 : 0);

            }
            else { m_shooterWheel1.Set(0);
                   m_shooterWheel2.Set(0);
            }
        }

        /// <summary>
        /// Returns shoots true if the shooter is holding a ball, the shooter button is pressed,
        /// and shooter wheel speed is more than 95% RPM.
        /// </summary>
        /// <returns>a boolean to shoot or not</returns>
        public void Shoot(bool ShootButton)
        {
            double tolerance = .95 * Rpm;
            if (m_ballSensor.Get() && ShootButton && (m_shooterEncoder1.GetRate() > tolerance)&&(m_shooterEncoder2.GetRate() > tolerance))
            {
                m_indexerWheel1.Set(1);
                m_indexerWheel2.Set(1);

            }
            else
            {
                m_indexerWheel1.Set(0);
                m_indexerWheel2.Set(0);
            }
        }

    }
}
