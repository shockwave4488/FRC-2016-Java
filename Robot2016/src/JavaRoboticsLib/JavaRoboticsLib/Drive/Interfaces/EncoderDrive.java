package JavaRoboticsLib.Drive.Interfaces;

import edu.wpi.first.wpilibj.Encoder;
import JavaRoboticsLib.Drive.DriveEncoders;
import JavaRoboticsLib.ControlSystems.MotionController;
import static JavaRoboticsLib.Utility.Util.accurateWaitSeconds;

public interface EncoderDrive extends TankDrive {

	public interface DynamicDriveDelegate{
		boolean drive(double valueLeft, double valueRight);
	}
	
	public interface DynamicEncoderDelegate{
		boolean drive(Encoder left, Encoder right);
	}
	
	public interface DynamicDriveEncoderDelegate{
		boolean drive(DriveEncoders encoders);
	}
	
	public DriveEncoders getEncoders();
	
	default public void driveToDistance(double location, double power, boolean brake, double interval) throws InterruptedException {
		double direction = getEncoders().getLinearDistance() < location ? -1 : 1;
		
		while((getEncoders().getLinearDistance() - location)*direction > 0){
			setPowers(power * direction, power * direction);
			accurateWaitSeconds(interval);
		}
		
		if(brake)
			setPowers(0, 0);
	}
	
	default public void driveToDistance(double location, double power, boolean brake) throws InterruptedException{
		driveToDistance(location, power, brake, 0.02);
	}
	
	default public void driveForDistance(double location, double power, boolean brake, double interval) throws InterruptedException{
		driveToDistance(location + getEncoders().getLinearDistance(), power, brake, interval);
	}
	
	default public void driveForDistance(double location, double power, boolean brake) throws InterruptedException{
		driveForDistance(location, power, brake, 0.02);
	}
	
	default public void driveToDistance(MotionController controller, double location, double tolerance, boolean brake, double interval) throws InterruptedException{
		controller.setSetPoint(location);

        while (Math.abs(location - getEncoders().getLinearDistance()) > tolerance)
        {
            double power = controller.get(getEncoders().getLinearDistance());
            setPowers(power, power);
            accurateWaitSeconds(interval);
        }
        
        if(brake)
        	setPowers(0, 0);
	}
	
	default public void driveForDistance(MotionController controller, double location, double tolerance, boolean brake, double interval) throws InterruptedException{
		driveToDistance(controller, location + getEncoders().getLinearDistance(), tolerance, brake, interval);
	}
	
	default public void driveToDistance(MotionController controller, double location, double tolerance, boolean brake) throws InterruptedException{
		driveToDistance(controller, location, tolerance, brake, 0.02);
	}
	
	default public void driveForDistance(MotionController controller, double location, double tolerance, boolean brake) throws InterruptedException{
		driveForDistance(controller, location, tolerance, brake, 0.02);
	}
	
	default public void driveToAtSpeed(MotionController speedController, double speed, double location, boolean brake, double interval) throws InterruptedException{
		speedController.setSetPoint(getEncoders().getLinearDistance() < location ? speed : -speed);
		
		while((getEncoders().getLinearDistance() - location) * Math.signum(speedController.getSetPoint()) > 0){
			double power = speedController.get(getEncoders().getLinearSpeed());
			setPowers(power, power);
			accurateWaitSeconds(interval);
		}
		
		if(brake)
			setPowers(0, 0);
	}
	
	default public void driveForAtSpeed(MotionController speedController, double speed, double location, boolean brake, double interval) throws InterruptedException{
		driveToAtSpeed(speedController, speed, location + getEncoders().getLinearDistance(), brake, interval);
	}
	
	default public void driveToAtSpeed(MotionController speedController, double speed, double location, boolean brake) throws InterruptedException{
		driveToAtSpeed(speedController, speed, location, brake, 0.02);
	}
	
	default public void driveForAtSpeed(MotionController speedController, double speed, double location, boolean brake) throws InterruptedException{
		driveForAtSpeed(speedController, speed, location, brake, 0.02);
	}
	
	default public void dynamicSpeedDrive(DynamicDriveDelegate expression, double interval) throws InterruptedException{
		while(!expression.drive(getEncoders().getLeftSpeed(), getEncoders().getRightSpeed())){
			accurateWaitSeconds(interval);
		}
	}
	
	default public void dynamicSpeedDrive(DynamicDriveDelegate expression) throws InterruptedException{
		dynamicSpeedDrive(expression, 0.02);
	}
	
	default public void dynamicDistanceDrive(DynamicDriveDelegate expression, double interval) throws InterruptedException{
		while(!expression.drive(getEncoders().getLeftDistance(), getEncoders().getRightDistance()))
			accurateWaitSeconds(interval);
	}
	
	default public void dynamicDistanceDrive(DynamicDriveDelegate expression) throws InterruptedException{
		dynamicDistanceDrive(expression, 0.02);
	}
	
	default public void dynamicEncoderDrive(DynamicEncoderDelegate expression, double interval) throws InterruptedException{
		while(!expression.drive(getEncoders().getLeft(), getEncoders().getRight()))
			accurateWaitSeconds(interval);
	}
	
	default public void dynamicEncoderDrive(DynamicEncoderDelegate expression) throws InterruptedException{
		dynamicEncoderDrive(expression, 0.02);
	}
	
	default public void dynamicEncoderDrive(DynamicDriveEncoderDelegate expression, double interval) throws InterruptedException{
		while(!expression.drive(getEncoders())){
			accurateWaitSeconds(interval);
		}
	}
	
	default public void dynamicEncoderDrive(DynamicDriveEncoderDelegate expression) throws InterruptedException{
		dynamicEncoderDrive(expression, 0.02);
	}
}
