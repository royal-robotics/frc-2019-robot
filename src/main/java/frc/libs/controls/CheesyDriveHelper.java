package frc.libs.controls;

import frc.libs.utils.*;
import static frc.libs.utils.RobotModels.*;

public class CheesyDriveHelper {
    private double _quickStopAccumulator;
    private static final double TurnSensitivity = 1.0;

    public TankThrottleValues cheesyDrive(Axis throttle, Axis wheel, IButton quickTurn) {
        double overPower, angularPower;
        if (quickTurn.isPressed()) {
            if (Math.abs(throttle.getValue()) < 0.2) {
                double alpha = 0.1;
                _quickStopAccumulator = (1 - alpha) * _quickStopAccumulator + alpha * Util.limit(wheel.getValue(), 1.0) * 2;
            }
            overPower = 1.0;
            angularPower = wheel.getValue();
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle.getValue()) * wheel.getValue() * TurnSensitivity - _quickStopAccumulator;
            if (_quickStopAccumulator > 1) {
                _quickStopAccumulator -= 1;
            } else if (_quickStopAccumulator < -1) {
                _quickStopAccumulator += 1;
            } else {
                _quickStopAccumulator = 0.0;
            }
        }

        double right = throttle.getValue() - angularPower;
        double left = throttle.getValue() + angularPower;
        if (left > 1.0) {
            right -= overPower * (left - 1.0);
            left = 1.0;
        } else if (right > 1.0) {
            left -= overPower * (right - 1.0);
            right = 1.0;
        } else if (left < -1.0) {
            right += overPower * (-1.0 - left);
            left = -1.0;
        } else if (right < -1.0) {
            left += overPower * (-1.0 - right);
            right = -1.0;
        }
        return new TankThrottleValues(left, right);
    }
}