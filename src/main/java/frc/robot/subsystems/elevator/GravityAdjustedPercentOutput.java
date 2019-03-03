package frc.robot.subsystems.elevator;

import com.google.common.collect.*;
import com.google.common.primitives.Doubles;
import edu.wpi.first.wpilibj.*;

// TODO: this should implement SpeedController
public class GravityAdjustedPercentOutput implements PIDOutput {
    private final SpeedController _motor;
    private final double _holdPower;
    private final Range<Double> _absoluteRange;

    public GravityAdjustedPercentOutput(SpeedController motor, double holdPower)
    {
        _motor = motor;
        _absoluteRange = Range.range(-1.0, BoundType.CLOSED, 1.0, BoundType.CLOSED);
        _holdPower = Doubles.constrainToRange(holdPower, _absoluteRange.lowerEndpoint(), _absoluteRange.upperEndpoint());
    }

    public void set(double percentOutputInput) {
        double percentOutput = gravityAdjusted(percentOutputInput);
        //double percentOutput = percentOutputInput + _holdPower;

        _motor.set(percentOutput);
    }

    public double get() {
        return _motor.get();
    }

    @Override
    public void pidWrite(double normalizedPercentOutput) {
        set(normalizedPercentOutput);
    }

    private double gravityAdjusted(double normalizedPercentOutput) {
        if (normalizedPercentOutput > 0.0) { 
            double range = _absoluteRange.upperEndpoint() - _holdPower;
            return _holdPower + (range * normalizedPercentOutput);
        } else {
            double range = _holdPower - _absoluteRange.lowerEndpoint();
            return _holdPower + (range * normalizedPercentOutput);
        }
    }
}