package frc.robot.subsystems.drivebase;

import java.io.*;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.*;
import frc.libs.components.*;
import frc.libs.motionprofile.IMotionProfile.*;

public class TankFollowerLogger {
    private final SpeedController _motor;
    private final RoyalEncoder _encoder;
    private final Gyro _gyro;
    private PrintStream _fileOutput;

    public TankFollowerLogger(String name, SpeedController motor, RoyalEncoder encoder, Gyro gyro) {
        _motor = motor;
        _encoder = encoder;
        _gyro = gyro;

        try
        {
            File file = createNewFile(name);
            _fileOutput = new PrintStream(file);

            _fileOutput.print("time");
            _fileOutput.print(", position");
            _fileOutput.print(", velocity");
            _fileOutput.print(", acceleration");
            _fileOutput.print(", heading");

            _fileOutput.print(", position");
            _fileOutput.print(", velocity");
            _fileOutput.print(", heading");
            _fileOutput.print(", power");
            _fileOutput.print(", headingError");
            _fileOutput.print(", distanceError\n");
        }
        catch (Exception e)
        {
            _fileOutput = null;
            System.err.println("Failed to create follower logger: " + e.getMessage());
        }
    }

    public void writeMotorUpdate(Segment expected, double headingError, double distanceError) {
        // We failed to setup the logging file for some reason
        if (_fileOutput == null)
            return;

        _fileOutput.printf(
            "%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f\n",
            (double)expected.time.toMillis(),
            expected.position,
            expected.velocity,
            expected.acceleration,
            expected.heading,
            _encoder.getDistance(),
            _encoder.getVelocity(),
            _gyro.getAngle(),
            _motor.get(),
            headingError,
            distanceError);
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
