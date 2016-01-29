

package org.usfirst.frc.team4488.robot;



import JavaRoboticsLib.ControlSystems.*;
import JavaRoboticsLib.ControlSystems.SimplePID;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.*;


class Manipulator
{
    private Intake m_intake;
    private Arm m_Arm;
    

    /// <summary>
    /// Manipulator constructor.
    /// </summary>
    public Manipulator(){
        
       
        m_Arm = new Arm();
        m_intake = new Intake();
    }

    public void SpinIntake()
    {
        m_intake.setSpinIntake(true);
    }

    public void StopIntake()
    {
        m_intake.setStopIntake(true);
    }

    public void OutputIntake()
    {
        m_intake.setOutputIntake(true);
    }
   
   /*
    * Gets the current Arm State from this class.
    */
    public armlocation getArmState(){
    	return armlocation;
    }
    
    /*
     *Sets the current Arm State to the input value
     */
    public void setArmState(armlocation newState){
    	armlocation = newState;
    }
    

    /// <summary>
    /// This function updates the arm.
    /// </summary>
    public void UpdateArm()
    {
        m_Arm.Update();
    }
}