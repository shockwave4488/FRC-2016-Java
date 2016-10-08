package org.usfirst.frc.team4488.robot.testing;
/*
 * This class corresponds to one test case 
 */
public class TestData {
	/*
	 * These are the abbreviations for each actuator and sensor
	 * LeftCIMMotor1 = LCM1
	 * LeftCIMMotor2 = LCM2
	 * LeftCIMMotor3 = LCM3
	 * LeftCIMMotorMaster = LCMM
	 * LeftCIMEncoder = LCE
	 * RightCIMMotor1 = RCM1
	 * RightCIMMotor1 = RCM2
	 * RightCIMMotor1 = RCM3
	 * RightCIMEncoder = RCE
	 * ArmMotor = AM
	 * ArmEncoder = AE
	 * ArmBeambreak = AB
	 * IntakeMotor = IM
	 * IntakeEncoder = IE
	 * TurretMotor = TM
	 * TurretEncoder = TE
	 * ShooterRightFrontWheel = SRCM1 
	 * ShooterLeftFrontWheel = SLCM1
	 * ShooterRightBackWheel = SRCM2
	 * ShooterLeftBackWheel = SLCM2
	 * ShooterRightEncoder = SRCE
	 * ShooterLeftEncoder = SLCE
	 */

	public String callFunction;
	private int NumberOfDevices = 3;
	public int target;
	public int offset;
	public int tolerance;
	public String[] actuators; // Should this be an int based upon some enum?
	public String[] sensors;
	public String textToPrint;
	public boolean autoMode;

	/*
	 * TODO How to test multiple motors simultaneously i.e. three cims per drive
	 * side tests all three, as well as one by one
	 */

	public TestData(String callFunction, int target, int offset, int tolerance, String act0, String act1, String act2,
			String sen0, String sen1, String sen2, String textToPrint, Boolean autoMode) {
		actuators = new String[NumberOfDevices];
		sensors = new String[NumberOfDevices];

		this.callFunction = callFunction;

		this.target = target;
		this.offset = offset;
		this.tolerance = tolerance;

		this.actuators = new String[NumberOfDevices];
		this.actuators[0] = act0;
		this.actuators[1] = act1;
		this.actuators[2] = act2;

		this.sensors = new String[NumberOfDevices];
		this.sensors[0] = sen0;
		this.sensors[1] = sen1;
		this.sensors[2] = sen2;

		this.textToPrint = textToPrint;
		this.autoMode = autoMode;
	}
}