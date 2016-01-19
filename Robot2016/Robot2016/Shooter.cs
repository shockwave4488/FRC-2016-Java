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

        private Talon m_shooterWheel;
        private Encoder m_shooterEncoder;
        private DigitalInput m_ballSensor;

        /// <summary>
        /// Target Rotations Per Minute for shooting.
        /// </summary>
        public double Rpm {get; set; }

        /// <summary>
        /// Initializes Shooter member variables.
        /// </summary>
        public Shooter()
        {
            m_shooterWheel = new Talon(4);
            m_shooterEncoder = new WPILib.Encoder(5, 6);
            m_ballSensor = new DigitalInput(3);
        }

        /// <summary>
        /// Spins the shooter wheel if the spin button is pressed.
        /// </summary>
        public void Spin(bool SpinButton)
        {
            if(SpinButton == true)
            {
                m_shooterWheel.Set(m_shooterEncoder.GetRate() < Rpm  ? 1 : 0);
            }
            else { m_shooterWheel.Set(0); }
        }

        /// <summary>
        /// Returns shoots true if the shooter is holding a ball, the shooter button is pressed,
        /// and shooter wheel speed is more than 95% RPM.
        /// </summary>
        /// <returns>a boolean to shoot or not</returns>
        public bool Shoot(bool ShootButton)
        {
            double tolerance = .95 * Rpm;
            if ((m_ballSensor.Get() == true) && (ShootButton == true) && (m_shooterEncoder.GetRate() > tolerance))
            {
                return true;
            }
            else { return false; }
        }

    }
}
