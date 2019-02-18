package frc.robot.autonomous.steps;

import frc.libs.autonomous.*;
import frc.robot.subsystems.Drivebase.DriveController;

import edu.wpi.first.wpilibj.Notifier;

public class DriveStep extends AutoStep {
    private final DriveController driveController;
    private final double speed;
    private final long msDriveTime;

    private Notifier finishDrivingNotifier = null;

    public DriveStep(DriveController driveController, double speed, long msDriveTime) {
        this.driveController = driveController;
        this.speed = speed;
        this.msDriveTime = msDriveTime;
    }

    @Override
    protected void initialize() {
        // When the step is invoked start the drive motors.
        driveController.drive(speed, speed);

        // Stop driving after the `msDriveTime` interval has passed.
        finishDrivingNotifier = new Notifier(() -> {
            driveController.drive(0.0, 0.0);
            this.complete();
        });
        finishDrivingNotifier.startSingle(msDriveTime);
    }

    @Override
    public void stop() {
        finishDrivingNotifier.stop();
        driveController.drive(0.0, 0.0);
    }
}