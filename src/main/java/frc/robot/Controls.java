package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.libs.controls.*;
import static frc.libs.utils.RobotModels.*;
import static frc.libs.controls.Controllers.*;

/**
 * The purpose of Controls is to have a static easy to understand mapping
 * of controller interaction -> robot function.
 * 
 * Remarks:
 * - For the most part these should be functional mappings.
 *   Component state should go in subsystem objects.
 * - Any controller mappings that correspond to control state should
 *   live here. Example: State for which drive control mode we're in.
 * - Use inner classes to try to scope controls to the subsystems and
 *   feature they relate to.
 */
public final class Controls {
    private static Joystick driver = new Joystick(0);

    public static class DriveSystem {

        public static Class<TankDrive> ControlMode = TankDrive.class;

        public static class TankDrive {
            private static Axis leftThrottle = new Axis(driver, Logitech310Axis.LeftStickY, 0.1);
            private static Axis rightThrottle = new Axis(driver, Logitech310Axis.RightStickY, 0.1);

            private static double getLeftThrottleValue() { return -leftThrottle.getValue(); }
            private static double getRightThrottleValue() { return -rightThrottle.getValue(); }
            public static TankThrottleValues getThrottleValues() { return new TankThrottleValues(getLeftThrottleValue(), getRightThrottleValue()); }
        }

        public static class DifferentialDrive {
            private static Axis throttle = new Axis(driver, Logitech310Axis.LeftStickY, 0.1);
            private static Axis turn = new Axis(driver, Logitech310Axis.RightStickX, 0.1);

            private static double getThrottleValue() { return -throttle.getValue(); }
            private static double turnPower() { return turn.getValue() * (3.0 / 4.0); }
            private static double getLeftThrottleValue() { return getThrottleValue() + turnPower(); }
            private static double getRightThrottleValue() { return getThrottleValue() - turnPower(); }
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