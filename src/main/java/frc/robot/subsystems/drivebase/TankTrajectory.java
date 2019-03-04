package frc.robot.subsystems.drivebase;

import java.time.*;
import frc.libs.motionprofile.*;

public class TankTrajectory {
    public final IMotionProfile leftProfile;
    public final IMotionProfile rightProfile;

    public TankTrajectory(double distance, double acceleration, boolean invert) {
        this.leftProfile = new LinearMotionProfile(distance, 100.0, acceleration, invert);
        this.rightProfile = new LinearMotionProfile(distance, 100.0, acceleration, invert);
    }

    public Duration duration() {
        return leftProfile.duration();
    }
}