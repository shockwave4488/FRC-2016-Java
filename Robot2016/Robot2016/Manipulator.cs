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
        public enum State
        {
            Low,
            High,
            Mid,
            Manual,
        }

        public Talon ManipulatorMotor;
        public Encoder ManipulatorEncoder;
        public SimplePID ManipulatorPID;
        private Boolean m_inPosition;
        private State m_state;
        public State ArmState
        {
            get { return m_state; }
            set { m_state = value; }
        }

        public Boolean InPosition
        {
            get { return m_inPosition; }
            set { m_inPosition = value; }
        }
        public Manipulator()
        {
            ManipulatorPID = new SimplePID(1,1,1);
            ManipulatorMotor = new Talon(0);
            ManipulatorEncoder = new Encoder(0,1);
            ManipulatorEncoder.DistancePerPulse = 1.0/64.0;
        }


        public void Update()
        {
            switch (ArmState)
            {
                case State.High:
                    ManipulatorPID.SetPoint = 1;
                    if (!InPosition)
                    {
                        while (!(ManipulatorEncoder.GetDistance() > (ManipulatorPID.SetPoint-0.1)&& ManipulatorEncoder.GetDistance() < (ManipulatorPID.SetPoint + 0.1)))
                        {
                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        }
                    }
                    InPosition = true;
                    break;

                case State.Low:
                    ManipulatorPID.SetPoint = 0;
                    if (!InPosition)
                    {
                        while (!(ManipulatorEncoder.GetDistance() > (ManipulatorPID.SetPoint - 0.1) && ManipulatorEncoder.GetDistance() < (ManipulatorPID.SetPoint + 0.1)))
                        {
                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        }
                    }
                    InPosition = true;
                    break;

                case State.Mid:
                    ManipulatorPID.SetPoint = 0.5;
                    if (!InPosition)
                    {
                        while (!(ManipulatorEncoder.GetDistance() > (ManipulatorPID.SetPoint - 0.1) && ManipulatorEncoder.GetDistance() < (ManipulatorPID.SetPoint + 0.1)))
                        {
                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        }
                    }
                    InPosition = true;
                    break;

                case State.Manual:
                    ManipulatorPID.SetPoint = ManipulatorEncoder.GetDistance();
                    if (!InPosition)
                    {
                        while (!(ManipulatorEncoder.GetDistance() > (ManipulatorPID.SetPoint - 0.1) && ManipulatorEncoder.GetDistance() < (ManipulatorPID.SetPoint + 0.1)))
                        {
                            
                            ManipulatorMotor.SetSpeed(ManipulatorPID.Get(ManipulatorEncoder.GetDistance()));
                        }
                    }
                    InPosition = true;
                    break;
            }

        }




    }
}
