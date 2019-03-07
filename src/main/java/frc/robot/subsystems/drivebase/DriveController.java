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
        TankThrottleValues throttleValues;
        switch(Controls.DriveSystem.ControlMode.getSimpleName())
        {
            case "TankDrive":
                throttleValues = Controls.DriveSystem.TankDrive.getThrottleValues();
                break;
            case "DifferentialDrive":
                throttleValues = Controls.DriveSystem.DifferentialDrive.getThrottleValues();
                break;
            case "CheesyDrive":
                throttleValues = Controls.DriveSystem.CheesyDrive.getThrottleValues();
                break;
            default:
                throw new UnsupportedOperationException();
        }
        _driveBase.driveTank(throttleValues);

        if (Controls.DriveSystem.LiftRobot())
        {
            _driveBase.enableLift();
        }
        else
        {
            _driveBase.disableLift();
        }

        if (Controls.DriveSystem.autoTestForward())
        {
            followMotionProfile(76.0, 50.0, false, () -> {
                System.out.println("Current Distance: " + _driveBase.leftEncoder.getDistance());
                System.out.println("Current Velocity: " + _driveBase.leftEncoder.getVelocity());
            });
        }

        if (Controls.DriveSystem.autoTestBackward())
        {
            followMotionProfile(76.0, 50.0, true, () -> {
                System.out.println("Current Distance: " + _driveBase.leftEncoder.getDistance());
                System.out.println("Current Velocity: " + _driveBase.leftEncoder.getVelocity());
            });
        }
    }

    // TODO: This should take a tank profile as a parameter
    public void followMotionProfile(double distance, double acceleration, boolean invert, Runnable onComplete) {
        TankTrajectory tankTrajectory = new TankTrajectory(distance, acceleration, invert);
        _tankFollower = new TankFollower(_driveBase, tankTrajectory, onComplete);
    }

    public void drive(double left, double right) {
        _driveBase.driveTank(new TankThrottleValues(left, right));
    }

    public void stop() {
        _driveBase.driveTank(new TankThrottleValues(0.0, 0.0));

        if (_tankFollower != null)
        {
            _tankFollower.stop();
        }
    }

    @Override
    public void diagnosticPeriodic() {
        _driveBase.diagnosticPeriodic();
    }
}