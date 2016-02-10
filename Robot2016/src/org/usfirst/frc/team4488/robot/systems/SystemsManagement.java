

package org.usfirst.frc.team4488.robot.systems;
/// <summary>
/// Controls all non-drive systems through a state machine
/// </summary>
import org.usfirst.frc.team4488.robot.components.*;
import JavaRoboticsLib.Utility.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4488.robot.operator.*;
public class SystemsManagement
{
    private Shooter m_shooter;
    private Manipulator m_manipulator;
    private boolean m_load = false;
    private boolean m_charge = false;
    private boolean m_shoot = false;
    private boolean m_intake = false;
    private boolean m_defenseLow = false;
    private boolean m_defenseHigh = false;
    private ManipulatorState m_manipulatorState;
    private ShooterState m_shooterState;

    /// <summary>
    /// Button to load the shooter
    /// </summary>
    public void setLoadButton(boolean val){
    	m_load = val;
    }
    


    /// <summary>
    /// Button to start spinning the shooter
    /// </summary>
    public void setChargeButton(boolean val){
    	m_charge = val;
    }
    
    
    /// <summary>
    /// Button to shoot the ball
    /// </summary>
    public void setShootButton(boolean val){
    	m_shoot = val;
    }
    
    

    /// <summary>
    /// Button to move the manipulator and start
    /// </summary>
    public void setIntakeButton(boolean val){
    	m_intake = val;
    }

    /// <summary>
    /// Button to move the manipulator down to handle defenses
    /// </summary>
    public void setDefenseLowButton(boolean val){
    	m_defenseLow = val;
    }

    /// <summary>
    /// Button to move the manipulator up to handle defenses
    /// </summary>
    public void setDefenseHighButton(boolean val){
    	m_defenseHigh = val;
    }

    /// <summary>
    /// Creates all managed systems (<see cref="Shooter"/>, <see cref="Manipulator"/>)
    /// </summary>
    public SystemsManagement(Shooter shooter)//, Manipulator manipulator)
    {
        m_shooterState = ShooterState.Idle;
        m_manipulatorState = ManipulatorState.Idle;
    	m_shooter = shooter;
    	m_shooterState = ShooterState.Idle;
        //m_manipulator = new Manipulator();
        Logger.addMessage("SystemsManagement Initialized", 0);
    }

    /// <summary>
    /// The state the <see cref="Manipulator"/> is currently in
    /// </summary>
    public ManipulatorState getManipulatorState(){
    	return m_manipulatorState;
    }

    /// <summary>
    /// Different states of the <see cref="Manipulator"/>
    /// </summary>
    public enum ManipulatorState{
        Idle,
        Intake,
        Store,
        Load,
        Shoot,
        DefenseLow,
        DefenseHigh,
    }

    /// <summary>
    /// The state the <see cref="Shooter"/> is currently in
    /// </summary>
    public ShooterState getshooterState(){
    	return m_shooterState;
    }

    /// <summary>
    /// Runs the <see cref="SystemsManagement"/>
    /// </summary>
    public void Update(){        
        SmartDashboard.putString("Shooter State", m_shooterState.toString());
    	switch (m_shooterState)
        {
            case Idle:
                ShooterIdle();
                if (m_load && !m_shooter.hasBall()) //&& m_manipulatorState == ManipulatorState.Store)
                {
                    m_shooterState = ShooterState.Load;
                    Logger.addMessage("ShooterState set to Load from Idle",0);
                }
                if (m_shooter.hasBall()&& m_charge){
                	m_shooterState = ShooterState.Charge;
                }
                break;

            case Load:
                ShooterLoad();
                if (m_shooter.hasBall())
                {
                    m_shooterState = ShooterState.Idle;
                    Logger.addMessage("ShooterState set to Charge from Load",0);
                }
                break;
                
            case Charge:
                ShooterCharge();
                if (m_shoot && m_charge)
                {
                    m_shooterState = ShooterState.Shoot;
                    Logger.addMessage("ShooterState set to Shoot from Charge",0);
                }
                if (!m_charge)
                {
                    m_shooterState = ShooterState.Load;
                    Logger.addMessage("ShooterState set to Load from Charge",0);
                }
                break;

            case Shoot:
                ShooterShoot();
                if (!m_shoot)
                {
                    m_shooterState = ShooterState.Idle;
                    Logger.addMessage("ShooterState set to Idle from Shoot",0);
                }
                break;
        }
        }

