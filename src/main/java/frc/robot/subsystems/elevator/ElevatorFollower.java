package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj.*;
import frc.libs.motionprofile.*;
import frc.libs.motionprofile.IMotionProfile.Segment;

public class ElevatorFollower extends SimpleFollower {

    private final ElevatorPositionHolder _holder;

    public ElevatorFollower(ElevatorPositionHolder holder, Encoder encoder, PIDOutput motor) {
        super(encoder, motor);
        _holder = holder;
    }

    @Override
    protected void complete() {

        System.out.println("elevator follower complete");

        Segment segment = this.motionProfile.getSegment(this.motionProfile.duration());
        _holder.setSetpoint(segment.position);
        _holder.enable();

        super.complete();
    }
}