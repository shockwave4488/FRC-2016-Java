package JavaRoboticsLib.Utility;

import edu.wpi.first.wpilibj.Timer;

/**
* Provides various mathematical and logical utility functions
*/
public class Util   
{
    /**
    * Converts degrees to radians
    * 
    *  @param degrees angle in degrees
    *  @return angle in radians
    */
    public static double toRadians(double degrees) {
        return degrees * (Math.PI / 180.0);
    }

    /**
    * Converts radians to degrees
    * 
    *  @param radians angle in radians
    *  @return angle in degrees
    */
    public static double toDegrees(double radians) {
        return radians * (180.0 / Math.PI);
    }

    /**
    * Limits the value to a low and high threshold
    * 
    *  @param value value to be limited
    *  @param low lower limit
    *  @param high upper limit
    *  @return value coerced to the nearest limit
    */
    public static double limit(double value, double low, double high) {
        if (value < low)
            return low;
         
        if (value > high)
            return high;
         
        return value;
    }

    /**
    * Waits for a specified time more accurately than Thread.Sleep()
    * 
    *  @param time Time in seconds to wait
    */
    public static void accurateWaitSeconds(double time) throws InterruptedException {
        Timer sw = new Timer();
        sw.start();
        int milliSeconds = ((int)((time * 1e3)));
        if (milliSeconds >= 20)
        {
            Thread.sleep(milliSeconds - 12);
        }
         
        while (sw.get() < time)
            ;
        sw.stop();
    }

    /**
    * Waits for a specified time more accurately than Thread.Sleep()
    * 
    *  @param time Time in milliseconds to wait
    */
    public static void accurateWaitMilliseconds(double time) throws InterruptedException {
        accurateWaitSeconds(time / 1000);
    }

    /**
    * Wraps degrees around -180 to 180
    * 
    *  @param degrees 
    *  @return
    */
    public static double wrapDegrees(double degrees) {
        degrees %= 360;
        return (degrees > 180 ? degrees - 360 : degrees);
    }

    /**
    * Wraos radians around -PI and PI
    * 
    *  @param radians 
    *  @return
    */
    public static double wrapRadians(double radians) {
        radians %= Math.PI * 2;
        return (radians > Math.PI ? radians - (Math.PI * 2) : radians);
    }

    /**
    * Wraps an error value around a maximum and minimum input.
    * 
    *  @param value 
    *  @param setpoint 
    *  @param min 
    *  @param max 
    *  @return
    */
    public static double wrapError(double value, double setpoint, double min, double max) {
        double error = setpoint - value;
        if (Math.abs(error) <= (max - min) / 2)
            return error;
         
        if (error < 0)
        {
            return (max - value) + (setpoint - min);
        }
        else
        {
            return (min - value) - (max - setpoint);
        } 
    }

}