        /*switch (m_manipulatorState)
        {
            case Idle:
                ManipulatorIdle();
                if (m_intake && !m_shooter.HasBall())
                {
                    m_manipulatorState = ManipulatorState.Intake;
                    Logger.addMessage("ManipulatorState set to Intake from Idle",0);
                }
                if (m_shoot && m_shooter.HasBall())
                {
                    m_manipulatorState = ManipulatorState.Shoot;
                    Logger.addMessage("ManipulatorState set to Shoot from Idle",0);
                }
                if (m_defenseLow)
                {
                    m_manipulatorState = ManipulatorState.DefenseLow;
                    Logger.addMessage("ManipulatorState set to DefenseLow from Idle",0);
                }
                if (m_defenseHigh)
                {
                    m_manipulatorState = ManipulatorState.DefenseHigh;
                    Logger.addMessage("ManipulatorState set to DefenseHigh from Idle",0);
                }
                break;

            case Intake:
                ManipulatorIntake();
                if (m_manipulator.IntakeHasBall())
                {
                    m_manipulatorState = ManipulatorState.Store;
                    Logger.addMessage("ManipulatorState set to Store from Intake",0);
                }
                if (!m_intake)
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Intake",0);
                }
                break;

            case Store:
                ManipulatorStore();
                if (m_load)
                {
                    m_manipulatorState = ManipulatorState.Load;
                    Logger.addMessage("ManipulatorState set to Load from Store",0);
                }
                if (m_defenseLow)
                {
                    m_manipulatorState = ManipulatorState.DefenseLow;
                    Logger.addMessage("ManipulatorState set to DefenseLow from Store",0);
                }
                if (m_defenseHigh)
                {
                    m_manipulatorState = ManipulatorState.DefenseHigh;
                    Logger.addMessage("ManipulatorState set to DefenseHigh from Store",0);
                }
                break;

            case Load:
                ManipulatorLoad();
                if (!m_manipulator.IntakeHasBall() && m_shooter.HasBall())
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Load",0);
                } 
                break;

            case Shoot:
                ManipulatorShoot();
                if (m_shooter.HasBall() || !m_shoot)
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Shoot",0);
                }
                break;
           
            case DefenseLow:
                ManipulatorDefenseLow();
                if (!m_defenseLow && m_manipulator.IntakeHasBall())
                {
                    m_manipulatorState = ManipulatorState.Store;
                    Logger.addMessage("ManipulatorState set to Store from DefenseLow",0);
                }
                if (!m_defenseLow && !m_manipulator.IntakeHasBall())
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from DefenseLow",0);
                }
                break;

            case DefenseHigh:
                ManipulatorDefenseHigh();
                if (!m_defenseHigh && m_manipulator.IntakeHasBall())
                {
                    m_manipulatorState = ManipulatorState.Store;
                    Logger.addMessage("ManipulatorState set to Store from DefenseHigh",0);
                }
                if (!m_defenseHigh && !m_manipulator.IntakeHasBall())
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from DefenseHigh",0);
                }
                break;
        }
        Logger.addMessage("SystemsManagement Update function completed",0);
    }*/

    /// <summary>
    /// Stops all wheels and sets turret down
    /// </summary>
    private void ShooterIdle()
    {
        m_shooter.StopWheels();
        //m_shooter.MovePosition(ShooterPosition.Stored);
    }

    /// <summary>
    /// Puts turret at load position and sets wheels to load if shooter has no ball. Stops all wheels and sets turret down if shooter has ball
    /// </summary>
    private void ShooterLoad(){
       m_shooter.load();
       //m_shooter.MovePosition(ShooterPosition.Load);
        
    }

    /// <summary>
    /// Spins shooter wheels and aims with turret
    /// </summary>
    private void ShooterCharge(){
        m_shooter.Spin();
        //m_shooter.MovePosition(ShooterPosition.Aiming);
    }

    /// <summary>
    /// Shoots ball
    /// </summary>
    private void ShooterShoot(){
    	m_shooter.Shoot();
    }
}

    /// <summary>
    /// Sets manipulator to inside the frame perimiter and waits
    /// </summary>
   /* private void ManipulatorIdle()
    {
        m_manipulator.stopIntake();
    }

    /// <summary>
    /// Moves manipulator down and runs wheels inwards
    /// </summary>
    private void ManipulatorIntake()
    {
        m_manipulator.spinIntake();
    }

    /// <summary>
    /// Leaves manipulator down and waits with the ball
    /// </summary>
    private void ManipulatorStore()
    {
        m_manipulator.stopIntake();
    }

    /// <summary>
    /// Moves the Manipulator back to pass the ball to the shooter
    /// </summary>
    private void ManipulatorLoad()
    {
        m_manipulator.loadIntake();
    }

    /// <summary>
    /// Moves the Manipulator out of the way of the shooter
    /// </summary>
    private void ManipulatorShoot()
    {
        m_manipulator.shoot();
    }

    /// <summary>
    /// Moves the manipulator down to deal with defenses
    /// </summary>
    private void ManipulatorDefenseLow()
    {
        m_manipulator.lowDefense();
    }

    /// <summary>
    /// moves the manipulator up to deal with defenses
    /// </summary>
    private void ManipulatorDefenseHigh()
    {
        m_manipulator.highDefense();
    }
}*/
