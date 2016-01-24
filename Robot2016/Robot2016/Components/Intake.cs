using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WPILib;

namespace Robot2016.Components
{
    class Intake
    {
        private Talon intakeMotor;
        private /*IDK, beam break?*/ armBallSensor;
        public Intake() {


            intakeMotor = new Talon(3);
            armBallSensor = new /*IDK, beam break?*/(/*Some arbitrary channel reference*/);


        }
        public void moveThoseRollers(bool button,bool output) {
            if (button || (!armBallSensor))
            {
                if (!output)
                {
                    intakeMotor.SetSpeed(1);
                }
                else {
                    intakeMotor.SetSpeed(-1);
                }
            }
            else {
                intakeMotor.SetSpeed(0);
            }
        }
    }
}
