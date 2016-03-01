package org.usfirst.frc.team4488.robot.systems;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team4488.robot.systems.LEDState;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class LEDController {
	private SerialPort m_arduinoPort;
	private static final HashMap<LEDState, Byte> byteMapping = new HashMap<LEDState, Byte>();
	
	static {
		byteMapping.put(LEDState.Null, (byte) 0);
		byteMapping.put(LEDState.Shoot, (byte) 1);
		byteMapping.put(LEDState.Feed, (byte) 2);
		byteMapping.put(LEDState.Charge, (byte) 3);		
	}
	
	public LEDController(){
		//m_arduinoPort = new SerialPort(1, Port.kOnboard);
		setLEDs(LEDState.Null);
	}
	
	public void setLEDs(LEDState value){
		try{
		m_arduinoPort.write(new byte[] {byteMapping.get(value)}, 1);
		}
		catch(Exception e){}
	}
}
