
package JavaRoboticsLib.Utility;

import edu.wpi.first.wpilibj.Timer;

/**
* Measures the delta-time value between Gets
*/
public class DeltaTime   
{
    private boolean m_manualDt;
    private double m_dt;
    private Timer m_timer;
    
    /**
    * new DeltaTime object
    */
    public DeltaTime() {
        m_manualDt = false;
        m_dt = 0;
        m_timer = new Timer();
        m_timer.start();
    }

    /**
    * Get or manually set the delta time value.
    * Set to any positive value to manually keep a value.
    * Set to -1 to determine dt automatically.
    */
    public double getValue()  {
        if (!m_manualDt)
        {
            m_dt = (double)m_timer.get();
            m_timer.reset();
        }
         
        return m_dt;
    }

    public void setValue(double value) {
        if (value < 0)
        {
            m_manualDt = false;
        }
        else
        {
            m_manualDt = true;
            m_dt = value;
        } 
    }

}


