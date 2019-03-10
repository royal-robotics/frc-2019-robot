package frc.robot.subsystems.drivebase;

import java.io.*;
import java.time.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.motionprofile.*;

public class TankTrajectory {
    public IMotionProfile leftProfile;
    public IMotionProfile rightProfile;

    public TankTrajectory(double distance, double maxVelocity, double acceleration, boolean invert) {
        this.leftProfile = new LinearMotionProfile(distance, maxVelocity, acceleration, invert);
        this.rightProfile = new LinearMotionProfile(distance, maxVelocity, acceleration, invert);
    }

    public TankTrajectory(String trajectoryName, boolean invert) {
        try {
            String leftTrajectoryName = trajectoryName + "-left";
            String rightTrajectoryName = trajectoryName + "-right";

            if (invert) {
                String tempLeft = leftTrajectoryName;
                leftTrajectoryName = rightTrajectoryName;
                rightTrajectoryName = tempLeft;
            }

            this.leftProfile = new CsvFileMotionProfile(getTrajectoryFile(leftTrajectoryName), invert);
            this.rightProfile = new CsvFileMotionProfile(getTrajectoryFile(rightTrajectoryName), invert);
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