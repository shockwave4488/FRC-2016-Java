package JavaRoboticsLib.WPIExtensions;

import edu.wpi.first.wpilibj.Encoder;
/**
* Provides an extension method for setting {@link #Encoder.DistancePerPulse}
*/
public class EncoderExtension   
{
    /**
    * Sets {@link #DistancePerPulse} to the proper value given by diameter of a wheel and counts per revolution
    * 
    *  @param wheelDiameter Diameter of the wheel. Units used here will determine units of {@link #GetRate}
    * 
    *  @param countPerRevolution Counts per revolution of the encoder. Usually 360 or 256.
    */
    public static void setDistancePerPulse(Encoder e, double wheelDiameter, int countPerRevolution) {
        e.setDistancePerPulse((Math.PI * wheelDiameter) / countPerRevolution);
    }

}


