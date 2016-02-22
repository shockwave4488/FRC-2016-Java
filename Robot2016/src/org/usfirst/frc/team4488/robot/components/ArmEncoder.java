package org.usfirst.frc.team4488.robot.components;

import edu.wpi.first.wpilibj.Encoder;

public class ArmEncoder extends Encoder {
	private double m_offset;
	
	public ArmEncoder(int aChannel, int bChannel){
		super(aChannel, bChannel);
	}
	
	public void reset(double value){
		m_offset = value;
		super.reset();
	}
	
	@Override
	public double pidGet(){
		return m_offset - super.pidGet();
	}
}
