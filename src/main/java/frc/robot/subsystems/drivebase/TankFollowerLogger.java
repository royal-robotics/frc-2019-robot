package frc.robot.subsystems.drivebase;

import java.io.*;
import java.time.*;

import frc.libs.components.*;
import frc.libs.motionprofile.IMotionProfile.*;

public class TankFollowerLogger {
    private final DriveBase _driveBase;
    private PrintStream _fileOutput;
    
    private final RoyalGyroOffset _gyro;
    private final RoyalEncoderOffset _leftEncoder;
    private final RoyalEncoderOffset _rightEncoder;

    public TankFollowerLogger(DriveBase driveBase, RoyalGyroOffset gyro) {
        _driveBase = driveBase;
        _gyro = gyro;
        _leftEncoder = new RoyalEncoderOffset(_driveBase.leftEncoder);
        _rightEncoder = new RoyalEncoderOffset(_driveBase.rightEncoder);

        try
        {
            _fileOutput = new PrintStream(createNewFile());
            writeHeaders(_fileOutput);
        }
        catch (Exception e)
        {
            _fileOutput = null;
            System.err.println("Failed to create follower logger: " + e.getMessage());
        }
    }

    private static void writeHeaders(PrintStream fileOutput)
    {
        if (fileOutput == null)
            return;

        fileOutput.print("time");
        fileOutput.print(", left");
        fileOutput.print(", left");
        fileOutput.print(", left");
        fileOutput.print(", left");
        fileOutput.print(", left");
        fileOutput.print(", left");

        fileOutput.print(", right");
        fileOutput.print(", right");
        fileOutput.print(", right");
        fileOutput.print(", right");
        fileOutput.print(", right");
        fileOutput.print(", right");
        
        fileOutput.print(", heading");
        fileOutput.print(", heading");
        fileOutput.print(", heading");
        fileOutput.print("\n");

        // Left
        fileOutput.print("time");
        fileOutput.print(", position-target");
        fileOutput.print(", position-measured");
        fileOutput.print(", velocity-target");
        fileOutput.print(", velocity-measured");
        fileOutput.print(", power-output");
        fileOutput.print(", power-adjustment");

        // Right
        fileOutput.print(", position-target");
        fileOutput.print(", position-measured");
        fileOutput.print(", velocity-target");
        fileOutput.print(", velocity-measured");
        fileOutput.print(", power-output");
        fileOutput.print(", power-adjustment");

        // Heading
        fileOutput.print(", target");
        fileOutput.print(", measured");
        fileOutput.print(", adjustment");
        fileOutput.print("\n");
    }

    public void writeMotorUpdate(Duration time, Segment leftSegment, double leftAdjustment, Segment rightSegment, double rightAdjustment, double headingAdjustment) {
        // We failed to setup the logging file for some reason
        if (_fileOutput == null)
            return;

        // Time
        _fileOutput.print((double)time.toMillis());

        // Left
        _fileOutput.printf(
            ",%.2f,%.2f,%.2f,%.2f,%.2f,%.2f",
            leftSegment.position,
            _leftEncoder.getDistance(),
            leftSegment.velocity,
            _leftEncoder.getVelocity(),
            _driveBase.leftDrive.get(),
            leftAdjustment);
            
        // Right
        _fileOutput.printf(
            ",%.2f,%.2f,%.2f,%.2f,%.2f,%.2f",
            rightSegment.position,
            _rightEncoder.getDistance(),
            rightSegment.velocity,
            _rightEncoder.getVelocity(),
            _driveBase.rightDrive.get(),
            rightAdjustment);

        _fileOutput.printf(
            ",%.2f,%.2f,%.2f\n",
            leftSegment.heading,
            _gyro.getAngle(),
            headingAdjustment);
    }

    private static File createNewFile() throws IOException {
        final String folderPath = "/home/lvuser/follower";
        File dir = new File(folderPath);
        dir.mkdir();

        for (int i = 0; true; i++) {
            String fileName = "/home/lvuser/follower/TankFollower_" + i + ".csv";
            File file = new File(fileName);
            if (!file.exists())
            {
                file.createNewFile();
                return file;
            }
        }
    }
}
