

/*package org.usfirst.frc.team4488.robot.systems;
/// <summary>
/// Controls all non-drive systems through a state machine
/// </summary>
import org.usfirst.frc.team4488.robot.components.*;
import JavaRoboticsLib.Utility.*;
import org.usfirst.frc.team4488.robot.operator.*;
import org.usfirst.frc.team4488.robot.*;
class SystemsManagement
{
    private Shooter m_shooter;
    private Manipulator m_manipulator;
    private Controllers m_input;
    private boolean load = false;
    private boolean charge = false;
    private boolean shoot = false;
    private boolean intake = false;
    private boolean defenseLow = false;
    private boolean defenseHigh = false;
    private ManipulatorState manipulatorState = ManipulatorState.Idle;

    /// <summary>
    /// Button to load the shooter
    /// </summary>
    public void setLoadButton(boolean val){
    	load = val;
    }

    /// <summary>
    /// Button to start spinning the shooter
    /// </summary>
    public void setChargeButton(boolean val){
    	charge = val;
    }

    /// <summary>
    /// Button to shoot the ball
    /// </summary>
    public void setShootButton(boolean val){
    	shoot = val;
    }

    /// <summary>
    /// Button to move the manipulator and start
    /// </summary>
    public void setIntakeButton(boolean val){
    	intake = val;
    }

    /// <summary>
    /// Button to move the manipulator down to handle defenses
    /// </summary>
    public void setDefenseLowButton(boolean val){
    	defenseLow = val;
    }

    /// <summary>
    /// Button to move the manipulator up to handle defenses
    /// </summary>
    public void setDefenseHighButton(boolean val){
    	defenseHigh = val;
    }

    /// <summary>
    /// Creates all managed systems (<see cref="Shooter"/>, <see cref="Manipulator"/>)
    /// </summary>
    public SystemsManagement(){
        shooterState = ShooterState.Idle;
        m_shooter = new Shooter();
        m_manipulator = new Manipulator();
        Logger.addMessage("SystemsManagement Initialized", 0);
    }

    /// <summary>
    /// The state the <see cref="Manipulator"/> is currently in
    /// </summary>
    public ManipulatorState getManipulatorState(){
    	return m_manipulator.
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
    public ShooterState shooterState { get; private set; }

    /// <summary>
    /// Possible states the <see cref="Shooter"/> can be in
    /// </summary>
    public enum ShooterState{
        Idle,
        Load,
        Charge,
        Shoot,
    }

    /// <summary>
    /// Runs the <see cref="SystemsManagement"/>
    /// </summary>
    public void Update(){
        Logger.addMessage("SystemsManagement Update function called",0);
        
        switch (shooterState)
        {
            case Idle:
                ShooterIdle();
                if (load && manipulatorState == ManipulatorState.Store)
                {
                    shooterState = ShooterState.Load;
                    Logger.addMessage("ShooterState set to Load from Idle",0);
                }
                break;

            case Load:
                ShooterLoad();
                if (charge && m_shooter.HasBall)
                {
                    shooterState = ShooterState.Charge;
                    Logger.addMessage("ShooterState set to Charge from Load",0);
                }
                break;
                
            case Charge:
                ShooterCharge();
                if (shoot && charge)
                {
                    shooterState = ShooterState.Shoot;
                    Logger.addMessage("ShooterState set to Shoot from Charge",0);
                }
                if (!ChargeButton)
                {
                    shooterState = ShooterState.Load;
                    Logger.addMessage("ShooterState set to Load from Charge",0);
                }
                break;

            case Shoot:
                ShooterShoot();
                if (m_shooter.HasBall)
                {
                    shooterState = ShooterState.Idle;
                    Logger.addMessage("ShooterState set to Idle from Shoot",0);
                }
                break;
        }
        switch (manipulatorState)
        {
            case Idle:
                ManipulatorIdle();
                if (intake && !m_shooter.HasBall)
                {
                    manipulatorState = ManipulatorState.Intake;
                    Logger.addMessage("ManipulatorState set to Intake from Idle",0);
                }
                if (shoot && m_shooter.HasBall)
                {
                    manipulatorState = ManipulatorState.Shoot;
                    Logger.addMessage("ManipulatorState set to Shoot from Idle",0);
                }
                if (defenseLow)
                {
                    manipulatorState = ManipulatorState.DefenseLow;
                    Logger.addMessage("ManipulatorState set to DefenseLow from Idle",0);
                }
                if (defenseHigh)
                {
                    manipulatorState = ManipulatorState.DefenseHigh;
                    Logger.addMessage("ManipulatorState set to DefenseHigh from Idle",0);
                }
                break;

            case Intake:
                ManipulatorIntake();
                if (m_manipulator.IntakeHasBall)
                {
                    manipulatorState = ManipulatorState.Store;
                    Logger.addMessage("ManipulatorState set to Store from Intake",0);
                }
                if (!intake)
                {
                    manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Intake",0);
                }
                break;

            case Store:
                ManipulatorStore();
                if (load)
                {
                    manipulatorState = ManipulatorState.Load;
                    Logger.addMessage("ManipulatorState set to Load from Store",0);
                }
                if (defenseLow)
                {
                    manipulatorState = ManipulatorState.DefenseLow;
                    Logger.addMessage("ManipulatorState set to DefenseLow from Store",0);
                }
                if (defenseHigh)
                {
                    manipulatorState = ManipulatorState.DefenseHigh;
                    Logger.addMessage("ManipulatorState set to DefenseHigh from Store",0);
                }
                break;

            case Load:
                ManipulatorLoad();
                if (!m_manipulator.IntakeHasBall && m_shooter.HasBall)
                {
                    manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Load",0);
                } 
                break;

            case Shoot:
                ManipulatorShoot();
                if (m_shooter.HasBall || !shoot)
                {
                    manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Shoot",0);
                }
                break;
           
            case DefenseLow:
                ManipulatorDefenseLow();
                if (!defenseLow && m_manipulator.IntakeHasBall)
                {
                    manipulatorState = ManipulatorState.Store;
                    Logger.addMessage("ManipulatorState set to Store from DefenseLow",0);
                }
                if (!defenseLow && !m_manipulator.IntakeHasBall)
                {
                    manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from DefenseLow",0);
                }
                break;

            case DefenseHigh:
                ManipulatorDefenseHigh();
                if (!defenseHigh && m_manipulator.IntakeHasBall)
                {
                    manipulatorState = ManipulatorState.Store;
                    Logger.addMessage("ManipulatorState set to Store from DefenseHigh",0);
                }
                if (!defenseHigh && !m_manipulator.IntakeHasBall)
                {
                    manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from DefenseHigh",0);
                }
                break;
        }
        Logger.addMessage("SystemsManagement Update function completed",0);
    }

    /// <summary>
    /// Stops all wheels and sets turret down
    /// </summary>
    private void ShooterIdle()
    {
        m_shooter.StopWheels();
        m_shooter.MovePosition(ShooterPosition.Stored);
    }

    /// <summary>
    /// Puts turret at load position and sets wheels to load if shooter has no ball. Stops all wheels and sets turret down if shooter has ball
    /// </summary>
    private void ShooterLoad()
    {
        if (!m_shooter.HasBall)
        {
            m_shooter.Load();
            m_shooter.MovePosition(ShooterPosition.Load);
        }
        else
        {
            m_shooter.StopWheels();
            m_shooter.MovePosition(ShooterPosition.Stored);
        }
    }

    /// <summary>
    /// Spins shooter wheels and aims with turret
    /// </summary>
    private void ShooterCharge()
    {
        m_shooter.Spin();
        m_shooter.MovePosition(ShooterPosition.Aiming);
    }

    /// <summary>
    /// Shoots ball
    /// </summary>
    private void ShooterShoot()
    {
        m_shooter.Shoot();
    }

    /// <summary>
    /// Sets manipulator to inside the frame perimiter and waits
    /// </summary>
    private void ManipulatorIdle()
    {
        m_manipulator.StopIntake();
    }

    /// <summary>
    /// Moves manipulator down and runs wheels inwards
    /// </summary>
    private void ManipulatorIntake()
    {
        m_manipulator.SpinIntake();
    }

    /// <summary>
    /// Leaves manipulator down and waits with the ball
    /// </summary>
    private void ManipulatorStore()
    {
        m_manipulator.StopIntake();
    }

    /// <summary>
    /// Moves the Manipulator back to pass the ball to the shooter
    /// </summary>
    private void ManipulatorLoad()
    {
        m_manipulator.LoadIntake();
    }

    /// <summary>
    /// Moves the Manipulator out of the way of the shooter
    /// </summary>
    private void ManipulatorShoot()
    {
        m_manipulator.Shoot();
    }

    /// <summary>
    /// Moves the manipulator down to deal with defenses
    /// </summary>
    private void ManipulatorDefenseLow()
    {
        m_manipulator.LowDefense();
    }

    /// <summary>
    /// moves the manipulator up to deal with defenses
    /// </summary>
    private void ManipulatorDefenseHigh()
    {
        m_manipulator.HighDefense();
    }
}*/
