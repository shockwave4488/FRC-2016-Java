
package org.usfirst.frc.team4488.robot.components;

import org.usfirst.frc.team4488.robot.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import JavaRoboticsLib.Utility.Logger;
import JavaRoboticsLib.ControlSystems.MotionControlledSystem;
import JavaRoboticsLib.ControlSystems.SimPID;
import JavaRoboticsLib.FlowControl.EdgeTrigger;

public class Arm {
	private SimPID m_pid;
	private Talon m_armMotor;
	private DigitalInput m_backLimit;
	private ArmPosition m_position;

	private boolean m_limitFound;
	private double lowLimit;

	private Timer m_findLimitWatchdog;
	private ArmEncoder m_encoder;
	private Preferences prefs;

	private Notifier m_periodic;

	private boolean m_manual;
	private double m_manualPower;
	private double m_semiManualPosition;

	private boolean armFailure;

	public Arm() {
		prefs = Preferences.getInstance();
		armFailure = false;
		m_armMotor = new Talon(RobotMap.ArmMotor);
		m_encoder = new ArmEncoder(RobotMap.ArmEncoderB, RobotMap.ArmEncoderA);
		double reduction = 2;
		m_encoder.setDistancePerPulse(1.0 / (1024 * reduction / 360));
		m_encoder.setReverseDirection(false);
		m_backLimit = new DigitalInput(RobotMap.ArmBackLimit);
		m_findLimitWatchdog = new Timer();
		m_findLimitWatchdog.start();
		m_limitFound = false;
		m_manual = false;
		lowLimit = -20;
		try {
			m_pid = new SimPID(prefs.getDouble("ArmP", 0), prefs.getDouble("ArmI", 0), prefs.getDouble("ArmD", 0),
					prefs.getDouble("ArmEps", 0));
			m_pid.setDoneRange(5);
			m_pid.setMaxOutput(.5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setPosition(ArmPosition.High);

		m_periodic = new Notifier(() -> {
			update();
		});
		m_periodic.startPeriodic(.020);
		Logger.addMessage("Arm Initialized", 1);
	}

	/**
	 * This moves the arm to the position requested as the input of the program.
	 */
	public void setPosition(ArmPosition value) {
		SmartDashboard.putString("Arm Position", value.toString());
		m_position = value;
		switch (value) {
		case High:
			m_pid.setDesiredValue(prefs.getDouble("ArmOffset", 120));
			break;

		case Load:
			m_pid.setDesiredValue(prefs.getDouble("ArmOffset", 120));
			break;

		case SemiManual:
			m_pid.setDesiredValue(m_semiManualPosition);
			break;

		case Intake:
			m_pid.setDesiredValue(22);
			break;

		case Shoot:
			m_pid.setDesiredValue(70);
			break;

		case BatterShoot:
			m_pid.setDesiredValue(33);
			break;

		case Low:
			m_pid.setDesiredValue(0);
			break;

		}
	}

	public void update() {
		SmartDashboard.putBoolean("ArmLimit", atBackLimit());
		SmartDashboard.putBoolean("Arm Limit Found", m_limitFound);
		if (!m_limitFound && !armFailure) {
			if (!DriverStation.getInstance().isEnabled()) {
				m_findLimitWatchdog.reset();
			}
			if (!atBackLimit() && m_findLimitWatchdog.get() < 1.5) {
				m_manual = true;
				m_manualPower = 0.25;
			} else if (!atBackLimit() && m_findLimitWatchdog.get() > 1.5) {
				armFailure = true;
				m_manual = true;
				m_manualPower = 0;
				Logger.addMessage("Arm failure: No limit found");
			} else if (atBackLimit()) {
				m_manual = false;
				m_limitFound = true;
				Logger.addMessage("Arm found limit");
			}
		}

		if (atBackLimit())
			resetEncoder(prefs.getDouble("ArmOffset", 120));

		double power = m_manual ? m_manualPower : m_pid.calcPID(m_encoder.pidGet());
		double highLimit = prefs.getDouble("ArmOffset", 120);

		if ((m_encoder.pidGet() < lowLimit && power < 0) || (m_encoder.pidGet() > highLimit && power > 0)) {
			power = 0;
		}
		
		m_armMotor.set(power);
	}

	public void resetArm() {
		Logger.addMessage("Arm Reset");
		m_limitFound = false;
		m_findLimitWatchdog.reset();
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

	/**
	 * Returns the current position of the arm
	 */
	public ArmPosition getPosition() {
		return m_position;
	}

	public double armAngle() {
		return m_encoder.pidGet();
	}

	public void setSemiManualPosition(double value) {
		m_semiManualPosition = value;
	}

	public void resetEncoder(double offset) {
		m_encoder.reset(offset);
	}

	public boolean atBackLimit() {
		return !m_backLimit.get();
	}

	public boolean getLimitFound() {
		return m_limitFound;
	}
	
	public void setDoneRange(double range) {
		m_pid.setDoneRange(range);
	}
	
	public double getDoneRange() {
		return m_pid.getDoneRangeVal();
	}

	public void setMinDoneCycles(int cycles) {
		m_pid.setMinDoneCycles(cycles);
	}

	public int getMinDoneCycles() {
		return m_pid.getMinDoneCycles();
	}
}