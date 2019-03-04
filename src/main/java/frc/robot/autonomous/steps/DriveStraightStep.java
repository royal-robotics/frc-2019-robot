package frc.robot.autonomous.steps;

import org.apache.logging.log4j.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.autonomous.AutoStep;
import frc.robot.subsystems.drivebase.*;

public class DriveStraightStep extends AutoStep {
    private final DriveController _driveController;

    // We don't construct it until the autoStep starts
    //private TankFollower _tankFollower = null;

    public DriveStraightStep(Marker markerParent, DriveController driveController, double distance) {
        super(markerParent);

        _driveController = driveController;
    }

    @Override
    protected void initialize() {
        _driveController.followMotionProfile(() -> {
            _driveController.drive(0.0, 0.0);
            this.complete();
        });
    }

    @Override
    public void stop() {
        _driveController.stop();
    }
}