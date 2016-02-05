

package org.usfirst.frc.team4488.robot;

import org.usfirst.frc.team4488.robot.systems.*;
import org.usfirst.frc.team4488.robot.systems.Drive;

public class AutonomousManager
{
	private SystemsManagement robotSystems = new SystemsManagement();
    private Drive m_drive = new Drive();

    public enum autonDefense
    {
        Portcullis,
        DriveOvers,
        ChevalDeFries,
        None
    };

    public enum autonPosition
    {
        Pos1,
        Pos2,
        Pos3,
        Pos4,
        Pos5,
        Spybot
    };

    public enum autonAction
    {
        HighGoal,
        LowGoal,
        None
    }
    public autonDefense autoDefense = autonDefense.None;
    public autonDefense getAutoDefense(){
    	return autoDefense;
    }
    public void setAutoDefense(autonDefense newVal){
    	autoDefense=newVal;
    }
    public autonPosition autoPosition = autonPosition.Spybot;
    public autonPosition getAutoPosition(){
    	return autoPosition;
    }
    public void setAutoPosition(autonPosition newAutoPosition){
    	autoPosition = newAutoPosition;
    }
    public autonAction autoAction = autonAction.None;
    public autonAction getAutoAction(){
    	return autoAction;
    }
    public void setAutoAction(autonAction newAction){
    	
    }
    /// <summary>
/// Moves the robot to a specified distance
/// </summary>
/// <param name="distance"></param>
private void moveToDistance(int distance)
{
    m_drive.resetEncoders();
    while (m_drive.getDistances() < distance)
    {
        m_drive.SetPowers(1, 1);

    }
    m_drive.SetPowers(0, 0);
    m_drive.resetEncoders();
}


/// <summary>
/// Turns the robot to a specified angle, given in Degrees
/// </summary>
/// <param name="angle"></param>
private void turnToAngle(double angle, double tolerance)
{

    while (!m_drive.angleWithinSpecs(angle, tolerance))
    {

        if (m_drive.getBotAngle() > angle)
        {
            m_drive.SetPowers(-1, -1);
        }
        else
        {
            m_drive.SetPowers(1, 1);
        }

    }

}

public void runSequence()
{
    switch (autoDefense)
    {
        case autonDefense.Portcullis:
            autonDefensePortcullis();
            break;
        case autonDefense.ChevalDeFries:
            autonDefenseChevalDeFries();
            break;
        case autonDefense.None:
            autonDefenseNone();
            break;
        case autonDefense.DriveOvers:
            autonDefenseDriveOvers();
            break;
    }
    switch (autoPosition)
    {
        case autonPosition.Pos1:
            autonPosition1();
            break;
        case autonPosition.Pos2:
            autonPosition2();
            break;
        case autonPosition.Pos3:
            autonPosition3();
            break;
        case autonPosition.Pos4:
            autonPosition4();
            break;
        case autonPosition.Pos5:
            autonPosition5();
            break;
        case autonPosition.Spybot:
            autonPositionSpybot();
            break;
    }
    switch (autoSecondary)
    {
        case autonSecondaryAction.HighGoal:
            autonActionHighGoal();
            break;
        case autonSecondaryAction.LowGoal:
            autonActionLowGoal();
            break;
        case autonSecondaryAction.None:
            autonActionNone();
            break;
    	}
	}

	/// <summary>
	/// Primary Function for handling the portcullis defense in autonomous
	/// </summary>
	private void autonDefensePortcullis()
	{
	    m_manipulator.LowDefense();
	    moveToDistance(9 /*Some arbitrary distance, to be modified later*/);
	while (m_drive.getDistances() < 4/*Some arbitrary distance, to be modified later*/)
	{
	    m_drive.SetPowers(0.3, 0.3);
	    m_manipulator.HighDefense();
	}
	moveToDistance(9/*Some arbitrary distance, to be modified later*/);
	}
	/// <summary>
	/// Primary Function for handling the Cheval de Fries defense in autonomous
	/// </summary>
	private void autonDefenseChevalDeFries()
	{
	    moveToDistance(9 /*Some arbitrary distance, to be modified later*/);
	m_manipulator.LowDefense();
	moveToDistance(3 /*Some arbitrary short distance, to be modified later*/);
	m_manipulator.LoadIntake();
	moveToDistance(10 /*Some arbitrary short distance, to be modified later*/);
	}
	/// <summary>
	/// Primary Function for situations with no defenses to breach (Spybot) in autonomous
	/// </summary>
	private void autonDefenseNone()
	{
	
	}
	/// <summary>
	/// Primary Function for handling any defense requiring no special components other than drive in autonomous
	/// </summary>
	private void autonDefenseDriveOvers()
	{
	    moveToDistance(30 /*Some arbitrary distance, to be modified later*/);
	}
	
	private void autonPosition1()
	{
	    turnToAngle(0, 1);
	    moveToDistance(10 /*Some arbitrary distance, to be modified later*/);
	turnToAngle(-50, 1);
	moveToDistance(10 /*Some arbitrary distance, to be modified later*/);
	}
	private void autonPosition2()
	{
	    turnToAngle(0, 1);
	    moveToDistance(10 /*Some arbitrary distance, to be modified later*/);
	turnToAngle(-50, 1);
	moveToDistance(10 /*Some arbitrary distance, to be modified later*/);
	}
	private void autonPosition3()
	{
	    turnToAngle(-10, 1);
	    moveToDistance(7 /*Some arbitrary distance, to be modified later*/);
	turnToAngle(0, 1);
	moveToDistance(3 /*Some arbitrary distance, to be modified later*/);
	}
	private void autonPosition4()
	{
	    turnToAngle(10, 1);
	    moveToDistance(7 /*Some arbitrary distance, to be modified later*/);
	turnToAngle(0, 1);
	moveToDistance(3 /*Some arbitrary distance, to be modified later*/);
	}
	private void autonPosition5()
	{
	    turnToAngle(-8, 1);
	    moveToDistance(12 /*Some arbitrary distance, to be modified later*/);
	turnToAngle(60, 1);
	moveToDistance(6 /*Some arbitrary distance, to be modified later*/);
	}
	private void autonPositionSpybot()
	{
	    turnToAngle(7, 1);
	    moveToDistance(10 /*Some arbitrary distance, to be modified later*/);
	turnToAngle(130, 1);
	moveToDistance(3 /*Some arbitrary distance, to be modified later*/);
	}
	
	private void autonActionHighGoal()
	{
	
	    m_shooter.MovePosition(Components.ShooterPosition.Aiming);
	    m_shooter.Spin();
	    while (!m_shooter.ShotBall)
	    {
	        m_shooter.Shoot();
	    }
	    
	}
	private void autonActionLowGoal()
	{
	    moveToDistance(5 /*Arbitrary Value to be modified later*/);
	    m_manipulator.OutputIntake();
	}
	private void autonActionNone()
	{
	    
	}


}
    