
package JavaRoboticsLib.Utility;

/**
* Numeric Time-based Integration
*/
public class Integral   
{
    private double m_value;
    private DeltaTime m_dt;
    
    /**
    * The change in time for this particular Controller loop in seconds.
    * Set to negative to determine dt automatically.
    */
    public void setDt(double value) {
        m_dt.setValue(value);
    }

    /**
    * New Integral.
    */
    public Integral() {
        m_value = 0;
        m_dt = new DeltaTime();
    }

    /**
    * Get the value of the integral
    * 
    *  @param x Value to add to the integral
    *  @return Integral(x)dt
    */
    public double get(double x) {
        m_value += (x * m_dt.getValue());
        return m_value;
    }

    /**
    * Gets the value of the integral
    * 
    *  @return Integral(x)dt
    */
    public double get() {
        return m_value;
    }

    /**
    * Reinitialize the integral to the specified value.
    * 
    *  @param value value to set the integral to
    */
    public void reInitialize(double value) {
        m_value = value;
    }

    /**
    * Reinitialize the integral to zero.
    */
    public void reInitialize() {
        m_value = 0;
    }

}


