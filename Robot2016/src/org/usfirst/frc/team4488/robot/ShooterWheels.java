package org.usfirst.frc.team4488.robot;

import edu.wpi.first.wpilibj.Notifier;

public class ShooterWheels {
	 private ShooterWheel m_right;
     private ShooterWheel m_left;
     private Notifier Periodic;

     /// <summary>
     /// Gets and sets RPM for both shooter wheels
     /// </summary>
     public double ShooterRPM() 
     {return (m_left.ShooterSpeed() + m_right.ShooterSpeed()) / 2;}
     
     public void SetShooterRPM(double RPM)
         {
             m_right.SetShooterSpeed(RPM);
             m_left.SetShooterSpeed(RPM);
         }
     
     /// <summary>
     /// Constructor for ShooterWheels, with updating Notifier
     /// </summary>
     public ShooterWheels()
     {
         ShooterWheel m_left = new ShooterWheel(RobotMap.ShooterWELeft_Channel, RobotMap.ShooterLeftCounter);
         ShooterWheel m_right = new ShooterWheel(RobotMap.ShooterWERight_Channel, RobotMap.ShooterRightCounter);
         Notifier Periodic = new Notifier(()-> { m_right.SpinWheel(); m_left.SpinWheel(); });
         Periodic.startPeriodic(.005);
     }

     /// <summary>
     /// Checks if both wheels are at the rate to shoot
     /// </summary>
     /// <returns>True if ready to shoot, false otherwise</returns>
     public Boolean IsBallShot()
     {
         return (m_left.IsBallShot() && m_right.IsBallShot());
     }
     
     /// <summary>
     /// Checks both wheels if the ball was shot
     /// </summary>
     /// <returns>True is was shot, false if not</returns>
     public Boolean IsReadyToShoot()
     {
    	return (m_left.atRate()&&m_right.atRate()); 
     }

     /// <summary>
     /// Sets both wheels to load
     /// </summary>
     public void Load()
     {
         m_left.SetLoad(true);
         m_right.SetLoad(true);
     }

     /// <summary>
     /// Sets both wheels to spin
     /// </summary>
     public void Spin()
     {
         m_left.SetSpin(true);
         m_right.SetSpin(true);
     }

     /// <summary>
     /// Stops both wheels
     /// </summary>
     public void StopWheels()
     {
         m_left.SetSpin(false);
         m_right.SetSpin(false);
     }

}
