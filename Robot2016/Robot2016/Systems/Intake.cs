using WPILib;

namespace Robot2016.Systems
{
    /// <summary>
    /// Intake hardware and control
    /// </summary>
    class Intake
    {
        private bool m_locked;
        private Talon m_talon;
        private DigitalInput m_ballSensor;
        private DigitalInput m_shooterSensor;

        /// <summary>
        /// starts motors and sensors on the intake
        /// </summary>
        public Intake()
        {
            m_talon = new Talon(1);
            m_ballSensor = new DigitalInput(0);
            m_shooterSensor = new DigitalInput(0);
        }
        
        /// <summary>
        /// Sets intake state
        /// </summary>
        /// <param name="argument">true: intaking false: idle</param>
        /// <param name="Override">overrides the lock</param>
        public void SetPosition(bool argument, bool Override)
        {
            if (argument && !m_locked)
            {
               m_talon.Set(1);
                // set intake down
            }
            else
            {
                m_talon.Set(0);
                // lift intake up
            }
            if (m_ballSensor.Get())
            {
                m_talon.Set(0);
                // lift intake up
                m_locked = true;
            }
            if (m_shooterSensor.Get() || Override)
            {
                m_locked = false;
            }
        }
    }
}


