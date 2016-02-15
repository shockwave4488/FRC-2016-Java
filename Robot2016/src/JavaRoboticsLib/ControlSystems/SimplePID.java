package JavaRoboticsLib.ControlSystems;

import JavaRoboticsLib.Utility.Derivative;
import JavaRoboticsLib.Utility.Integral;
import JavaRoboticsLib.Utility.Util;

/**
* Simpler form of PID logic
*/
public class SimplePID implements MotionController
{
    private double m_kP, m_kI, m_kD;
    private Derivative m_d;
    private double m_lastError;
    private Integral m_i;
    private double m_max;   
    private double m_min;
    private double m_setPoint;
    private boolean m_continuous;
    private double m_maxInput;
    private double m_minInput;
    
    /**
    * The change in time for this particular PID loop in seconds.
    * Set to negative to determine dt automatically.
    */
    public void setDt(double value) {
        m_d.setDt(value);
        m_i.setDt(value);
    }

    /**
    * Maximum value the PID Controller can return
    */
    public double getMax() {
        return m_max;
    }
    public void setMax(double value) {
        m_max = value;
    }

    /**
    * Minimum value the PID Controller can return
    */
    public double getMin() {
        return m_min;
    }
    public void setMin(double value) {
        m_min = value;
    }

    /**
    * Current setpoint the PID Controller is reacting to
    */
    @Override
	public double getSetPoint() {
        return m_setPoint;
    }
    @Override
	public void setSetPoint(double value) {
        m_setPoint = value;
        resetIntegral();
    }

    /**
    * Defines if the system is continuous
    */
    public boolean getContinuous() {
        return m_continuous;
    }

    public void setContinuous(boolean value) {
        m_continuous = value;
    }

    /**
    * Maximum possible input
    */
    public double getMaxInput() {
        return m_maxInput;
    }

    public void setMaxInput(double value) {
        m_maxInput = value;
    }

    /**
    * Minimum possible input
    */
    public double getMinInput() {
        return m_minInput;
    }

    public void setMinInput(double value) {
        m_minInput = value;
    }
    
    public void setGains(double p, double i, double d){
    	m_kP = p;
    	m_kI = i;
    	m_kD = d;
    }

    /**
    * Creates a new instance of the SimplePID class
    * 
    *  @param p Proportional constant
    *  @param i Integral Constant
    *  @param d Derivative Constant
    *  @param min Minimum allowed output of the PID loop
    *  @param max Maximum allowed output of the PID loop
    */
    public SimplePID(double p, double i, double d, double min, double max) throws Exception {
        if (max < min)
            throw new Exception("Invalid Arguments: {max} Is less than {min}");
         
        m_kP = p;
        m_kI = i;
        m_kD = d;
        setMax(max);
        setMin(min);
        m_d = new Derivative();
        m_i = new Integral();
        setSetPoint(0);
        setContinuous(false);
    }

    /**
    * Creates a new SimplePID object
    * 
    *  @param p Proportional Constant
    *  @param i Integral Constant
    *  @param d Derivative Constant
    */
    public SimplePID(double p, double i, double d) throws Exception {
        this(p, i, d, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    /**
    * Gets the value of the PID loop, using the point given as the input for the proportional value
    * 
    *  @param currentPoint current point of the system as read by a sensor
    *  @return value calculated by the PID loop
    */
    @Override
	public double get(double currentPoint) {
        double error = (getSetPoint() - currentPoint);
        if (getContinuous())
            error = Util.wrapError(currentPoint, getSetPoint(), getMinInput(), getMaxInput());
         
        
        double p = m_kP == 0 ? 0 : m_kP * error;
        double I = m_kI == 0 ? 0 : m_kI * m_i.get(error);
        double d = m_kD == 0 ? 0 : m_kD * (error - m_lastError);
        m_lastError = error;
        return Util.limit(p + I + d, getMin(), getMax());
    }

    /**
    * Reset the integral value.
    */
    public void resetIntegral() {
        m_i.reInitialize();
    }

    public void setP(double value){
    	m_kP = value;
    }
}


