using CSharpRoboticsLib.ControlSystems;
using WPILib;
using Robot2016.Components;

namespace Robot2016.Systems
{
    class Manipulator
    {
        private Intake m_intakeMotor;
        private Arm m_Arm;
        private DigitalInput m_ballSensor;
        private DigitalInput m_shooterSensor;
        private SimplePID m_PID;
        private Encoder m_positionEncoder;
        private double m_tolerance = .05;
    
        /// <summary>
        /// Manipulator constructor.
        /// </summary>
        public Manipulator()
        {
            
            m_PID = new SimplePID(1,1,1);
            m_Arm = new Arm();
            m_positionEncoder = new Encoder(2,3);
            m_positionEncoder.DistancePerPulse = 1.0/64.0;
            m_ballSensor = new DigitalInput(0);
            m_shooterSensor = new DigitalInput(0);
        }

        /// <summary>
        /// The different "states" to the ArmState machine. 
        /// </summary>
        public enum State
        {
            Low,
            Intake,
            High,
            Load,
            Lower
        }


        /// <summary>
        /// A get, set for the arm state. 
        /// </summary>
        public State ArmState { get; set; }
       
        public bool InPosition => (m_positionEncoder.Get() < m_PID.SetPoint + m_tolerance) && (m_positionEncoder.Get() > m_PID.SetPoint - m_tolerance);
        

        /// <summary>
        /// Uses the override and intake buttons to lock and unlock the intake, and changes the arm state accordingly.
        /// </summary>
        /// <param name="Override">The override button for the intake.</param>
        /// <param name="button">The intake button.</param>
        public void Intake(bool button, bool reverse)
        {

            if (m_ballSensor.Get() || !button)

                {                    
                    ;
                    //lift arm up
                    ArmState = ArmState;
                }

            {
                m_intakeMotor.moveThoseRollers(false,false);
                ArmState = ArmState;
            }
            

            if (button && !m_ballSensor.Get())
                {
                if (!reverse)
                {
                    m_intakeMotor.moveThoseRollers(true,false);
                    //set arm down
                }
                else {
                    m_intakeMotor.moveThoseRollers(true,true);
                }
            }
            else
                {
                    m_intakeMotor.moveThoseRollers(false,false);
                    //lift arm up
                } 

        }

        /// <summary>
        /// Grabs the secondary control "GetIntakeArmManual"
        /// </summary>
        public double ArmManual { get; set; }







        /// <summary>
        /// This funtion updates the arm.
        /// </summary>
        public void UpdateArm()
        {
            m_Arm.Update();
        }
    }
}