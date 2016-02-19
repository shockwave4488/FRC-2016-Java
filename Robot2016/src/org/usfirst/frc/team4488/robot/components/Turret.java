package org.usfirst.frc.team4488.robot.components;

import org.usfirst.frc.team4488.robot.RobotMap;
import JavaRoboticsLib.Utility.Logger;
import JavaRoboticsLib.ControlSystems.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends MotionControlledSystem{
	
	private double m_aimingAngle;	
	private ShooterPosition m_position;
	
	public Turret(){
		Motor = new Talon(RobotMap.TurretMotor);
		Sensor = new AnalogPotentiometer(RobotMap.TurretPotentiometer, -360, 269.3);
		SetpointTolerance = 1;
		lowLimit = 15.0;
		highLimit = 100.0;
		try {
			Controller = new SimplePID(SmartDashboard.getNumber("TurretP", 0), SmartDashboard.getNumber("TurretI", 0), SmartDashboard.getNumber("TurretD", 0), -0.5, 0.5);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Motor.setInverted(true);
		Logger.addMessage("Turret Initialized", 1);
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
            setSetPoint(m_aimingAngle); //replace 123 with distance as reported by camera
            break;
        case Load:
            setSetPoint(40);
            break;
        case Stored:
        	setSetPoint(0);
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
		((SimplePID)Controller).setGains(SmartDashboard.getNumber("TurretP", 0), SmartDashboard.getNumber("TurretI", 0), SmartDashboard.getNumber("TurretD", 0));
		if(getAngle() > 65)
			((SimplePID)Controller).setP(SmartDashboard.getNumber("TurretP", 0) / 2.0);
		super.Update();
	}
}
