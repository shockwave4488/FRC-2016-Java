package JavaRoboticsLib.FlowControl;

/**
* Will return true after a threshold has been met by waiting for a number of call counts
*/
public class WaitByCallCount   
{
    private int m_count;
    private int m_threshold;
    
    /**
    * Call Count limit - after this, {@link #WaitComplete} will return true
    */
    public int getThreshold() {
        return m_threshold;
    }

    public void setThreshold(int value) {
        m_threshold = value;
    }

    /**
    * New instance of WaitByCallCount
    * 
    *  @param count the threshold after which it should return true
    */
    public WaitByCallCount(int count) throws Exception {
        if (count < 1)
            throw new Exception("Call Count to wait ({count}) less than one");
         
        setThreshold(count);
        m_count = 0;
    }

    /**
    * returns if the update function has been called enough times
    */
    public boolean getWaitComplete() {
        return m_count >= getThreshold();
    }

    /**
    * Increases the internal count by 1 if true, resets count to 0 if false
    */
    public void update(boolean increase) {
        m_count = increase ? m_count + 1 : 0;
    }

    /**
    * Increases the internal count by 1
    */
    public void update() {
        m_count++;
    }

    /**
    * Increases the internal count by the amount specified
    * 
    *  @param count amount to increase by
    */
    public void update(int count) {
        m_count += count;
    }

    /**
    * Resets the internal count to zero
    */
    public void resetCount() {
        m_count = 0;
    }

}


