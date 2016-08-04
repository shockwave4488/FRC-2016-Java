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
    
    public Manipulator(){       
        try {
			m_arm = new Arm();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Manipulator Initialization Failure");
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
    	return m_arm.AtSetpoint();
    }
    
    public boolean armReady() {
    	return m_arm.getLimitFound();
    }

    public void spinIntake(){
        m_intake.intakeBall();
        m_arm.setPosition(ArmPosition.Intake);
    }

    public void stopIntake(){
        m_intake.off();
        m_arm.setPosition(ArmPosition.High);
    }
    
    public void outputIntake(){
    	//System.out.println("Out");
        m_arm.setPosition(ArmPosition.Intake);
    	//if(m_arm.AtSetpoint())
    		m_intake.output();
    }
    
    public void readyOutput(){
    	//System.out.println("Ready");
    	m_arm.setPosition(ArmPosition.Intake);
    	m_intake.off();
    }

    public void loadIntake()
    {        
       m_arm.setPosition(ArmPosition.Load);
       m_intake.load();     
    }

    public void shoot()
    {
        m_intake.off();
        m_arm.setPosition(ArmPosition.Shoot);
    }

    public void lowDefense()
    {
        m_intake.off();
        m_arm.setPosition(ArmPosition.Low);
    }

    public void semiManualDefense()
    {
        m_intake.off();
        m_arm.setPosition(ArmPosition.SemiManual);
    } 
    
    public void batterCharge(){
    	m_intake.off();
    	m_arm.setPosition(ArmPosition.High);//ArmPosition.BatterShoot);
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
    
    public void resetArm(){
    	m_arm.resetArm();
    }
    
    public void setArmPositionHigh(){
    	m_arm.setPosition(ArmPosition.High);
    }
    
    public void setArmPositionLow(){
    	m_arm.setPosition(ArmPosition.Low);
    }
}