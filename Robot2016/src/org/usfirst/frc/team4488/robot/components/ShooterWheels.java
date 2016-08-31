package org.usfirst.frc.team4488.robot.components;

import org.usfirst.frc.team4488.robot.RobotMap;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterWheels {
	
	 private ShooterWheel m_right;
     private ShooterWheel m_left;
     private Notifier m_periodic;
     private Preferences prefs;

     public double getShooterRPM() {
    	 return (m_left.getShooterSpeed() + m_right.getShooterSpeed()) / 2.0;
     }
     
     public void setShooterRPM(double RPM) {
         m_right.setShooterSpeed(RPM);
         m_left.setShooterSpeed(RPM);
     }
     
	public ShooterWheels() {
		prefs = Preferences.getInstance();

		m_left = new ShooterWheel(RobotMap.ShooterMotorLeft, RobotMap.ShooterLeftCounter,
				prefs.getDouble("ShooterP_Left", 0), prefs.getDouble("ShooterI_Left", 0),
				prefs.getDouble("ShooterD_Left", 0), prefs.getDouble("ShooterEps_Left", 0),
				prefs.getDouble("ShooterPIDDoneRange_Left", 0));
		m_right = new ShooterWheel(RobotMap.ShooterMotorRight, RobotMap.ShooterRightCounter,
				prefs.getDouble("ShooterP_Right", 0), prefs.getDouble("ShooterI_Right", 0),
				prefs.getDouble("ShooterD_Right", 0), prefs.getDouble("ShooterEps_Right", 0),
				prefs.getDouble("ShooterPIDDoneRange_Right", 0));

		m_periodic = new Notifier(() -> {
			SpinWheels();
		});
		m_periodic.startPeriodic(.010);
	}
     
     public void SpinWheels(){
    	 m_left.SpinWheel();
    	 m_right.SpinWheel();
    	 SmartDashboard.putNumber("Current Rate Left", m_left.getSpeed());
    	 SmartDashboard.putNumber("Current Rate Right", m_right.getSpeed());
     }

     public boolean atSpeed(){
    	return (m_left.atSpeed()&&m_right.atSpeed()); 
     }

     public void deJam(){
    	 m_left.setDeJam(true);
    	 m_right.setDeJam(true);
    	 m_left.setLoad(false);
    	 m_right.setLoad(false);
    	 m_left.setSpin(false);
    	 m_right.setSpin(false);
     }
     
     public void Load() {
         m_left.setLoad(true);
         m_right.setLoad(true);
         m_left.setSpin(false);
         m_right.setSpin(false);
    	 m_left.setDeJam(false);
    	 m_right.setDeJam(false);
     }

     public void Spin(){
    	 m_left.setLoad(false);
         m_right.setLoad(false);
    	 m_left.setSpin(true);
         m_right.setSpin(true);
    	 m_left.setDeJam(false);
    	 m_right.setDeJam(false);
     }

     public void Stop()
     {
    	 m_left.setLoad(false);
         m_right.setLoad(false);
         m_left.setSpin(false);
         m_right.setSpin(false);
    	 m_left.setDeJam(false);
    	 m_right.setDeJam(false);
     }
}
