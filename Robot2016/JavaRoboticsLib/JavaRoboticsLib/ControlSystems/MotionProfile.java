package JavaRoboticsLib.ControlSystems;

import java.util.ArrayList;

public class MotionProfile {
	private ArrayList<MotionSetpoint> m_path;
	private double m_dt;
	
	/**
	 * creates a new MotionProfile that works under the constraints specified
	 * @param distance
	 * @param maxVelocity
	 * @param acceleration
	 * @param dt Time interval that each point will be calculated for
	 */
	public MotionProfile(double distance, double maxVelocity, double acceleration, double dt){
		m_dt = dt;
		m_path = new ArrayList<MotionSetpoint>();
		m_path.add(new MotionSetpoint(0, 0, acceleration));
		
        acceleration = Math.abs(acceleration) * Math.signum(distance);
        maxVelocity = Math.abs(maxVelocity)*Math.signum(distance);
        
        MotionSetpoint last = m_path.get(0);
        
        while (Math.abs(last.position) < Math.abs(distance))
        {
            MotionSetpoint next = new MotionSetpoint(last, dt);

            double stopDistance = last.position - last.velocity * last.velocity / (2 * -acceleration);
            double finalV = Math.sqrt(2*-acceleration*(distance - last.position) + last.velocity*last.velocity);

            if (Math.abs(stopDistance) > Math.abs(distance) || finalV * Math.signum(distance) > 0 || last.acceleration < 0)
            {
                last.acceleration = -acceleration;
                next = new MotionSetpoint(last, dt);
            }
            else if (Math.abs(next.velocity) >= Math.abs(maxVelocity))
            {
                last.velocity = maxVelocity;
                last.acceleration = (last.acceleration < 0) ? last.acceleration : 0;
                next = new MotionSetpoint(last, dt);
            }
            else next.acceleration = acceleration;

            m_path.add(next);
            last = next;
        }
        
        m_path.add(new MotionSetpoint(distance, 0, 0));
	}
	
    private MotionSetpoint get(double time){
        try
        {
            return m_path.get((int)(time / m_dt));
        }
        catch (Exception e)
        {
            return time < 0 ? m_path.get(0) : m_path.get(m_path.size() - 1);
        }
    }
    
    /**
     * Gets the expected acceleration at the given time
     * @param time
     * @return
     */
    public double getAcceleration(double time){
    	return get(time).acceleration;
    }
    
    /**
     * Gets the expected velocity at the given time
     * @param time
     * @return
     */
    public double getVelocity(double time){
    	return get(time).velocity;
    }
    
    /**
     * Gets the expected position at the given time
     * @param time
     * @return
     */
    public double getPosition(double time){
    	return get(time).position;
    }
}
