package JavaRoboticsLib.WPIExtensions;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.Notifier;
import java.lang.Runnable;

/**
* A Compressor which will not run if the voltage is too low.
*/
public class ManagedCompressor  extends Compressor 
{
    private Notifier m_periodic;
    private boolean m_usingTimer;
    private final double m_period;
    private double m_voltageThreshold;
    private double m_voltageDeadband;
    
    /**
    * If the {@link #ManagedCompressor} is being updated automatically and periodically
    */
    public void useTimer(boolean value) {
        m_usingTimer = value;
        if (value)
            m_periodic.startPeriodic(m_period);
        else
            m_periodic.stop(); 
    }

    /**
    * Voltage to turn off the compressor at
    */
    public double getVoltageThreshold() {
        return m_voltageThreshold;
    }

    public void setVoltageThreshold(double value) {
        m_voltageThreshold = value;
    }

    /**
    * Deadband for the voltage on/off trigger
    */
    public double getVoltageDeadband() {
        return m_voltageDeadband;
    }

    public void setVoltageDeadband(double value) {
        m_voltageDeadband = value;
    }

    /**
    * Creates a new Managed Compressor, which will turn off when the voltage is below the specified level.
    * 
    *  @param voltageThreshold Voltage to turn off the compressor at
    */
    public ManagedCompressor(double voltageThreshold) {
        this(voltageThreshold, 0.1);
    }

    /**
    * Creates a new Managed Compressor, which will turn off when the voltage is below the specified level.
    * 
    *  @param voltageThreshold Voltage to turn off the compressor at
    *  @param Period time to wait between on and off values.
    */
    public ManagedCompressor(double voltageThreshold, double period) {
        m_period = period;
        setVoltageThreshold(voltageThreshold);
        Runnable runUpdate = new Runnable() {
        	@Override
			public void run() {update();}
        };
        m_periodic = new Notifier(runUpdate);
        useTimer(true);
    }

    /**
    * Manually Updates the compressor, turning it on or off if necessary.
    * Called automatically according to the period value if {@link #UseTimer} is enabled.
    */
    public void update() {
        if (ControllerPower.getInputVoltage() < getVoltageThreshold() - getVoltageDeadband())
        {
            stop();
        }
        else if (ControllerPower.getInputVoltage() > getVoltageThreshold() + getVoltageDeadband())
        {
            start();
        }
          
    }

}


