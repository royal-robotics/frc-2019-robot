package frc.robot.subsystems.drivebase;

import java.time.Duration;

import edu.wpi.first.wpilibj.*;
import frc.libs.motionprofile.IMotionProfile.*;

public class DistanceFollowerError implements IFollowerError {
    private final Encoder _encoder;
    private double _lastPositionError;

    // TODO: Pass these in or make these abstract properties.
    private static final double _kP = 0.2; // distance proportional
    private static final double _kI = 0.0; // distance integral
    private static final double _kD = 0.0; // distance derivative

    public DistanceFollowerError(Encoder encoder) {
        _encoder = encoder;
    }

    @Override
    public double getError(Segment segment, Duration duration) {
        double position = _encoder.getDistance();
        double positionError = segment.position - position;
        double derivativeError = (positionError - _lastPositionError) / secondsFromDuration(duration);
        _lastPositionError = positionError;

        double proportionalAdjustment = _kP * positionError;
        double derivativeAdjustment = _kD * derivativeError;
        double distanceAdjustment = proportionalAdjustment + derivativeAdjustment;

        return distanceAdjustment;
    }

    private static double secondsFromDuration(Duration duration) {
        double seconds = duration.getSeconds();
        seconds += duration.getNano() / 1_000_000_000.0;
        return seconds;
    }
}