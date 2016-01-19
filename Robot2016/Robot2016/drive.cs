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
    /// <summary>
    /// Robot drive hardware
    /// </summary>
    class Drive : ITankDrive
    {

        /// <summary>
        /// Left motor controllers
        /// </summary>
        private SpeedControllerGroup m_left;

        /// <summary>
        /// Right motor controlloers
        /// </summary>
        private SpeedControllerGroup m_right;

        /// <summary>
        /// Creates left and right motors
        /// </summary>
        public Drive()
        {
            m_left = new SpeedControllerGroup(typeof(Talon));
            m_right = new SpeedControllerGroup(typeof(Talon));
        }

        /// <summary>
        /// Sets drive powers
        /// </summary>
        /// <param name="left">Left power</param>
        /// <param name="right">Right power</param>
        public void SetPowers(double left, double right)
        {
            m_left.Set(left);
            m_right.Set(right);
        }
    }
}
