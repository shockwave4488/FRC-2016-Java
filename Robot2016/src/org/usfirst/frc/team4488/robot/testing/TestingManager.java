package org.usfirst.frc.team4488.robot.testing;

import org.usfirst.frc.team4488.robot.systems.Manipulator;
import org.usfirst.frc.team4488.robot.systems.Shooter;
import org.usfirst.frc.team4488.robot.systems.ShooterState;
import org.usfirst.frc.team4488.robot.systems.SmartDrive;
import org.usfirst.frc.team4488.robot.systems.SystemsManagement;

import JavaRoboticsLib.Utility.Logger;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.Supplier;

import org.usfirst.frc.team4488.robot.RobotMap;
import org.usfirst.frc.team4488.robot.components.ShooterPosition;
import org.usfirst.frc.team4488.robot.operator.Controllers;

/**
 * Class contains the testing suite manager. The layout of this class is as
 * follows - Constructor - Start - Stop - InitLogToConsole - LogToConsole -
 * CloseLogToConsole - InitLogToFile - LogToFile - CloseLogToFile -
 * GetTuningPreferances
 */
public class TestingManager {
	// variables & objects
	private Thread m_thread;
	private Manipulator m_manip;
	private Shooter m_shooter;
	private SystemsManagement m_systems;
	private SmartDrive m_drive;
	private Controllers controllers;

	private int NumberOfTests = 6;
	private int NumberOfDevices = 2;
	private TestData[] tests;

	TestLogger logger;

	/*
	 * Constructor class that initializes preferences and imports/creates test
	 * cases
	 */
	public TestingManager(SmartDrive smartDrive, Shooter shooter, Manipulator manipulator, SystemsManagement systems,
			Controllers controllers) {
		tests = new TestData[NumberOfTests];

		// Necessary for driving the robot
		this.m_manip = manipulator;
		this.m_shooter = shooter;
		this.m_drive = smartDrive;
		this.m_systems = systems;
		this.controllers = controllers;
	}

	/*
	 * The Start function kicks off the thread which will maintain state of the
	 * test.
	 */
	public void start() {

		logger = new TestLogger();

		m_thread = new Thread(() -> {
			int testCounter = 0;
			boolean test = true;

			while (test && testCounter <= 10) {
				// Check to see if the robot was disabled
				if (!check())
					return;
				switch (checkButtonState()) {
				case 0:
					// loop through and try again - no button pressed
					break;
				case 1:
					
					// Pressed A - Proceed to test
					System.out.println("Pressed A - advance to next test.");
					if (runTestComp(testCounter))
						System.out.println("SUCCESS");
					else
						System.out.println("Failure");
					// increment to next test
					testCounter++;
					System.out.println("Press A for next test, press X to repeat last test,press Y to abort testing.");
					System.out.println("----------------------------------------------------------------------------");
					break;
				case 3:
					// Pressed X - replay test
					// decrement counter to replay test
					testCounter--;
					System.out.println("Pressed X - repeat of previous test.");
					// System.out.println("running Test seven");
					if (runTestComp(testCounter))
						System.out.println("SUCCESS");
					else
						System.out.println("Failure");
					// increment counter to return to state before repeating
					// test
					testCounter++;
					System.out.println("Press A for next test, press X to repeat last test,press Y to abort testing.");
					break;
				case 4:
					// Pressed Y - abort
					System.out.println("Pressed Y - abort test routine.");
					test = false;
					break;
				default:
					test = false;
					break;
				}
			}

			System.out.println("Testing complete");

		});
		m_thread.start();
	}

	/*
	 * The stop function will clean up any aspect of the test scenario and will
	 * deal with corner- cases of the testing not being completed fully -i.e.
	 * killing it halfway through
	 */
	public void stop() {
		logger.closeLogTestManager();
	}

