using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WPILib;
using CSharpRoboticsLib.ControlSystems;

namespace Robot2016.Components
{
    enum armlocation
    {
        Low,
        High,
        Intake,
        Load,
        Lower
    }

    class Arm : MotionControlledSystem
    {
        private SimplePID armPID;
        private Talon armMotor;
        private AnalogPotentiometer m_armPotentiometer;
        public Arm() {
            armMotor = new Talon(1);
            m_armPotentiometer = new AnalogPotentiometer(1);
            armPID = new SimplePID(1, 1, 1);
            base.Controller = armPID;
            base.Motor = armMotor;
            base.Sensor = m_armPotentiometer;
        }
        public void newPosition(armlocation armPlace)
        {
            switch (armPlace)
            {
                case armlocation.High:
                    base.SetPoint = 0/*Arbitrary Number*/;

                    break;

                case armlocation.Load:
                    base.SetPoint = 0/*Arbitrary Number*/;

                    break;
                case armlocation.Low:
                    base.SetPoint = 0/*Arbitrary Number*/;

                    break;
                case armlocation.Intake:
                    base.SetPoint = 0/*Arbitrary Number*/;

                    break;
                case armlocation.Lower:
                    base.SetPoint = 0/*Arbitrary Number*/;

                    break;
            }
        }

}
}
