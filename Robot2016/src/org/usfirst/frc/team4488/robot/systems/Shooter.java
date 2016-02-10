package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.components.Indexer;
import org.usfirst.frc.team4488.robot.components.ShooterPosition;
import org.usfirst.frc.team4488.robot.components.ShooterWheels;
import org.usfirst.frc.team4488.robot.components.Turret;


public class Shooter {
	private ShooterWheels m_shooterWheels;
    private Indexer m_indexer;
    private Turret m_turret;

    /// <summary>
    /// Initializes Shooter member variables.
    /// </summary>
    public Shooter()
    {
        m_shooterWheels = new ShooterWheels();
        m_indexer = new Indexer();
        //m_turret = new Turret();
    }
    
    public Boolean hasBall(){
    	return m_indexer.ballInShooter();
    }
    
    public Boolean ShotBall(){
    	return m_shooterWheels.ballShot();
    }
    
    public Boolean AtRate(){
    	return m_shooterWheels.atRate();
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
    
    public void Load(){
    	m_shooterWheels.Load();
    	m_indexer.load();
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
    public void MovePosition(ShooterPosition position){
    	m_turret.setPosition(position);
    }
    
    /*
     * This tells the shooter how far it is from the goal, so that it can automatically aim accordingly.
     */
    public void setDistance(double distance){
    	double currentAngle = m_turret.getAngle();
    	double heightChange = 8.083 - (8 + 18 * Math.sin(currentAngle / (180 / Math.PI))) / 12; 
    	double speed = Math.sqrt(2*32.174* heightChange);
    	double targetAngle = Math.atan(2 * heightChange / distance) * (180 / Math.PI);
    	m_shooterWheels.setShooterRPM(speed * (180 / Math.PI));   	
    	m_turret.setPosition(ShooterPosition.Aiming);
    }
}


