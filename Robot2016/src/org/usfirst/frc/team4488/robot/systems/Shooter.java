package org.usfirst.frc.team4488.robot.systems;

import org.usfirst.frc.team4488.robot.components.Indexer;
import org.usfirst.frc.team4488.robot.components.ShooterPosition;
import org.usfirst.frc.team4488.robot.components.ShooterWheels;
import org.usfirst.frc.team4488.robot.components.Turret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        m_turret = new Turret();
    }
    
    public Boolean HasBall(){
    	return m_indexer.ballInShooter();
    }
    
    public Boolean ShotBall(){
    	return m_shooterWheels.ballShot();
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
        if (m_shooterWheels.atRate()){
        	m_indexer.shoot();
    	}
        else{
        	m_indexer.stop();
        }
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
   /* public void Load(){
        m_shooterWheels.Load();
        m_indexer.load();
    }*/

    /// <summary>
    /// Sets turret position
    /// </summary>
    /// <param name="position">Turret position@param
    public void MovePosition(ShooterPosition position){
    	m_turret.setPosition(position);
    }
    
    public void MoveShooterWheels(double leftTrigger, double rightTrigger){
    	m_shooterWheels.MoveWheels(leftTrigger, rightTrigger);
    }
    
    public void Test(Boolean loadButton, Boolean shootButton, double leftTrigger, double rightTrigger){
    	if(m_indexer.ballInShooter()&&loadButton){
    		m_indexer.load();
    		m_shooterWheels.Load();
    		SmartDashboard.putString("State", "Loading");
    	}
    	else if(!m_indexer.ballInShooter()&&shootButton){
    		m_indexer.shoot();
    		m_shooterWheels.MoveWheels(leftTrigger, rightTrigger);
    		SmartDashboard.putString("State", "Shooting");
    	}
    	else{
        	m_indexer.stop();
    		m_shooterWheels.MoveWheels(leftTrigger, rightTrigger);
    		SmartDashboard.putString("State", "Idle/Spinning");
    	}

    }
    
    /*public void ShootTest(Boolean button){
    	if(!m_indexer.ballInShooter()&&button){
    		m_indexer.shoot();
    	}
    }*/
    
   /* public void setDistance(double distance){
    	double angle = m_turret.
    	double heightChange = 
    	double speed = Math.sqrt(2*32.174* heightChange);
    	m_shooterWheels.setShooterRPM();
    	
    	
    }*/

}
