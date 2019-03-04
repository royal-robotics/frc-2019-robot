package frc.libs.motionprofile;

import java.time.*;
import com.google.common.primitives.*;

/**
 * Assumes we start at 
 */
public class LinearMotionProfile implements IMotionProfile {
    public final double targetDistance;
    public final double maxVelocity;
    public final double accelaretion;
    public final boolean invertOutputs;

    // The inital velcoity, we assume this is zero.
    private final double _initialVelocity = 0.0;

    // The time spent accelerating/braking to/from max velocity.
    // If we never reach max velocity velocity then this is the midpoint of the profile.
    private final double _rampSeconds;

    // The distance traveled to reach (or slow down from) max velocity.
    // If we never reach reach max velocity then this is (targetDistance / 2).
    private final double _rampDistance;

    // The distance traveled at max velocity.
    private final double _maxVelocityDistance;

    // The time in seconds we're at max velocity.
    private final double _maxVelocitySeconds;

    public LinearMotionProfile(double targetDistance, double maxVelocity, double accelaretion, boolean invertOutputs) {
        if (targetDistance < 0 || maxVelocity < 0 || accelaretion < 0)
            throw new IllegalArgumentException("position values required");

        this.targetDistance = targetDistance;
        this.maxVelocity = maxVelocity;
        this.accelaretion = accelaretion;
        this.invertOutputs = invertOutputs;

        // The seconds and distance required to reach max velocity.
        final double rampSeconds = calculateTime(_initialVelocity, maxVelocity, accelaretion);
        final double rampDistance = calculateDistance(rampSeconds, _initialVelocity, accelaretion);

        // Check if we ever reach max velocity or not.
        if (targetDistance > rampDistance * 2)
        {
            _rampSeconds = rampSeconds;
            _rampDistance = rampDistance;
            _maxVelocityDistance = targetDistance - (rampDistance  * 2);
            _maxVelocitySeconds = _maxVelocityDistance / maxVelocity;
        }
        else
        {   // s = (Vi * t) + ((1 / 2) * A * t^2)
            // t = sqrt(s * 2 * A)
            // TODO: Add initial velocity stuff
            _rampDistance = targetDistance / 2;
            _rampSeconds = Math.sqrt(_rampDistance * 2.0 / accelaretion);

            // We never reach max velocity
            _maxVelocityDistance = 0.0;
            _maxVelocitySeconds = 0.0;
        }
    }

    @Override
    public Duration duration() {
        double seconds = _maxVelocitySeconds + _rampSeconds * 2;
        return durationFromSeconds(seconds);
    }

    @Override
    public Segment getSegment(Duration index) {
        double seconds = secondsFromDuration(index);
        double duration = secondsFromDuration(duration());

        // Check if the index exceeds the duration of the profile.
        if (index.isNegative() || seconds > duration)
            return null;
        
        // Check if we reach max velocity or not
        if (_maxVelocitySeconds == 0)
        {
            // Check if we're accelerating, or braking
            if (seconds < duration / 2.0)
            {
                // We're accelerating / speeding up
                double acceleratingDistance = calculateDistance(seconds, _initialVelocity, accelaretion);
                double acceleratingVelocity = calculateVelocity(_initialVelocity, seconds, accelaretion);
                return linearSegment(seconds, acceleratingDistance, acceleratingVelocity, accelaretion);
            }
            else
            {   
                // We're breaking / slowing down
                // The peak velocity that is reached before we start breaking.
                double peakVelocity = calculateVelocity(_initialVelocity, _rampSeconds, accelaretion);
                
                double timeBraking = seconds - _rampSeconds;
                double brakingDistance = calculateDistance(timeBraking, peakVelocity, -accelaretion);
                double brakingVelocity = calculateVelocity(peakVelocity, timeBraking, -accelaretion);

                double distance = _rampDistance + brakingDistance;
                return linearSegment(seconds, distance, brakingVelocity, -accelaretion);
            }
        }
        else
        {
            // Check if we're accelerating, cruising, or breaking
            if (seconds <= _rampSeconds)
            {
                // We're accelerating / speeding up
                double acceleratingDistance = calculateDistance(seconds, _initialVelocity, accelaretion);
                double acceleratingVelocity = calculateVelocity(_initialVelocity, seconds, accelaretion);
                return linearSegment(seconds, acceleratingDistance, acceleratingVelocity, accelaretion);
            }
            else if (seconds <= _rampSeconds + _maxVelocitySeconds)
            {
                // We're cruising / are at max velocity
                double cruisingSeconds = seconds - _rampSeconds;
                double cruisingDistance = maxVelocity * cruisingSeconds;
                double distance = _rampDistance + cruisingDistance;
                return linearSegment(seconds, distance, maxVelocity, 0.0);
            }
            else
            {
                // We're breaking / slowing down
                double brakingSeconds = seconds - (_rampSeconds + _maxVelocitySeconds);
                double brakingDistance = calculateDistance(seconds, maxVelocity, -accelaretion);
                double brakingVelocity = calculateVelocity(maxVelocity, brakingSeconds, -accelaretion);
                double distance = _rampDistance + _maxVelocityDistance + brakingDistance;
                return linearSegment(seconds, distance, brakingVelocity, -accelaretion);
            }
        }
    }

    private static final double calculateVelocity(double vI, double t, double a) {
        // Vf = Vi + (t * A)
        return vI + (t * a);
    }

    private static final double calculateTime(double vI, double vF, double a) {
        // t = (Vf - Vi) / A
        return Math.round((vF - vI) / a);
    }

    private static final double calculateDistance(double t, double vI, double a) {
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

    private Segment linearSegment(double seconds, double distance, double velocity, double acceleration) {
        Duration time = durationFromSeconds(seconds);
        if (invertOutputs)
            return new Segment(time, 0.0, -distance, -distance, -velocity, -acceleration, 0.0, 0.0);
        else
            return new Segment(time, 0.0, distance, distance, velocity, acceleration, 0.0, 0.0);
    }
}