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
	        m_arm.Start(0.05);
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
    	if(!m_arm.getLimitFound())
    		return false;
    	return m_arm.AtSetpoint();
    }
    
    public boolean armAtPosition(double position, double tolerance){
    	if(!m_arm.getLimitFound())
    		return false;
    	return m_arm.armAngle() > position - tolerance && m_arm.armAngle() < position + tolerance;
    }

    /// <summary>
    /// spins intake and moves arm 
    /// </summary>
    public void spinIntake(){
        m_intake.intakeBall();
        m_arm.setPosition(ArmPosition.Intake);
    }

    /// <summary>
    /// stops intake motors and moves arm up
    /// </summary>
    public void stopIntake(){
        m_intake.off();
        m_arm.setPosition(ArmPosition.High);
    }
    
    /// <summary>
    /// outputs ball
    /// </summary>
    public void outputIntake(){
        m_intake.output();
        m_arm.setPosition(ArmPosition.Intake);
    }

    /// <summary>
    /// loads ball
    /// </summary>
    public void loadIntake()
    {
        
       m_arm.setPosition(ArmPosition.Load);
       m_intake.load();     
    }

    /// <summary>
    /// shoots ball
    /// </summary>
    public void shoot()
    {
        m_intake.off();
        m_arm.setPosition(ArmPosition.Shoot);
    }

    /// <summary>
    /// moves arm down for low defenses and stops intake motor
    /// </summary>
    public void lowDefense()
    {
        m_intake.off();
        m_arm.setPosition(ArmPosition.Low);
    }

    /// <summary>
    /// moves arm up for high defenses and stops intake motor
    ///  </summary>
    public void semiManualDefense()
    {
        m_intake.off();
        m_arm.setPosition(ArmPosition.SemiManual);
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