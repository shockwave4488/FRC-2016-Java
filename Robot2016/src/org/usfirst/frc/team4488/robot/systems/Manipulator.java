package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.components.Arm;
import org.usfirst.frc.team4488.robot.components.ArmPosition;
import org.usfirst.frc.team4488.robot.components.Intake;

import JavaRoboticsLib.Utility.Logger;

public class Manipulator
{
    private Intake m_intake;
    private Arm m_arm;
    
    public boolean IntakeHasBall(){
	return m_intake.ballInIntake();
    }
    
    /// <summary>
    /// Manipulator constructor.
    /// </summary>
    public Manipulator(){       
        try {
			m_arm = new Arm();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        m_intake = new Intake();
    }
    
    public double getArmAngle(){
    	return m_arm.armAngle();
    }
    
    public boolean HasBall(){
    	return m_intake.ballInIntake();
    }
    
    public boolean armAtPosition(){
    	return m_arm.AtSetpoint();
    }

    /// <summary>
    /// spins intake and moves arm 
    /// </summary>
    public void spinIntake(){
        m_intake.intakeBall();
        m_arm.setPosition(ArmPosition.Intake);
        m_arm.Update();
    }

    /// <summary>
    /// stops intake motors and moves arm up
    /// </summary>
    public void stopIntake(){
        m_intake.off();
        m_arm.setPosition(ArmPosition.High);
        m_arm.Update();
    }
    
    /// <summary>
    /// outputs ball
    /// </summary>
    public void outputIntake(){
        m_intake.output();
        m_arm.setPosition(ArmPosition.Intake);
        m_arm.Update();
    }

    /// <summary>
    /// loads ball
    /// </summary>
    public void loadIntake()
    {
        
       m_arm.setPosition(ArmPosition.Load);
       m_arm.Update();
       m_intake.load();     
    }

    /// <summary>
    /// shoots ball
    /// </summary>
    public void shoot()
    {
        m_intake.off();
        m_arm.setPosition(ArmPosition.Shoot);
       m_arm.Update();
    }

    /// <summary>
    /// moves arm down for low defenses and stops intake motor
    /// </summary>
    public void lowDefense()
    {
        m_intake.off();
        m_arm.setPosition(ArmPosition.Low);
        m_arm.Update();
    }

    /// <summary>
    /// moves arm up for high defenses and stops intake motor
    ///  </summary>
    public void semiManualDefense()
    {
        m_intake.off();
        m_arm.setPosition(ArmPosition.SemiManual);
        m_arm.Update();
    }
    
    public void PortcullisUp(){
    	m_intake.off();
    	m_arm.setPosition(ArmPosition.PortcullisUp);
    	m_arm.Update();
    }
    
    public void PortcullisDown(){
    	m_intake.off();
    	m_arm.setPosition(ArmPosition.PortcullisDown);
    	m_arm.Update();
    }
    
    public void setArmManual(boolean value){
    	m_arm.setManual(value);
    	Logger.addMessage("Arm set to " + (value ? "Manual" : "Automatic"));
    }
    
    public void setArmManualPower(double power){
    	m_arm.setManualPower(power);
    }
    
    public void setArmSemiManualPosition(double value){
    	m_arm.setSemiManualPosition(value);
    }
}