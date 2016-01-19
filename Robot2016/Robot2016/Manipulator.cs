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
        /// <summary>
        /// These are the different "states" to out state machine. 
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

        private Boolean m_inPosition;
        private State m_state;
        
        /// <summary>
        /// Represents the ArmState of the state machine for the manipulator and sets it to a value. 
        /// </summary>
        public State ArmState
        {
            get { return m_state; }
            set { m_state = value; }
        }

        /// <summary>
        /// This boolean says to return the member variable "InPosition" and set it to a value.
        /// </summary>
        public Boolean InPosition
        {
            get { return m_inPosition; }
            set { m_inPosition = value; }
        }

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
        /// This funtion represents the different stages of the state machine and their operation.
        /// </summary>
        public void Update()
        {
            switch (ArmState)
            {
                case State.High:
                    ManipulatorPID.SetPoint = 1;
                    if (!InPosition)
                    {
                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        
                    }
                    InPosition = true;
                    break;

                case State.Low:
                    ManipulatorPID.SetPoint = 0;
                    if (!InPosition)
                    {
                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        
                    }
                    InPosition = true;
                    break;

                case State.Mid:
                    ManipulatorPID.SetPoint = 0.5;
                    if (!InPosition)
                    {

                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));

                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        
                    }
                    InPosition = true;
                    break;

                case State.Manual:
                    ManipulatorPID.SetPoint = ManipulatorEncoder.GetDistance();
                    if (!InPosition)
                    {
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        
                    }
                    InPosition = true;
                    break;
            }

        }




    }
}
