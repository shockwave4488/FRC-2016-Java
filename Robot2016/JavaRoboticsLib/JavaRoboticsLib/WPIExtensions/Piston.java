package JavaRoboticsLib.WPIExtensions;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
* A subclass of DoubleSolenoid meant to more intuitively model a piston
*/
public class Piston  extends DoubleSolenoid 
{
    private boolean m_extended;
    private boolean m_inverted;
    
    /**
    * True if the piston is extended, false if not
    */
    public boolean getExtended() {
        return m_extended;
    }

    public void setExtended(boolean value) {
        m_extended = value;
        value ^= m_inverted;
        set(value ? Value.kForward : Value.kReverse);
    }

    /**
    * Inverts the setting of the piston
    */
    public boolean getInverted() {
        return m_inverted;
    }

    public void setInverted(boolean value) {
        m_inverted = value;
        setExtended(getExtended());
    }

    //Update Extended
    /**
    * {@link #Piston} with specified forward and reverse channels
    * 
    *  @param forwardChannel 
    *  @param reverseChannel
    */
    public Piston(int forwardChannel, int reverseChannel) {
        super(forwardChannel, reverseChannel);
        setInverted(false);
        setExtended(false);
    }

    /**
    * {@link #Piston} with forward and reverse channels at a specific PCM
    * 
    *  @param moduleNumber 
    *  @param forwardChannel 
    *  @param reverseChannel
    */
    public Piston(int moduleNumber, int forwardChannel, int reverseChannel) {
        super(moduleNumber, forwardChannel, reverseChannel);
        setInverted(false);
        setExtended(false);
    }

}


