package frc.robot.autonomous.steps;

import org.apache.logging.log4j.*;
import frc.libs.autonomous.AutoStep;
import frc.robot.subsystems.drivebase.*;

public class DriveStraightStep extends AutoStep {
    private final DriveController _driveController;
    private final double _distance;

    public DriveStraightStep(Marker markerParent, DriveController driveController, double distance) {
        super(markerParent);

        _driveController = driveController;
        _distance = distance;
    }

    @Override
    protected void initialize() {
        TankTrajectory tankTrajectory = new TankTrajectory(_distance, 100.0, false);
        _driveController.followTankTrajectory(tankTrajectory, () -> {
            _driveController.drive(0.0, 0.0);
            this.complete();
        });
    }

    @Override
    public void stop() {
        _driveController.stop();
    }
}