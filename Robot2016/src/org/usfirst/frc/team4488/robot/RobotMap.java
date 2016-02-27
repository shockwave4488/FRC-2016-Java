package org.usfirst.frc.team4488.robot;

import edu.wpi.first.wpilibj.SPI;

public class RobotMap {	
	//Constants having to do with the drive system.
    public static final int DriveMotorLeftM = 1;
    public static final int DriveMotorLeft2 = 2;
    public static final int DriveMotorLeft3 = 3;
    public static final int DriveMotorRightM = 6;
    public static final int DriveMotorRight2 = 5;
    public static final int DriveMotorRight3 = 4;

    public static final int ShooterMotorLeft = 0;
    public static final int ShooterMotorRight = 7;
    public static final int IndexMotorLeft = 1;
    public static final int IndexMotorRight = 8;
    public static final int TurretMotor = 6;
    public static final int ArmMotor = 9;
    public static final int IntakeMotor = 2;
  
    public static final int ShooterLeftCounter = 10; //MXP 0
    public static final int ShooterRightCounter = 11; //MXP 1
    public static final int TurretPotentiometer = 0;
    public static final int IndexerBeamBreak = 22; //MXP 8

    //Constants having to do with the Arm and Intake systems.
    
    public static final int IntakeBeamBreak = 18; //MXP 4
    public static final int ArmPotentiometer = 1;
    public static final int ArmEncoderA = 12; //MXP 2
    public static final int ArmEncoderB = 13; //MXP 3
    public static final int ArmBackLimit = 20; // MXP 6
}
