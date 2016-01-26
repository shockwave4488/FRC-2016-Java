

package org.usfirst.frc.team4488.robot;



import JavaRoboticsLib.ControlSystems.*;
import JavaRoboticsLib.ControlSystems.SimplePID;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.*;


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
    public Manipulator() throws Exception
    {
        
        m_PID = new SimplePID(1,1,1);
        m_Arm = new Arm();
        m_positionEncoder = new Encoder(2,3);
        m_positionEncoder.setDistancePerPulse(1.0/64);
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

    public State ArmState = State.High;
    
   /*
    * Gets the current Arm State from this class.
    */
    public Manipulator.State getArmState(){
    	return ArmState;
    }
    
    /*
     *Sets the current Arm State to the input value
     */
    public void setArmState(State newState){
    	ArmState = newState;
    }
    

    /// <summary>
    /// Uses the override and intake buttons to lock and unlock the intake, and changes the arm state accordingly.
    /// </summary>
    /// <param name="Override">The override button for the intake.</param>
    /// <param name="button">The intake button.</param>
    public void Intake(boolean button, boolean reverse)
    {

        if (m_ballSensor.get() || !button)

            {                    
                ;
                //lift arm up
                ArmState = ArmState;
            }

        {
            m_intakeMotor.moveThoseRollers(false,false);
            ArmState = ArmState;
        }
        

        if (button && !m_ballSensor.get())
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
    /// This funtion updates the arm.
    /// </summary>
    public void UpdateArm()
    {
        m_Arm.Update();
    }
}