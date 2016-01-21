using WPILib;

namespace Robot2016.Components
{ 
/// <summary>
/// Shooter Wheel class, consisting of one shooter wheel and accompanying encoder
/// </summary>
    class ShooterWheel
    {
        private Talon m_shooterWheel;
        private Encoder m_shooterEncoder;

        /// <summary>
        /// Target Rotations Per Minute for shooting.
        /// </summary>
        public double Rpm { get; set; }

        /// <summary>
        /// Initializes ShooterWheel member variables using input channels
        /// </summary>
        public ShooterWheel(int TalonChannel, int EncoderChannel1, int EncoderChannel2)
        {
            m_shooterWheel = new Talon(TalonChannel);
            m_shooterEncoder = new Encoder(EncoderChannel1, EncoderChannel2);
        }
        
        /// <summary>
        /// Spins shooter wheel
        /// </summary>
        public void Spin()
        {
            m_shooterWheel.Set(m_shooterEncoder.GetRate() < Rpm ? 1 : 0);
        }
       
        /// <summary>
        /// Sets shooter wheel to zero
        /// </summary>
        public void SetZero()
        {
            m_shooterWheel.Set(0);
        }
        
        /// <summary>
        /// Gets current rate of wheel from encoder
        /// </summary>
        /// <returns>Current rate of wheel</returns>
        public double getCurrentRate()
        {
            return m_shooterEncoder.GetRate();
        }
        
        /// <summary>
        /// Checks if wheel rate is within tolerance of 95% of desired RPM
        /// </summary>
        /// <returns>True if within tolerance, false otherwise</returns>
        public bool atRate()
        {
            double tolerance = Rpm * .95;
            if(m_shooterEncoder.GetRate() > tolerance) { return true; }
            else { return false; }
        }

    }
}
