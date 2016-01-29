

package org.usfirst.frc.team4488.robot;
import edu.wpi.first.wpilibj.*;
import JavaRoboticsLib.*;
import JavaRoboticsLib.ControlSystems.MotionControlledSystem;
import JavaRoboticsLib.ControlSystems.SimplePID;
enum armlocation
{
    Low,
    High,
    Intake,
    Load,
    Lower
}

class Arm extends MotionControlledSystem
{
    private SimplePID armPID;
    private Talon armMotor;
    private AnalogPotentiometer m_armPotentiometer;
    
    public Arm() throws Exception {
        armMotor = new Talon(RobotMap.ArmMotor_Channel);
        m_armPotentiometer = new AnalogPotentiometer(RobotMap.ArmPotentiometer);
        armPID = new SimplePID(1, 1, 1);
        super.Controller = armPID;
        super.Motor = armMotor;
        super.Sensor = m_armPotentiometer;
    }
    
    /*
     * This moves the arm to the position requested as the input of the program.
     * Should work, (should)....................................................
     */
    public void newPosition(armlocation armPlace)
    {
        switch (armPlace)
        {
        case High:
            super.setSetPoint(0);

            break;

        case Load:
            super.setSetPoint(0);

            break;
        case Low:
            super.setSetPoint(0);

            break;
        case Intake:
            super.setSetPoint(0);

            break;
        case Lower:
            super.setSetPoint(0);

                break;
        }
    }

}
