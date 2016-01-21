using CSharpRoboticsLib.Drive.Interfaces;
using CSharpRoboticsLib.WPIExtensions;
using WPILib;
using CSharpRoboticsLib.Drive.Interfaces;
using WPILib.Extras.NavX;

namespace Robot2016.Systems
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

        ///<summary>
        /// Right motor Encoder
        ///</summary>
        private Encoder m_rightEncoder;


        ///<summary>
        /// Left motor Encoder
        ///</summary>
        private Encoder m_leftEncoder;

        /// <summary>
        /// The AHRS that is necessary for a NavX Gyro to be initialized in the code
        /// </summary>
        private AHRS m_robotGyro;

        /// <summary>
        /// Property used to access the current angular bearing of the AHRS
        /// </summary>
        public double getAngle => m_robotGyro.GetAngle();
      

        /// <summary>
        /// Creates left and right motors & encoders
        /// </summary>
        public Drive()
        {
            m_left = new SpeedControllerGroup(typeof(Talon));
            m_right = new SpeedControllerGroup(typeof(Talon));
            m_leftEncoder = new Encoder(1,2);
            m_rightEncoder = new Encoder(3,4);
            m_robotGyro = new AHRS(WPILib.SPI.Port.OnboardCS2,20);
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
