package frc.libs.motionprofile;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.google.common.base.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.motionprofile.IMotionProfile.*;

public abstract class LinearFollower {

    private final IMotionProfile _motionProfile;
    private final TalonSRX _motor;
    private final Encoder _encoder;

    private final Stopwatch _stopwatch;
    private final Notifier _controlLoop;

    private double _lastPositionError;

    // TODO: Pass these in or make these abstract properties.
    private static final double _kP = 0.0; // distance proportional
    private static final double _kI = 0.0; // distance integral
    private static final double _kD = 0.0; // distance derivative
    private static final double _kVf = 0.0; // velocity feed
    private static final double _kAf = 0.0; // acceleration feed

    // kVf = 0.4 (or 0.1)
    // kAf = 0.0
    // kP = 0.8
    // kI = 0.0
    // kD = 0.0

    // kVf = 2.5 / this.maxVelocity
    // kAf = 0.0
    // kP = 0.2
    // kI = kIntegralFactor
    // kD = kDifferentialFactor

    public LinearFollower(IMotionProfile motionProfile, TalonSRX motor, Encoder encoder) {
        _motionProfile = motionProfile;
        _motor = motor;
        _encoder = encoder;

        final double ControlLoopIntervalMs = 50.0;
        _controlLoop = new Notifier(() -> controlLoop());
        _controlLoop.startPeriodic(ControlLoopIntervalMs / 1000.0);
        _stopwatch = Stopwatch.createStarted();
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
        _motor.set(ControlMode.PercentOutput, power);
    }

    public void stop() {
        _controlLoop.stop();
    }

    protected void complete() {
        stop();
    }
}