package org.usfirst.frc.team4488.robot.components;

import java.util.function.Function;
import org.usfirst.frc.team4488.robot.RobotMap;
import JavaRoboticsLib.Utility.Logger;
import JavaRoboticsLib.ControlSystems.*;
import JavaRoboticsLib.WPIExtensions.RampMotor;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;

public class Turret {

	private ShooterPosition m_position;
	private Timer m_oscillateTimer;
	private SimPID m_pid;
	private Preferences prefs;

	private double m_turretDoneRange;

	private SpeedController m_motor;
	private AnalogPotentiometer m_sensor;

	private Notifier m_periodic;

	private double m_aimingAngle;
	private double m_angleOffset;
	private double m_loadAngle;

	private boolean m_manual;
	private double m_manualPower;

	private final int m_sensorSamplesLength = 5;
	private double[] m_sensorSamples;
	private int m_sensorSamplesIndex;

	public Turret() {
		try {
			RampMotor temp = new RampMotor(Talon.class, RobotMap.TurretMotor);
			temp.setMaxAccel(0.1);
			temp.setMaxDecel(0.05);
			m_motor = temp;
			prefs = Preferences.getInstance();
			m_pid = new SimPID(prefs.getDouble("TurretP", 0), prefs.getDouble("TurretI", 0),
					prefs.getDouble("TurretD", 0), prefs.getDouble("TurretEps", 0));
			m_pid.setMaxOutput(.5);
			m_turretDoneRange = prefs.getDouble("TurretDoneRange", 0);
			m_pid.setDoneRange(m_turretDoneRange);
			m_angleOffset = prefs.getDouble("TurretAngleOffset", 0);
			m_loadAngle = prefs.getDouble("TurretLoadAngle", 0);

			// create and initialize sensorSamples for moving average
			m_sensorSamples = new double[m_sensorSamplesLength];
			for (int i = 0; i < m_sensorSamplesLength; i++) {
				m_sensorSamples[i] = 0;
			}
			m_sensorSamplesIndex = 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m_sensor = new AnalogPotentiometer(RobotMap.TurretPotentiometer, -360, m_angleOffset);

		m_motor.setInverted(true);
		Logger.addMessage("Turret Initialized", 1);

		m_oscillateTimer = new Timer();
		m_oscillateTimer.start();
		m_aimingAngle = 60;

		m_periodic = new Notifier(() -> {
			update();
		});
		m_periodic.startPeriodic(.010);
	}

	public ShooterPosition getPosition() {
		return m_position;
	}

	/*
	 * Sets the position of the shooter's turret based on the current
	 * ShooterState state being inputed.
	 */
	public void setPosition(ShooterPosition value) {
		SmartDashboard.putString("Turret Positon", value.toString());

		m_position = value;
		switch (m_position) {
		case Aiming:
			m_pid.setDesiredValue(m_aimingAngle);
			m_pid.setDoneRange(m_turretDoneRange);
			m_pid.setMinDoneCycles(5);
			break;
		case Load:
			m_pid.setDesiredValue(m_loadAngle + Math.sin(m_oscillateTimer.get() * Math.PI * 2) * 3);
			m_pid.setDoneRange(3);
			m_pid.setMinDoneCycles(1);
			break;
		case Stored:
			m_pid.setDesiredValue(0);
			break;
		}
	}

	public void setAimingAngle(double angle) {
		m_aimingAngle = angle;
	}

	/*
	 * Gets the value that the turret's potentiometer is reading and reports a
	 * running average.
	 */
	public double getAngle() {
		m_sensorSamples[m_sensorSamplesIndex] = m_sensor.get();
		m_sensorSamplesIndex = (m_sensorSamplesIndex + 1) % m_sensorSamplesLength;
		double m_tempSensorSum = 0;
		for (int i = 0; i < m_sensorSamplesLength; i++) {
			m_tempSensorSum += m_sensorSamples[i];
		}

		return m_tempSensorSum / m_sensorSamplesLength;
	}

	public boolean AtSetpoint() {
		return m_pid.isDone();
	}

	public double getSetPoint() {
		return m_pid.getDesiredVal();
	}

	public void setManual(boolean manual) {
		m_manual = manual;
	}

	public void setManualPower(double power) {
		m_manualPower = power;
	}

	public void update() {
		double power = 0;
		if (getAngle() > 120) { // upper limit protection
			power = 0;
		} else {
			if (m_manual) {
				power = m_manualPower;
			} else {
				power = m_pid.calcPID(getAngle());
			}
		}

		// limit downward speed
		if (power <= -0.2 && m_pid.getDesiredVal() == 0) {
			if (getAngle() > 20) {
				power = -0.5;
			} else {
				power = -0.2;
			}
		}

		m_motor.set(power);
		SmartDashboard.putBoolean("TurretManualMode", m_manual);
		SmartDashboard.putNumber("TurretPower", power);
		SmartDashboard.putNumber("TurretPot", getAngle());
		SmartDashboard.putNumber("TurretSetPoint", m_pid.getDesiredVal());
		SmartDashboard.putBoolean("TurretAtSetPoint", AtSetpoint());
	}
}
