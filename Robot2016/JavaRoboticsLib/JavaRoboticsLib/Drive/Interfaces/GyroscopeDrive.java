package JavaRoboticsLib.Drive.Interfaces;

import edu.wpi.first.wpilibj.Timer;
import JavaRoboticsLib.ControlSystems.MotionController;
import edu.wpi.first.wpilibj.GyroBase; //Change to Gyro Interface or GyroBase when new WPI comes out
import static JavaRoboticsLib.Utility.Util.accurateWaitSeconds;

public interface GyroscopeDrive extends TankDrive {
	
	public interface DynamicDriveDelegate{
		public boolean drive(double GyroValue);
	}
	
	public interface DynamicGyroscopeDelegate{
		public boolean drive(GyroBase gyro); //Change to Gyro Interface or GyroBase when new WPI comes out
	}
	
	public GyroBase getGyroscope();
	
	public default void turnToAngle(double power, double angle, boolean brake, double interval) throws InterruptedException{
		int direction = getGyroscope().getAngle() < angle ? -1 : 1;
		
		while((getGyroscope().getAngle() - angle) * direction > 0){
			setPowers(power * direction, -power * direction);
			accurateWaitSeconds(interval);
		}
		
		if(brake)
			setPowers(0, 0);
	}
	
	public default void turnToAngle(double power, double angle, boolean brake) throws InterruptedException{
		turnToAngle(power, angle, brake, 0.02);
	}
	
	public default void turnForAngle(double power, double angle, boolean brake, double interval) throws InterruptedException{
		turnToAngle(power, angle + getGyroscope().getAngle(), brake, interval);
	}
	
	public default void turnForAngle(double power, double angle, boolean brake) throws InterruptedException{
		turnForAngle(power, angle, brake, 0.02);
	}
	
	public default void turnToAngle(MotionController controller, double angle, double tolerance, boolean brake, double interval) throws InterruptedException{
		controller.setSetPoint(angle);
		
		while(Math.abs(getGyroscope().getAngle() - angle) > tolerance){
			double power = controller.get(getGyroscope().getAngle());
			setPowers(power, -power);
			accurateWaitSeconds(interval);
		}
		
		if(brake)
			setPowers(0, 0);
	}
	
	public default void turnToAngle(MotionController controller, double angle, double tolerance, boolean brake) throws InterruptedException{
		turnToAngle(controller, angle, tolerance, brake, 0.02);
	}
	
	public default void turnForAngle(MotionController controller, double angle, double tolerance, boolean brake, double interval) throws InterruptedException{
		turnToAngle(controller, angle + getGyroscope().getAngle(), tolerance, brake, interval);
	}
	
	public default void turnForAngle(MotionController controller, double angle, double tolerance, boolean brake) throws InterruptedException{
		turnForAngle(controller, angle, tolerance, brake, 0.02);
	}
	
	public default void driveStraightForTime(MotionController correction, double power, double time, boolean brake, double interval) throws InterruptedException{
		correction.setSetPoint(getGyroscope().getAngle());
		Timer t = new Timer();
		t.start();
		
		while(!t.hasPeriodPassed(time)){
			double correctingPower = correction.get(getGyroscope().getAngle());
			setPowers(power + correctingPower, power - correctingPower);
			accurateWaitSeconds(interval);
		}
		
		if(brake)
			setPowers(0, 0);
	}
	
	public default void driveStraightForTime(MotionController correction, double power, double time, boolean brake) throws InterruptedException{
		driveStraightForTime(correction, power, time, brake, 0.02);
	}
	
	public default void dynamicGyroscopeDrive(DynamicDriveDelegate expression, double interval) throws InterruptedException{
		while(!expression.drive(getGyroscope().getAngle())){
			accurateWaitSeconds(interval);
		}
	}
	
	public default void dynamicGyroscopeDrive(DynamicDriveDelegate expression) throws InterruptedException{
		dynamicGyroscopeDrive(expression, 0.02);
	}
	
	public default void dynamicGyroscopeDrive(DynamicGyroscopeDelegate expression, double interval) throws InterruptedException{
		while(!expression.drive(getGyroscope())){
			accurateWaitSeconds(interval);
		}
	}
	
	public default void dynamicGyroscopeDrive(DynamicGyroscopeDelegate expression) throws InterruptedException{
		dynamicGyroscopeDrive(expression, 0.02);
	}
}
