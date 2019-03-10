package frc.robot.autonomous.steps;

import org.apache.logging.log4j.*;
import frc.libs.autonomous.AutoStep;
import frc.robot.subsystems.drivebase.*;

public class DriveStraightStep extends AutoStep {
    private final DriveController _driveController;
    private final double _distance;
    private final double _maxVelocity;
    private final double _maxAcceleration;
    private final boolean _invert;

    public DriveStraightStep(Marker markerParent, DriveController driveController, double distance, double maxVelocity, double maxAcceleration) {
        super(markerParent);

        _driveController = driveController;
        if (distance < 0.0) {
            _distance = -distance;
            _invert = true;
        }
        else {
            _distance = distance;
            _invert = false;
        }
        _maxVelocity = Math.abs(maxVelocity);
        _maxAcceleration = Math.abs(maxAcceleration);
    }

    @Override
    protected void initialize() {
        TankTrajectory tankTrajectory = new TankTrajectory(_distance, this._maxVelocity, this._maxAcceleration, this._invert);
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