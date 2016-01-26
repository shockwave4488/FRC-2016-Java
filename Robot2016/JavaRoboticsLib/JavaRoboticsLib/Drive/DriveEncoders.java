package JavaRoboticsLib.Drive;

import edu.wpi.first.wpilibj.Encoder;
import JavaRoboticsLib.WPIExtensions.EncoderExtension;

/**
* Encapsulates a left and right encoder and provides drive-specific calculations
*/
public class DriveEncoders   
{
    private Encoder m_left;
    private Encoder m_right;
    
    /**
    * Left side Encoder
    */
    public Encoder getLeft() {
        return m_left;
    }

    protected void setLeft(Encoder value) {
        m_left = value;
    }

    /**
    * Right side Encoder
    */
    public Encoder getRight() {
        return m_right;
    }

    protected void setRight(Encoder value) {
        m_right = value;
    }

    /**
    * Initializes both encoders to the channels specified, with the RIGHT encoder reversed.
    * 
    *  @param lAChannel Left A Channel
    *  @param lBChannel Left B Channel
    *  @param rAChannel Right A Channel
    *  @param rBChannel Right B Channel
    */
    public DriveEncoders(int lAChannel, int lBChannel, int rAChannel, int rBChannel) {
        this(new Encoder(lAChannel, lBChannel), new Encoder(rAChannel, rBChannel));
    }

    /**
    * Encapsulates a Left and Right Encoder, with the right encoder reversed.
    * 
    *  @param left Left Encoder
    *  @param right Right Encoder
    */
    public DriveEncoders(Encoder left, Encoder right) {
        setLeft(left);
        setRight(right);
        getRight().setReverseDirection(true);
    }

    /**
    * Just for {@link #EnhancedDriveEncoders}
    */
    protected DriveEncoders() {
    }

    /**
     * distance reported by the left encoder
     * @return
     */
    public double getLeftDistance() {return m_left.getDistance();}
    
    /**
     * distance reported by the right encoder
     * @return
     */
    public double getRightDistance() {return m_right.getDistance();}
    
    /**
     * speed reported by the left encoder
     * @return
     */
    public double getLeftSpeed() {return m_left.getRate();}
    
    /**
     * speed reported by the right encoder
     * @return
     */
    public double getRightSpeed() {return m_right.getRate();}
    
    /**
     * distance the robot has traveled linearly
     * @return
     */
    public double getLinearDistance() {return (getLeftDistance() + getRightDistance()) / 2;}
    
    /**
     * distance the robot has traveled rotationally
     * @return
     */
    public double getTurnDistance() {return (getLeftDistance() - getRightDistance()) / 2;}
    
    /**
     * speed the robot is traveling linearly
     * @return
     */
    public double getLinearSpeed() {return (getLeftSpeed() + getRightSpeed()) / 2;}
    
    /**
     * speed the robot is traveling rotationally
     * @return
     */
    public double getTurnSpeed() {return (getLeftSpeed() - getRightSpeed()) / 2;}
    
    public void reset(){
    	m_left.reset();
    	m_right.reset();
    }
    
    /**
     * Sets the left and right DistancePerPulse in feet based on wheel diameter and counts per revolution
     * @param wheelDiameter
     * @param countsPerRevolution
     */
    public void setDistancePerPulse(double wheelDiameter, int countsPerRevolution){
    	EncoderExtension.setDistancePerPulse(m_left, wheelDiameter, countsPerRevolution);
    	EncoderExtension.setDistancePerPulse(m_right, wheelDiameter, countsPerRevolution);
    }
}