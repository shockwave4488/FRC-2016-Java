
package JavaRoboticsLib.Utility;


/**
* Input filter functionally identical to that in the NI LabVIEW Controller library
*/
public class InputFilter   
{
    private static final double[] ForewardCoefficients = { 0.144426, 0.235649, 0.239850, 0.235649, 0.144426 };
    private double[] m_internalState;
    
    /**
    * Length of the internal status buffer
    */
    public static final int BufferLength = 5;
    /**
    * Constructs a new InputFilter
    * 
    *  @param initialState Initial state of the filter
    */
    public InputFilter(double initialState) {
        reInitialize(initialState);
    }

    /**
    * Constructs a new InputFilter initialized to Zero
    */
    public InputFilter() {
        this(0);
    }

    /**
    * sets all internal states to the double specified
    */
    public void reInitialize(double setTo) {
        m_internalState = new double[ForewardCoefficients.length];
        for (int i = 0;i < m_internalState.length;i++)
            m_internalState[i] = setTo;
    }

    /**
    * Updates the internal state by adding the input
    * 
    *  @param input new input
    */
    private void update(double input) {
        for (int i = m_internalState.length - 1;i >= 1;i--)
            m_internalState[i] = m_internalState[i - 1];
        m_internalState[0] = input;
    }

    /**
    * Gets the filtered output of the internal state
    */
    private double getValue() {
        double toReturn = 0;
        for (int i = 0;i < m_internalState.length;i++)
            toReturn += m_internalState[i] * ForewardCoefficients[i];
        return toReturn;
    }

    /**
    * Gets the filtered output of the internal state
    * 
    *  @param value Value to update the {@link #InputFilter} with
    *  @return Filtered output
    */
    public double get(double value) {
        update(value);
        return getValue();
    }

    /**
    * Gets the filtered value of the input without pushing a new value to the internal buffer
    * 
    *  @return
    */
    public double get() {
        return getValue();
    }

}


