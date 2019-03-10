package frc.robot.subsystems.drivebase;

import java.io.*;
import java.time.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.motionprofile.*;

public class TankTrajectory {
    public IMotionProfile leftProfile;
    public IMotionProfile rightProfile;

    public TankTrajectory(double distance, double acceleration, boolean invert) {
        this.leftProfile = new LinearMotionProfile(distance, 80.0, acceleration, invert);
        this.rightProfile = new LinearMotionProfile(distance, 80.0, acceleration, invert);
    }

    public TankTrajectory(String trajectoryName) {
        try {
            String leftTrajectoryName = trajectoryName + "-left";
            this.leftProfile = new CsvFileMotionProfile(getTrajectoryFile(leftTrajectoryName));

            String rightTrajectoryName = trajectoryName + "-right";
            this.rightProfile = new CsvFileMotionProfile(getTrajectoryFile(rightTrajectoryName));
        } catch (Exception e) {
            System.out.println("This is really bad and everything is broken sorry :(");
            this.leftProfile = null;
            this.rightProfile = null;
        }
    }

    public Duration duration() {
        // Both profiles should have the same duration
        return leftProfile.duration();
    }

    private static File getTrajectoryFile(String templateName) {
        String templateFilename = templateName + ".trajectory.csv";
        return new File(Filesystem.getDeployDirectory(), templateFilename);
    }
}