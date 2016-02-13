package JavaRoboticsLib.WPIExtensions;

import java.lang.reflect.Constructor;
import edu.wpi.first.wpilibj.SpeedController;

/**
* A subclass of {@link #SpeedController} intended to allow controlled change in power, for safety or voltage regulation.
*/
public class RampMotor implements SpeedController 
{
    private SpeedController m_controller;
    private double m_power;
    private double m_maxAccel, m_maxDecel;
    /**
    * The max amount of change in power the motor can accelerate with
    */
    public double getMaxAccel() {
        return m_maxAccel;
    }

    public void setMaxAccel(double value) throws Exception{
        if (value < 0)
            throw new Exception("MaxAccel must be greater than zero. Value given: " + value);
         
        m_maxAccel = value;
    }

    /**
    * The max amount of change in power the motor can decellerate with
    */
    public double getMaxDecel() {
        return m_maxDecel;
    }

    public void setMaxDecel(double value) throws Exception {
        if (value < 0)
            throw new Exception("MaxDecel must be greater than zero. Value given: " + value);
         
        m_maxDecel = value;
    }

    /**
    * Sets both MaxAccel and MaxDecel
    */
    public void setMaxChange(double value) throws Exception {
        setMaxAccel(value);
        setMaxDecel(value);
    }

    /**
    * Opens a new RampingMotor of a specific motor type
    * 
    *  @param controllerType Type of motor controller (Jaguar, Victor, Spark, CAN Talon, etc.)
    *  @param port The PWM channel that the motor is attached to. 0-9 are on-board, 10-19 are on the MXP port
    */
    public RampMotor(Class<?> controllerType, int port) throws Exception {
        Constructor<?> ctor = controllerType.getConstructor(int.class);
        m_controller = (SpeedController)ctor.newInstance(new Object[]{port});
        setMaxChange(1);
        m_power = 0;
    }

    /**
    * Wraps an existing 
    *  {@link #ISpeedController}
    *  with 
    *  {@link #RampMotor{T}}
    * 
    *  @param motorController existing Motor Controller
    */
    public RampMotor(SpeedController motorController) throws Exception {
        m_controller = motorController;
        setMaxChange(1);
        m_power = 0;
    }

    /**
    * Sets the value to the motor, within the change limitations
    * 
    *  @param value value to set to or approach
    */
    @Override
	public void set(double value) {
        //The motor is DECELLERATING if |value| < |power| OR value and power are not both positive or both negative
        //Likewise, the motor is ACCELERATING if |value| > |power| AND value and power are both negative or both positive
        //If power is zero, the motor is accelerating.
        //if the motor is ACCELERATING, use MaxAccel. If the robot is DECELLERATING, use MaxDecel.
        boolean accel = (Math.signum(value) == Math.signum(m_power) && Math.abs(value) > Math.abs(m_power)) || Math.abs(m_power) < getMaxAccel();
        double delta = accel ? getMaxAccel() : getMaxDecel();
        if (value > m_power + delta)
            //If the motor wants to change power faster than it is allowed, change it by the max power change allowed
            m_power += delta;
        else if (value < m_power - delta)
            m_power -= delta;
        else
            //If the motor wants to go to a power within the change limitations, set the power to the value.
            m_power = value;  
        m_controller.set(m_power);
    }

    /**
    * Set the power of a motor regardless of the ramping limitations
    * 
    *  @param value Power to set to
    */
    public void forcePower(double value){
        m_power = value;
        m_controller.set(value);
    }

    /**
    * Returns the last value set to this controller
    * 
    *  @return
    */
    @Override
	public double get() {
        return m_controller.get();
    }

    /**
    * Sets the output value for this controller
    * 
    *  @param value 
    *  @param syncGroup
    */
    @Override
	public void set(double value, byte syncGroup) {
        m_controller.set(value, syncGroup);
    }

    /**
    * Disable the speed controller
    */
    @Override
	public void disable() {
        m_controller.disable();
    }

    /**
    * Set the output to the value calculated by the PIDController
    * 
    *  @param value
    */
    @Override
	public void pidWrite(double value) {
        set(value);
    }
    
    @Override
	public void setInverted(boolean value){
    	m_controller.setInverted(value);
    }
    
    @Override
	public boolean getInverted(){
    	return m_controller.getInverted();
    }

	@Override
	public void stopMotor() {
		forcePower(0);		
	}
}


