package JavaRoboticsLib.WPIExtensions;

import edu.wpi.first.wpilibj.AnalogInput;

/**
* An Analog Ultrasonic sensor class complete with built-in scaling
*/
public class AnalogueUltrasonic extends AnalogInput 
{
    private double scalingFactor;
    /**
    * Creates a new Analog Ultrasonic sensor on the specified port
    * 
    *  @param port port to create the sensor with
    *  @param scaleFactor scaling factor to apply to the input voltage
    */
    public AnalogueUltrasonic(int port, double scaleFactor) {
        super(port);
        scalingFactor = scaleFactor;
    }

    /**
    * Gets the value of the ultrasonic sensor modified by the scaling factor
    */
    public double get() {
        return getAverageVoltage() / scalingFactor;
    }

    /**
    * Gets the value of the ultrasonic sensor modified by the scaling factor
    * 
    *  @return
    */
    @Override
	public double pidGet() {
        return get();
    }

}


