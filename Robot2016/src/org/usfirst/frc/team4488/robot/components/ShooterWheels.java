package org.usfirst.frc.team4488.robot.components;

import org.usfirst.frc.team4488.robot.RobotMap;

import edu.wpi.first.wpilibj.Notifier;

public class ShooterWheels {
	
	 private ShooterWheel m_right;
     private ShooterWheel m_left;
     private Notifier m_periodic;

     public double getShooterRPM() {
    	 return (m_left.getShooterSpeed() + m_right.getShooterSpeed()) / 2.0;
     }
     
     public void setShooterRPM(double RPM) {
         m_right.setShooterSpeed(RPM);
         m_left.setShooterSpeed(RPM);
     }
     
     public ShooterWheels(){
          m_left = new ShooterWheel(RobotMap.ShooterMotorLeft, RobotMap.ShooterLeftCounter);
          m_right = new ShooterWheel(RobotMap.ShooterMotorRight, RobotMap.ShooterRightCounter);
         m_periodic = new Notifier(()-> { m_left.SpinWheel(); m_right.SpinWheel(); });
         m_periodic.startPeriodic(.010);
     }

     public boolean atRate(){
    	return (m_left.atRate()&&m_right.atRate()); 
     }

     public void Load() {
         m_left.setLoad(true);
         m_right.setLoad(true);
     }

     public void Spin(){
    	 m_left.setLoad(false);
         m_right.setLoad(false);
    	 m_left.setSpin(true);
         m_right.setSpin(true);
     }

     public void Stop()
     {
    	 m_left.setLoad(false);
         m_right.setLoad(false);
         m_left.setSpin(false);
         m_right.setSpin(false);
     }
}
