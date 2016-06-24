package org.usfirst.frc.team4488.robot.autonomous;

import edu.wpi.first.wpilibj.DigitalInput;
/**
 * This class is supposed to encapsulate the reading from the digital inputs of the RoboRio
 * A set of digital inputs represent the starting position of the robot and the trajectory
 * that the robot should follow once the defense is traversed. The other set of digital
 * inputs represent the type of defense in this initial position. 
 * 
 * @author Unkown
 *
 */
public class AutonDecoder {
	/**
	 * The robot starts at position 2 and moves to the center, in front of the middle goal
	 */
	private DigitalInput TwoCenter;
	/**
	 * The robot starts at position 2 and moves to the left, in front of the left goal
	 */
	private DigitalInput TwoLeft;
	/**
	 * The robot starts at position 3 and moves to the center, in front of the middle goal
	 */
	private DigitalInput ThreeCenter;
	/**
	 * The robot starts at position 3 and moves to the left, in front of the left goal
	 */
	private DigitalInput ThreeLeft;
	/** 
	 * The robot starts at position 4 and moves to the center
	 */
	private DigitalInput Four;
	/**
	 * The robot starts at position 5 and moves to the center
	 */
	private DigitalInput Five;
	/**
	 * The robot start at position 5 and after a delay moves to the center
	 */
	private DigitalInput FiveDelay;
	/**
	 * Defense types: 
	 */
	private DigitalInput CDF;
	private DigitalInput Portcullis;
	private DigitalInput LowBar;
	
	/**
	 * Constructor for the class
	 */
	public AutonDecoder(){
		
		TwoCenter = new DigitalInput(0);
		TwoLeft = new DigitalInput(1);
		ThreeCenter = new DigitalInput(2);
		ThreeLeft = new DigitalInput(3);
		Four = new DigitalInput(4);
		Five = new DigitalInput(5);
		FiveDelay = new DigitalInput(6);
		CDF = new DigitalInput(7);
		Portcullis = new DigitalInput(8);
		LowBar = new DigitalInput(9);
		
	}

	/**
	 * Read the digital inputs and return the type of defense that is in front of the robot. 
	 * When none of the inputs is active, the robot only challenges the defense (approaches it).
	 * 
	 * @return type of defense to be crossed
	 */
	public AutonDefense getDefense(){

		if (!CDF.get()){
			return AutonDefense.ChevalDeFrise;
		} else if (!Portcullis.get()){
			return AutonDefense.Portcullis;
		} else if (!LowBar.get()){ 
			return AutonDefense.LowBar;
		} else { 
			return AutonDefense.Challenge;
		}
		
	}
	
	/**
	 * Read the digital inputs to identify the initial position for the robot, and the type
	 * of movement that the robot is going to make after crossing the defense
	 * 
	 * @return integer value that corresponds to the position and movement combined (this is weird...)
	 */
	public int getPosition(){

		if (!LowBar.get()){return 1;}
		else if (!TwoCenter.get()){return 2;}
		else if (!ThreeCenter.get()){return 3;}
		else if (!Four.get()){return 4;}
		else if (!FiveDelay.get()){return 5;}
		else if (!TwoLeft.get()){return 7;}
		else if (!ThreeLeft.get()){return 8;}
		else if (!Five.get()){return 9;}
		else return 0;

	}
	

}
