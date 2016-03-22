package org.usfirst.frc.team4488.robot.systems;

import java.util.TreeMap;

import org.usfirst.frc.team4488.robot.components.CameraLights;
import org.usfirst.frc.team4488.robot.components.Indexer;
import org.usfirst.frc.team4488.robot.components.ShooterPosition;
import org.usfirst.frc.team4488.robot.components.ShooterWheels;
import org.usfirst.frc.team4488.robot.components.Turret;

import JavaRoboticsLib.ControlSystems.SetPointProfile;
import JavaRoboticsLib.Utility.InputFilter;
import JavaRoboticsLib.Utility.Logger;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Shooter {
	private ShooterWheels m_shooterWheels;
    private Indexer m_indexer;
    private Turret m_turret;
	private CameraLights m_lights;
    
	private boolean m_batterShot;
    private double m_rangeSnapshot;
    private InputFilter m_rangeFilter;
    private Timer m_rangeWait;
    private SetPointProfile m_rpmProfile;
    private SetPointProfile m_angleProfile;
    
    public Shooter()
    {
        m_shooterWheels = new ShooterWheels();
        m_indexer = new Indexer();
        m_turret = new Turret();
        m_lights = new CameraLights();
        
        m_rangeFilter = new InputFilter();
        m_rangeWait = new Timer();
        m_rangeWait.start();
        m_batterShot = false;
        
        m_rpmProfile = new SetPointProfile();
        /* PRACTICE
        m_rpmProfile.add(5.15, 3000);
        m_rpmProfile.add(6.1, 2500);
        m_rpmProfile.add(7.53, 2500);
        m_rpmProfile.add(8.2, 2600);
        m_rpmProfile.add(9.11, 2600);
        m_rpmProfile.add(10.01, 2600);
        m_rpmProfile.add(10.55, 2700);
        m_rpmProfile.add(11.1, 2700);
        m_rpmProfile.add(11.93, 2800);
        m_rpmProfile.add(12.5, 2800);
        m_rpmProfile.add(13.1, 2800);
        m_rpmProfile.add(13.45, 3000);
        */
        /* OLD COMPETITION
        m_rpmProfile.add(4.8, 2500);
        m_rpmProfile.add(6.08, 2000);
        m_rpmProfile.add(9.56, 2500);
        m_rpmProfile.add(10.18,  2600);
        m_rpmProfile.add(12.14, 2600);
        m_rpmProfile.add(12.82, 2650);
        */
        m_rpmProfile.add(4.8, 2000);
        m_rpmProfile.add(6.08, 2000);
        m_rpmProfile.add(6.83, 2100);
        m_rpmProfile.add(7.48, 2150);
        m_rpmProfile.add(8.36, 2250);
        m_rpmProfile.add(8.90, 2300);
        m_rpmProfile.add(9.56, 2400);
        m_rpmProfile.add(10.18, 2500);
        m_rpmProfile.add(10.91, 2600);
        m_rpmProfile.add(12.14, 2600);
        m_rpmProfile.add(12.82, 2650);
        
        m_angleProfile = new SetPointProfile();
        /* PRACTICE
        m_angleProfile.add(5.15, 56);
        m_angleProfile.add(6.1, 56);
        m_angleProfile.add(7.53, 53);
        m_angleProfile.add(8.2, 52);
        m_angleProfile.add(9.11, 50);
        m_angleProfile.add(10.01, 48);
        m_angleProfile.add(10.55, 47);
        m_angleProfile.add(11.1, 47);
        m_angleProfile.add(11.93,  47);
        m_angleProfile.add(12.5, 46);
        m_angleProfile.add(13.1, 46);
        m_angleProfile.add(13.45, 44);
        */
        /* OLD COMPETITION
        m_angleProfile.add(4.8, 63);
        m_angleProfile.add(6.08, 65);
        m_angleProfile.add(6.83, 57);
        m_angleProfile.add(7.48, 55);
        m_angleProfile.add(8.36, 53);
        m_angleProfile.add(8.90, 52);
        m_angleProfile.add(9.56, 51);
        m_angleProfile.add(10.18, 49);
        m_angleProfile.add(10.91, 48.5);
        m_angleProfile.add(11.55, 48.5);
        m_angleProfile.add(12.14, 48);
        m_angleProfile.add(12.82, 48);
        */
        m_angleProfile.add(4.8, 68);
        m_angleProfile.add(6.08, 65);
        m_angleProfile.add(6.83, 63);
        m_angleProfile.add(7.48, 60.75);
        m_angleProfile.add(8.36, 57);
        m_angleProfile.add(8.90, 54);
        m_angleProfile.add(9.56, 53);
        m_angleProfile.add(10.18, 49);
        m_angleProfile.add(10.91, 48.5);
        m_angleProfile.add(11.55, 48.5);
        m_angleProfile.add(12.14, 48);
        m_angleProfile.add(12.82, 48);
    }
    
    public void setBatterShot(boolean value){
    	m_batterShot = value;
    }
    
    public boolean hasBall(){
    	return m_indexer.ballInShooter();
    }
    
    public boolean AtRate(){
    	return m_shooterWheels.atRate();
    }
    
    public double TurretAngle(){
    	return m_turret.getAngle();
    }
    
    public ShooterPosition TurretPosition(){
    	return m_turret.getPosition();
    }
    
    /**
     * Sets the RPM for the shooter, how fast to spin the shooter wheels.
     */
    public void setShooterRPM(double RPM){
    	m_shooterWheels.setShooterRPM(RPM);
    }
    
    public void Spin(){
        m_shooterWheels.Spin();
        m_indexer.stop();        
    }

    public void Shoot(){
        //if (m_shooterWheels.atRate()){
        	m_indexer.shoot();
        	m_shooterWheels.Spin();     

    	/*}
        else{
        	m_indexer.stop();
        }*/
    }
    
    public void StopWheels(){
    	m_shooterWheels.Stop();
    	m_indexer.stop();
    }

    public void load(){
        m_shooterWheels.Load();
        m_indexer.load();
    }

    public void MoveTurretPosition(ShooterPosition position){
    	m_turret.setPosition(position);
    	m_lights.setLights(position == ShooterPosition.Aiming ? Relay.Value.kForward : Relay.Value.kReverse, SmartDashboard.getNumber("Cam Light Brightness", .5));
    }
    
    public void resetRangeFinding(){
    	m_rangeSnapshot = 0;
    	m_rangeWait.reset();
    	m_rangeFilter.reInitialize(0);
    }
    
    /**
     * This tells the shooter how far it is from the goal, so that it can automatically aim accordingly.
     */
    /*
    public void setDistance(){ 
    	if(m_batterShot){
    		m_turret.setAimingAngle(SmartDashboard.getNumber("Angle Setpoint", 60));
    	}
    	else if(m_rangeSnapshot == 0){
    		m_turret.setAimingAngle(60);
    		if(!(m_turret.AtSetpoint() && SmartDashboard.getBoolean("TargetFound", true))){
    			m_rangeWait.reset();
    			m_rangeFilter.reInitialize(0);
    		}
    		else
    			m_rangeFilter.get(SmartDashboard.getNumber("Range", 8));
    			
    		if(m_turret.AtSetpoint() && m_turret.getAngle() > 45 && m_rangeWait.get() > 0.25){
    			Logger.addMessage("Range Found");
    			m_rangeSnapshot = m_rangeFilter.get();
    			//double angle = m_rangeTable.get(m_rangeSnapshot);
    			double angle = Math.atan(16 / (6.184 + m_rangeSnapshot)) * (180.0 / Math.PI);
            	m_turret.setAimingAngle(angle);
            	SmartDashboard.putNumber("target Angle", angle);
    		}
    	} 
    	else{
        	double angle = Math.atan(16 / (6.184 + m_rangeSnapshot)) * (180.0 / Math.PI);
        	m_turret.setAimingAngle(angle);
        	SmartDashboard.putNumber("target Angle", angle);
    	}
    	
    	//m_turret.setAimingAngle(SmartDashboard.getNumber("Angle Setpoint", 60));
    	m_shooterWheels.setShooterRPM(m_batterShot ? 5500 : 5500);
    }
    */
    
    public void setDistance(){
    	if(m_batterShot){
    		m_turret.setAimingAngle(SmartDashboard.getNumber("Angle Setpoint", 60));
    		m_shooterWheels.setShooterRPM(SmartDashboard.getNumber("Shooting Scalar", 2500));
    	}
    	else{
    		
    		//double distance = SmartDashboard.getNumber("Range", 8.175); //M4
    		//double dragFactor = 1.014 + (distance - 5) * 0.008 / 75; //M6
    		//double targetHeight = 8.083 - (8 + 17 * Math.sin(m_turret.getAngle() * (Math.PI / 180.0)))/12; //M7
    		//double angle = SmartDashboard.getNumber("Angle Setpoint", 60);//Math.atan(2 * targetHeight / distance) * (180.0 / Math.PI); //A2
    		//double launchSpeed = dragFactor * Math.sqrt(2 * 32.174 * targetHeight + distance * distance * 32.174 / (2 * targetHeight)); //A1
    		//double rpm = SmartDashboard.getNumber("Shooting Scalar", 3000);//launchSpeed * (180.0 / Math.PI) * 2.5; //A4
    		
    		
    		double range = SmartDashboard.getNumber("Range", 8.2);
    		double angle = Math.abs(m_angleProfile.get(range));
    		double rpm = Math.abs(m_rpmProfile.get(range));
    		
    		
    		m_rangeSnapshot = 1;
    		m_turret.setAimingAngle(angle);
    		m_shooterWheels.setShooterRPM(rpm);
    	}
    	SmartDashboard.putNumber("target Angle", m_turret.getSetPoint());
    }
    
    public void startShootTest(){
    	this.MoveTurretPosition(ShooterPosition.Aiming);
    	m_turret.setAimingAngle(50);
    }
    
    public void stopShootTest(){
    	this.MoveTurretPosition(ShooterPosition.Stored);
    }
    
    public void shooterManualTest(){
    	m_turret.setAimingAngle(SmartDashboard.getNumber("Angle Setpoint", 50));
    	m_shooterWheels.setShooterRPM(SmartDashboard.getNumber("Shooting Scalar", 3000));
    	this.MoveTurretPosition(ShooterPosition.Aiming);
    	this.Spin();
    }
    
    public void shooterManualStop(){
    	this.MoveTurretPosition(ShooterPosition.Stored);
    	this.StopWheels();
    }
    
    public double getShooterRPM(){
    	return m_shooterWheels.getShooterRPM();
    }
    
    public void setTurretManual(boolean manual){
    	m_turret.setManual(manual);
    }
    
    public void setTurretManualPower(double power){
    	m_turret.setManualPower(power);
    }
    
    public boolean turretAtPosition(){
    	return m_turret.AtSetpoint();
    }
    
    public boolean readyToShoot(){
    	if(!m_batterShot)
    		return m_turret.AtSetpoint() && m_rangeSnapshot != 0 && m_shooterWheels.atRate();
    	else
    		return m_shooterWheels.atRate();
    }
    
    public boolean turretAtShootAngle(){
    	if(m_rangeSnapshot == 0 && !m_batterShot)
    		return false;
    	else
    		return m_turret.AtSetpoint() && m_turret.getPosition() == ShooterPosition.Aiming;
    }
    
}


