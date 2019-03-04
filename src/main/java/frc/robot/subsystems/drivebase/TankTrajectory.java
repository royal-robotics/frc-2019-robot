package frc.robot.subsystems.drivebase;

import java.time.*;
import frc.libs.motionprofile.*;

public class TankTrajectory {
    public final IMotionProfile leftProfile;
    public final IMotionProfile rightProfile;

    public TankTrajectory() {
        this.leftProfile = new LinearMotionProfile(50.0, 100.0, 50.0);
        this.rightProfile = new LinearMotionProfile(50.0, 100.0, 50.0);
    }

    public Duration duration() {
        return leftProfile.duration();
    }
}