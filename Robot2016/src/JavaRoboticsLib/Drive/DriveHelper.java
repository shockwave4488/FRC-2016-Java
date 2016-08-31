package JavaRoboticsLib.Drive;

import JavaRoboticsLib.Drive.Interfaces.TankDrive;
import edu.wpi.first.wpilibj.DriverStation;


/**
 * Does all calculations for driving the robot split-arcade style. Contains a TankDrive object.
 * Does not handle shifting
 */
public class DriveHelper {

  private TankDrive drive;
  private double m_speedDeadZone = 0.02;
  private double m_turnDeadZone = 0.02;

  public DriveHelper(TankDrive drive, double speedDeadzone, double turnDeadzone) {
    this.drive = drive;
    m_speedDeadZone = speedDeadzone;
    m_turnDeadZone = turnDeadzone;
  }
  
  public TankDrive getDrive() {return drive;}

  public void Drive(double throttle, double turn) {
    if (DriverStation.getInstance().isAutonomous()) {
      return;
    }

    turn = handleDeadband(turn, m_turnDeadZone);
    throttle = handleDeadband(throttle, m_speedDeadZone);
    
    double leftPwm, rightPwm;
    double angularPower;
    double linearPower;

    linearPower = throttle;
    angularPower = Math.signum(turn) * (1 - Math.cos(turn*Math.PI/2));
    rightPwm = leftPwm = linearPower;
    leftPwm += angularPower;
    rightPwm -= angularPower;
    
    if (leftPwm > 1.0) {
    	leftPwm = 1.0;
    } else if (leftPwm < -1.0) {
    	leftPwm = -1.0;
    }
    if (rightPwm > 1.0) {
    	rightPwm = 1.0;
    } else if (rightPwm < -1.0) {
    	rightPwm = -1.0;
    }
    
    drive.setPowers(leftPwm, rightPwm);
  }

	private static double handleDeadband(double val, double deadband) {
	  if (Math.abs(val) > Math.abs(deadband)) {
		  if (val > 0) {
			  return (val-deadband)/(1-deadband);
		  } else {
			  return (val+deadband)/(1-deadband);
		  }
	  } else {
		  return 0.0;
	  }
	}
}