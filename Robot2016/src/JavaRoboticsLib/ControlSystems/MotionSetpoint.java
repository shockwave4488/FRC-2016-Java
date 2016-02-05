package JavaRoboticsLib.ControlSystems;

class MotionSetpoint   
{
    public MotionSetpoint() {
    }

    public double position;
    public double velocity;
    public double acceleration;
    
    public MotionSetpoint(double position, double velocity, double acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public MotionSetpoint(MotionSetpoint previous, double dt) {
        acceleration = previous.acceleration;
        velocity = previous.velocity + acceleration * dt;
        velocity = Math.round(velocity / (dt)) * dt;
        position = previous.position + (previous.velocity + velocity) * dt / 2;
        position = Math.round(position / (dt * dt * dt)) * (dt * dt * dt);
    }

}


