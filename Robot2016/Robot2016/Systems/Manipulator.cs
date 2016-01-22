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

        /// <summary>
        /// starts motors and sensors on the intake
        /// </summary>
        public void Intake()
        {
            m_intakeMotor = new Talon(1);
            m_ballSensor = new DigitalInput(0);
            m_shooterSensor = new DigitalInput(0);
        }

        /// <summary>
        /// The different "states" to the arm state machine. 
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
        /// Represents the ArmState of the state machine for the manipulator and sets it to a value. 
        /// </summary>
        public State ArmState { get; set; }
       

        /// <summary>
        /// This boolean says to return the member variable "InPosition" and set it to a value.
        /// </summary>
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

        /// <summary>
        /// 
        /// </summary>
        /// <param name="Override">overrides the lock</param>
        public void Intake(bool Override)
        {

            if (m_ballSensor.Get())
            {
                m_intakeMotor.Set(0);
                ArmState = ArmState;
                m_locked = true;
            }
            if (m_shooterSensor.Get() || Override)
            {
                m_locked = false;
            }
        }

        /// <summary>
        /// This funtion represents the different stages of the state machine and their operation.
        /// </summary>
        /// <param name="argument">intake button</param>
        public void Arm(bool argument)
        {
            switch (ArmState)
            {
                case State.High:
                    ManipulatorPID.SetPoint = 1;
                    if (!InPosition)
                    {
                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        
                    }
                    break;

                case State.Low:
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

                case State.Mid:
                    ManipulatorPID.SetPoint = 0.5;
                    if (!InPosition)
                    {

                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));

                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        
                    }
                    break;

                case State.Manual:
                    ManipulatorPID.SetPoint = ManipulatorEncoder.GetDistance();
                    if (!InPosition)
                    {
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        
                    }
                    break;

                    
            }

        }




    }
}
