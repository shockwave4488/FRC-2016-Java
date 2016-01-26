package org.usfirst.frc.team4488.robot;

import edu.wpi.first.wpilibj.Notifier;

public class ShooterWheels {
	 private ShooterWheel m_right;
     private ShooterWheel m_left;
     private Notifier Periodic;

     public double ShooterRPM() 
     {return (m_left.ShooterSpeed() + m_right.ShooterSpeed()) / 2;}
     
     public void SetShooterRPM(double RPM)
         {
             m_right.SetShooterSpeed(RPM);
             m_left.SetShooterSpeed(RPM);
         }
     

     public ShooterWheels()
     {
         ShooterWheel m_left = new ShooterWheel();
         ShooterWheel m_right = new ShooterWheel();
         Notifier Periodic = new Notifier(()-> { m_right.SpinWheel(); m_left.SpinWheel(); });
         Periodic.startPeriodic(.005);
     }

     public Boolean IsBallShot()
     {
         return (m_left.IsBallShot() && m_right.IsBallShot());
     }

     public void Load()
     {
         m_left.SetLoad(true);
         m_right.SetLoad(true);
     }

     public void Spin()
     {
         m_left.SetSpin(true);
         m_right.SetSpin(true);
     }

     public void StopWheels()
     {
         m_left.SetSpin(false);
         m_right.SetSpin(false);
     }

}
