package frc.robot.subsystems.drivebase;

import frc.robot.*;
import com.ctre.phoenix.motorcontrol.*;

import frc.libs.components.Limelight;
import frc.libs.utils.RobotModels.*;
import frc.robot.subsystems.*;

public class DriveController implements IRobotController {
    private final DriveBase _driveBase = new DriveBase();
    public final Limelight limelight = new Limelight();
    private final DriveVisionRotater _driveVisionRotater;

    private TankFollower _tankFollower;

    public DriveController() {
        _driveVisionRotater = new DriveVisionRotater(limelight, _driveBase);
        _driveVisionRotater.disable();
    }

    @Override
    public void init(boolean isTeleop) {
        if (isTeleop)
            return;
            
        _driveBase.reset();
    }

    @Override
    public void teleopPeriodic() {
        if (!isFollowerRunning())
        {
            if (Controls.DriveSystem.autoTargetTest())
            {
                limelight.setPipeline(1);
                _driveVisionRotater.enable();
            }
            else
            {
                TankThrottleValues throttleValues = readThrottleValues();
                
                limelight.setPipeline(0);
                _driveVisionRotater.disable();
                _driveBase.driveTank(throttleValues);
            }
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
            TankTrajectory trajectory = new TankTrajectory(115.0, 60, 60, false);
            followTankTrajectory(trajectory);
        }

        if (Controls.DriveSystem.autoTestBackward())
        {
            TankTrajectory trajectory = new TankTrajectory("example", true);
            followTankTrajectory(trajectory);
        }
    }

    public void followTankTrajectory(TankTrajectory tankTrajectory) {
        followTankTrajectory(tankTrajectory, () -> {
            System.out.println("Current Distance: " + _driveBase.leftEncoder.getDistance());
            System.out.println("Current Velocity: " + _driveBase.leftEncoder.getVelocity());
        });
    }

    public TankFollower followTankTrajectory(TankTrajectory tankTrajectory, Runnable onCompleted) {
        // Stop the current follower if one is already running.
        if (isFollowerRunning())
            _tankFollower.stop();

        _tankFollower = new TankFollower(_driveBase, tankTrajectory, onCompleted);
        return _tankFollower;
    }

    public TankFollower followTankTrajectory(TankTrajectory tankTrajectory, Limelight limelight, Runnable onCompleted) {
        // Stop the current follower if one is already running.
        if (isFollowerRunning())
            _tankFollower.stop();

        _tankFollower = new TankFollower(_driveBase, tankTrajectory, limelight, onCompleted);
        return _tankFollower;
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

    public void setNeutralMode(NeutralMode mode) {
        _driveBase.setNeutralMode(mode);
    }

    public void stop() {
        if (_tankFollower != null)
        {
            _tankFollower.stop();
            _tankFollower = null;
        }

        _driveBase.driveTank(new TankThrottleValues(0.0, 0.0));
    }

    @Override
    public void diagnosticPeriodic() {
        _driveBase.diagnosticPeriodic();
    }
}