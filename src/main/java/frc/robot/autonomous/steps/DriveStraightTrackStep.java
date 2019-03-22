package frc.robot.autonomous.steps;

import org.apache.logging.log4j.*;
import frc.libs.autonomous.AutoStep;
import frc.libs.components.Limelight;
import frc.robot.subsystems.drivebase.*;

public class DriveStraightTrackStep extends AutoStep {
    private final DriveController _driveController;
    private final Limelight _limelight;
    private final double _distance;
    private final double _maxVelocity;
    private final double _maxAcceleration;
    private final boolean _invert;

    public DriveStraightTrackStep(Marker markerParent, DriveController driveController, Limelight limelight, double distance, double maxVelocity, double maxAcceleration) {
        super(markerParent);

        _driveController = driveController;
        _limelight = limelight;
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
        _limelight.setPipeline(1);
        TankTrajectory tankTrajectory = new TankTrajectory(_distance, this._maxVelocity, this._maxAcceleration, this._invert);
        _driveController.followTankTrajectory(tankTrajectory, _limelight, () -> {
            _driveController.drive(0.0, 0.0);
            this.complete();
        });
    }

    @Override
    public void stop() {
        _limelight.setPipeline(0);
        _driveController.stop();
    }
}