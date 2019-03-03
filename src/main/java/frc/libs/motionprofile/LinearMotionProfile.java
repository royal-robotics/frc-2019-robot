package frc.libs.motionprofile;

import java.time.*;
import com.google.common.primitives.*;

/**
 * Assumes we start at 
 */
public class LinearMotionProfile implements IMotionProfile {
    private final double _targetDistance;
    private final double _maxVelocity;
    private final double _accelaretion;

    // The inital velcoity, we assume this is zero.
    private final double _vInitial = 0.0;

    // The time to reach the max velocity (or slow down from max velocity)
    private final double _rampTimeSeconds;

    // The distance to reach max velocity (or slow down from max velocity)
    private final double _rampDistance;

    // The distance traveled at max velocity
    private final double _maxVelocityDistance;

    // The time in seconds we're at max velocity
    private final double _maxVelocitySeconds;

    public LinearMotionProfile(double targetDistance, double maxVelocity, double accelaretion) {
        _targetDistance = targetDistance;
        _maxVelocity = maxVelocity;
        _accelaretion = accelaretion;

        _rampTimeSeconds = accelerationTime(_vInitial, maxVelocity, accelaretion);
        _rampDistance = distanceTraveled(_rampTimeSeconds, _vInitial, accelaretion);

        final double maxVelocityDistance = _targetDistance - (_rampDistance  * 2);
        _maxVelocityDistance = Doubles.constrainToRange(maxVelocityDistance, 0, Double.MAX_VALUE);
        _maxVelocitySeconds = _maxVelocityDistance / _maxVelocity;
    }


    @Override
    public Duration duration() {
        if (_targetDistance > _rampDistance * 2)
        {
            final double durationSeconds = _maxVelocitySeconds + (_rampTimeSeconds * 2);
            return durationFromSeconds(durationSeconds);
        }
        else
        {
            double halfRampTimeSeconds = Math.sqrt(_rampDistance / _accelaretion);
            double durationSeconds = halfRampTimeSeconds * 2;
            return durationFromSeconds(durationSeconds);
        }
    }

    @Override
    public Segment getSegment(Duration index) {
        double seconds = secondsFromDuration(index);
        Duration duration = duration();

        // Check if the index exceeds the duration of the profile.
        if (index.isNegative() || index.toNanos() > duration.toNanos())
            return null;
        
        // This logic assumes _targetDistance > _rampDistance * 2

        if (seconds <= _rampTimeSeconds)
        {
            double distance = distanceTraveled(seconds, _vInitial, _accelaretion);
            double velcoity = velocityTime(_vInitial, seconds, _accelaretion);
            return linearSegment(seconds, distance, velcoity, _accelaretion);
        }
        else if (seconds <= _rampTimeSeconds + _maxVelocityDistance)
        {
            double maxVelocitySeconds = seconds - _rampTimeSeconds;
            double maxVelocityDistance = _maxVelocity * maxVelocitySeconds;
            return linearSegment(seconds, _rampDistance + maxVelocityDistance, _maxVelocity, 0.0);
        }
        else
        {
            double decelerateSeconds = seconds - (_rampTimeSeconds + _maxVelocitySeconds);
            double deceleratedDistance = distanceTraveled(decelerateSeconds, _maxVelocity, -_accelaretion);
            double velocity = velocityTime(_maxVelocity, decelerateSeconds, -_accelaretion);
            double distance = _rampDistance + _maxVelocityDistance + deceleratedDistance;
            return linearSegment(seconds, distance, velocity, -_accelaretion);
        }
    }

    private static final double velocityTime(double vI, double t, double a) {
        // Vf = Vi + (t * A)
        return vI + (t * a);
    }

    private static final double accelerationTime(double vI, double vF, double a) {
        // t = (Vf - Vi) / A
        return Math.round((vF - vI) / a);
    }

    private static final double distanceTraveled(double t, double vI, double a) {
        //s = (Vi * t) + ((1 / 2) * A * t^2)
        final double vInitialDistance = vI * t;
        final double aDistance = 0.5 * a * t * t;
        return vInitialDistance + aDistance;
    }

    private static double secondsFromDuration(Duration duration) {
        double seconds = duration.getSeconds();
        seconds += duration.getNano() / 1_000_000_000.0;
        return seconds;
    }

    private static Duration durationFromSeconds(double seconds) {
        final long secondsOnly = (long)seconds;
        final long nanos = (long)((seconds - secondsOnly) * 1_000_000_000);
        return Duration.ofSeconds(secondsOnly, nanos);
    }

    private static Segment linearSegment(double seconds, double distance, double velocity, double acceleration) {
        Duration time = durationFromSeconds(seconds);
        return new Segment(time, 0.0, distance, distance, velocity, acceleration, 0.0, 0.0);
    }
}