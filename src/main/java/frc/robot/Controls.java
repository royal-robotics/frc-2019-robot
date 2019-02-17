package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.robot.libs.controls.*;
import static frc.robot.libs.utils.RobotModels.*;
import static frc.robot.libs.controls.Controllers.*;

public final class Controls {
    private static Joystick driver = new Joystick(0);

    public static class DriveSystem {
        public static class TankDrive {
            private static Axis leftThrottle = new Axis(driver, Logitech310Axis.LeftStickY, 0.1);
            private static Axis rightThrottle = new Axis(driver, Logitech310Axis.RightStickY, 0.1);

            public static double getLeftThrottleValue() { return -leftThrottle.getValue(); }
            public static double getRightThrottleValue() { return -rightThrottle.getValue(); }
            public static TankThrottleValues getThrottleValues() { return new TankThrottleValues(0.0, 0.0); }
        }

        public static class DifferentialDrive {
            private static Axis throttle = new Axis(driver, Logitech310Axis.LeftStickY, 0.1);
            private static Axis turn = new Axis(driver, Logitech310Axis.RightStickX, 0.1);

            private static double getThrottleValue() { return -throttle.getValue(); }
            private static double turnPower() { return turn.getValue() * (3.0 / 4.0); }
            public static double getLeftThrottleValue() { return getThrottleValue() + turnPower(); }
            public static double getRightThrottleValue() { return getThrottleValue() - turnPower(); }
            public static TankThrottleValues getThrottleValues() { return new TankThrottleValues(getLeftThrottleValue(), getRightThrottleValue()); }
        }

        public static class CheesyDrive {
            private static Axis throttle = new Axis(driver, Logitech310Axis.LeftStickY, 0.1);
            private static Axis wheel = new Axis(driver, Logitech310Axis.RightStickX, 0.1);
            private static IButton quickTurn = new Button(driver, Logitech310Button.LeftBumper, IButton.ButtonType.Hold);
    
            private static CheesyDriveHelper cheesyDriveHelper = new CheesyDriveHelper();
            public static TankThrottleValues getThrottleValues() { return cheesyDriveHelper.cheesyDrive(throttle, wheel, quickTurn); }
        }
    }
}