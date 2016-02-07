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
    /// Sets turret position
    /// </summary>
    /// <param name="position">Turret position@param
    public void MovePosition(ShooterPosition position){
    	m_turret.setPosition(position);
    }
    
}


