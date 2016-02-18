package org.usfirst.frc.team4488.robot.components;

import org.usfirst.frc.team4488.robot.RobotMap;

import edu.wpi.first.wpilibj.Notifier;

public class ShooterWheels {
	
	 private ShooterWheel m_right;
     private ShooterWheel m_left;
     private Notifier m_periodic;

     /// <summary>
     /// Gets and sets RPM for both shooter wheels
     /// </summary>
     public double getShooterRPM() {
    	 return m_left.getShooterSpeed() + m_right.getShooterSpeed();
     }
     
     public void setShooterRPM(double RPM) {
         m_right.setShooterSpeed(RPM);
         m_left.setShooterSpeed(RPM);
     }
     
     
     //returns the average shooter speed of the two shooters.
     public double getShooterPowers(){
    	 return (m_right.getPower()+m_left.getPower())/2;
     }
     
     /// <summary>
     /// Constructor for ShooterWheels, with updating Notifier
     /// </summary>
     public ShooterWheels(){
          m_left = new ShooterWheel(RobotMap.ShooterMotorLeft, RobotMap.ShooterLeftCounter);
          m_right = new ShooterWheel(RobotMap.ShooterMotorRight, RobotMap.ShooterRightCounter);
         m_periodic = new Notifier(()-> { m_left.SpinWheel(); m_right.SpinWheel(); });
         m_periodic.startPeriodic(.010);
     }

     
     /// <summary>
     /// Checks if both wheels are at the rate to shoot
     /// </summary>
     /// <returns>True if ready to shoot, false otherwise</returns>
     public boolean ballShot() {
         return (m_left.ballShot() && m_right.ballShot());
     }
     
     /// <summary>
     /// Checks both wheels if the ball was shot
     /// </summary>
     /// <returns>True is was shot, false if not</returns>
     public boolean atRate(){
    	return (m_left.atRate()&&m_right.atRate()); 
     }

     /// <summary>
     /// Sets both wheels to load
     /// </summary>
     public void Load() {
         m_left.setLoad(true);
         m_right.setLoad(true);
     }

     /// <summary>
     /// Sets both wheels to spin
     /// </summary>
     public void Spin(){
    	 m_left.setLoad(false);
         m_right.setLoad(false);
    	 m_left.setSpin(true);
         m_right.setSpin(true);
     }

     /// <summary>
     /// Stops both wheels
     /// </summary>
     public void Stop()
     {
    	 m_left.setLoad(false);
         m_right.setLoad(false);
         m_left.setSpin(false);
         m_right.setSpin(false);
     }
}
