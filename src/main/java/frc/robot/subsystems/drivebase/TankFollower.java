package frc.robot.subsystems.drivebase;

import java.time.*;
import com.google.common.base.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.components.*;
import frc.libs.motionprofile.IMotionProfile.Segment;
import frc.robot.Components;

import static frc.libs.utils.RobotModels.*;

public class TankFollower implements ITrajectoryFollower {
    private final DriveBase _driveBase;
    private final RoyalEncoder _leftEncoder;
    private final RoyalEncoder _rightEncoder;
    private final TankTrajectory _tankTrajectory;
    private final Runnable _onComplete;

    private final Stopwatch _stopwatch;
    private final Notifier _controlLoop;

    private final ErrorContext _leftError;
    private final ErrorContext _rightError;
    
    private final TankFollowerLogger _leftLogger;
    private final TankFollowerLogger _rightLogger;

    // TODO: Pass these in or make these abstract properties.
    private static final double _kP = 0.1; // distance proportional
    private static final double _kD = 0.0; // distance derivative

    public TankFollower(DriveBase driveBase, TankTrajectory tankTrajectory, Runnable onComplete)
    {
        _driveBase = driveBase;
        _leftEncoder = driveBase.leftEncoder;
        _rightEncoder = driveBase.rightEncoder;
        _tankTrajectory = tankTrajectory;
        _onComplete = onComplete;

        _leftError = new ErrorContext();
        _rightError = new ErrorContext();
        
        // When the follower starts we reset the encoders to zero because
        // the trajectory assumes we start at zero.
        _leftEncoder.reset();
        _rightEncoder.reset();
        _driveBase.gyro.reset();

        _leftLogger = new TankFollowerLogger("Left", Components.DriveBase.leftDrive1, _leftEncoder, driveBase.gyro);
        _rightLogger = new TankFollowerLogger("Right", Components.DriveBase.rightDrive1, _rightEncoder, driveBase.gyro);

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

        Segment segmentLeft = _tankTrajectory.leftProfile.getSegment(timeIndex);
        Segment segmentRight = _tankTrajectory.rightProfile.getSegment(timeIndex);
        double headingAdjustment = getHeadingAdjustment(segmentLeft, segmentRight);

        double leftDistanceError = getDistanceAdjustment(segmentLeft, timeIndex, _leftEncoder, _leftError, headingAdjustment);
        double leftOutputFeed = getOutputFeed(segmentLeft);
        double leftPower = leftOutputFeed + leftDistanceError;
        _leftLogger.writeMotorUpdate(timeIndex, segmentLeft, headingAdjustment, leftDistanceError);

        double rightDistanceError = getDistanceAdjustment(segmentRight, timeIndex, _rightEncoder, _rightError, -headingAdjustment);
        double rightOutputFeed = getOutputFeed(segmentLeft);
        double rightPower = rightOutputFeed + rightDistanceError;
        _rightLogger.writeMotorUpdate(timeIndex, segmentRight, -headingAdjustment, rightDistanceError);

        _driveBase.driveTank(new TankThrottleValues(leftPower, rightPower));
    }

    public double getOutputFeed(Segment segment) {
        final double velocityFeed = getVelocityFeed(segment.velocity);

        final double kAf = 0.0;
        final double accelerationFeed = kAf * segment.acceleration;

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
        // Magic number used to tune the impact the heading error has on the output.
        final double kAngleAdjustment = 0.0;

        // Both the left and right segments should have the same heading.
        final double angle = _driveBase.gyro.getAngle();
        final double targetAngle = leftTarget.heading;
        final double headingErrorGyro = targetAngle - angle;

        // Calculate the heading error caused by position errors.
        // We subtract this from the observed angle error so we don't over compensate.
        final double leftPosError = leftTarget.position - _driveBase.leftEncoder.getDistance();
        final double rightPosError = rightTarget.position - _driveBase.rightEncoder.getDistance();
        final double positionErrorShift = leftPosError - rightPosError;
        final double headingErrorPosition = Math.toDegrees(Math.asin((positionErrorShift) / DriveBase.WheelbaseWidth));

        final double headingError = headingErrorGyro - headingErrorPosition;
        final double wheelbaseCircumference = 2 * Math.PI * DriveBase.WheelbaseWidth;
        final double distanceHeadingAdjustment = wheelbaseCircumference * (headingError / 360.0);

        return (distanceHeadingAdjustment / 2) * kAngleAdjustment;
    }

    public double getDistanceAdjustment(Segment segment, Duration duration, RoyalEncoder encoder, ErrorContext errorContext, double headingAdjustment) {
        double positionError = (segment.position - encoder.getDistance()) + headingAdjustment;
        double derivativeError = (positionError - errorContext.lastPositionError) / secondsFromDuration(duration);
        errorContext.lastPositionError = positionError;

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
        stop();
        _onComplete.run();
    }

    public void stop() {
        _controlLoop.stop();
    }

    private class ErrorContext {
        public double lastPositionError = 0.0;
    }
}