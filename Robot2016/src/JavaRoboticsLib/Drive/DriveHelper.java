package JavaRoboticsLib.Drive;

import JavaRoboticsLib.Drive.Interfaces.TankDrive;
import edu.wpi.first.wpilibj.DriverStation;

//Shamelessly copy-pasted from Team 254's Robot Code

/**
 * Does all calculations for driving the robot split-arcade style. Contains a TankDrive object.
 * Does not handle shifting
 */
public class DriveHelper {

  private TankDrive drive;
  private double m_oldTurn, m_quickStopAccumulator;
  private double m_highNonLinearity, m_lowNonLinearity;
  private double m_highSensitivity, m_lowSensitivity;
  private double m_speedDeadZone = 0.02;
  private double m_turnDeadZone = 0.02;

  public DriveHelper(TankDrive drive, double speedDeadzone, double turnDeadzone, double highNonLinearity, double lowNonLinearity, double highSensitivity, double lowSensitivity) {
    this.drive = drive;
    m_highNonLinearity = highNonLinearity;
    m_lowNonLinearity = lowNonLinearity;
    m_speedDeadZone = speedDeadzone;
    m_turnDeadZone = turnDeadzone;
    m_highSensitivity = highSensitivity;
    m_lowSensitivity = lowSensitivity;
  }
  
  public TankDrive getDrive() {return drive;}

  public void Drive(double throttle, double turn, boolean isQuickTurn, boolean isHighGear) {
    if (DriverStation.getInstance().isAutonomous()) {
      return;
    }

    double wheelNonLinearity;

    turn = handleDeadband(turn, m_turnDeadZone);
    throttle = handleDeadband(throttle, m_speedDeadZone);
    isQuickTurn |= throttle == 0;

    //double negInertia = turn - m_oldTurn;
    //m_oldTurn = turn;

    if (isHighGear) {
      wheelNonLinearity = m_highNonLinearity;
      // Apply a sin function that's scaled to make it feel better.
      turn = Math.sin(Math.PI / 2.0 * wheelNonLinearity * turn)
              / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
      turn = Math.sin(Math.PI / 2.0 * wheelNonLinearity * turn)
              / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
    } else {
      wheelNonLinearity = m_lowNonLinearity;
      // Apply a sin function that's scaled to make it feel better.
      turn = Math.sin(Math.PI / 2.0 * wheelNonLinearity * turn)
              / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
      turn = Math.sin(Math.PI / 2.0 * wheelNonLinearity * turn)
              / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
      turn = Math.sin(Math.PI / 2.0 * wheelNonLinearity * turn)
              / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
    }

    double leftPwm, rightPwm, overPower;
    double sensitivity;

    double angularPower;
    double linearPower;

    // Negative inertia!
    // currently not enabled
    //double negInertiaAccumulator = 0.0;
    //double negInertiaScalar;
    if (isHighGear) {
      //negInertiaScalar = 5.0;
      sensitivity = m_highSensitivity;
    } else {
    	/*
      if (wheel * negInertia > 0) {
        negInertiaScalar = 2.5;
      } else {
        if (Math.abs(wheel) > 0.65) {
          negInertiaScalar = 5.0;
        } else {
          negInertiaScalar = 3.0;
        }
      }
      */
      sensitivity = m_lowSensitivity;
    }
    //double negInertiaPower = negInertia * negInertiaScalar;
    //negInertiaAccumulator += negInertiaPower;
    /*
    wheel = wheel + negInertiaAccumulator;
    if (negInertiaAccumulator > 1) {
      negInertiaAccumulator -= 1;
    } else if (negInertiaAccumulator < -1) {
      negInertiaAccumulator += 1;
    } else {
      negInertiaAccumulator = 0;
    }
    */
    linearPower = throttle;

    // Quickturn!
    if (isQuickTurn) {
      if (Math.abs(linearPower) < 0.2) {
        double alpha = 0.1;
        m_quickStopAccumulator = (1 - alpha) * m_quickStopAccumulator + alpha
                * ((Math.abs(turn) < 1) ? turn : Math.signum(turn)) * 5;
      }
      overPower = 1.0;
      if (isHighGear) {
        sensitivity = 1.0;
      } else {
        sensitivity = 1.0;
      }
      angularPower = turn;
    } else {
      overPower = 0.0;
      angularPower = Math.abs(throttle) * turn * sensitivity - m_quickStopAccumulator;
      if (m_quickStopAccumulator > 1) {
        m_quickStopAccumulator -= 1;
      } else if (m_quickStopAccumulator < -1) {
        m_quickStopAccumulator += 1;
      } else {
        m_quickStopAccumulator = 0.0;
      }
    }

    rightPwm = leftPwm = linearPower;
    leftPwm += angularPower;
    rightPwm -= angularPower;

    if (leftPwm > 1.0) {
      rightPwm -= overPower * (leftPwm - 1.0);
      leftPwm = 1.0;
    } else if (rightPwm > 1.0) {
      leftPwm -= overPower * (rightPwm - 1.0);
      rightPwm = 1.0;
    } else if (leftPwm < -1.0) {
      rightPwm += overPower * (-1.0 - leftPwm);
      leftPwm = -1.0;
    } else if (rightPwm < -1.0) {
      leftPwm += overPower * (-1.0 - rightPwm);
      rightPwm = -1.0;
    }
    drive.setPowers(leftPwm, rightPwm);

  }

  private static double handleDeadband(double val, double deadband) {
    return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
  }
}