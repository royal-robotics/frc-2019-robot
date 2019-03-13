package frc.libs.components;

import edu.wpi.first.wpilibj.interfaces.*;

public class RoyalGyroOffset {
    private final Gyro _gyro;
    private final double _initialAngle;
    private double _offset = 0.0; 

    public RoyalGyroOffset(Gyro gyro) {
        _gyro = gyro;
        _initialAngle = _gyro.getAngle();
    }

    public void setOffset(double offset) {
        _offset = offset;
    }

    public double getAngle() {
        return _gyro.getAngle() + _offset - _initialAngle;
    }
}