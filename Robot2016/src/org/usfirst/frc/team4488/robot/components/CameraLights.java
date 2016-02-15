package org.usfirst.frc.team4488.robot.components;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.Relay;

public class CameraLights {
	private Relay m_relay;
	private AnalogOutput m_analog;
	
	public CameraLights(){
		m_relay = new Relay(0);
		m_analog = new AnalogOutput(0);
	}

	public void setLights(Relay.Value value, double brightness){
		m_relay.set(value);
		m_analog.setVoltage((brightness *2) + 0.5 );
	}
}
