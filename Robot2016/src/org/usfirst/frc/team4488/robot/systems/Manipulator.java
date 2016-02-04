package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.components.Arm;
import org.usfirst.frc.team4488.robot.components.ArmPosition;
import org.usfirst.frc.team4488.robot.components.Intake;

public class Manipulator
{
    private Intake m_intake;
    private Arm m_Arm;
    

    /// <summary>
    /// Manipulator constructor.
    /// </summary>
    public Manipulator(){       
        try {
			m_Arm = new Arm();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        m_intake = new Intake();
    }

    
   /*
    * Gets the current Arm State from this class.
    */
    /*public ArmPosition getArmState(){
    	//return m_arm.;
    	return ArmPosition.;
    }*/
    
    /*
     *Sets the current Arm State to the input value
     */
    public void setArmState(ArmPosition newState){
    	//ArmLocation = newState;
    }
    

    /// <summary>
    /// This function updates the arm.
    /// </summary>
    public void UpdateArm(){
        m_Arm.Update();
    }
}