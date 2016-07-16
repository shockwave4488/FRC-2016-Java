package org.usfirst.frc.team4488.robot.systems;

import java.util.TreeMap;

import org.usfirst.frc.team4488.robot.components.CameraLights;
import org.usfirst.frc.team4488.robot.components.Indexer;
import org.usfirst.frc.team4488.robot.components.PressureSensor;
import org.usfirst.frc.team4488.robot.components.ShooterPosition;
import org.usfirst.frc.team4488.robot.components.ShooterWheels;
import org.usfirst.frc.team4488.robot.components.Turret;

import JavaRoboticsLib.ControlSystems.SetPointProfile;
import JavaRoboticsLib.Utility.InputFilter;
import JavaRoboticsLib.Utility.Logger;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Shooter {
	private ShooterWheels m_shooterWheels;
    private Indexer m_indexer;
    private Turret m_turret;
	private PressureSensor m_ballSensor;
    
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
        m_ballSensor = new PressureSensor();
        
        m_rangeFilter = new InputFilter();
        m_rangeWait = new Timer();
        m_rangeWait.start();
        m_batterShot = false;
        
        m_rpmProfile = new SetPointProfile();
        m_rpmProfile.add(6.5,2200);
        m_rpmProfile.add(7.1, 2200);
        m_rpmProfile.add(8.39, 2300);
        m_rpmProfile.add(9.1, 2400);
        m_rpmProfile.add(9.7, 2400);
        m_rpmProfile.add(10.3, 2600);
        m_rpmProfile.add(10.7, 2650);

        m_angleProfile = new SetPointProfile();
        m_angleProfile.add(6.5, 67);
        m_angleProfile.add(7.1, 67);
        m_angleProfile.add(8.39, 62);
        m_angleProfile.add(9.1, 59);
        m_angleProfile.add(9.7, 58);
        m_angleProfile.add(10.3, 51);
        m_angleProfile.add(10.7, 51);
        
    }
    
    public void setBatterShot(boolean value){
    	m_batterShot = value;
    }
    
    public boolean hasBall(){
    	return m_indexer.ballInShooter();
    }
    
    public boolean atSpeed(){
    	return m_shooterWheels.atSpeed();
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
        //m_indexer.spin();     
    }
    
    public void Stir(){
    	m_shooterWheels.Stop();
    	m_indexer.spin();
    }

    public void Shoot(){
        //if (m_shooterWheels.atSpeed()){
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
        m_ballSensor.resetSnapshot();
    }
    
    public void startLoad(){
    	m_ballSensor.setNormal();
    }

    public void MoveTurretPosition(ShooterPosition position){
    	m_turret.setPosition(position);
    }
    
    public void resetRangeFinding(){
    	m_rangeSnapshot = 0;
    	m_rangeWait.reset();
    	m_rangeFilter.reInitialize(0);
    }
    
	public void setDistance() {
		double range = SmartDashboard.getNumber("Range", 8.2);
		double angle = Math.abs(m_angleProfile.get(range));
		double rpm = Math.abs(m_rpmProfile.get(range));

		m_rangeSnapshot = 1;
		m_turret.setAimingAngle(angle);
		m_shooterWheels.setShooterRPM(rpm);// + m_ballSensor.getRPMAdjust());//
											// * m_ballSensor.getScalar());
		SmartDashboard.putNumber("target Angle", m_turret.getSetPoint());
	}
    
    public void batterShot(){
		m_rangeSnapshot = 1;
    	m_turret.setAimingAngle(SmartDashboard.getNumber("Angle Setpoint", 69));
    	m_shooterWheels.setShooterRPM(SmartDashboard.getNumber("Shooting Scalar", 1812));// + m_ballSensor.getRPMAdjust());
    }
    
    public void startShootTest(){
    	this.MoveTurretPosition(ShooterPosition.Aiming);
    	this.Spin();
    	m_turret.setAimingAngle(SmartDashboard.getNumber("Angle Setpoint", 50));
    	m_shooterWheels.setShooterRPM(SmartDashboard.getNumber("Shooting Scalar", 3000));
    }
    
    public void stopShootTest(){
    	this.MoveTurretPosition(ShooterPosition.Stored);
    	this.StopWheels();
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
    	return m_turret.AtSetpoint() && m_shooterWheels.atSpeed() && m_turret.getAngle() > 10;
    }
    
    public boolean turretAtShootAngle(){
    	if(m_rangeSnapshot == 0 && !m_batterShot)
    		return false;
    	else
    		return m_turret.AtSetpoint() && m_turret.getPosition() == ShooterPosition.Aiming;
    }
    
    public void deJam(){
    	m_shooterWheels.deJam();
    	m_indexer.load();
    }
    
    public String getInfo(){
    	return m_turret.getAngle() + ":" + m_shooterWheels.getShooterRPM() + ":" + SmartDashboard.getNumber("Range", -1) + ":" + m_ballSensor.getSnapshot();
    }
}


