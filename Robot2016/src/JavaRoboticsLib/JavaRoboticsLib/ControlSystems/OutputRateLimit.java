package JavaRoboticsLib.ControlSystems;

import JavaRoboticsLib.Utility.DeltaTime;

/**
* Limits the rate of change of an output
*/
public class OutputRateLimit   
{
    private DeltaTime m_dt = new DeltaTime();
    private double m_feedback;
    private double m_unitsPerSecond;
    
    /**
    * Delta-Time value - set negative to determine dt automatically
    */
    public void setDt(double value) throws Exception {
        m_dt.setValue(value);
    }

    /**
    * Maximum units per second of change
    */
    public double getUnitsPerSecond() {
        return m_unitsPerSecond;
    }

    public void setUnitsPerSecond(double value) {
        m_unitsPerSecond = value;
    }

    /**
    * How many seconds it should take for one unit of change
    */
    public double getSecondsPerUnit() throws Exception {
        return 1.0 / getUnitsPerSecond();
    }

    public void setSecondsPerUnit(double value) throws Exception {
        setUnitsPerSecond(1.0 / value);
    }

    /**
    * new OutputRateLimit
    * 
    *  @param unitsPerSecond
    */
    public OutputRateLimit(double unitsPerSecond) throws Exception {
        m_dt = new DeltaTime();
        setUnitsPerSecond(unitsPerSecond);
        m_feedback = 0;
    }

    /**
    * Gets the value and updates the internal state
    * 
    *  @param input value to be rate limited
    *  @return
    */
    public double get(double input) throws Exception {
        double units = Math.abs(getUnitsPerSecond() * m_dt.getValue());
        double delta = input - m_feedback;
        double max = units * Math.signum(delta);
        double toReturn = units >= Math.abs(delta) ? input : max + m_feedback;
        m_feedback = toReturn;
        return toReturn;
    }

    /**
    * Force the internal state to a value.
    * 
    *  @param value
    */
    public void force(double value) throws Exception {
        m_feedback = value;
    }

}


