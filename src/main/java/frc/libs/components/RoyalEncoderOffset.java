package frc.libs.components;

public class RoyalEncoderOffset {
    private RoyalEncoder _encoder;

    private final double _initialDistance;

    public RoyalEncoderOffset(RoyalEncoder encoder) {
        _encoder = encoder;
        _initialDistance = _encoder.getDistance();
    }

    public double getDistance() {
        return _encoder.getDistance() - _initialDistance;
    }

    public double getVelocity() {
        return _encoder.getVelocity();
    }
}