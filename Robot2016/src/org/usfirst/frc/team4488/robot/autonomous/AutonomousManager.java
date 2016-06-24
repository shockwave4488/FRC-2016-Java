package org.usfirst.frc.team4488.robot.autonomous;

import org.usfirst.frc.team4488.robot.components.ShooterPosition;
import org.usfirst.frc.team4488.robot.systems.*;

import JavaRoboticsLib.Utility.Logger;
import java.lang.FunctionalInterface;
import java.lang.Thread;
import java.util.function.Supplier;
import java.util.function.Predicate;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class that is executed while in autonomous mode.
 * 
 * @author Programmers
 *
 */
public class AutonomousManager {
	
	public final double[] firstHeading = new double[1];
	 
	 private Thread m_thread;
	
	 private Manipulator m_manip;
	 private Shooter m_shooter;
	 private SystemsManagement m_systems;
	 private SmartDrive m_drive;
	 private AutonDecoder m_decoder;
	 
	 /** 
	  * Constructor for the class AutonomousManager 
	  * 
	  * @param drive Element of of class SmartDrive
	  * @param shooter Object of class shooter
	  * @param manip Object of class manipulator
	  * @param systems Object of class systemsManagement
	  */
	 public AutonomousManager(SmartDrive drive, Shooter shooter, Manipulator manip, SystemsManagement systems){
		 m_manip = manip;
		 m_shooter = shooter;
		 m_drive = drive;
		 m_systems = systems;
		 m_decoder = new AutonDecoder();
		 
	 }
	 
