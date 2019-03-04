package frc.robot.subsystems.drivebase;

import java.time.*;
import com.google.common.base.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.components.*;
import frc.libs.motionprofile.IMotionProfile;
import frc.libs.motionprofile.LinearMotionProfile;
import frc.libs.motionprofile.IMotionProfile.Segment;
import static frc.libs.utils.RobotModels.*;

public class TankFollower implements ITrajectoryFollower {
    private final DriveBase _driveBase;
    private final RoyalEncoder _leftEncoder;
    private final RoyalEncoder _rightEncoder;
    private final Runnable _onComplete;

    private final IMotionProfile _leftProfile;
    private final IMotionProfile _rightProfile;

    private final Stopwatch _stopwatch;
    private final Notifier _controlLoop;

    private final ErrorContext _leftError;
    private final ErrorContext _rightError;

    // TODO: Pass these in or make these abstract properties.
    private static final double _kP = 0.2; // distance proportional
    private static final double _kI = 0.0; // distance integral
    private static final double _kD = 0.0; // distance derivative
    private static final double _kVf = 0.05; // velocity feed
    private static final double _kAf = 0.0; // acceleration feed

    public TankFollower(DriveBase driveBase, Runnable onComplete)
    {
        _driveBase = driveBase;
        _leftEncoder = driveBase.leftEncoder;
        _rightEncoder = driveBase.rightEncoder;
        _onComplete = onComplete;

        _leftProfile = new LinearMotionProfile(150.0, 60.0, 300.0);
        _rightProfile = new LinearMotionProfile(150.0, 60.0, 300.0);

        _leftError = new ErrorContext();
        _rightError = new ErrorContext();

        _stopwatch = Stopwatch.createStarted();
        _controlLoop = new Notifier(() -> controlLoop());
        
        final double ControlLoopIntervalMs = 10.0;
        _controlLoop.startPeriodic(ControlLoopIntervalMs / 1000.0);
    }

    private void controlLoop() {
        Duration timeIndex = _stopwatch.elapsed();
        if (timeIndex.toNanos() > getDuration().toNanos())
        {
            complete();
            return;
        }

        Encoder leftEncoder = _leftEncoder.encoder;
        Segment segmentLeft = _leftProfile.getSegment(timeIndex);
        double leftDistanceError = getDistanceError(segmentLeft, timeIndex, leftEncoder, _leftError);
        double leftVelocityFeed = _kVf * segmentLeft.velocity;
        double leftAccelerationFeed = _kAf * segmentLeft.acceleration;
        double leftPower = leftVelocityFeed + leftAccelerationFeed + leftDistanceError;

        Encoder rightEncoder = _rightEncoder.encoder;
        Segment segmentRight = _rightProfile.getSegment(timeIndex);
        double rightDistanceError = getDistanceError(segmentRight, timeIndex, rightEncoder, _rightError);
        double rightVelocityFeed = _kVf * segmentRight.velocity;
        double rightAccelerationFeed = _kAf * segmentRight.acceleration;
        double rightPower = rightVelocityFeed + rightAccelerationFeed + rightDistanceError;

        _driveBase.driveTank(new TankThrottleValues(leftPower, rightPower));
    }

    public double getDistanceError(Segment segment, Duration duration, Encoder encoder, ErrorContext errorContext) {
        double position = encoder.getDistance();
        double positionError = segment.position - position;
        double derivativeError = (positionError - errorContext.lastPositionError) / secondsFromDuration(duration);
        errorContext.lastPositionError = positionError;

        double proportionalAdjustment = _kP * positionError;
        double derivativeAdjustment = _kD * derivativeError;
        double distanceAdjustment = proportionalAdjustment + derivativeAdjustment;
        return distanceAdjustment;
    }

    private Duration getDuration() {
        return _leftProfile.duration();
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