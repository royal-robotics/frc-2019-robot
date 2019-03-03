package frc.libs.motionprofile;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.google.common.base.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.motionprofile.IMotionProfile.*;

public abstract class SimpleFollower {
    private final Encoder _encoder;
    private final SpeedController _motor;

    private final Stopwatch _stopwatch;
    private final Notifier _controlLoop;
    private IMotionProfile _motionProfile;

    private double _lastPositionError;

    // TODO: Pass these in or make these abstract properties.
    private static final double _kP = 0.0; // distance proportional
    private static final double _kI = 0.0; // distance integral
    private static final double _kD = 0.0; // distance derivative
    private static final double _kVf = 0.0; // velocity feed
    private static final double _kAf = 0.0; // acceleration feed

    public SimpleFollower(Encoder encoder, SpeedController motor) {
        _encoder = encoder;
        _motor = motor;
        
        _controlLoop = new Notifier(() -> controlLoop());
        _stopwatch = Stopwatch.createUnstarted();
    }

    public final void setMotionProfile(IMotionProfile motionProfile) {
        _stopwatch.reset();
        _stopwatch.start();

        _motionProfile = motionProfile;
        final double ControlLoopIntervalMs = 50.0;
        _controlLoop.startPeriodic(ControlLoopIntervalMs / 1000.0);
    }

    public boolean isRunning() {
        return _motionProfile != null;
    }

    public void stop() {
        _controlLoop.stop();
        _motionProfile = null;;
    }

    protected void complete() {
        stop();
    }

    private void controlLoop() {
        if (_stopwatch.elapsed().toNanos() > _motionProfile.duration().toNanos())
        {
            complete();
            return;
        }

        Segment segment = _motionProfile.getSegment(_stopwatch.elapsed());
        double position = 0.0;

        double positionError = segment.position - position;
        double derivativeError = (positionError - _lastPositionError) / (_stopwatch.elapsed().getSeconds());
        _lastPositionError = positionError;

        double proportionalAdjustment = _kP * positionError;
        double derivativeAdjustment = _kD * derivativeError;
        double distanceAdjustment = proportionalAdjustment + derivativeAdjustment;

        double power = _kVf * segment.velocity + _kAf * segment.acceleration + distanceAdjustment;
        _motor.set(power);
    }
}