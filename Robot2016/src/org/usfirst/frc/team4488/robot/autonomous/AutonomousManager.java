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
	 private SendableChooser m_position, m_defense, m_action;
	
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
		 m_defense.addDefault("Challenge", AutonDefense.Challenge);
		 m_defense.addObject("Low Bar", AutonDefense.LowBar);
		 m_defense.addObject("Moat", AutonDefense.Moat);
		 m_defense.addObject("Rough Terrain", AutonDefense.RoughTerrain);
		 m_defense.addObject("Ramparts", AutonDefense.Ramparts);
		 m_defense.addObject("Rock Wall", AutonDefense.RockWall);
		 m_defense.addObject("Cheval De Frise", AutonDefense.ChevalDeFrise);
		 m_action = new SendableChooser();
		 m_action.addDefault("No Action", AutonAction.None);
		 m_action.addObject("High Goal", AutonAction.HighGoal);
		 m_action.addObject("Low Goal", AutonAction.LowGoal);
	 }
	 
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
	  * The code called based on action to perform after the breach, hupatrigh or low goal, or nothing.
	  */
	 public void run(Runnable periodic){
		 Thread thread = new Thread(() -> {
			 driveAutonomous();
			 chevalDeFrise();
			 m_manip.stopIntake();
			 shoot(4);
			 }); //To Add Later
		 m_drive.resetAll();
		 Logger.addMessage("Starting Autonomous");
		 thread.run();
		 
		 while(thread.isAlive() && DriverStation.getInstance().isAutonomous() && DriverStation.getInstance().isEnabled()){
			 try {
				Thread.sleep(20);
				periodic.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
		 
		 Logger.addMessage("Ending Autonomous" + (DriverStation.getInstance().isAutonomous() ? " Early" : ""));
	 }
	 
	 public void driveAutonomous(){
		 wait(m_manip::armReady, () -> {});
		 m_drive.getDrive().resetAngle();
		 m_drive.getDrive().resetEncoders();
		 wait(() -> driveAtPosition(2.8, 0.1), () -> m_drive.driveToDistance(3));
		 m_drive.stop();
	 }
	 
	 public void lowBar(){
		 m_manip.lowDefense();
		 wait(m_manip::armAtPosition, m_manip::lowDefense);
		 wait(() -> driveAtPosition(12, 0.1), () -> m_drive.driveToDistance(12));
		 m_drive.stop();
		 m_manip.stopIntake();
		 wait(m_manip::armAtPosition, m_manip::stopIntake);
	 }
	 
	 public void chevalDeFrise(){
		 m_drive.resetAll();
		 wait(() -> driveAtPosition(0.8, 0.05), () -> m_drive.driveToDistance(2));
		 m_drive.stop();
		 m_manip.lowDefense();
		 
		 wait(() -> m_manip.armAtPosition(7, 2) || m_manip.armAtPosition(), m_manip::lowDefense);
		 wait(() -> driveAtPosition(1.5, 0.1), () -> m_drive.driveToDistance(5));
		 wait(() -> driveAtPosition(7, 0.1), () -> {m_drive.getDrive().setPowers(0.4, 0.4); m_manip.stopIntake();});
		 m_drive.stop();
	 }
	 
	 public void portcullis(){
		 m_manip.setArmSemiManualPosition(-15);
		 m_drive.resetAll();
		 wait(() -> driveAtPosition(2.5,  0.05), () -> {m_drive.getDrive().setPowers(0.3,  0.3); m_manip.semiManualDefense();});
		 
		 m_drive.getDrive().setPowers(.05, .05);
		 m_manip.setArmSemiManualPosition(40);
		 m_drive.resetAll();
		 wait(() -> m_manip.armAtPosition(30, 1), () -> m_manip.semiManualDefense());
		 wait(() -> driveAtPosition(6, .25), () -> {
			 m_drive.driveToDistance(10); 
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
	 
	 public void shoot(int position){
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
		 	case 1: //LOW BAR
			 	break;
		 	case 2 : 
		 		args[0] = -25;
		 		break;
		 	case 3 : 
		 		args[0] = 25;  
		 		break;
		 	case 4:
		 		args[0] = -5;
		 		args[1] = 5;
		 		args[2] = 5;
		 		break;
		 	case 5 :
		 		args[0] = 10;
		 		args[1] = 9.5;
		 		args[2] = -60;
		 		break;
		 }
		 
		 //Turn
		 Logger.addMessage("Turning");
		 double[] heading = {m_drive.getDrive().getAngle()};
		 wait(() -> driveAtAngle(args[0] + heading[0], 3), () -> m_drive.getDrive().setPowers(0.4 * Math.signum(args[0]), -0.4 * Math.signum(args[0])));
		 m_drive.stop();
		 
		 //Drive
		 Logger.addMessage("Driving");
		 double dist = m_drive.getDrive().getLinearDistance();
		 wait(() -> driveAtPosition(args[1] + dist, 0.1), () -> m_drive.driveToDistance(args[1] + dist));
		 m_drive.stop();
		 
		 //Turn
		 Logger.addMessage("Turning");
		 wait(() -> driveAtAngle(args[2] + heading[0], 2), () -> m_drive.getDrive().setPowers(0.4 * Math.signum(args[2]), -0.4 * Math.signum(args[2])));
		 m_drive.stop();
		 
		 //Charge and Shoot
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



