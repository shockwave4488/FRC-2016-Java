package JavaRoboticsLib.WPIExtensions;

import edu.wpi.first.wpilibj.SpeedController;
import java.lang.reflect.Constructor;

/**
 * An encapsulated collection of {@link #SpeedController}s
 *
 */
public class SpeedControllerGroup implements SpeedController {
	
	private SpeedController[] m_controllers;
	
	/**
	 * Gets the raw {@link #SpeedController} array
	 * @return
	 */
	public SpeedController[] getData(){
		return m_controllers;
	}
	
	public SpeedControllerGroup(SpeedController[] controllers){
		m_controllers = controllers;
	}
	
	/**
	 * Automatically creates an array of {@link #SpeedController}s of type {@link #controllerType} at the ports listed
	 * @param controllerType
	 * @param ports
	 * @throws Exception
	 */
	public SpeedControllerGroup(Class<?> controllerType, int[] ports) throws Exception {
		Constructor<?> ctor = controllerType.getConstructor(int.class);
		m_controllers = new SpeedController[ports.length];
		
		for(int i = 0; i < ports.length; i++){
			m_controllers[i] = (SpeedController)ctor.newInstance( new Object[] {ports[i]} );
		}
	}
	
	@Override
	public void pidWrite(double arg0) {
		for(SpeedController s : m_controllers){
			s.pidWrite(arg0);
		}
	}

	@Override
	public void disable() {
		for(SpeedController s : m_controllers){
			s.disable();
		}
	}

	@Override
	public double get() {
		double toReturn = 0;
		for(SpeedController s : m_controllers){
			toReturn += s.get();
		}
		return toReturn / m_controllers.length;
	}

	@Override
	public void set(double arg0) {
		for(SpeedController s : m_controllers){
			s.set(arg0);
		}
	}

	@Override
	public void set(double arg0, byte arg1) {
		for(SpeedController s : m_controllers){
			s.set(arg0, arg1);
		}
	}

	@Override
	public void setInverted(boolean value){
		for(SpeedController s : m_controllers){
			s.setInverted(value);
		}
	}
	
	@Override
	public boolean getInverted(){
		if(m_controllers.length > 0)
			return m_controllers[0].getInverted();
		else
			return false;
	}

	@Override
	public void stopMotor() {
		for(SpeedController s : m_controllers){
			s.stopMotor();
		}		
	}
}
