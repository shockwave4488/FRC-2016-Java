package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.components.CameraLights;
import org.usfirst.frc.team4488.robot.components.Indexer;
import org.usfirst.frc.team4488.robot.components.ShooterPosition;
import org.usfirst.frc.team4488.robot.components.ShooterWheels;
import org.usfirst.frc.team4488.robot.components.Turret;

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

    /// <summary>
    /// Initializes Shooter member variables.
    /// </summary>
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
    
    /*
     * Sets the RPM for the shooter, how fast to spin the shooter wheels.
     */
    public void setShooterRPM(double RPM){
    	m_shooterWheels.setShooterRPM(RPM);
    }
    

    /// <summary>
    /// Spins the shooter wheels 
    /// </summary>
    public void Spin(){
        m_shooterWheels.Spin();
        m_indexer.stop();        
    }

    /// <summary>
    /// Shoots ball if the shooter wheels are at the correct rate
    /// </summary>
    public void Shoot(){
        //if (m_shooterWheels.atRate()){
        	m_indexer.shoot();
        	m_shooterWheels.Spin();     

    	/*}
        else{
        	m_indexer.stop();
        }*/
    }
    
    /// <summary>
    /// Stops shooter and indexer wheels
    /// </summary>
    public void StopWheels(){
    	m_shooterWheels.Stop();
    	m_indexer.stop();
    }

    /// <summary>
    /// Sets both shooter and indexer wheels to load
    /// </summary>
    public void load(){
        m_shooterWheels.Load();
        m_indexer.load();
    }

    /// <summary>
    /// Sets turret position
    /// </summary>
    /// <param name="position">Turret position@param
    public void MoveTurretPosition(ShooterPosition position){
    	m_turret.setPosition(position);
    	m_lights.setLights(position == ShooterPosition.Aiming ? Relay.Value.kForward : Relay.Value.kReverse, SmartDashboard.getNumber("Cam Light Brightness", .5));
    }
    
    public void resetRangeFinding(){
    	m_rangeSnapshot = 0;
    	m_rangeWait.reset();
    	m_rangeFilter.reInitialize(0);
    }
    
    /*
     * This tells the shooter how far it is from the goal, so that it can automatically aim accordingly.
     */
    public void setDistance(){ 
    	if(m_batterShot){
    		m_turret.setAimingAngle(SmartDashboard.getNumber("Angle Setpoint", 62.5));
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
    			m_rangeSnapshot = m_rangeFilter.get();
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
    	m_shooterWheels.setShooterRPM(5500);
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
    		return m_turret.AtSetpoint() && m_shooterWheels.atRate();
    }
    
    public boolean turretAtShootAngle(){
    	if(m_rangeSnapshot == 0 && !m_batterShot)
    		return false;
    	else
    		return m_turret.AtSetpoint() && m_turret.getPosition() == ShooterPosition.Aiming;
    }
    
}


