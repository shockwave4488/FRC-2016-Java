package JavaRoboticsLib.ControlSystems;

import java.util.ArrayList;
import JavaRoboticsLib.ControlSystems.MotionController;
import JavaRoboticsLib.ControlSystems.Setpoint;

/**
* A custom profile for a control system. Can be used for any measurable quantity (speed, displacement, time, etc.)
*/
public class SetPointProfile implements MotionController
{
    private ArrayList<Setpoint> m_profile;
    private double m_setPoint;
    
    /**
    * Point to approach
    */
    @Override
	public double getSetPoint() {
        return m_setPoint;
    }

    @Override
	public void setSetPoint(double value) {
        m_setPoint = value;
    }

    /**
    * Constructs a new, empty {@link #SetPointProfile}
    */
    public SetPointProfile() {
        m_profile = new ArrayList<Setpoint>();
        setSetPoint(0);
    }

    private Setpoint nearestLess(double currentPoint) {
        Setpoint toReturn = m_profile.get(0);
        for (int i = 1;i < m_profile.size();i++)
        {
            if (m_profile.get(i).Point > currentPoint)
                return toReturn;
             
            toReturn = m_profile.get(i);
        }
        return toReturn;
    }

    private Setpoint nearestGreater(double currentPoint) {
        Setpoint toReturn = m_profile.get(m_profile.size() - 1);
        for (int i = m_profile.size() - 2;i >= 0;i--)
        {
            if (m_profile.get(i).Point < currentPoint)
                return toReturn;
             
            toReturn = m_profile.get(i);
        }
        return toReturn;
    }

    /**
    * Get the value of the path at the current point
    * 
    *  @param currentPoint point along the profile to measure
    *  @return value of the profile at that point
    */
    @Override
	public double get(double currentPoint){
        //be warned: here be lots of math
        Setpoint next = nearestGreater(currentPoint);
        Setpoint prev = nearestLess(currentPoint);
        double direction = Math.signum(getSetPoint() - currentPoint);
        if (next.Point == prev.Point)
        {
            return next.Value * direction;
        }
         
        //If they are the EXACT same point
        double setpointSlope = (prev.Value - next.Value) / (prev.Point - next.Point);
        setpointSlope = Double.isNaN(setpointSlope) ? 0 : setpointSlope;
        //Handle division by zero
        double ratioComplete = (currentPoint - prev.Point) / (next.Point - prev.Point);
        ratioComplete = Double.isNaN(ratioComplete) ? 0 : ratioComplete;
        //Handle division by zero
        double delta = ratioComplete * setpointSlope;
        return (delta + prev.Value) * direction;
    }

    /**
    * Adds a setpoint to the path
    * 
    *  @param point location of the setpoint
    *  @param value value of the setpoint
    */
    public void add(double point, double value){
        for (int i = 0;i < m_profile.size();i++)
        {
            if (m_profile.get(i).Point > point)
            {
                m_profile.add(i, new Setpoint(point,value));
                return ;
            }
             
        }
        //If the point is greatest or the list is empty
        m_profile.add(new Setpoint(point,value));
    }

}


