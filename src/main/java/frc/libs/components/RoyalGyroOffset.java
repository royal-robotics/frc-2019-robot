package frc.libs.components;

import edu.wpi.first.wpilibj.interfaces.*;

public class RoyalGyroOffset {
    private final Gyro _gyro;
    private final double _initialAngle;

    public RoyalGyroOffset(Gyro gyro) {
        _gyro = gyro;
        _initialAngle = _gyro.getAngle();
    }

    public double getAngle() {
        return _gyro.getAngle() - _initialAngle;
    }
}