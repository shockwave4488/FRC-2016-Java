package org.usfirst.frc.team4488.robot.autonomous;

import edu.wpi.first.wpilibj.DigitalInput;

public class AutonDecoder {
	private DigitalInput TwoCenter;
	private DigitalInput TwoLeft;
	private DigitalInput ThreeCenter;
	private DigitalInput ThreeLeft;
	private DigitalInput Four;
	private DigitalInput Five;
	private DigitalInput FiveDelay;
	private DigitalInput CDF;
	private DigitalInput Portcullis;
	private DigitalInput LowBar;
	
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

	public AutonDefense getDefense(){
		System.out.println();
		if (!CDF.get()){ return AutonDefense.ChevalDeFrise;}
		else if (!Portcullis.get()){ return AutonDefense.Portcullis;}
		else if (!LowBar.get()){ return AutonDefense.LowBar;}
		else { return AutonDefense.Challenge;}
		
	}
	
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