	/*
	 * Import tuning preferences from file
	 */
	private void getTuningPreferences() {
		// Looking for left & right drive encoders
		// Looking for arm encoders
		// Shooter turret encoders
		// left & right shooter wheel encoders
		// Tolerance for everything? -- Percentage
		// Target for everything -- PID value
	}

	private int checkButtonState() {
		if (controllers.getTestSuccess_NextButton())
			return 1;
		if (controllers.getTestFail_ReplayButton())
			return 3;
		if (controllers.getTestAbortButton())
			return 4;
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			System.out.println("Thread error");
			e.printStackTrace();
		}
		return 0;
	}

	private void wait(Supplier<Boolean> expression, Runnable periodic) {
		if (DriverStation.getInstance().isTest()) {
			periodic.run();
		}

		while (!expression.get() && DriverStation.getInstance().isTest()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			periodic.run();
		}
	}

	private boolean runTestComp(int testCase) {

		boolean returnFlag = true;

		switch (testCase) {
		case 0:
			System.out.println("Running test ten.");
			returnFlag = testTen(55);
			break;
		case 1:
			// System.out.println("Running test eleven.");
			// returnFlag = testEleven();
			break;
		case 2:
			System.out.println("Running test twelve.");
			returnFlag = testTwelve();
			break;
		case 3:
			System.out.println("Running test thirteen.");
			returnFlag = testThirteen();
			break;
		case 4:
			System.out.println("Running test fourteen.");
			returnFlag = testFourteen(0,90);
			break;
		case 5:
			System.out.println("Running test sixteen.");
			returnFlag = testSixteen();
			break;
		case 6:
			System.out.println("Running test seventeen.");
			returnFlag = testSeventeen(0);
			break;
		case 7:
			System.out.println("Running test eighteen.");
			returnFlag = testEighteen(0);
			break;
		case 8:
			System.out.println("Running test nineteen.");
			returnFlag = testNineteen();
			break;
		case 9:
			System.out.println("Running test twenty.");
			returnFlag = testTwenty();
			break;
		case 10:
			// System.out.println("Running test twentyone.");
			// testTwentyOne();
			break;

		}
		return (returnFlag);
	}
	
	private boolean runTestPractice(int testCase) {

		boolean returnFlag = true;

		switch (testCase) {
		case 0:
			System.out.println("Running test ten.");
			returnFlag = testTen(55);
			break;
		case 1:
			// System.out.println("Running test eleven.");
			// returnFlag = testEleven();
			break;
		case 2:
			System.out.println("Running test twelve.");
			returnFlag = testTwelve();
			break;
		case 3:
			System.out.println("Running test thirteen.");
			returnFlag = testThirteen();
			break;
		case 4:
			System.out.println("Running test fourteen.");
			returnFlag = testFourteen(90,180);
			break;
		case 5:
			System.out.println("Running test sixteen.");
			returnFlag = testSixteen();
			break;
		case 6:
			System.out.println("Running test seventeen.");
			returnFlag = testSeventeen(0);
			break;
		case 7:
			System.out.println("Running test eighteen.");
			returnFlag = testEighteen(0);
			break;
		case 8:
			System.out.println("Running test nineteen.");
			returnFlag = testNineteen();
			break;
		case 9:
			System.out.println("Running test twenty.");
			returnFlag = testTwenty();
			break;
		case 10:
			// System.out.println("Running test twentyone.");
			// testTwentyOne();
			break;

		}
		return (returnFlag);
	}

	/*
	 * Drive test Look at the wheels if they actually rotated. Target = 55
	 */
	private boolean testTen(double Target) {
		// Run drive for 3 seconds. Check encoder - Auto
		// Given power and time.
		m_drive.getDrive().resetEncoders();
		Timer resetTimer = new Timer();
		resetTimer.start();
		wait(() -> resetTimer.get() > .25, () -> {
		});

		Timer driveTimer = new Timer();
		driveTimer.start();
		m_drive.driveToSpeed(20, 0); // Ends up driving both motors...

		wait(() -> driveTimer.get() > 2.4, () -> {
		});
		m_drive.stop();
		driveTimer.stop();

		double Lresult = m_drive.getDrive().getLeftDistance();
		System.out.println("Test ten left encoder result = " + Lresult);

		double Rresult = m_drive.getDrive().getRightDistance();
		System.out.println("Test ten right encoder result = " + Rresult);

		// if within tolerance, report success
		if (Rresult > (Target - 20) && Rresult < (Target + 20) && Lresult > (Target - 20) && Lresult < (Target + 20))
			return true;
		else
			return false;
	}

	/*
	 * Arm intake test Target = 120
	 */
	private boolean testTwelve() {
		System.out.println(
				"This next test will lower the arm to the intake position and spin the intake until you trip the beam break");
		if (m_manip.IntakeHasBall()) {
			// Cannot run the test, the beam break was tripped?
			System.out.println("Error - please reset the beam break before proceeding");
			return false;
		}
		m_manip.spinIntake();

		while (true) {
			if (m_manip.IntakeHasBall()) {
				m_manip.stopIntake();
				return true;
			}
			if (controllers.getTestFail_ReplayButton()) {
				wait(()->!controllers.getTestFail_ReplayButton(),()->{});
				return false;
			}
		}
	}

	/*
	 * Automatic Drive Train test - All drive wheels should spin 360 deg,
	 * comfirmation by eye.
	 */
	private boolean testThirteen() {
		m_drive.getDrive().resetEncoders();
		// wait for reset to take effect
		Timer resetTimer = new Timer();
		resetTimer.start();
		wait(() -> resetTimer.get() > .25, () -> {
		});

		System.out.println("Wheels will rotate 360 degrees. Press A to run test...");
		wait(() -> controllers.getTestSuccess_NextButton(), () -> {
		});
		wait(() -> !controllers.getTestSuccess_NextButton(), () -> {
		});
		// Rotate wheels 360 degrees
		wait(() -> m_drive.isDriveDistanceDone(), () -> m_drive.driveToDistance(29)); // 7.8
																						// *
																						// pi
																						// =
																						// 24.5
		m_drive.stop();
		// Post-test text

		System.out.println("Did wheels rotate 360 degrees? Press A for yes, Press X for no.");

		while (true) {
			if (controllers.getTestSuccess_NextButton()) {
				wait(()->!controllers.getTestSuccess_NextButton(),()->{});
				return true;
			}
			if (controllers.getTestFail_ReplayButton()) {
				wait(()-> !controllers.getTestFail_ReplayButton(),()->{});
				return false;
			}
		}
	}

	/*
	 * Manual Turret test - Moving turret from resting to upright positions and
	 * checking for correct encoder values. 
	 * Practice Target = 180 resting = 90
	 * Comp Target = 90 resting = 0
	 */
	private boolean testFourteen(double resting, double Target) {
		Timer resetTimer = new Timer();
		resetTimer.start();
		wait(() -> resetTimer.get() > .25, () -> {
		});
		//TODO Need to check hall effect sensor
		System.out.println("Beginning Shooter Test");
		System.out.println("Beginning Angle" + m_shooter.TurretAngle());
		if (!(m_shooter.TurretAngle() > (resting - 5) && m_shooter.TurretAngle() < (resting + 5))) {
			System.out.println("Turret not at resting position");
			return false;
		}
		System.out.println("Please move the turret to the upright position, press A to continue...");
		// wait until button pressed
		wait(() -> controllers.getTestSuccess_NextButton(), () -> {
		});
		// wait for button depress
		wait(() -> !controllers.getTestSuccess_NextButton(), () -> {
		});
		double result = m_shooter.TurretAngle();
		System.out.println("Ending Angle" + result);
		if (result > (Target - 10) && result < (Target + 10)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Automatic Shooter test - Spins Indexer wheels for 3 seconds, confirmation
	 * by eye.
	 */
	private boolean testSixteen() {
		System.out.println("Index Wheels will spins for 3 seconds. Press A to run test...");
		wait(() -> controllers.getTestSuccess_NextButton(), () -> {
		});
		wait(() -> !controllers.getTestSuccess_NextButton(), () -> {
		});
		// Rotate wheels 3 seconds
		Timer indexTimer = new Timer();
		indexTimer.start();
		wait(() -> indexTimer.get() > 3, () -> m_shooter.load());

		m_shooter.StopWheels();
		// Post-test text

		System.out.println("Did index wheels spin? Press A for yes, Press X for no.");

		while (true) {
			if (controllers.getTestSuccess_NextButton()) {
				wait(()->!controllers.getTestSuccess_NextButton(),()->{});
					return true;
			}
			if (controllers.getTestFail_ReplayButton()) {
				wait(()->!controllers.getTestFail_ReplayButton(),()->{});
					return false;
			}
		}
	}

	/*
	 * Manual Arm test - Moving arm from resting position to ground position,
	 * and back again while checking limit switch and encoder values. Target = 0
	 */
	private boolean testSeventeen(double Target) {
		System.out.println("Please put arm in the resting position, Press A when completed.");
		// wait until button pressed - to ensure we didn't over or under shoot.
		// TODO BUG
		wait(() -> controllers.getTestSuccess_NextButton(), () -> {
		});
		// Also check to make sure it gets depressed.
		wait(() -> !controllers.getTestSuccess_NextButton(), () -> {
		});
		if (!m_manip.armReady()) {
			System.out.println("Arm not at resting position. please reset and try again");
			return false;
		}
		// DO I need to unbreak the motor?
		System.out.println("SUCCESS");
		System.out.println("Please move the arm all the way to the ground, Press A when completed.");
		// wait until button pressed - to ensure we didn't over or undershoot.
		wait(() -> controllers.getTestSuccess_NextButton(), () -> {
		});
		// Also check to make sure it gets depressed.
		wait(() -> !controllers.getTestSuccess_NextButton(), () -> {
		});
		double result1 = m_manip.getArmAngle();
		System.out.println("Test one result = " + result1);

		// if within tolerance, report success
		if (!(result1 > (Target - 10) && result1 < (Target + 10))) {
			System.out.println("FAILURE");
			return false;
		}

		System.out.println("SUCCESS");
		m_manip.resetArm();
		System.out.println("Please move the arm all the way to resting position.");
		// wait until button pressed - to ensure we didn't over or undershoot.
		wait(() -> m_manip.armReady(), () -> {
		});
		System.out.println("SUCCESS. TEST COMPLETE.");
		return true;
	}

	/*
	 * Automatic Arm test - Moves arm from resting position to ground position,
	 * and back again while checking limit switch and encoder values. Target = 0
	 */
	private boolean testEighteen(double Target) {
		System.out.println("Please put arm in the resting position, Press A when completed.");
		// wait until button pressed - to ensure we didn't over or undershoot.
		wait(() -> controllers.getTestSuccess_NextButton(), () -> {
		});
		// Also check to make sure it gets depressed.
		wait(() -> !controllers.getTestSuccess_NextButton(), () -> {
		});
		if (!m_manip.armReady()) {
			System.out.println("Arm not at resting position. please reset and try again");
			return false;
		}
		System.out.println("SUCCESS");
		System.out.println("Press A to move to ground position.");
		// wait until button pressed - to ensure we didn't over or undershoot.
		wait(() -> controllers.getTestSuccess_NextButton(), () -> {
		});
		// Also check to make sure it gets depressed.
		wait(() -> !controllers.getTestSuccess_NextButton(), () -> {
		});

		m_manip.setArmPositionLow();
		wait(m_manip::armAtPosition, m_manip::lowDefense);
		double result1 = m_manip.getArmAngle();

		// if within tolerance, report success
		if (!(result1 > (Target - 10) && result1 < (Target + 10))) {
			System.out.println("FAILURE");
			return false;
		}
		System.out.println("SUCCESS");

		System.out.println("Press A to move arm back to resting position.");
		// wait until button pressed - to ensure we didn't over or undershoot.
		wait(() -> controllers.getTestSuccess_NextButton(), () -> {
		});
		// Also check to make sure it gets depressed.
		wait(() -> !controllers.getTestSuccess_NextButton(), () -> {
		});
		m_manip.setArmPositionHigh();
		wait(m_manip::armAtPosition, m_manip::lowDefense);
		if(m_manip.armReady()){
			System.out.println("SUCCESS. TEST COMPLETE.");
			return true;
		}
		else
			return false;
	}

	// Beam Break test - check both
	private boolean testNineteen() {
		System.out.println("Please put hand in shooter beambreak, press A to continue...");
		while (true) {
			if (controllers.getTestFail_ReplayButton()) {
				wait(() -> !controllers.getTestFail_ReplayButton(), () -> {
				});
				System.out.println("User Abort test");
				return false;
			}
			if (m_shooter.hasBall()) {
				break;
			}
		}
		System.out.println("SUCCESS");
		System.out.println("Please put hand in arm beambreak, press A to continue...");
		while (true) {
			if (controllers.getTestFail_ReplayButton()) {
				wait(() -> !controllers.getTestFail_ReplayButton(), () -> {
				});
				System.out.println("User Abort test");
				return false;
			}
			if (m_manip.HasBall()) {
				break;
			}
		}
		return true;
	}

	// Another drive test - running wheels to perform a 360 on the ground
	private boolean testTwenty() {
		System.out.println("Resetting encoders");
		m_drive.resetAll(); // set both encoders to zero
		
		Timer resetTimer = new Timer();
		resetTimer.start();
		wait(() -> resetTimer.get() > .25, () -> {
		});
		// Have the robot do a 180, and ensure one encoder is positive while the
		// other is negative.
		
		System.out.println("Beginning Turn");
		m_drive.getDrive().setPowers(20, -20);
		double Lresult = m_drive.getDrive().getLeftDistance();
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Lresult = m_drive.getDrive().getLeftDistance();
			if (Lresult > 29) {
				break;
			}
		}
		System.out.println("Turn is Done");
		m_drive.stop();
		double Rresult = m_drive.getDrive().getRightDistance();
		if (Lresult > 0 && Rresult < 0) {
			// success
			return true;
		}
		return false;
	}

	/*
	 * TODO - Don to write function New shooter test Should lift turret, and
	 * pretend to fire. Either need to find which global variables need to be
	 * set else find more simplistic functionality to pretend to fire.
	 */
	private boolean testTwentyOne() {
		System.out.println("In 21");
		System.out.println(m_shooter.TurretAngle());
		m_shooter.MoveTurretPosition(ShooterPosition.Load);
		wait(()->m_shooter.turretAtPosition(),()->m_shooter.MoveTurretPosition(ShooterPosition.Load));
		System.out.println("Turret Raised");
		// while(true)
		// m_shooter.update();
		
		Timer wheelTimer = new Timer();
		wheelTimer.start();
		wait(() -> wheelTimer.get() > 3, () -> 		m_shooter.setShooterRPM(100));


		m_shooter.setShooterRPM(0);
		m_shooter.MoveTurretPosition(ShooterPosition.Stored);
		wait(()->m_shooter.turretAtPosition(),()->m_shooter.MoveTurretPosition(ShooterPosition.Stored));

		return true;
	}

	private boolean check() {
		// if (!(m_thread.isAlive() &&
		// DriverStation.getInstance().isAutonomous()
		// && DriverStation.getInstance().isEnabled())) {
		// m_thread.interrupt();
		// }
		if (m_thread.isAlive() && DriverStation.getInstance().isTest() && !DriverStation.getInstance().isEnabled()) {
			System.out.println("KILLIN IT");
			return false;
		}
		// DriverStation.getInstance().isBrownedOut();
		return true;
	}
}
