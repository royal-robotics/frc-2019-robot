package frc.libs.components;

import java.lang.ref.WeakReference;
import java.util.*;
import edu.wpi.first.wpilibj.*;

/**
 * An Encoder wrapper which manages a sample of the most recent encoder readings.
 * Using this buffer of values we're able to calcuate the various derivatives of position
 * with respect to time (velocity, acceleration, jerk).
 */
public class RoyalEncoder {
    private final int PositionBufferSize = 5;
    private final LinkedList<TimestampedValue<Double>> _positionBuffer = new LinkedList<>();
    
    public final Encoder encoder;

    // When writing a motion profile follower its necessary to figure out
    // how motor percent output relates to velocity, we keep track of max/min
    // velocities to help with this.
    public double velocityMax = Double.MIN_VALUE;
    public double velocityMin = Double.MAX_VALUE;
    
    public RoyalEncoder(Encoder encoder, double distancePerPulse, boolean reverse)
    {
        this.encoder = encoder;
        encoder.setDistancePerPulse(distancePerPulse);
        encoder.setReverseDirection(reverse);
        encoder.reset();

        EncoderUpdater.registerEncoder(this);
    }

    public void reset() {
        encoder.reset();
    }

    private final void updateCache() {
        TimestampedValue<Double> position = new TimestampedValue<Double>(encoder.getDistance());
        _positionBuffer.add(position);

        if (_positionBuffer.size() > PositionBufferSize) {
            _positionBuffer.remove();
        }

        double velocity = this.getVelocity();
        if (velocity > velocityMax)
            velocityMax = velocity;

        if (velocity < velocityMin)
            velocityMin = velocity;
    }

    public boolean getDirection() {
        return encoder.getDirection();
    }

    public double getDistance() {
        return encoder.getDistance();
    }

    public double getVelocity() {
        if (_positionBuffer.size() < 2)
            return 0.0;

        LinkedList<TimestampedValue<Double>> velocities = getDerivativeValues(_positionBuffer);
        return getWeightedAverage(velocities, 0.75);
    }

    public double getAcceleration() {
        if (_positionBuffer.size() < 3)
            return 0.0;

        LinkedList<TimestampedValue<Double>> velocities = getDerivativeValues(_positionBuffer);
        LinkedList<TimestampedValue<Double>> accelerations = getDerivativeValues(velocities);
        return getWeightedAverage(accelerations, 0.75);
    }

    public double getJerk() {
        if (_positionBuffer.size() < 4)
            return 0.0;

        LinkedList<TimestampedValue<Double>> velocities = getDerivativeValues(_positionBuffer);
        LinkedList<TimestampedValue<Double>> accelerations = getDerivativeValues(velocities);
        LinkedList<TimestampedValue<Double>> jerks = getDerivativeValues(accelerations);
        return getWeightedAverage(jerks, 0.75);
    }

    private static LinkedList<TimestampedValue<Double>> getDerivativeValues(LinkedList<TimestampedValue<Double>> values) {
        LinkedList<TimestampedValue<Double>> derivativeValues = new LinkedList<>();
        for (int i = 0; i < values.size() - 1; i++)
        {
            double v1 = values.get(i).value;
            double v2 = values.get(i + 1).value;

            final double nanoSecondsPerSecond = 1_000_000_000.0;
            long tNano1 = values.get(i).nanoTime;
            long tNano2 = values.get(i + 1).nanoTime;
            double dt = (tNano2 - tNano1) / nanoSecondsPerSecond;

            double derivativeValue = (v2 - v1) / dt;
            long nanoTime = tNano1 + tNano2 / 2;
            derivativeValues.add(new TimestampedValue<Double>(derivativeValue, nanoTime));
        }

        return derivativeValues;
    }

    private static double getWeightedAverage(LinkedList<TimestampedValue<Double>> values, final double weightPerStage) {
        double averageValue = 0.0;
        double weightAvailable = 1.0;
        for (int i = 0; i < values.size(); i++)
        {
            double weight;
            if (i + 1 < values.size()) {
                weight = weightAvailable * weightPerStage;
                weightAvailable -= weight;
            } else {
                weight = weightAvailable;
            }

            averageValue += weight * values.get(i).value;
        }
        
        return averageValue;
    }

    private static class EncoderUpdater {
        private static List<WeakReference<RoyalEncoder>> encoders = new ArrayList<>();
        private static Notifier updater = new Notifier(() -> {
            for (WeakReference<RoyalEncoder> encoderReference: encoders) {
                RoyalEncoder encoder = encoderReference.get();

                if (encoder == null)
                    encoders.remove(encoderReference);
                else
                    encoder.updateCache();
                
            }
        });

        static {
            final double UpdateIntervalMs = 50.0;
            updater.startPeriodic(UpdateIntervalMs / 1000.0);
        }

        public static void registerEncoder(RoyalEncoder encoder) {
            encoders.add(new WeakReference<>(encoder));
        }
    }

    private static class TimestampedValue<TValue> {
        public final TValue value;
        public final long nanoTime;

        public TimestampedValue(TValue value) {
            this(value, System.nanoTime());
        }

        public TimestampedValue(TValue value, long nanoTime) {
            this.value = value;
            this.nanoTime = nanoTime;
        }
    }
}