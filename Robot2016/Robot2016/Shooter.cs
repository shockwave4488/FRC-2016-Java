using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WPILib;
using CSharpRoboticsLib.WPIExtensions;

namespace Robot2016
{
    class Shooter
    {
        private Talon m_shooterWheel;
        private WPILib.Encoder m_shooterEncoder;
        private DigitalInput m_ballSensor;

        // in constant class
        public double Rpm {get; set; }
        public bool SpinButton { get; set; }
        public bool ShootButton { get; set; }
        //

        public Shooter()
        {
            m_shooterWheel = new Talon(4);
            m_shooterEncoder = new WPILib.Encoder(1, 2);
            m_ballSensor = new DigitalInput(3);
        }

        public void Spin()
        {
            if(SpinButton == true)
            {
                m_shooterWheel.Set(m_shooterEncoder.GetRate() < Rpm  ? 1 : 0);
            }
            else { m_shooterWheel.Set(0); }
        }

        public bool Shoot()
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
