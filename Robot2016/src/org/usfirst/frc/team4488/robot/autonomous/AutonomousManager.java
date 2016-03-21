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

public class AutonomousManager {
	
	public final String challenge = "challenge";
	public final String lowBar = "lowBar";
	public final String moat = "moat";
	public final String roughTerrain = "roughTerrain";
	public final String ramparts = "ramparts";
	public final String rockWall = "rockWall";
	public final String chevalDeFrise = "chevalDeFrise";
	public final String portcullis = "portcullis";
	public final String none = "none";
	
	public final String shoot = "shoot";
	
	public final double[] firstHeading = new double[1];
	 
	 private SendableChooser m_position, m_defense, m_action;
	 private Thread m_thread;
	
	 private Manipulator m_manip;
	 private Shooter m_shooter;
	 private SystemsManagement m_systems;
	 private SmartDrive m_drive;
	
	 public AutonomousManager(SmartDrive drive, Shooter shooter, Manipulator manip, SystemsManagement systems){
		 m_manip = manip;
		 m_shooter = shooter;
		 m_drive = drive;
		 m_systems = systems;
		 m_position = new SendableChooser();
		 m_position.addDefault("Do Nothing", 0);
		 m_position.addObject("Position 1", 1); 
		 m_position.addObject("Position 2", 2); 
		 m_position.addObject("Position 3", 3); 
		 m_position.addObject("Position 4", 4); 
		 m_position.addObject("Position 5", 5);
		 m_position.addObject("Spy Bot", 6);
		 m_defense = new SendableChooser();
		 m_defense.addDefault("Challenge", challenge);
		 m_defense.addObject("Low Bar", lowBar);
		 m_defense.addObject("Moat", moat);
		 m_defense.addObject("Rough Terrain", roughTerrain);
		 m_defense.addObject("Ramparts", ramparts);
		 m_defense.addObject("Rock Wall", rockWall);
		 m_defense.addObject("Cheval De Frise", chevalDeFrise);
		 m_defense.addObject("Portcullis", portcullis);
		 m_action = new SendableChooser();
		 m_action.addDefault("No Action", none);
		 m_action.addObject("High Goal", shoot);
		 
		 SmartDashboard.putData("Autonomous Position", m_position);
		 SmartDashboard.putData("Autonomous Defense", m_defense);
		 SmartDashboard.putData("Autonomous Action", m_action);
	 }
	 
