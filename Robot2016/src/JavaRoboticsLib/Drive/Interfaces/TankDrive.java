package JavaRoboticsLib.Drive.Interfaces;

import edu.wpi.first.wpilibj.Timer;
import static JavaRoboticsLib.Utility.Util.accurateWaitSeconds;

public interface TankDrive {
	
	public interface DynamicTimedDelegate {
		public void drive(double time);
	}
	
	public interface DynamicDriveDelegate{
		public boolean drive(double time);
	}
	
	public void setPowers(double leftPower, double rightPower);
	
	public default void driveForTime(double lPower, double rPower, double time, boolean brake, double interval) throws InterruptedException{
		Timer s = new Timer();
		s.start();
		
		while(!s.hasPeriodPassed(time)){
			setPowers(lPower, rPower);
			accurateWaitSeconds(interval);
		}
		
		if(brake) setPowers(0, 0);
	}
	
	public default void driveForTime(double lPower, double rPower, double time, boolean brake) throws InterruptedException{
		driveForTime(lPower, rPower, time, brake, 0.02);
	}
	
	public default void straightForTime(double power, double time, boolean brake, double interval) throws InterruptedException{
		driveForTime(power, power, time, brake, interval);
	}
	
	public default void straightForTime(double power, double time, boolean brake) throws InterruptedException{
		driveForTime(power, power, time, brake);
	}
	
	public default void turnForTime(double power, double time, boolean brake, double interval) throws InterruptedException{
		driveForTime(power, -power, time, brake, interval);
	}
	
	public default void turnForTime(double power, double time, boolean brake) throws InterruptedException{
		driveForTime(power, -power, time, brake);
	}
	
	public default void dynamicDriveForTime(DynamicTimedDelegate expression, double time, double interval) throws InterruptedException{
		Timer s = new Timer();
		s.start();
		
		while(!s.hasPeriodPassed(time)){
			expression.drive(s.get());
			accurateWaitSeconds(interval);
		}
	}
	
	public default void dynamicDriveForTime(DynamicTimedDelegate expression, double time) throws InterruptedException{
		dynamicDriveForTime(expression, time, 0.02);
	}
	
	public default void dynamicDriveForTime(DynamicDriveDelegate expression, double interval) throws InterruptedException{
		Timer s = new Timer();
		s.start();
		
		while(!expression.drive(s.get())){
			accurateWaitSeconds(interval);
		}
	}
	
	public default void dynamicDriveForTime(DynamicDriveDelegate expression) throws InterruptedException{
		dynamicDriveForTime(expression, 0.02);
	}
}
