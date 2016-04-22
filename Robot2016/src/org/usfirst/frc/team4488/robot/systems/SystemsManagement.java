package org.usfirst.frc.team4488.robot.systems;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.usfirst.frc.team4488.robot.components.*;
import JavaRoboticsLib.FlowControl.Toggle;
import JavaRoboticsLib.Utility.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SystemsManagement
{
    private Shooter m_shooter;
    private Manipulator m_manipulator;
    private LEDController m_leds;
    
    private boolean m_charge = false;
    private boolean m_shoot = false;
    private boolean m_intake = false;
    private boolean m_lowGoalIntake = false;
    private boolean m_defenseLow = false;
    private boolean m_batterCharge = false;
    private boolean m_purge = false;
    
    private double m_armSemiManualPosition = 0;
    private Toggle m_intakeToggle;
    private ManipulatorState m_manipulatorState;
    private ShooterState m_shooterState;
    private PrintWriter m_logFile;
    
    private Timer m_shootTimer;
    private Timer m_manipTimer;   

    public void setPurgeButton(boolean val){
    	m_purge = val;
    }
    
    public void setBatterChargeButton(boolean val){
    	m_batterCharge = val;
    }
    
    public void setChargeButton(boolean val){
    	m_charge = val;
    }
    
    public void setShootButton(boolean val){
    	m_shoot = val;
    }
    
    public void setIntakeButton(boolean val){
    	m_intake = val;
    }

    public void setDefenseLowButton(boolean val){
    	m_defenseLow = val;
    }

    public void setSemiManualPosition(double value){
    	m_armSemiManualPosition = value;
    	m_manipulator.setArmSemiManualPosition(110.0 - (value * 130.0));
    }
    
    public void setLowGoalIntake(boolean value){
    	m_lowGoalIntake = value || m_lowGoalIntake;
    }
    
    public SystemsManagement(Shooter shooter, Manipulator manipulator)
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
        m_leds = new LEDController();
        try {
			m_logFile = new PrintWriter("/home/lvuser/Shooting.txt");
		} catch (FileNotFoundException e) {
			//java pls
		}
        m_logFile.println("-------- New Session --------");
        m_logFile.println("And Again");
        
        Logger.addMessage("SystemsManagement Initialized", 0);
    }

    public ManipulatorState getManipulatorState(){
    	return m_manipulatorState;
    }

    public ShooterState getShooterState(){
    	return m_shooterState;
    }

    public void Update(){        
        SmartDashboard.putString("Shooter State", m_shooterState.toString());
        SmartDashboard.putString("Manipulator State", m_manipulatorState.toString());
        m_intakeToggle.setState(m_intake);
        
        if(m_purge && !m_shooter.hasBall())
        	m_shooter.deJam();
        else
    	switch (m_shooterState)
        {
            case Idle:
                ShooterIdle();
                if (!m_shooter.hasBall() && m_manipulatorState == ManipulatorState.Store && !m_lowGoalIntake)
                {
                    m_shooterState = ShooterState.Load;
                    Logger.addMessage("ShooterState set to Load from Idle",0);
                }
                if (m_shooter.hasBall() && (m_charge || m_batterCharge)){
                	m_leds.setLEDs(LEDState.Charge);
                	m_shooter.resetRangeFinding();
                	m_shooterState = m_batterCharge ? ShooterState.BatterCharge : ShooterState.Charge;
                	Logger.addMessage("ShooterState set to " + (m_batterCharge ? "Batter" : "") + "Charge from Idle", 0);
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
                if(!m_shooter.readyToShoot()) m_shootTimer.reset();
                if (m_shoot && m_charge && m_shootTimer.get() > 0.25 && SmartDashboard.getBoolean("TargetFound", false))//&& m_shooter.readyToShoot())
                {
                	m_logFile.println(m_shooter.getInfo() + SmartDashboard.getNumber("Drive Speed Left", 0) + ":" + SmartDashboard.getNumber("Drive Speed Right", 0));
                	m_leds.setLEDs(LEDState.Shoot);
                    m_shooterState = ShooterState.Shoot;
                	m_shootTimer.reset();
                    Logger.addMessage("ShooterState set to Shoot from Charge",0);
                }
                if (!m_charge)
                {
                	m_leds.setLEDs(LEDState.Null);
                    m_shooterState = ShooterState.Idle;
                    Logger.addMessage("ShooterState set to Idle from Charge",0);
                }
                break;

            case Shoot:
                ShooterShoot();
                if (/*!m_shoot &&*/ m_shootTimer.get() > 0.25)
                {
                	m_leds.setLEDs(LEDState.Null);
                    m_shooterState = ShooterState.Idle;
                    Logger.addMessage("ShooterState set to Idle from Shoot",0);
                }
                break;
            case BatterCharge:
            	ShooterBatterCharge();
                if(!m_shooter.readyToShoot()) m_shootTimer.reset();
                if (m_shoot && m_batterCharge && m_shootTimer.get() > 0.25)//&& m_shooter.readyToShoot())
                {
                	m_logFile.println(m_shooter.getInfo() + SmartDashboard.getNumber("Drive Speed Left", 0) + ":" + SmartDashboard.getNumber("Drive Speed Right", 0));
                	m_leds.setLEDs(LEDState.Shoot);
                    m_shooterState = ShooterState.Shoot;
                	m_shootTimer.reset();
                    Logger.addMessage("ShooterState set to Shoot from BatterCharge",0);
                }
                if (!m_batterCharge)
                {
                	m_leds.setLEDs(LEDState.Null);
                    m_shooterState = ShooterState.Idle;
                    Logger.addMessage("ShooterState set to Idle from BatterCharge",0);
                }
            	
		default:
			break;
        }
        

        switch (m_manipulatorState)
        {
            case Idle:
                ManipulatorIdle();
                if (m_intakeToggle.getState() && !m_shooter.hasBall())
                {
                	m_leds.setLEDs(LEDState.Feed);
                    m_manipulatorState = ManipulatorState.Intake;
                    Logger.addMessage("ManipulatorState set to Intake from Idle",0);
                }
                if ((m_charge || m_batterCharge) && m_shooter.hasBall())
                {
                    m_manipulatorState = m_batterCharge ? ManipulatorState.BatterShot : ManipulatorState.Shoot;
                    Logger.addMessage("ManipulatorState set to " + (m_batterCharge ? "Batter" : "") + "Shoot from Idle",0);
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
                	m_lowGoalIntake = false;
                	m_leds.setLEDs(LEDState.Null);
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Intake",0);
                }
                break;

            case Store:
                ManipulatorStore();
                if (m_shooter.turretAtPosition() && m_shooter.TurretAngle() > 30 && !m_lowGoalIntake)
                {
                    m_manipulatorState = ManipulatorState.Load;
                    Logger.addMessage("ManipulatorState set to Load from Store",0);
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
                if(m_charge && m_lowGoalIntake){
                	m_manipulatorState = ManipulatorState.Output;
                }
                break;

            case Load:
                ManipulatorLoad();
                m_intakeToggle.force(false);
                if (m_shooter.hasBall())
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Load",0);
                    m_leds.setLEDs(LEDState.Null);
                } 
                break;

            case Shoot:
                ManipulatorShoot();
                if (!(m_shooterState.equals(ShooterState.Shoot) || m_shooterState.equals(ShooterState.Charge) || m_shooterState.equals(ShooterState.BatterCharge)))
                {
                    m_manipulatorState = ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from Shoot",0);
                }
                break;
           
           case DefenseLow:
                ManipulatorDefenseLow();
                if (!m_defenseLow)
                {
                    m_manipulatorState = m_manipulator.IntakeHasBall() ? ManipulatorState.Store : ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from DefenseLow",0);
                }
               
                break;

            case DefenseSemiManual:
                ManipulatorDefenseSemiManual();
                if (m_armSemiManualPosition < 0.05)
                {
                    m_manipulatorState = m_manipulator.IntakeHasBall() ? ManipulatorState.Store : ManipulatorState.Idle;
                    Logger.addMessage("ManipulatorState set to Idle from SemiManual",0);
                }
                break;
                
            case Output:
            	ManipulatorOutput();
            	if(!m_charge && m_manipulator.HasBall()){
            		m_manipulatorState = ManipulatorState.Store;
            	}
            	if(!m_charge && !m_manipulator.HasBall()){
            		m_manipulatorState = ManipulatorState.Idle;
            		m_intakeToggle.force(false);
            		m_lowGoalIntake = false;
            	}
            	break;
		case BatterShot:
			m_manipulator.batterCharge();
			if(!(m_shooterState.equals(ShooterState.Shoot) || m_shooterState.equals(ShooterState.Charge) || m_shooterState.equals(ShooterState.BatterCharge))){
				m_manipulatorState = ManipulatorState.Idle;
			}
			break;
		default:
			break;
          }
    }

    private void ShooterIdle()
    {
        m_shooter.StopWheels();
        m_shooter.MoveTurretPosition(ShooterPosition.Stored);
    }

    private void ShooterLoad(){
       m_shooter.load();
       m_shooter.MoveTurretPosition(ShooterPosition.Load);        
    }

    private void ShooterCharge(){
    	m_shooter.setDistance();
        m_shooter.Spin();
        m_shooter.MoveTurretPosition(ShooterPosition.Aiming);
    }

    private void ShooterShoot(){
    	m_shooter.Shoot();
    }

    private void ManipulatorIdle()
    {
    	m_lowGoalIntake = false;
        m_manipulator.stopIntake();
    }

    private void ManipulatorIntake()
    {
        m_manipulator.spinIntake();
    }

    private void ManipulatorStore()
    {
        m_manipulator.stopIntake();
    }

    private void ManipulatorLoad()
    {
        m_manipulator.loadIntake();
    }

    private void ManipulatorShoot()
    {
        m_manipulator.shoot();
    }
    
    public void Reset(){
    	Logger.addMessage("State Reset");
    	m_shooterState = ShooterState.Idle;
    	m_manipulatorState = ManipulatorState.Idle;
    }

    private void ManipulatorDefenseLow()
    {
        m_manipulator.lowDefense();
    }

    private void ManipulatorDefenseSemiManual()
    {
        m_manipulator.semiManualDefense();
    }
    
    private void ManipulatorOutput(){
    	if(m_shoot)
    		m_manipulator.outputIntake();
    	else
    		m_manipulator.readyOutput();
    }
        
    private void ShooterBatterCharge(){
    	m_shooter.batterShot();        
    	m_shooter.Spin();
        m_shooter.MoveTurretPosition(ShooterPosition.Aiming);
    }
    
    public void disabledInit(){
    	m_logFile.close();
    }
}