	 public void wait(Supplier<Boolean> expression, Runnable periodic) {
		 while(!expression.get() && DriverStation.getInstance().isAutonomous()){
			 periodic.run();
			 try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	 }
	 
	 public boolean driveAtPosition(double position, double tolerance){
		 return m_drive.getDrive().getLinearDistance() > position - tolerance && m_drive.getDrive().getLinearDistance() < position + tolerance;
	 }
	 
	 public boolean driveAtAngle(double angle, double tolerance){
		 return m_drive.getDrive().getAngle() > angle - tolerance && m_drive.getDrive().getAngle() < angle + tolerance;
	 }
	 
	 /*
	  * Begins the autonomous routine, during the autonomous period of the match
	  * The routine is a melding of three parts:
	  * The code called based on what defense is being breached
	  * The code called based on position of the bot, spot 1, spot 2, spybot, etc.
	  * The code called based on action to perform after the breach, high or low goal, or nothing.
	  */
	 public void start(){
		 m_thread = new Thread(() -> {
			 if((int)m_position.getSelected() == 0)
				 return;
			 else
				 driveAutonomous();
			 
			 switch((String)m_defense.getSelected()){
			 case portcullis:
				 portcullis();
				 break;
			 case chevalDeFrise:
				 chevalDeFrise();
				 break;
			 case rockWall:
				 rockWall();
				 break;
			 case roughTerrain:
				 roughTerrain();
				 break;
			 case ramparts:
				 ramparts();
				 break;
			 case moat:
				 moat();
				 break;
			 case lowBar:
				 lowBar();
				 break;
			 default:
				 break;
			 }
			 
			 m_manip.stopIntake();
			 			 
			 driveToShoot((int)m_position.getSelected());
			 
			 if(((String)m_action.getSelected()).equals(shoot))
				 shoot();
			 
			 }); //To Add Later
		 m_drive.resetAll();
		 Logger.addMessage("Starting Autonomous");
		 m_thread.start();
		 
		 Logger.addMessage("Ending Autonomous" + (DriverStation.getInstance().isAutonomous() ? " Early" : ""));
	 }
	 
	 public void check(){	 
		 if(!(m_thread.isAlive() && DriverStation.getInstance().isAutonomous() && DriverStation.getInstance().isEnabled())){
			m_thread.interrupt();
		 }
	 }
	 
	 public void kill(){
		 if(m_thread != null && m_thread.isAlive())
			 m_thread.interrupt();
	 }
	 
	 public void driveAutonomous(){
		 wait(m_manip::armReady, () -> {});
		 m_drive.getDrive().resetAngle();
		 m_drive.getDrive().resetEncoders();
		 Timer t = new Timer();
		 t.start();
		 wait(() -> t.get() > 0.05, () -> {});
		 firstHeading[0] = m_drive.getDrive().getAngle();
		 wait(() -> driveAtPosition(2.8, 0.1), () -> m_drive.driveToDistance(3, firstHeading[0]));
		 m_drive.stop();
	 }
	 
	 public void lowBar(){
		 m_manip.lowDefense();
		 wait(m_manip::armAtPosition, m_manip::lowDefense);
		 wait(() -> driveAtPosition(12, 0.1), () -> m_drive.driveToDistance(12, firstHeading[0]));
		 m_drive.stop();
		 m_manip.stopIntake();
		 wait(m_manip::armAtPosition, m_manip::stopIntake);
	 }
	 
	 public void chevalDeFrise(){
		 m_drive.resetAll();
		 wait(() -> driveAtPosition(1.1, 0.05), () -> m_drive.driveToDistance(2));
		 m_drive.stop();
		 m_manip.lowDefense();
		 
		 wait(() -> m_manip.armAtPosition(7, 2) || m_manip.armAtPosition(), m_manip::lowDefense);
		 wait(() -> driveAtPosition(2.0, 0.1), () -> m_drive.driveToDistance(5));
		 wait(() -> driveAtPosition(7, 0.1), () -> {m_drive.getDrive().setPowers(0.4, 0.4); m_manip.stopIntake();});
		 m_drive.stop();
	 }
	 
	 public void portcullis(){
		 m_manip.setArmSemiManualPosition(-15);
		 m_drive.getDrive().resetEncoders();
		 wait(() -> driveAtPosition(2.5,  0.05), () -> {m_drive.getDrive().setPowers(0.3,  0.3); m_manip.semiManualDefense();});
		 
		 m_drive.getDrive().setPowers(.05, .05);
		 m_manip.setArmSemiManualPosition(40);
		 m_drive.getDrive().resetEncoders();
		 wait(() -> m_manip.armAtPosition(30, 1), () -> m_manip.semiManualDefense());
		 wait(() -> driveAtPosition(5, .25), () -> {
			 m_drive.driveToDistance(10, firstHeading[0]); 
			 m_manip.setArmSemiManualPosition(70);
			 m_manip.semiManualDefense();
		 });		
		 m_drive.stop();
	 }
	 
	 public void rockWall(){
		 Timer overTimer = new Timer();
		 overTimer.start();
		 wait(() -> overTimer.get() > 2.75, () -> m_drive.getDrive().setPowers(0.75, 0.75));
		 m_drive.stop();
	 }
	 
	 public void roughTerrain(){
		 Timer overTimer = new Timer();
		 overTimer.start();
		 wait(() -> overTimer.get() > 2, () -> m_drive.getDrive().setPowers(0.75, 0.75));
		 m_drive.stop();
	 }
	 
	 public void moat(){
		 Timer overTimer = new Timer();
		 overTimer.start();
		 wait(() -> overTimer.get() > 2, () -> m_drive.getDrive().setPowers(0.75, 0.75));
		 m_drive.stop();
	 }
	 
	 public void ramparts(){
		 Timer overTimer = new Timer();
		 overTimer.start();
		 wait(() -> overTimer.get() > 2, () -> m_drive.getDrive().setPowers(0.75, 0.75));
		 m_drive.stop();
	 }
	 
	 public void driveToShoot(int position){
		 /*
		 double distance = m_drive.getDrive().getLinearDistance();
		 wait(() -> driveAtPosition(2.8 + distance, 0.1), () -> m_drive.driveToDistance(3 + distance, 0));
		 m_drive.stop();
		 
		 m_shooter.resetRangeFinding();
		 m_shooter.setDistance();
		 Logger.addMessage("Raising Turret");
		 m_shooter.MoveTurretPosition(ShooterPosition.Aiming);
		 wait(() -> m_shooter.turretAtPosition(), () -> {});
		 Logger.addMessage("Done Raising Turret");
		 
		 Logger.addMessage("Looking for Target");
		 double power = position > 3 ? 0.2 : -0.2;
		 wait(() -> SmartDashboard.getBoolean("TargetFound", false), () -> {
			 m_shooter.StopWheels();
			 m_drive.getDrive().setPowers(power, -power);
			 });
		 m_drive.stop();
		 m_shooter.resetRangeFinding();
		 wait(() -> false, () -> m_shooter.setDistance());
 
		 /*
		 Logger.addMessage("Turning to Target");
		 wait(() -> m_drive.atCamera(0.05), m_drive::turnToCamera);
		 
		 m_drive.stop();
		 Logger.addMessage("Closing in");
		 Timer rangeTimer = new Timer();
		 rangeTimer.start();
		 wait(() -> m_shooter.readyToShoot(), () -> m_shooter.Spin());
		 m_drive.stop();
		 m_shooter.Shoot();
		 
		 Logger.addMessage("Ready to Shoot");
		 double range = SmartDashboard.getNumber("Range", 8);
		 if(range > 10){
			 m_drive.getDrive().resetEncoders();
			 wait(() -> driveAtPosition(10.0 - range, 1.0), () -> m_drive.driveToDistance(10 - range, 0));
		 }
		 m_drive.stop();
		 */
		 double[] args = new double[]{0, 0, 0};
		 
		 switch(position){
		 	case 0: //DOES NOTHING, SHOULD NEVER RUN
		 		break;
		 	case 1: //LOW BAR, ASSUMED
			 	args[0] = 20;
			 	args[1] = 9.5;
			 	args[2] = 70;
			 	break;
		 	case 2 : //ASSUMED
		 		args[0] = 60;
		 		args[1] = 9.5;
		 		args[2] = 10;
		 		break;
		 	case 3 : //ASSUMED
		 		args[0] = 45;
		 		args[1] = 3;
		 		args[2] = 0; 
		 		break;
		 	case 4: //TESTED, NOT WORKING
		 		args[0] = 0;
		 		args[1] = 3;
		 		args[2] = 0;
		 		break;
		 	case 5 : //TESTED, WORKING
		 		args[0] = -60;
		 		args[1] = 9.5;
		 		args[2] = -10;
		 		break;
		 	case 6: //SPY BOT, ASSUMED
		 		args[0] = 5;
		 		args[1] = 5;
		 		args[2] = -10;
		 		break;
		 }
		 
		 //Turn
		 Logger.addMessage("Turning");
		 wait(() -> driveAtAngle(args[0] + firstHeading[0], 3), () -> m_drive.turnToAngle(args[0] + firstHeading[0]));//() -> m_drive.getDrive().setPowers(0.4 * Math.signum(args[0]), -0.4 * Math.signum(args[0])));
		 m_drive.stop();
		 
		 //Drive
		 Logger.addMessage("Driving");
		 double heading = m_drive.getDrive().getAngle();
		 double dist = m_drive.getDrive().getLinearDistance();
		 wait(() -> driveAtPosition(args[1] + dist, 0.1), () -> m_drive.driveToDistance(args[1] + dist, heading));
		 m_drive.stop();
		 
		 //Turn
		 Logger.addMessage("Turning");
		 wait(() -> driveAtAngle(args[2] + firstHeading[0], 2), () -> m_drive.turnToAngle(args[2] + firstHeading[0]));//() -> m_drive.getDrive().setPowers(0.4 * Math.signum(args[2]), -0.4 * Math.signum(args[2])));
		 m_drive.stop();		 
	 }
	 
	 public void shoot(){
		 Logger.addMessage("Shooting");
		 m_systems.setChargeButton(true);
		 wait(() -> m_shooter.readyToShoot(), m_systems::Update);
		 Timer alignTimer = new Timer();
		 alignTimer.start();
		 wait(() -> alignTimer.get() > 2, () -> {m_drive.turnToCamera(); m_systems.Update();});
		 m_drive.stop();
		 /*
		 m_systems.setChargeButton(false);
		 wait(() -> m_systems.getShooterState() == ShooterState.Idle, () -> m_systems.Update());
		 m_systems.setChargeButton(true);
		 */
		 wait(() -> m_systems.getShooterState() == ShooterState.Shoot, () -> {m_systems.Update(); m_systems.setShootButton(true);});
		 m_systems.setShootButton(false);
		 wait(() -> false, () -> m_systems.Update());
	 }
}



