package JavaRoboticsLib.ControlSystems;


/**
* An interface defining different control schemes that can be used for {@link #MotionControlledSystem}
*/
public interface MotionController   
{
    /**
    * Gets the value of the controller and updates any internal state.
    * 
    *  @param input value of the sensor
    *  @return value to be sent to the motor
    */
    double get(double input);

    /**
    * The setpoint for the controller.
    */
    double getSetPoint();

    /**
     * The setpoint for the controller
     * @param value
     */
    void setSetPoint(double value);

}


