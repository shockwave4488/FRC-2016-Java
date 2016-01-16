using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WPILib;
using CSharpRoboticsLib.WPIExtensions;
using CSharpRoboticsLib.Drive.Interfaces;

namespace Robot2016
{
    class Drive : ITankDrive
    {

        //Left
        private SpeedControllerGroup m_left;

        //Right
        private SpeedControllerGroup m_right;

        public Drive()
        {
            m_left = new SpeedControllerGroup(typeof(Talon));
            m_right = new SpeedControllerGroup(typeof(Talon));
        }

        public void SetPowers(double left, double right)
        {
            m_left.Set(left);
            m_right.Set(right);
        }
    }
}
