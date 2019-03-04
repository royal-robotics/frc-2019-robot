package frc.libs.utils;

public final class Util {
    public static final int EncoderTicksPerRotation = 4096;
    /**
     * Limits the given input to the given magnitude.
     */
    public static double limit(double v, double limit) {
        return (Math.abs(v) < limit) ? v : limit * (v < 0 ? -1 : 1);
    }

    public static double encoderToAngle(int encoderTicks) {
        double rotationPercent = ((double)encoderTicks / EncoderTicksPerRotation);
        return rotationPercent * 360.0;
    }

    public static int angleToEncoder(double angle) {
        double rotationPercent = angle / 360.0;
        return (int)(rotationPercent * EncoderTicksPerRotation);
    }
}