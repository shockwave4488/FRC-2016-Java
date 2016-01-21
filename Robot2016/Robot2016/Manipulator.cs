using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using CSharpRoboticsLib.ControlSystems;
using WPILib;
using CSharpRoboticsLib.WPIExtensions;
using HAL.Base;


namespace Robot2016
{
    class Manipulator
    {
        private bool m_locked;
        private Talon m_talon;
        private DigitalInput m_ballSensor;
        private DigitalInput m_shooterSensor;
        private Talon m_manipulatorMotor;
        private Encoder m_positionEncoder;
        private SimplePID m_PID;
        private State m_state;
        private double m_tolerance = .05;
    
        /// <summary>
        /// Manipulator constructor.
        /// </summary>
        public Manipulator()
        {
            m_PID = new SimplePID(1,1,1);
            m_manipulatorMotor = new Talon(0);
            m_positionEncoder = new Encoder(0,1);
            m_positionEncoder.DistancePerPulse = 1.0/64.0;
            m_talon = new Talon(1);
            m_ballSensor = new DigitalInput(0);
            m_shooterSensor = new DigitalInput(0);
        }

        /// <summary>
        /// The different "states" to the ArmState machine. 
        /// </summary>
        public enum State
        {
            Low,
            High,
            Mid,
            Manual,
        }
     
        /// <summary>
        /// A get, set for the arm state. 
        /// </summary>
        public State ArmState { get; set; }

        /// <summary>
        /// This boolean says to return the member variable "InPosition" and set it to a value.
        /// </summary>
        public bool InPosition
        {
            get
            {
                return (m_positionEncoder.Get() < m_PID.SetPoint + m_tolerance) &&
                       (m_positionEncoder.Get() > m_PID.SetPoint - m_tolerance);
            }
        }
        
        /// <summary>
        /// Uses the override and intake buttons to lock and unlock the intake, and changes the arm state accordingly.
        /// </summary>
        /// <param name="Override">The override button for the intake.</param>
        /// <param name="button">The intake button.</param>
        public void Intake(bool Override, bool button)
        {

            if (m_ballSensor.Get())
                {                    
                    m_talon.Set(0);
                    //lift arm up
                    ArmState = m_state;
                    m_locked = true;
                }

            if (m_shooterSensor.Get() || Override)
                {
                    m_locked = false;
                }

            if (button && !m_locked)
                {
                    m_talon.Set(1);
                    //set arm down
                }
             else
                {
                    m_talon.Set(0);
                    //lift arm up
                } 

        }

        /// <summary>
        /// Grabs the secondary control "GetIntakeArmManual"
        /// </summary>
        public double ArmManual { get; set; }

        /// <summary>
        /// This funtion represents the different stages of the state machine and their operation.
        /// </summary>
        public void Arm()
        {
            switch (ArmState)
            {
                case State.High:
                    m_PID.SetPoint = 1;
                    if (!InPosition)
                        {
                            m_manipulatorMotor.SetSpeed(m_PID.Get(m_positionEncoder.GetDistance()));
                        }
                    break;

                case State.Low:
                    m_PID.SetPoint = 0;
                        if (!InPosition)
                            {
                                m_manipulatorMotor.SetSpeed(m_PID.Get(m_positionEncoder.GetDistance()));
                            }
                        break;

                case State.Mid:
                    m_PID.SetPoint = 0.5;
                        if (!InPosition)
                            {
                                m_manipulatorMotor.SetSpeed(m_PID.Get(m_positionEncoder.GetDistance()));
                            }
                        break;

                case State.Manual:
                        m_manipulatorMotor.SetSpeed(ArmManual);
                        break;
            }
        }
    }
}
