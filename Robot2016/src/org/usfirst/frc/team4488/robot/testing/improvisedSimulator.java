package org.usfirst.frc.team4488.robot.testing;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;

public class improvisedSimulator {
	public JRadioButton rdbtnTest = new JRadioButton("Test1");
	public JRadioButton rdbtnTest_1 = new JRadioButton("Test2");
	public JRadioButton rdbtnTest_2 = new JRadioButton("Test3");
	public JRadioButton rdbtnTest_3 = new JRadioButton("Test4");
	JProgressBar Timer = new JProgressBar();
	JFrame window;
	private final JProgressBar leftpwr = new JProgressBar();
	private final JProgressBar rightpwr = new JProgressBar();
	private enum windowState{
		Logger,
		Statistical
	}
	public windowState simulatorState = windowState.Statistical;
	private final JLabel lblMotorPowers = new JLabel("Motor Power Percentages");
	public improvisedSimulator(){
		window = new JFrame("4488 Simulator");
		window.getContentPane().setLayout(null);
		rdbtnTest.setSelected(true);
		rdbtnTest.setBounds(6, 185, 109, 23);
		window.getContentPane().add(rdbtnTest);
		
		
		rdbtnTest_1.setBounds(117, 185, 109, 23);
		window.getContentPane().add(rdbtnTest_1);
		
		
		rdbtnTest_2.setBounds(6, 211, 109, 23);
		window.getContentPane().add(rdbtnTest_2);
		
		
		rdbtnTest_3.setBounds(117, 211, 109, 23);
		window.getContentPane().add(rdbtnTest_3);
		Timer.setStringPainted(true);
		Timer.setToolTipText("Displays the amount of time left in the current segment.\r\n\r\nWhat am i even saying?");
		Timer.setForeground(Color.BLUE);
		Timer.setValue(44);
		Timer.setBounds(6, 256, 206, 23);
		window.getContentPane().add(Timer);
		
		JLabel lblMotorValues = new JLabel("Timer");
		lblMotorValues.setBounds(6, 241, 202, 14);
		window.getContentPane().add(lblMotorValues);
		leftpwr.setForeground(Color.RED);
		leftpwr.setStringPainted(true);
		leftpwr.setMinimum(-100);
		leftpwr.setToolTipText("Left motor's power value percent");
		leftpwr.setBounds(6, 40, 206, 28);
		
		window.getContentPane().add(leftpwr);
		rightpwr.setToolTipText("Right motor's power value percent");
		rightpwr.setForeground(Color.RED);
		rightpwr.setStringPainted(true);
		rightpwr.setMinimum(-100);
		rightpwr.setBounds(6, 79, 206, 28);
		
		window.getContentPane().add(rightpwr);
		lblMotorPowers.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMotorPowers.setBounds(6, 11, 206, 18);
		
		window.getContentPane().add(lblMotorPowers);
		
		window.setVisible(true);
	}
	public void setProgressBar(JProgressBar jpb,int value){
		jpb.setValue(value);
		jpb.setString("Motor Value:"+value+"% Power");
		if(jpb.getPercentComplete() >= 0.72){       //Sets foreground to green if percent is greater than 72%
			jpb.setForeground(new Color(0,0,255));
		}else if (jpb.getPercentComplete() >= 0.46){//Sets foreground to green if percent is between 46 and 72%
			jpb.setForeground(new Color(0,255,0));
		}else if (jpb.getPercentComplete() >= 0.22){//Sets foreground to yellow if percent is between 22 and 46%
			jpb.setForeground(new Color(255,255,0));
		}else{                                      //Sets foreground to red if percent is less than 22%
			jpb.setForeground(new Color(255,0,0));
		}
	}
	public void modifyProgressBar(JProgressBar jpb, int value){
		jpb.setValue(value);
		jpb.setString("Motor Value:"+value+"% Power");
		if(jpb.getPercentComplete() >= 0.72){       //Sets foreground to green if percent is greater than 72%
			jpb.setForeground(new Color(0,0,255));
		}else if (jpb.getPercentComplete() >= 0.46){//Sets foreground to green if percent is between 46 and 72%
			jpb.setForeground(new Color(0,255,0));
		}else if (jpb.getPercentComplete() >= 0.22){//Sets foreground to yellow if percent is between 22 and 46%
			jpb.setForeground(new Color(255,255,0));
		}else{                                      //Sets foreground to red if percent is less than 22%
			jpb.setForeground(new Color(255,0,0));
		}
	}
	public void setMaximumAndValue(JProgressBar jpb,int maximum){
		jpb.setMaximum(maximum);
		jpb.setValue(maximum);
	}
	public void setRadioButtonValue(JRadioButton jrb,boolean value){
		jrb.setSelected(value);
	}
}
