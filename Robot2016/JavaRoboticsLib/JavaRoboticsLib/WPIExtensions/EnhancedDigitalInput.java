package JavaRoboticsLib.WPIExtensions;

import JavaRoboticsLib.FlowControl.EdgeTrigger;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Digital input with built-in edge detecting and inversion
 *
 */
public class EnhancedDigitalInput extends DigitalInput{
	private EdgeTrigger m_edge;
	private boolean m_inverted;
	
	/**
	 * inverts the read value of the DigitalInput
	 * @param value
	 */
	public void setInverted(boolean value){
		m_inverted = value;
	}
	
	public EnhancedDigitalInput(int channel){
		super(channel);
		setInverted(false);
		m_edge = new EdgeTrigger();
	}
	
	@Override
	public boolean get(){
		return super.get() ^ m_inverted;
	}
	
	public boolean getRising(){
		return m_edge.getRising(get());
	}
	
	public boolean getFalling(){
		return m_edge.getFalling(get());
	}
	
	public boolean getRisingUpdate(){
		return m_edge.getRisingUpdate(get());
	}
	
	public boolean getFallingUpdate(){
		return m_edge.getFallingUpdate(get());
	}
	
	public void Update(){
		m_edge.update(get());
	}
}
