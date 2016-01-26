package JavaRoboticsLib.Utility;

/**
* Single-Order derivative function.
*/
public class Derivative   
{
    private double m_xPrev;
    private DeltaTime m_dt;
    
    /**
    * The change in time for this particular Controller loop in seconds.
    * Set to negative to determine dt automatically.
    */
    public void setDt(double value) {
        m_dt.setValue(value);
    }

    /**
    * Creates a new Derivative object.
    * 
    *  @param initialCondition Initial value to set x
    */
    public Derivative(double initialCondition) {
        m_xPrev = initialCondition;
        m_dt = new DeltaTime();
    }

    /**
    * Creates a new derivative object with the initial value set to zero.
    */
    public Derivative() {
        this(0);
    }

    /**
    * Gets dx/dt
    * 
    *  @return dx/dt
    */
    public double get(double x) {
        double toReturn = (x - m_xPrev) / m_dt.getValue();
        //Handle division by zero errors, ignoring the input if there is no change in time.
        if (Double.isNaN(toReturn))
        {
            toReturn = 0;
        }
        else
        {
            m_xPrev = x;
        } 
        return toReturn;
    }

    /**
    * resets the derivative to the specified value
    * 
    *  @param value value to reset to
    */
    public void reInitialize(double value) {
        m_xPrev = value;
    }

    /**
    * resets the derivative to zero
    */
    public void reInitialize() {
        reInitialize(0);
    }

}


