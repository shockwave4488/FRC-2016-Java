package JavaRoboticsLib.Drive;

import edu.wpi.first.wpilibj.Encoder;
import JavaRoboticsLib.WPIExtensions.EnhancedEncoder;

/**
 * {@link #DriveEncoders} implementation with {@link #EnhancedEncoder}s
 *
 */
public class EnhancedDriveEncoders extends DriveEncoders {
	
	@Override
	protected void setLeft(Encoder value) {
		if(value.getClass().equals(EnhancedEncoder.class)){
			super.setLeft(value);
		}
	}
	
	@Override
	protected void setRight(Encoder value) {
		if(value.getClass().equals(EnhancedEncoder.class)){
			super.setRight(value);
		}
	}
	
	/**
	 * sets the Delta Time for both EnhancedEncoders
	 * @param value
	 */
	public void setDt(double value){
		((EnhancedEncoder)getLeft()).setDt(value);
		((EnhancedEncoder)getRight()).setDt(value);
	}
	
	public EnhancedDriveEncoders(int lAChannel, int lBChannel, int rAChannel, int rBChannel){
		setLeft(new EnhancedEncoder(lAChannel, lBChannel));
		setRight(new EnhancedEncoder(rAChannel, rBChannel));
		
		getRight().setReverseDirection(true);
	}
	
	public EnhancedDriveEncoders(EnhancedEncoder left, EnhancedEncoder right){
		setLeft(left);
		setRight(right);
		
		getRight().setReverseDirection(true);
	}
}
