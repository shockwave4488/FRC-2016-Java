

package org.usfirst.frc.team4488.robot;


import edu.wpi.first.wpilibj.*;

class Intake
{
    private Talon intakeMotor;
    private DigitalInput armBallSensor;
    public Intake() {


        intakeMotor = new Talon(3);
        armBallSensor = new DigitalInput(1);


    }
    
    /*
     * Moves the rollers depending on whether the button is pressed and whether or not output is true
     * If output is true, and the button, then the intake SHOULD spin backwards to release the ball.
     * Otherwise the intake spins normally, assuming that the ball sensor has not tripped.
     * If this happens, nothing happens as a result of pressing the button.
     */
    public void moveThoseRollers(boolean button,boolean output) {
        if (button || (!armBallSensor.get()))
        {
            if (!output)
            {
                intakeMotor.set(1);
            }
            else {
                intakeMotor.set(-1);
            }
        }
        else {
            intakeMotor.set(0);
        }
    }
}