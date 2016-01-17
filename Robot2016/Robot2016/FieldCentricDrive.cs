using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WPILib.Extras.NavX;
using CSharpRoboticsLib.ControlSystems;

namespace Robot2016
{
    class FieldCentricDrive
    {
        private AHRS m_navx;
        private SimplePID m_pid;
        private Drive m_drive;

        public FieldCentricDrive(Drive drive)
        {
            m_navx = new AHRS(WPILib.SPI.Port.OnboardCS2);
            m_pid = new SimplePID(1.0/90.0, 0, 0);
            m_drive = drive;
        }

        public void Drive(double x, double y, double speed)
        {
            double angle = Math.Atan2(y, x);
            m_pid.SetPoint = angle;
            double turn = m_pid.Get(m_navx.GetAngle());
            m_drive.SetPowers(speed + turn, speed - turn);
        }
    }
}
