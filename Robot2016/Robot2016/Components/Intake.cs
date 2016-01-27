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
        private DigitalInput armBallSensor;
        public Intake() {


            intakeMotor = new Talon(3);
            armBallSensor = new DigitalInput(0);


        }

        /// <summary>
        /// Sets the input speed for the intake depending on both the controller button and a separate button
        /// The separate button will determine whether or not the bot needs to 
        /// </summary>
        /// <param name="button"></param>
        /// <param name="output"></param>
        public void moveThoseRollers(bool button,bool output) {
            if (button)
            {
                if (!armBallSensor.Get())
                {
                    intakeMotor.SetSpeed(1);
                }
                else if (!output)
                {
                    intakeMotor.SetSpeed(1);
                }
                else {
                    intakeMotor.SetSpeed(0);
                }
            }
            else {
                intakeMotor.SetSpeed(0);
            }
        }
    }
}
