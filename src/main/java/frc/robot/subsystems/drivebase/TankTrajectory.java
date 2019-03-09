package frc.robot.subsystems.drivebase;

import java.time.*;
import frc.libs.motionprofile.*;

public class TankTrajectory {
    public IMotionProfile leftProfile;
    public IMotionProfile rightProfile;

    public TankTrajectory(double distance, double acceleration, boolean invert) {
        // this.leftProfile = new LinearMotionProfile(distance, 100.0, acceleration, invert);
        // this.rightProfile = new LinearMotionProfile(distance, 100.0, acceleration, invert);

        try {
            this.leftProfile = new CsvFileMotionProfile("/home/lvuser/deploy/tank-drive-trajectory-left.csv");
            this.rightProfile = new CsvFileMotionProfile("/home/lvuser/deploy/tank-drive-trajectory-right.csv");
        } catch (Exception e) {
            System.out.println("This is really bad and everything is broken sorry :(");
            this.leftProfile = null;
            this.rightProfile = null;
        }
    }

    public Duration duration() {
        return leftProfile.duration();
    }
}