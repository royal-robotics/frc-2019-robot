package frc.robot.subsystems.drivebase;

import frc.robot.Controls;
import frc.libs.utils.RobotModels.*;
import frc.robot.subsystems.*;

public class DriveController implements IRobotController {
    private final DriveBase _driveBase = new DriveBase();

    private TankFollower _tankFollower;

    @Override
    public void init() {
        _driveBase.reset();
    }

    @Override
    public void teleopPeriodic() {
        if (!isFollowerRunning())
        {
            TankThrottleValues throttleValues = readThrottleValues();
            _driveBase.driveTank(throttleValues);
        }

        if (Controls.DriveSystem.LiftRobotFront())
        {
            _driveBase.enableFrontLift();
        }
        else
        {
            _driveBase.disableFrontLift();
        }

        if (Controls.DriveSystem.LiftRobotBack())
        {
            _driveBase.enableBackLift();
        }
        else
        {
            _driveBase.disableBackLift();
        }

        if (Controls.DriveSystem.autoTestForward())
        {
            TankTrajectory tankTrajectory = new TankTrajectory("example");
            followTankTrajectory(tankTrajectory);
        }

        if (Controls.DriveSystem.autoTestBackward())
        {
            TankTrajectory tankTrajectory = new TankTrajectory(76.0, 50.0, true);
            followTankTrajectory(tankTrajectory);
        }
    }

    public void followTankTrajectory(TankTrajectory tankTrajectory) {
        followTankTrajectory(tankTrajectory, () -> {
            System.out.println("Current Distance: " + _driveBase.leftEncoder.getDistance());
            System.out.println("Current Velocity: " + _driveBase.leftEncoder.getVelocity());
        });
    }

    public void followTankTrajectory(TankTrajectory tankTrajectory, Runnable onCompleted) {
        // Stop the current follower if one is already running.
        if (isFollowerRunning())
            _tankFollower.stop();

        _tankFollower = new TankFollower(_driveBase, tankTrajectory, onCompleted);
    }

    public void drive(double left, double right) {
        _driveBase.driveTank(new TankThrottleValues(left, right));
    }

    public boolean isFollowerRunning() {
        return _tankFollower != null && _tankFollower.isRunning();
    }

    private static TankThrottleValues readThrottleValues() {
        switch(Controls.DriveSystem.ControlMode.getSimpleName())
        {
            case "TankDrive":
                return Controls.DriveSystem.TankDrive.getThrottleValues();
            case "DifferentialDrive":
                return Controls.DriveSystem.DifferentialDrive.getThrottleValues();
            case "CheesyDrive":
                return Controls.DriveSystem.CheesyDrive.getThrottleValues();
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void stop() {
        _driveBase.driveTank(new TankThrottleValues(0.0, 0.0));

        if (_tankFollower != null)
        {
            _tankFollower.stop();
            _tankFollower = null;
        }
    }

    @Override
    public void diagnosticPeriodic() {
        _driveBase.diagnosticPeriodic();
    }
}