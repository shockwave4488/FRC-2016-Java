package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.components.Arm;
import org.usfirst.frc.team4488.robot.components.ArmPosition;
import org.usfirst.frc.team4488.robot.components.Intake;

class Manipulator
{
    private Intake m_intake;
    private Arm m_Arm;
    
    public boolean IntakeHasBall(){
	return m_intake.ballInIntake();
    }
    
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

    /// <summary>
    /// spins intake and moves arm 
    /// </summary>
    public void spinIntake(){
        m_intake.intakeBall();
        m_Arm.setPosition(ArmPosition.Intake);
        m_Arm.Update();
    }

    /// <summary>
    /// stops intake motors and moves arm up
    /// </summary>
    public void stopIntake(){
        m_intake.off();
        m_Arm.setPosition(ArmPosition.High);
        m_Arm.Update();
    }
    
    /// <summary>
    /// outputs ball
    /// </summary>
    public void outputIntake(){
        m_intake.output();
        m_Arm.setPosition(ArmPosition.Intake);
        m_Arm.Update();
    }

    /// <summary>
    /// loads ball
    /// </summary>
    public void loadIntake()
    {
        m_intake.load();
        m_Arm.setPosition(ArmPosition.Load);
        m_Arm.Update();
    }

    /// <summary>
    /// shoots ball
    /// </summary>
    public void shoot()
    {
        m_intake.off();
        m_Arm.setPosition(ArmPosition.Low);
        m_Arm.Update();
    }

    /// <summary>
    /// moves arm down for low defenses and stops intake motor
    /// </summary>
    public void lowDefense()
    {
        m_intake.off();
        m_Arm.setPosition(ArmPosition.Lower);
        m_Arm.Update();
    }

    /// <summary>
    /// moves arm up for high defenses and stops intake motor
    ///  </summary>
    public void highDefense()
    {
        m_intake.off();
        m_Arm.setPosition(ArmPosition.High);
        m_Arm.Update();
    }
}