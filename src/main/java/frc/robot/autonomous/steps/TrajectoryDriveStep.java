package frc.robot.autonomous.steps;

import org.apache.logging.log4j.*;
import frc.libs.autonomous.*;
import frc.robot.subsystems.drivebase.*;

public class TrajectoryDriveStep extends AutoStep {
    private final DriveController _driveController;
    private final TankTrajectory _trajectory;

    private TankFollower _follower;

    public TrajectoryDriveStep(Marker parent, DriveController driveController, String trajectoryName, boolean invert) {
        super(parent);

        _driveController = driveController;
        _trajectory = new TankTrajectory(trajectoryName, invert);
    }

    @Override
    protected void initialize() {
        _follower = _driveController.followTankTrajectory(_trajectory, () -> {
            _driveController.drive(0.0, 0.0);
            complete();
        });
    }

    @Override
    public void stop() {
        _follower.stop();
    }
}