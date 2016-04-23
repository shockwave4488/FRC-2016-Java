package org.usfirst.frc.team4488.robot.components;

import java.util.function.Function;
import org.usfirst.frc.team4488.robot.RobotMap;
import JavaRoboticsLib.Utility.Logger;
import JavaRoboticsLib.ControlSystems.*;
import JavaRoboticsLib.WPIExtensions.RampMotor;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends MotionControlledSystem{
	
	private double m_aimingAngle;	
	private ShooterPosition m_position;
	private Timer m_oscillateTimer;
	
	private class feedForwardPID implements MotionController{
		private SimplePID m_pid;
		private Function<Double, Double> ffFunction;
		
		public feedForwardPID(double p, double i, double d, Function<Double, Double> f, double minvalue, double maxvalue){
			try {
				m_pid = new SimplePID(p, i, d, minvalue, maxvalue);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ffFunction = f;
		}
		
		public void setFunction(Function<Double, Double> function){
			ffFunction = function;
		}
		
		@Override
		public double get(double input) {
			m_pid.setGains(SmartDashboard.getNumber("TurretP", 0.05), SmartDashboard.getNumber("TurretI", 0), SmartDashboard.getNumber("TurretD", 0));
			return m_pid.get(input) + ffFunction.apply(input);
		}

		@Override
		public double getSetPoint() {
			return m_pid.getSetPoint();
		}

		@Override
		public void setSetPoint(double value) {
			m_pid.setSetPoint(value);			
		}
		
	}
	
	public Turret(){
		try {
			RampMotor temp = new RampMotor(Talon.class, RobotMap.TurretMotor);
			temp.setMaxAccel(0.1);
			temp.setMaxDecel(0.05);
			super.Motor = temp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sensor = new AnalogPotentiometer(RobotMap.TurretPotentiometer, -360, SmartDashboard.getNumber("TurretOffset", 263.9));
		SetpointTolerance = 1;
		lowLimit = 10.0;
		highLimit = 95.0;
		try {
			Controller = new feedForwardPID(
					SmartDashboard.getNumber("TurretP", 0), 
					SmartDashboard.getNumber("TurretI", 0), 
					SmartDashboard.getNumber("TurretD", 0), 
					this::feedForward, 
					-0.5, 0.5
					);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Motor.setInverted(true);
		Logger.addMessage("Turret Initialized", 1);
		
		m_oscillateTimer = new Timer();
		m_oscillateTimer.start();
		m_aimingAngle = 60;
				
		super.periodic = new Notifier(this::Update);
		super.Start(0.01);
	}
	
	public ShooterPosition getPosition(){
		return m_position;
	}
	/*
	 * Sets the position of the shooter's turret based on the current ShooterState state being inputed.
	 */ 
	public void setPosition(ShooterPosition value){
		SmartDashboard.putString("Turret Positon", value.toString());
		m_position = value;
		switch(value){
		case Aiming:
            setSetPoint(m_aimingAngle + SmartDashboard.getNumber("Angle Offset", 0)); 
            break;
        case Load:
            setSetPoint(SmartDashboard.getNumber("Turret Load Angle", 35) + Math.sin(m_oscillateTimer.get() * Math.PI * 2) * 3);
            break;
        case Stored:
        	setSetPoint(-2);
            break;
		}
	}
	
	public void setAimingAngle(double angle){
		m_aimingAngle = angle;
	}
	
	/*
	 * Gets the value that the turret's potentiometer is reading.
	 */
	public double getAngle(){
		return Sensor.pidGet();
	} 
	
	@Override
	public void Update(){
		//((SimplePID)Controller).setGains(SmartDashboard.getNumber("TurretP", 0), SmartDashboard.getNumber("TurretI", 0), SmartDashboard.getNumber("TurretD", 0));
		//if(getAngle() > 65)
		//	((SimplePID)Controller).setP(SmartDashboard.getNumber("TurretP", 0) / 2.0);
		if(getAngle() > 140){ //something went REALLY wrong
			super.setManual(true);
			super.setManualPower(0);
			return;
		}
		else{
			super.setManual(false);
		}
		super.Update();
		SmartDashboard.putNumber("TurretPot", getAngle());
	}
	
	private double feedForward(double setpoint){
		//return 0.01; None
		//return getAngle() * 0.00075; Surgical Tubing
		//Practice return getAngle() > 50 ? 0.01 : 0; //Delrin Block
		return getAngle() > 50 ? 0.01 : 0;
		//return 0;
	}
	/*
	@Override
	public boolean AtSetpoint(){
		return base//(Sensor.pidGet() < Controller.getSetPoint()- 0.25 + SetpointTolerance) && (Sensor.pidGet() > Controller.getSetPoint() - 0.25 - SetpointTolerance);
	}*/
}
