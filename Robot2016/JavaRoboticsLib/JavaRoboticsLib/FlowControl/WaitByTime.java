
package JavaRoboticsLib.FlowControl;

import edu.wpi.first.wpilibj.Timer;


/**
* Will return true after an amount of time has passed
*/
public class WaitByTime   
{
    private Timer m_timer;
    private int m_waitTimeMilliseconds;
    
    /**
    * Time until the timer is finished in seconds. Does not reset the timer.
    */
    public double getWaitTimeSeconds() {
        return getWaitTimeMilliseconds() / 1000.0;
    }

    public void setWaitTimeSeconds(double value) {
        setWaitTimeMilliseconds(((int)((value * 1000))));
    }

    /**
    * Time until the timer is finished in milliseconds. Does not reset the timer.
    */
    public int getWaitTimeMilliseconds() {
        return m_waitTimeMilliseconds;
    }

    public void setWaitTimeMilliseconds(int value) {
        m_waitTimeMilliseconds = value;
    }

    /**
    * constructs a new instance of WaitByTime
    * 
    *  @param threshold Wait time in milliseconds
    */
    public WaitByTime(int milliseconds) {
        setWaitTimeMilliseconds(milliseconds);
        m_timer.start();
        reset();
    }

    /**
    * constructs a new instance of WaitByTime
    * 
    *  @param seconds Wait time in seconds
    */
    public WaitByTime(double seconds) {
        setWaitTimeSeconds(seconds);
        reset();
    }

    /**
    * Reset the timer to the time span specified by WaitTimeMilliseconds
    */
    public void reset() {         
        m_timer.reset();
    }

}


/**
* Returns true if enough time has passed since last reset
*/