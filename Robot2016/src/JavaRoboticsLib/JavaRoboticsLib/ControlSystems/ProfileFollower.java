package JavaRoboticsLib.ControlSystems;

import edu.wpi.first.wpilibj.Timer;

/**
 * A MotionController designed to follow a {@link #MotionProfile}
 *
 */
public class ProfileFollower implements MotionController {
	private double m_kv, m_ka, m_setpoint;
	
	private Timer m_timer;
	private MotionProfile m_profile;
	private MotionController m_correction;
	
	public ProfileFollower(MotionProfile profile, double kv, double ka){
		this(profile, kv, ka, null);
	}
	
	public ProfileFollower(MotionProfile profile, double kv, double ka, MotionController correction){
		m_profile = profile;
		m_correction = correction;
		m_kv = kv;
		m_ka = ka;
		m_timer = new Timer();
		m_timer.start();
		setSetPoint(m_profile.getPosition(Double.MAX_VALUE));
	}
	
	@Override
	/**
	 * Gets the motor power, using the input for the correction sensor
	 */
	public double get(double input) {
        double velocity = m_kv*m_profile.getVelocity(m_timer.get());
        double acceleration = m_ka*m_profile.getAcceleration(m_timer.get());
        
        double correction = 0;
        if(!m_correction.equals(null))
        	correction = m_correction.get(m_profile.getPosition(m_timer.get()) - input);
        
        boolean reverse = m_profile.getPosition(m_timer.get()) > getSetPoint();

        return (velocity + acceleration + correction) * (reverse ? -1 : 1);
	}

	@Override
	public double getSetPoint() {
		return m_setpoint;
	}

	@Override
	public void setSetPoint(double value) {
		m_timer.reset();
		m_setpoint = value;
	}

}
