package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.RobotMap;
import com.kauailabs.navx.frc.AHRS;
import JavaRoboticsLib.Drive.Interfaces.*;
import JavaRoboticsLib.WPIExtensions.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive implements TankDrive{
	
	private static final double RAMPRATE = 60; //Volts per second
	
	private CANTalon m_left;
	private SpeedControllerGroup m_leftFollowers;
	private CANTalon m_right;
	private SpeedControllerGroup m_rightFollowers;
	private CANTalon rightSlave1;
	private CANTalon rightSlave2;
	private CANTalon leftSlave1;
	private CANTalon leftSlave2;
 
	
	private AHRS m_navx;
	
	public Drive() {
		try {

			m_left = new CANTalon(RobotMap.DriveMotorLeftM);
			leftSlave1 = new CANTalon(RobotMap.DriveMotorLeft2);
            leftSlave2 = new CANTalon(RobotMap.DriveMotorLeft3);
            leftSlave1.changeControlMode(TalonControlMode.Follower);
            leftSlave1.set(RobotMap.DriveMotorLeftM);
            leftSlave2.changeControlMode(TalonControlMode.Follower);
            leftSlave2.set(RobotMap.DriveMotorLeftM);
            m_leftFollowers = new SpeedControllerGroup(new SpeedController[]{leftSlave1, leftSlave2});
                        
            m_right = new CANTalon(RobotMap.DriveMotorRightM);
            rightSlave1 = new CANTalon(RobotMap.DriveMotorRight2);
            rightSlave2 = new CANTalon(RobotMap.DriveMotorRight3);
            rightSlave1.changeControlMode(TalonControlMode.Follower);
            rightSlave1.set(RobotMap.DriveMotorRightM);
            rightSlave2.changeControlMode(TalonControlMode.Follower);
            rightSlave2.set(RobotMap.DriveMotorRightM);
            m_rightFollowers = new SpeedControllerGroup(new SpeedController[]{rightSlave1, rightSlave2});
            
            m_left.enableBrakeMode(false);
            leftSlave1.enableBrakeMode(false);
            leftSlave2.enableBrakeMode(false);
            m_right.enableBrakeMode(false);
            rightSlave1.enableBrakeMode(false);
            rightSlave2.enableBrakeMode(false);
            
            m_left.setVoltageRampRate(RAMPRATE);
            leftSlave1.setVoltageRampRate(RAMPRATE);
            leftSlave2.setVoltageRampRate(RAMPRATE);
            m_right.setVoltageRampRate(RAMPRATE);
            rightSlave1.setVoltageRampRate(RAMPRATE);
            rightSlave2.setVoltageRampRate(RAMPRATE);
            
            m_right.setInverted(true);
            m_navx = new AHRS(SPI.Port.kMXP);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	/**
	 * Sets the CIM motors on the West Coast Drive train to the leftPower and rightPower. Essential for the TankDrive interface
	 */
	public void setPowers(double leftPower, double rightPower) {
		m_left.set(leftPower);
		m_right.set(rightPower);
		SmartDashboard.putNumber("Drive Speed Left", m_left.getSpeed());
		SmartDashboard.putNumber("Drive Power Left", leftPower);
		SmartDashboard.putNumber("Drive Speed Right", m_right.getSpeed());
		SmartDashboard.putNumber("Drive Power Right", rightPower);
	}
	
	public double getAngle(){
		return m_navx.getYaw();
	}
	
	public double getCompass(){
		return m_navx.getCompassHeading();
	}
	
	public void resetAngle(){
		m_navx.zeroYaw();
	}
	
	public AHRS getGyroscope(){
		return m_navx;
	}
	
	public double getLeftDistance(){
		return -m_left.getPosition() * (7.8 / 12.0) * Math.PI / 1024.0;
	}
	
	public double getRightDistance(){
		return m_right.getPosition() * (7.8 / 12.0) * Math.PI / 1024.0;
	}
	
	public double getLeftSpeed(){
		return m_left.getSpeed() * 8.0 * Math.PI;
	}
	
	public double getRightSpeed(){
		return m_right.getSpeed() * 8.0 * Math.PI;
	}
	
	public double getLinearDistance() {return (getLeftDistance() + getRightDistance()) / 2;}
	public double getTurnDistance() {return (getLeftDistance() - getRightDistance()) / 2;}
	public double getLinearSpeed() {return (getLeftSpeed() + getRightSpeed()) / 2;}
	public double getTurnSpeed() {return (getLeftSpeed() - getRightSpeed()) / 2;}
	
	public void printBrakeMode(){
        System.out.println("m_left: " + m_left.getBrakeEnableDuringNeutral());
        System.out.println("m_right: " + m_right.getBrakeEnableDuringNeutral());
        System.out.println("leftSlave1: " + leftSlave1.getBrakeEnableDuringNeutral());
        System.out.println("leftSlave2: " + leftSlave2.getBrakeEnableDuringNeutral());
        System.out.println("rightSlave1: " + rightSlave1.getBrakeEnableDuringNeutral());
        System.out.println("rightSlave2: " + rightSlave2.getBrakeEnableDuringNeutral());
	}
	
	public void resetEncoders(){
		m_left.setEncPosition(0);
		m_right.setEncPosition(0);
	}
	
	public void BreakModeAll(){
		m_left.enableBrakeMode(true);
        leftSlave1.enableBrakeMode(true);
        leftSlave2.enableBrakeMode(true);
        m_right.enableBrakeMode(true);
        rightSlave1.enableBrakeMode(true);
        rightSlave2.enableBrakeMode(true);
	}
	
	public void UnBreakModeAll(){
		m_left.enableBrakeMode(false);
        leftSlave1.enableBrakeMode(false);
        leftSlave2.enableBrakeMode(false);
        m_right.enableBrakeMode(false);
        rightSlave1.enableBrakeMode(false);
        rightSlave2.enableBrakeMode(false);
        
}
}
