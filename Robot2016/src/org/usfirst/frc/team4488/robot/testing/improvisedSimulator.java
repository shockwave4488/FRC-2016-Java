package org.usfirst.frc.team4488.robot.testing;

import javax.swing.*;
import java.awt.Color;

public class improvisedSimulator {
	public JRadioButton rdbtnTest = new JRadioButton("Test1");
	public JRadioButton rdbtnTest_1 = new JRadioButton("Test2");
	public JRadioButton rdbtnTest_2 = new JRadioButton("Test3");
	public JRadioButton rdbtnTest_3 = new JRadioButton("Test4");
	JProgressBar motorValue = new JProgressBar();
	JFrame window;
	public improvisedSimulator(){
		window = new JFrame("4488 Simulator");
		window.getContentPane().setLayout(null);
		rdbtnTest.setSelected(true);
		rdbtnTest.setBounds(6, 7, 109, 23);
		window.getContentPane().add(rdbtnTest);
		
		
		rdbtnTest_1.setBounds(117, 7, 109, 23);
		window.getContentPane().add(rdbtnTest_1);
		
		
		rdbtnTest_2.setBounds(6, 33, 109, 23);
		window.getContentPane().add(rdbtnTest_2);
		
		
		rdbtnTest_3.setBounds(117, 33, 109, 23);
		window.getContentPane().add(rdbtnTest_3);
		motorValue.setToolTipText("Displays the motor values given.  Maximums can be set at ease.\r\nThis is great");
		motorValue.setStringPainted(true);
		motorValue.setForeground(new Color(255, 0, 0));
		motorValue.setValue(34);
		motorValue.setBounds(6, 256, 206, 23);
		window.getContentPane().add(motorValue);
		
		JLabel lblMotorValues = new JLabel("Motor Values");
		lblMotorValues.setBounds(6, 241, 202, 14);
		window.getContentPane().add(lblMotorValues);
		
		window.setVisible(true);
	}
	public void setProgressBar(int value){
		motorValue.setValue(value);
		motorValue.setString("Motor Value Percentage:"+value);
	}
	public void setMaximumAndValue(int maximum){
		motorValue.setMaximum(maximum);
		motorValue.setValue(maximum);
	}
}
