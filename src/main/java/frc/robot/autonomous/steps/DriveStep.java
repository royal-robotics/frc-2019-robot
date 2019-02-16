package frc.robot.autonomous.steps;

import frc.robot.autonomous.*;
import frc.robot.subsystems.Drivebase.DriveController;
import java.util.*;

public class DriveStep extends AutoStep {
    private DriveController driveController;
    private double speed;
    private long msDriveTime;

    private Timer driveTimer = null;

    public DriveStep(DriveController driveController, double speed, long msDriveTime) {
        this.driveController = driveController;
        this.speed = speed;
        this.msDriveTime = msDriveTime;
    }

    @Override
    protected void initialize() {
        if (driveTimer == null) {
            driveTimer = new Timer();
        }

        driveController.drive(speed, speed);

        // Stop driving after specified time
        driveTimer.schedule(new TimerTask() {
            public void run() {
                stopDriving();
            }
        }, msDriveTime);
    }

    @Override
    public boolean isCompleted() {
        return driveTimer == null;
    }

    @Override
    public void stop() {
        stopDriving();
    }

    private void stopDriving() {
        driveController.drive(0.0, 0.0);
        driveTimer.cancel();
        driveTimer.purge();
        driveTimer = null;
    }
}