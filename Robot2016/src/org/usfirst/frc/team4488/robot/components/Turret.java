package org.usfirst.frc.team4488.robot.components;

import org.usfirst.frc.team4488.robot.RobotMap;
import JavaRoboticsLib.Utility.Logger;
import JavaRoboticsLib.ControlSystems.*;
import edu.wpi.first.wpilibj.*;

public class Turret extends MotionControlledSystem{
	
	private final double G = 32.174;
	
	private ShooterPosition m_position;
	
	public Turret(){
		Motor = new Talon(RobotMap.TurretMotor);
		Sensor = new AnalogPotentiometer(RobotMap.TurretPontentiometer, 360);
		SetpointTolerance = 1;
		
		try {
			Controller = new SimplePID(0, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logger.addMessage("Turret Initialized", 1);
	}
	
	public ShooterPosition getPosition(){
		return m_position;
	}		
	/*
	 * Sets the position of the shooter's turret based on the current ShooterState state being inputed.
	 */
	public void setPosition(ShooterPosition value){
		m_position = value;
		switch(value){
		case Aiming:
            setSetPoint(Math.atan(123 * G)); //replace 123 with distance as reported by camera
            break;
        case Load:
            setSetPoint(Preferences.getInstance().getDouble("ShooterLow", 0.0));
            break;
        case Stored:
        	setSetPoint(Preferences.getInstance().getDouble("ShooterStored", 0.0));
            break;
		}
	}
	
	/*
	 * Gets the value that the turret's potentiometer is reading.
	 */
	public double getAngle(){
		return Sensor.pidGet();
	}
}