	 /**
	  * This method evaluates the expression passed as a parameter and if it is autonomous mode
	  * the given periodic function is run. The thread is put to sleep for 20ms before the control
	  * is returned to the caller. Why is the Thread being stopped? Is this a hack to emulate the 
	  * call period of the auto-mode thread?
	  * 
	  * @param expression
	  * @param periodic
	  */
	 public void wait(Supplier<Boolean> expression, Runnable periodic) {
		 while(!expression.get() && DriverStation.getInstance().isAutonomous()){
			 periodic.run();
			 try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	 }
	 
	 /**
	  * This method checks the linear position of the robot, if it is inside a range centered at position passed as a parameter.
	  * 
	  * @param position
	  * @param tolerance
	  * @return true when the position is inside the range [position-tolerance,position+tolerance]
	  */
	 public boolean driveAtPosition(double position, double tolerance){
		 //System.out.println("current position: " + m_drive.getDrive().getLinearDistance());
		 return m_drive.getDrive().getLinearDistance() > position - tolerance && m_drive.getDrive().getLinearDistance() < position + tolerance;
	 }
	 
	 /**
	  * This method checks the angular position of the robot, to indicate if it is in a range centered at angle passed as a parameter
	  * 
	  * @param angle
	  * @param tolerance
	  * @return true when angle is inside the range [angle-tolerance,angle+tolerance]
	  */
	 public boolean driveAtAngle(double angle, double tolerance){
		 return m_drive.getDrive().getAngle() > angle - tolerance && m_drive.getDrive().getAngle() < angle + tolerance;
	 }
	 
	 /** 
	  * Get the robot position at the start of autonomous (combined with movement after defense crossing)
	  * 
	  * @return position and movement combined - look at notes on the AutonDecoder class method documentation
	  */
	 public int getPosition(){
		 return m_decoder.getPosition();
	 }
	 
	 /**
	  * Get the defense to be crossed during autonomous and pass a string that represents it.
	  * 
	  * @return string with the defense identification
	  */
	 public String getDefense(){
		 return m_decoder.getDefense().toString();
	 }
	 
	 /**
	  * Begins the autonomous routine, during the autonomous period of the match
	  * The routine is a melding of three parts:
	  *  1. The code called based on what defense is being breached
	  *  2. The code called based on position of the bot, spot 1, spot 2, spybot, etc.
	  *  3. The code called based on action to perform after the breach, high or low goal, or nothing.
	  */
	 public void start(){
		 m_thread = new Thread(() -> {
			 AutonDefense defense = m_decoder.getDefense();
			 
			 Logger.addMessage("Selected defense: " + defense);
			  
//			 m_systems.setIntakeButton(true);
//			 wait(m_shooter::hasBall, () -> {});
			 
			 // Experimental code to avoid ball stall condition - It didn't work that well
//			 m_shooter.MoveTurretPosition(ShooterPosition.Load);
//			 wait(m_shooter::hasBall, m_shooter::deJam);
//			 wait(m_shooter::turretAtPosition, () -> m_shooter.MoveTurretPosition(ShooterPosition.Stored));
//			 m_shooter.StopWheels();
			 
			 // Perform autonomous initialization
			 driveInit();
			 
 			 // Based on the type of defense, perform movement to cross it
			 switch(defense){
			 case Portcullis:
				 //m_shooter.Stir();
				 //m_shooter.StopWheels();
				 portcullis();
				 break;
			 case ChevalDeFrise:
				 //m_shooter.Stir();
				 //m_shooter.StopWheels();				 
				 chevalDeFrise(); 
				 break;
			 case RockWall:
				 rockWall();
				 break;
			 case RoughTerrain:
				 roughTerrain();
				 break;
			 case Ramparts:
				 ramparts();
				 break;
			 case Moat:
				 moat();
				 break;
			 case LowBar:
				 //m_shooter.Stir();
				 //m_shooter.StopWheels();
				 lowBar();
				 break;
			 case Challenge:
				 challenge();
				 break;
			 default:
				 return;
			 }
			 
			 m_manip.stopIntake();
			 			 
			 int position = m_decoder.getPosition();
			 driveToShoot(position);
			 
			 if((defense != AutonDefense.Challenge) && (position != 0))
				 shoot();
			 
			 }); //m_thread end
		 m_thread.start();
	 }
	 /**
	  * This method is used during period autonomous tasks. It stops the thread when it is not in autonomous mode and the system is enabled.
	  * 
	  */
	 public void check(){	 
		 if(!(m_thread.isAlive() && DriverStation.getInstance().isAutonomous() && DriverStation.getInstance().isEnabled())){
			m_thread.interrupt();
		 }
	 }
	 /**
	  * Kills the thread that is running the auto mode code
	  */
	 public void kill(){
		 if(m_thread != null && m_thread.isAlive())
			 m_thread.interrupt();
	 }
	 
	/**
	  * This routine is called at the beginning of every auto mode to move the robot forward by a small amount.
	  */
	 public void driveInit(){
		 wait(m_manip::armReady, () -> {});
		 m_drive.getDrive().resetAngle();
		 m_drive.getDrive().resetEncoders();
		 Timer resetTimer = new Timer();
		 resetTimer.start();
		 wait(() -> resetTimer.get() > .1, () -> {}); //wait for resets to take effect
		 firstHeading[0] = m_drive.getDrive().getAngle();
	 }
	 /**
	  * The following methods describe the movements for each of the defenses
	  */
	 /**
	  * Low bar defense: this method takes the robot to the courtyard
	  */
	 public void lowBar(){
		 m_manip.lowDefense();
		 wait(m_manip::armAtPosition, m_manip::lowDefense);
		 wait(() -> driveAtPosition(15, 0.25), () -> m_drive.driveToDistance(15, firstHeading[0]));
		 m_drive.stop();
		 m_manip.stopIntake();
		 wait(m_manip::armAtPosition, m_manip::stopIntake);
	 }
	 /**
	  * Cheval: takes the robot to the courtyard
	  * 
	  */
	 public void chevalDeFrise(){
		 wait(() -> driveAtPosition(3.9, 0.1), () -> m_drive.driveToDistance(4.25));
		 m_drive.stop();
		 m_manip.lowDefense();
		 
		 wait(() -> m_manip.armAtPosition(7, 2) || m_manip.armAtPosition(), m_manip::lowDefense);
		 wait(() -> driveAtPosition(5.0, 0.1), () -> m_drive.driveToDistance(8));
		 wait(() -> driveAtPosition(11, 0.1), () -> {m_drive.getDrive().setPowers(0.4, 0.4); m_manip.stopIntake();});
		 m_drive.stop();
	 }
	 
	 public void portcullis(){
		 m_manip.setArmSemiManualPosition(-15);
		 m_drive.getDrive().resetEncoders();
		 wait(() -> driveAtPosition(5.5,  0.1), () -> {m_drive.getDrive().setPowers(0.3,  0.3); m_manip.semiManualDefense();});
		 m_drive.getDrive().setPowers(.05, .05);
		 m_manip.setArmSemiManualPosition(40);
		 m_drive.getDrive().resetEncoders();
		 wait(() -> m_manip.armAtPosition(30, 1), () -> m_manip.semiManualDefense());
		 wait(() -> driveAtPosition(10, .25), () -> {
			 m_drive.driveToDistance(15, firstHeading[0]); 
			 m_manip.setArmSemiManualPosition(70);
			 m_manip.semiManualDefense();
		 });		
		 m_drive.stop();
	 }
	 
	 public void rockWall(){
		 wait(() -> driveAtPosition(3, 0.1), () -> m_drive.driveToDistance(4.25));
		 m_drive.stop();
		 Timer overTimer = new Timer();
		 overTimer.start();
		 wait(() -> overTimer.get() > 2.75, () -> m_drive.getDrive().setPowers(0.75, 0.75));
		 m_drive.stop();
	 }
	 
	 public void roughTerrain(){
		 wait(() -> driveAtPosition(3, 0.1), () -> m_drive.driveToDistance(4.25));
		 m_drive.stop();
		 Timer overTimer = new Timer();
		 overTimer.start();
		 wait(() -> overTimer.get() > 2, () -> m_drive.getDrive().setPowers(0.75, 0.75));
		 m_drive.stop();
	 }
	 
	 public void moat(){
		 wait(() -> driveAtPosition(3, 0.1), () -> m_drive.driveToDistance(4.25));
		 m_drive.stop();
		 Timer overTimer = new Timer();
		 overTimer.start();
		 wait(() -> overTimer.get() > 2, () -> m_drive.getDrive().setPowers(0.75, 0.75));
		 m_drive.stop();
	 }
	 
	 public void ramparts(){
		 wait(() -> driveAtPosition(3, 0.1), () -> m_drive.driveToDistance(4.25));
		 m_drive.stop();
		 Timer overTimer = new Timer();
		 overTimer.start();
		 wait(() -> overTimer.get() > 2, () -> m_drive.getDrive().setPowers(0.75, 0.75));
		 m_drive.stop();
	 }
	 
	 public void challenge(){
		 wait(() -> driveAtPosition(5, 0.1), () -> m_drive.driveToDistance(5));
		 m_drive.stop();
	 }
	 
	 public void driveToShoot(int position){
		 double firstTurnAngle;
		 double driveDistance;
		 double secondTurnAngle;
		 
		 Logger.addMessage("Selected position: " + position);
		 
		 switch(position){
		 	case 0: //DOES NOTHING
		 		firstTurnAngle = 0;
			 	driveDistance = 0;
			 	secondTurnAngle = 0;
		 		break;
		 	case 1: //LOW BAR
		 		firstTurnAngle = 20;
			 	driveDistance = 10;
			 	secondTurnAngle = 85;
			 	break;
		 	case 2 : //2C
		 		firstTurnAngle = 60;
		 		driveDistance = 9.5;
		 		secondTurnAngle = 30;
		 		break;
		 	case 3 : //3C
		 		firstTurnAngle = 20;
		 		driveDistance = 5;
		 		secondTurnAngle = 35; 
		 		break;
		 	case 4: //4
		 		firstTurnAngle = 0;
		 		driveDistance = 3;
		 		secondTurnAngle = 0;
		 		break;
		 	case 5 : //5D
		 		firstTurnAngle = -50;
		 		driveDistance = 5.5;
		 		secondTurnAngle = -5;
		 		Timer delayTimer = new Timer();
		 		delayTimer.start();
				wait(() -> delayTimer.get() > 3.5, () -> {});
		 		break;
		 	case 6: //SPY BOT, ASSUMED
		 		firstTurnAngle = 5;
		 		driveDistance = 5;
		 		secondTurnAngle = -5;
		 		break;
		 	case 7: //2L
		 		firstTurnAngle = 0;
		 		driveDistance = 10;
		 		secondTurnAngle = 65;
		 		break;
		 	case 8: //3L
		 		firstTurnAngle = 0;
		 		driveDistance = 5;
		 		secondTurnAngle = 30;
		 		break;
		 	case 9: //5C
		 		firstTurnAngle = -50;
		 		driveDistance = 5.5;
		 		secondTurnAngle = -5;
		 		break;
		 	default:
		 		firstTurnAngle = 0;
			 	driveDistance = 0;
			 	secondTurnAngle = 0;
		 }
		 
		 //Turn
		 Logger.addMessage("Turning");
		 wait(() -> driveAtAngle(firstTurnAngle + firstHeading[0], 3), () -> m_drive.turnToAngle(firstTurnAngle + firstHeading[0]));
		 m_drive.stop();
		 
		 //Drive
		 Logger.addMessage("Driving");
		 double heading = m_drive.getDrive().getAngle();
		 double dist = m_drive.getDrive().getLinearDistance();
		 wait(() -> driveAtPosition(driveDistance + dist, 0.1), () -> m_drive.driveToDistance(driveDistance + dist, heading));
		 m_drive.stop();
		 
		 //Turn
		 Logger.addMessage("Turning");
		 wait(() -> driveAtAngle(secondTurnAngle + firstHeading[0], 5), () -> m_drive.turnToAngle(secondTurnAngle + firstHeading[0]));
		 m_drive.stop();
	 }
	 
	 public void shoot(){
		 Logger.addMessage("Shooting");
		 m_systems.setChargeButton(true);
		 wait(() -> m_shooter.readyToShoot(), m_systems::Update);
		 Timer alignTimer = new Timer();
		 alignTimer.start();
		 wait(() -> alignTimer.get() > 3 && SmartDashboard.getBoolean("TargetFound", false)&& m_drive.atCamera(2), () -> {m_drive.turnToCamera(); m_systems.Update();});
		 m_drive.stop();
		 wait(() -> m_systems.getShooterState() == ShooterState.Shoot, () -> {m_systems.Update(); m_systems.setShootButton(true);});
		 m_systems.setShootButton(false);
		 wait(() -> false, () -> m_systems.Update());
	 }
}



