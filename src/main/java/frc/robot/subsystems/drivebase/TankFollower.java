package frc.robot.subsystems.drivebase;

import java.time.*;
import com.google.common.base.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.components.*;
import frc.libs.motionprofile.IMotionProfile.*;
import static frc.libs.utils.RobotModels.*;

public class TankFollower implements ITrajectoryFollower {
    private final DriveBase _driveBase;
    private final TankTrajectory _tankTrajectory;
    private final Limelight _limelight;
    private final Runnable _onComplete;

    private final TankFollowerLogger _logger;
    private final RoyalGyroOffset _gyro;
    private final RoyalEncoderOffset _leftEncoder;
    private final RoyalEncoderOffset _rightEncoder;
    
    private final ErrorContext _leftError;
    private final ErrorContext _rightError;

    private final Stopwatch _stopwatch;
    private final Notifier _controlLoop;
    private boolean _isFinished = false;

    public TankFollower(DriveBase driveBase, TankTrajectory tankTrajectory, Runnable onComplete) {
        this(driveBase, tankTrajectory, null, onComplete);
    }

    public TankFollower(DriveBase driveBase, TankTrajectory tankTrajectory, Limelight limelight, Runnable onComplete)
    {
        _driveBase = driveBase;
        _tankTrajectory = tankTrajectory;
        _limelight = limelight;
        _onComplete = onComplete;

        
        _leftEncoder = new RoyalEncoderOffset(_driveBase.leftEncoder);
        _rightEncoder = new RoyalEncoderOffset(_driveBase.rightEncoder);
        _gyro = new RoyalGyroOffset(_driveBase.gyro);
        // Set the the encoder offset to match the initial heading of the trajectory
        _gyro.setOffset(tankTrajectory.leftProfile.getSegment(Duration.ZERO).heading);

        _logger = new TankFollowerLogger(driveBase, _gyro);

        _leftError = new ErrorContext();
        _rightError = new ErrorContext();

        final double ControlLoopIntervalMs = 10.0;
        _stopwatch = Stopwatch.createStarted();
        _controlLoop = new Notifier(() -> controlLoop());
        _controlLoop.startPeriodic(ControlLoopIntervalMs / 1000.0);
    }

    private void controlLoop() {
        Duration timeIndex = _stopwatch.elapsed();
        if (timeIndex.toNanos() > getDuration().toNanos())
        {
            complete();
            return;
        }

        Segment leftSegment = _tankTrajectory.leftProfile.getSegment(timeIndex);
        Segment rightSegment = _tankTrajectory.rightProfile.getSegment(timeIndex);
        double headingAdjustment = getHeadingAdjustment(leftSegment, rightSegment);
        double limelightAdjustment = getLimelightAdjustment();

        double leftAdjustment = getDistanceAdjustment(leftSegment, timeIndex, _leftEncoder, _leftError, headingAdjustment);
        double leftOutputFeed = getOutputFeed(leftSegment);
        double leftPower = leftOutputFeed + leftAdjustment + limelightAdjustment;

        if (Math.abs(leftPower) > 1.0)
            System.err.printf("Left output power set too high (%.2f)\n", leftPower);

        double rightAdjustment = getDistanceAdjustment(rightSegment, timeIndex, _rightEncoder, _rightError, -headingAdjustment);
        double rightOutputFeed = getOutputFeed(leftSegment);
        double rightPower = rightOutputFeed + rightAdjustment - limelightAdjustment;
        
        if (Math.abs(rightPower) > 1.0)
            System.err.printf("Right output power set too high (%.2f)\n", rightPower);

        _driveBase.driveTank(new TankThrottleValues(leftPower, rightPower));
        _logger.writeMotorUpdate(timeIndex, leftSegment, leftAdjustment, rightSegment, rightAdjustment, headingAdjustment);
    }

    private double getLimelightAdjustment() {
        if (_limelight == null)
            return 0.0;

        if (!_limelight.hasTarget())
            return 0.0;

        final double kP = 0.04;
        return _limelight.xTarget() * 0.04;
    }

    public double getOutputFeed(Segment segment) {
        final double velocityFeed = getVelocityFeed(segment.velocity);
        final double accelerationFeed = 0.0 * segment.acceleration;

        return velocityFeed + accelerationFeed;
    }

    public static double getVelocityFeed(double velocity) {
        // Constant Velocity Feed:
        // final double kVf = 1.35 / DriveBase.TopSpeed;
        // return (kVf * segment.velocity);

        // Linear Velocity Feed:
        final double m = 0.0056;
        final double b = 0.062;
        return (m * velocity) + b;

        // Quadratic Velocity Feed:
        // final double a = 0.056;
        // final double b = 0.0061;
        // final double c = -0.0000035;
        // return a + (b * velocity) + (c * Math.pow(c, 2));
    }

    public double getHeadingAdjustment(Segment leftTarget, Segment rightTarget) {
        // Both the left and right segments should have the same heading.
        final double angle = _gyro.getAngle();
        final double targetAngle = leftTarget.heading;
        final double headingErrorGyro = targetAngle - angle;

        // Calculate the heading error caused by position errors.
        // We subtract this from the observed angle error so we don't over compensate.
        final double leftPosError = leftTarget.position - _leftEncoder.getDistance();
        final double rightPosError = rightTarget.position - _rightEncoder.getDistance();
        final double positionErrorShift = leftPosError - rightPosError;
        final double headingErrorPosition = Math.toDegrees(Math.asin((positionErrorShift) / DriveBase.WheelbaseWidth));

        final double headingError = headingErrorGyro - headingErrorPosition;
        final double wheelbaseCircumference = 2 * Math.PI * DriveBase.WheelbaseWidth;
        final double distanceHeadingAdjustment = wheelbaseCircumference * (headingError / 360.0);

        // Magic number used to tune the impact the heading error has on the output.
        final double kAngleAdjustment = 1.0;
        return (distanceHeadingAdjustment / 2) * kAngleAdjustment;
    }

    public double getDistanceAdjustment(Segment segment, Duration duration, RoyalEncoderOffset encoder, ErrorContext errorContext, double headingAdjustment) {
        double positionError = (segment.position - encoder.getDistance()) + headingAdjustment;
        double derivativeError = (positionError - errorContext.lastPositionError) / secondsFromDuration(duration);
        errorContext.lastPositionError = positionError;
        
        // TODO: Pass these in or make these abstract properties.
        final double _kP = 0.2; // distance proportional
        final double _kD = 0.0; // distance derivative
        double proportionalAdjustment = _kP * positionError;
        double derivativeAdjustment = _kD * derivativeError;
        return proportionalAdjustment + derivativeAdjustment;
    }

    private Duration getDuration() {
        return _tankTrajectory.duration();
    }

    private static double secondsFromDuration(Duration duration) {
        double seconds = duration.getSeconds();
        seconds += duration.getNano() / 1_000_000_000.0;
        return seconds;
    }

    @Override
    public void complete() {
        _isFinished = true;
        stop();
        _onComplete.run();
    }

    public void stop() {
        _controlLoop.stop();
        _controlLoop.close();
    }

    public boolean isRunning() {
        return !_isFinished;
    }

    private class ErrorContext {
        public double lastPositionError = 0.0;
    }
}