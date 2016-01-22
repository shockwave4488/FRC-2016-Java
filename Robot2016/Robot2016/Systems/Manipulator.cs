using CSharpRoboticsLib.ControlSystems;
using WPILib;

namespace Robot2016.Systems
{
    class Manipulator
    {
        private bool m_locked;
        private Talon m_intakeMotor;
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
<<<<<<< HEAD
            m_intakeMotor = new Talon(1);
=======
            m_PID = new SimplePID(1,1,1);
            m_manipulatorMotor = new Talon(0);
            m_positionEncoder = new Encoder(0,1);
            m_positionEncoder.DistancePerPulse = 1.0/64.0;
            m_talon = new Talon(1);
>>>>>>> refs/remotes/shockwave4488/master
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


        private Talon ManipulatorMotor;
        private Encoder ManipulatorEncoder;
        private SimplePID ManipulatorPID;
        private double m_tolerance = .05;

        /// <summary>
        /// A get, set for the arm state. 
        /// </summary>
        public State ArmState { get; set; }

        /// <summary>
        /// This boolean says to return the member variable "InPosition" and set it to a value.
        /// </summary>
<<<<<<< HEAD
        public bool InPosition => (ManipulatorEncoder.Get() < ManipulatorPID.SetPoint + m_tolerance) && (ManipulatorEncoder.Get() > ManipulatorPID.SetPoint - m_tolerance);
       
        /// <summary>
        /// Sets the manipulator parts equal to their function.
        /// </summary>
        public Manipulator()
        {
            ManipulatorPID = new SimplePID(1,1,1);
            ManipulatorMotor = new Talon(0);
            ManipulatorEncoder = new Encoder(0,1);
            ManipulatorEncoder.DistancePerPulse = 1.0/64.0;
        }

=======
        public bool InPosition
        {
            get
            {
                return (m_positionEncoder.Get() < m_PID.SetPoint + m_tolerance) &&
                       (m_positionEncoder.Get() > m_PID.SetPoint - m_tolerance);
            }
        }
        
>>>>>>> refs/remotes/shockwave4488/master
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

            {
                m_intakeMotor.Set(0);
                ArmState = ArmState;
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
<<<<<<< HEAD
                    ManipulatorPID.SetPoint = 0;
                    if (argument && !m_locked)
                    {
                    m_intakeMotor.Set(1);
                    ArmState = State.High;
                    }
                    else
                    {
                    m_intakeMotor.Set(0);
                    ArmState = State.High;
                    }
            if (!InPosition)
                    {
                    ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                    }
                    break;
=======
                    m_PID.SetPoint = 0;
                        if (!InPosition)
                            {
                                m_manipulatorMotor.SetSpeed(m_PID.Get(m_positionEncoder.GetDistance()));
                            }
                        break;
>>>>>>> refs/remotes/shockwave4488/master

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
