package frc.robot.subsystems.drivebase;

import java.io.*;
import frc.libs.components.*;
import frc.libs.motionprofile.IMotionProfile.*;

public class TankFollowerLogger {
    private final RoyalEncoder _encoder;
    private PrintStream _fileOutput;

    public TankFollowerLogger(String name, RoyalEncoder encoder) {
        _encoder = encoder;

        try
        {
            File file = createNewFile(name);
            _fileOutput = new PrintStream(file);
        }
        catch (Exception e)
        {
            _fileOutput = null;
            System.err.println("Failed to create follower logger: " + e.getMessage());
        }
    }

    public void writeMotorUpdate(Segment expected, double distanceError, double velocityFeed) {
        // We failed to setup the logging file for some reason
        if (_fileOutput == null)
            return;

        // Expected: time, position, velocity, acceleration
        // Actual: position, velocity, positionError, velocityFeed
        _fileOutput.printf(
            "%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f\n",
            (double)expected.time.toMillis(),
            expected.position,
            expected.velocity,
            expected.acceleration,
            _encoder.getDistance(),
            _encoder.getVelocity(),
            distanceError,
            velocityFeed);
    }

    private static File createNewFile(String name) throws IOException {

        File dir = new File("/home/lvuser/follower");
        dir.mkdir();

        for (int i = 0; true; i++) {
            File file = new File("/home/lvuser/follower/" + name + "_" + i + ".csv");
            if (!file.exists())
            {
                file.createNewFile();
                return file;
            }
        }
    }
}
