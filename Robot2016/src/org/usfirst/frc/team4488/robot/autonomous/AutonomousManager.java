package org.usfirst.frc.team4488.robot.autonomous;

import org.usfirst.frc.team4488.robot.systems.*;

import JavaRoboticsLib.Utility.Logger;
import java.lang.FunctionalInterface;
import java.lang.Thread;
import java.util.function.Supplier;
import java.util.function.Predicate;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousManager {
	 private SendableChooser m_position, m_defense, m_action;
	
	 private Manipulator m_manip;
	 private Shooter m_shooter;
	 private SmartDrive m_drive;
	
	 public AutonomousManager(SmartDrive drive, Shooter shooter, Manipulator manip){
		 m_manip = manip;
		 m_shooter = shooter;
		 m_drive = drive;
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
	 public void run(){
		 Thread thread = new Thread(() -> {portcullis();}); //To Add Later
		 m_drive.resetAll();
		 thread.run();
		 Logger.addMessage("Starting Autonomous");
		 
		 while(thread.isAlive() && DriverStation.getInstance().isAutonomous()){
			 try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
		 
		 Logger.addMessage("Ending Autonomous" + (DriverStation.getInstance().isAutonomous() ? " Early" : ""));
	 }
	 
	 public void driveAutonomous(){
		 m_drive.getDrive().resetAngle();
		 m_drive.getDrive().resetEncoders();
		 wait(() -> driveAtPosition(7, 0.1), () -> m_drive.driveToDistance(7, 0));
		 m_drive.stop();
	 }
	 
	 public void turnAutonomous(){
		 m_drive.getDrive().resetAngle();
		 wait(() -> driveAtAngle(90, 0.25), () -> m_drive.turnToAngle(90));
		 m_drive.stop();
	 }
	 
	 public void lowBar(){
		 m_manip.lowDefense();
		 wait(m_manip::armAtPosition, m_manip::lowDefense);
		 wait(() -> driveAtPosition(5, 0.1), () -> m_drive.driveToDistance(10,  0.1));
		 m_drive.stop();
		 m_manip.stopIntake();
		 wait(m_manip::armAtPosition, m_manip::stopIntake);
	 }
	 
	 public void chevalDeFrise(){
		 wait(() -> driveAtPosition(0.8, 0.05), () -> m_drive.driveToDistance(2,  0.5));
		 m_drive.stop();
		 m_manip.lowDefense();
		 
		 wait(() -> m_manip.armAtPosition(7, 1) || m_manip.armAtPosition(), m_manip::lowDefense);
		 wait(() -> driveAtPosition(1.5, 0.1), () -> m_drive.driveToDistance(5,0));
		 wait(() -> driveAtPosition(5, 0.1), () -> {m_drive.driveToDistance(10, 0); m_manip.stopIntake();});
		 m_drive.stop();
	 }
	 
	 public void portcullis(){
		 wait(() -> driveAtPosition(0.8,  0.05), () -> {m_drive.driveToDistance(2,  0.5); m_manip.lowDefense();});
		 m_manip.setArmSemiManualPosition(-15);
		 wait(() -> m_manip.armAtPosition(-15, 1), () -> m_manip.semiManualDefense());
		 m_manip.setArmSemiManualPosition(40);
		 wait(() -> m_manip.armAtPosition(40, 1), () -> m_manip.semiManualDefense());
		 wait(() -> driveAtPosition(6, .25), () -> {
			 m_drive.driveToDistance(10,  0); 
			 m_manip.setArmSemiManualPosition(70);
			 m_manip.semiManualDefense();
		 });
		 m_drive.stop();
	 }
}



