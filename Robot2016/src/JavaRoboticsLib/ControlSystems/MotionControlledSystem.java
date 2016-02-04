package JavaRoboticsLib.ControlSystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Notifier;

/**
* Extendible controller for a {@link #MotionController} Controlled mechanism.
* Defines No Constructors.
*/
public abstract class MotionControlledSystem   
{
    private double m_manualPower;
	
    /**
    * Notifier to update the {@link #MotionControlledSystem} periodically
    */
    protected Notifier periodic;
    
    /**
    * The {@link #MotionController} for the system to follow
    */
    protected MotionController Controller;
    
    /**
    * The {@link #PIDSource} for the {@link #MotionController} to react to
    */
    protected PIDSource Sensor;
    
    /**
    * The {@link #SpeedController} to be controlled by the {@link #MotionController}
    */
    protected SpeedController Motor;
    
    /**
    * Positional tolerance of the 
    *  {@link #SetPoint}
    * , used in the AtSetPoint property.
    */
    protected double SetpointTolerance;
    
    /**
    * Set the {@link #MotionControlledSystem} into manual mode of operation
    */
    private boolean m_manual;
    
    public boolean getManual() {
        return m_manual;
    }
    public void setManual(boolean value) {
        m_manual = value;
    }

    /**
    * The power to be used if {@link #Manual} is enabled
    */
    public void setManualPower(double value) {
        m_manualPower = value;
    }
    
    /**
     * Returns true if the {@link #Sensor} is at the setpoint within the specified tolerance
     */
    public boolean AtSetpoint() { 
    	return (Sensor.pidGet() < Controller.getSetPoint() + SetpointTolerance) && (Sensor.pidGet() > Controller.getSetPoint() - SetpointTolerance);
    	}

    public double getSetPoint(){
    	return Controller.getSetPoint();
    }
    public void setSetPoint(double value){
    	Controller.setSetPoint(value);
    }

    /**
     * Updates the system based on {@link #getSetPoint()}. If the system is in manual, sets the motor to zero
     */
    public void Update()
    {
        Motor.set(m_manual ? m_manualPower : Controller.get(Sensor.pidGet()));
    }


    public void Start(double period)
    {
        periodic.startPeriodic(period);
    }

    public void Stop()
    {
        periodic.stop();
    }

}
