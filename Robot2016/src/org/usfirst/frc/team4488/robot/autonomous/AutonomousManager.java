package org.usfirst.frc.team4488.robot.autonomous;

import org.usfirst.frc.team4488.robot.systems.*;

import JavaRoboticsLib.Utility.Logger;

import java.lang.Thread;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousManager {
	 private SendableChooser m_position, m_defense, m_action;
	
	 private Manipulator m_manip;
	 private Shooter m_shooter;
	 private Drive m_drive;
	
	 public AutonomousManager(Drive drive, Shooter shooter, Manipulator manip){
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
	 
	 
	 
	 public void moveToDistance(double distance){
		 while(m_drive.)
	 }
	 
	 
	 
	 
	 /*
	  * Begins the autonomous routine, during the autonomous period of the match
	  * The routine is a melding of three parts:
	  * The code called based on what defense is being breached
	  * The code called based on position of the bot, spot 1, spot 2, spybot, etc.
	  * The code called based on action to perform after the breach, high or low goal, or nothing.
	  */
	public void run(){
		 Thread thread = new Thread(() -> {
			 if(true){
				switch((int)SmartDashboard.getNumber("AutonDefense")){
				case 0:
					//Cheval De Fries
				case 1:
					//Ramparts
				case 2:
					//Rock Wall
				case 3:
					//Rough Terrain
				case 4:
					//Moat
				case 5:
					//Low Bar
				case 6:
					//Challenge
				
				}
				switch((int)SmartDashboard.getNumber("AutonPosition")){
				case 0:
					//Spybot
				case 1:
					//Position 1
				case 2:
					//Position 2
				case 3:
					//Position 3
				case 4:
					//Position 4
				case 5:
					//Position 5
				}
				switch((int)SmartDashboard.getNumber("AutonAction")){
				case 0:
					//Breach-only
				case 1:
					//Low Goal
				case 2:
					//High Goal
				case 3:
					//High Goal+Breach-Spy only
				case 4:
					//Low Goal and Breach- Spy only
				}
			 }
		 }); //To Add Later
		 thread.run();
		 Logger.addMessage("Starting Autonomous");
		 while(thread.isAlive() && DriverStation.getInstance().isAutonomous()){
			 try {
				
				thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
		 
		 Logger.addMessage("Ending Autonomous" + (DriverStation.getInstance().isAutonomous() ? " Early" : ""));
	 }
}
