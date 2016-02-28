

package org.usfirst.frc.team4488.robot.systems;
/// <summary>
/// Controls all non-drive systems through a state machine
/// </summary>
import org.usfirst.frc.team4488.robot.components.*;

import JavaRoboticsLib.FlowControl.Toggle;
import JavaRoboticsLib.Utility.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4488.robot.operator.*;
public class SystemsManagement
{
    private Shooter m_shooter;
    private Manipulator m_manipulator;
    private boolean m_charge = false;
    private boolean m_shoot = false;
    private boolean m_intake = false;
    private boolean m_defenseLow = false;
    private double m_armSemiManualPosition = 0;
    private boolean m_reset = false;
    private Toggle m_intakeToggle;
    private ManipulatorState m_manipulatorState;
    private ShooterState m_shooterState;
    
    private Timer m_shootTimer;
    private Timer m_manipTimer;   

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
    
    public void setResetButton(boolean val){
    	m_reset = val;
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

    public void setSemiManualPosition(double value){
    	m_armSemiManualPosition = value;
    	m_manipulator.setArmSemiManualPosition(110.0 - (value * 130.0));
    }
    
    /// <summary>
    /// Creates all managed systems (<see cref="Shooter"/>, <see cref="Manipulator"/>)
    /// </summary>
    public SystemsManagement(Shooter shooter, Manipulator manipulator)//, Manipulator manipulator)
    {
        m_shooterState = ShooterState.Idle;
        m_manipulatorState = ManipulatorState.Idle;
    	m_shooter = shooter;
    	m_shootTimer = new Timer();
    	m_shootTimer.start();
    	m_manipTimer = new Timer();
    	m_manipTimer.start();
        m_manipulator = manipulator;
        m_intakeToggle = new Toggle();
        Logger.addMessage("SystemsManagement Initialized", 0);
    }

    /// <summary>
    /// The state the <see cref="Manipulator"/> is currently in
    /// </summary>
    public ManipulatorState getManipulatorState(){
    	return m_manipulatorState;
    }

    /// <summary>
    /// The state the <see cref="Shooter"/> is currently in
    /// </summary>
    public ShooterState getShooterState(){
    	return m_shooterState;
    }

    /// <summary>
    /// Runs the <see cref="SystemsManagement"/>
    /// </summary>
    public void Update(){        
        SmartDashboard.putString("Shooter State", m_shooterState.toString());
        SmartDashboard.putString("Manipulator State", m_manipulatorState.toString());
        m_intakeToggle.setState(m_intake);
        
    	switch (m_shooterState)
        {
            case Idle:
                ShooterIdle();
                if (!m_shooter.hasBall() && m_manipulatorState == ManipulatorState.Store)
                {
                    m_shooterState = ShooterState.Load;
                    Logger.addMessage("ShooterState set to Load from Idle",0);
                }
                if (m_shooter.hasBall()&& m_charge){
                	m_shooter.resetRangeFinding();
                	m_shooterState = ShooterState.Charge;
                	Logger.addMessage("ShooterState set to Charge from Idle", 0);
                	m_shootTimer.reset();
                }
                break;

            case Load:
                ShooterLoad();
                if (m_shooter.hasBall())
                {
                    m_shooterState = ShooterState.Idle;
                    Logger.addMessage("ShooterState set to Idle from Load",0);
                }
                break;
                
            case Charge:
                ShooterCharge();
                if(!m_shooter.AtRate()) m_shootTimer.reset();
                if (m_shoot && m_charge && m_shootTimer.get() > 2)//&& m_shooter.readyToShoot())
                {
                    m_shooterState = ShooterState.Shoot;
                	m_shootTimer.reset();
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
                if (/*!m_shoot &&*/ m_shootTimer.get() > 1)
                {
                    m_shooterState = ShooterState.Idle;
                    Logger.addMessage("ShooterState set to Idle from Shoot",0);
                }
                break;
        }
        

        switch (m_manipulatorState)
        {
            case Idle:
                ManipulatorIdle();
                if (m_intakeToggle.getState() && !m_shooter.hasBall())
                {
                    m_manipulatorState = ManipulatorState.Intake;
                    Logger.addMessage("ManipulatorState set to Intake from Idle",0);
                }
                if (m_charge && m_shooter.hasBall())
                {
                    m_manipulatorState = ManipulatorState.Shoot;
                    Logger.addMessage("ManipulatorState set to Shoot from Idle",0);
                }
                if (m_defenseLow)
                {
                    m_manipulatorState = ManipulatorState.DefenseLow;
                    Logger.addMessage("ManipulatorState set to DefenseLow from Idle",0);
                }
                if (m_armSemiManualPosition > 0.05)
                {
                    m_manipulatorState = ManipulatorState.DefenseSemiManual;
                    Logger.addMessage("ManipulatorState set to SemiManual from Idle",0);
                }
                break;

            case Intake:
                ManipulatorIntake();
                if (m_manipulator.IntakeHasBall())
                {
                    m_manipulatorState = ManipulatorState.Store;
                    Logger.addMessage("ManipulatorState set to Store from Intake",0);
                }
                if (!m_intakeToggle.getState())
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Intake",0);
                }
                break;

            case Store:
                ManipulatorStore();
                if (m_shooter.turretAtPosition() && m_manipulator.getArmAngle() > 20)
                {
                    m_manipulatorState = ManipulatorState.Load;
                    Logger.addMessage("ManipulatorState set to Load from Store",0);
                }
                break;

            case Load:
                ManipulatorLoad();
                m_intakeToggle.force(false);
                if (m_shooter.hasBall())
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Load",0);
                } 
                break;

            case Shoot:
                ManipulatorShoot();
                if (!m_charge)
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Shoot",0);
                }
                break;
           
           case DefenseLow:
                ManipulatorDefenseLow();
                if (!m_defenseLow)
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from DefenseLow",0);
                }
               
                break;

            case DefenseSemiManual:
                ManipulatorDefenseSemiManual();
                if (m_armSemiManualPosition < 0.05)
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from SemiManual",0);
                }
                break;
                     }
    }

    /// <summary>
    /// Stops all wheels and sets turret down
    /// </summary>
    private void ShooterIdle()
    {
        m_shooter.StopWheels();
        m_shooter.MoveTurretPosition(ShooterPosition.Stored);
    }

    /// <summary>
    /// Puts turret at load position and sets wheels to load if shooter has no ball. Stops all wheels and sets turret down if shooter has ball
    /// </summary>
    private void ShooterLoad(){
       m_shooter.load();
       m_shooter.MoveTurretPosition(ShooterPosition.Load);        
    }

    /// <summary>
    /// Spins shooter wheels and aims with turret
    /// </summary>
    private void ShooterCharge(){
    	m_shooter.setDistance();
        m_shooter.Spin();
        m_shooter.MoveTurretPosition(ShooterPosition.Aiming);
    }

    /// <summary>
    /// Shoots ball
    /// </summary>
    private void ShooterShoot(){
    	m_shooter.Shoot();
    }


    /// <summary>
    /// Sets manipulator to inside the frame perimiter and waits
    /// </summary>
    private void ManipulatorIdle()
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
    
    public void Reset(){
    	if(m_reset){
    		m_shooterState = ShooterState.Idle;
    		m_manipulatorState = ManipulatorState.Idle;
    	}
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
    private void ManipulatorDefenseSemiManual()
    {
        m_manipulator.semiManualDefense();
    }
}
