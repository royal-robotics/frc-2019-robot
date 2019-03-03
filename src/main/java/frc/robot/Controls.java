package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.libs.controls.*;
import frc.libs.controls.IButton.ButtonType;

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
    private static Joystick operator = new Joystick(1);

    public static class DriveSystem {

        public static Class<TankDrive> ControlMode = TankDrive.class;

        public static class TankDrive {
            private static Axis leftThrottle = new Axis(driver, Logitech310Axis.LeftStickY, 0.1);
            private static Axis rightThrottle = new Axis(driver, Logitech310Axis.RightStickY, 0.1);

            private static double getLeftThrottleValue() { return leftThrottle.getValue(); }
            private static double getRightThrottleValue() { return rightThrottle.getValue(); }
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

        private static Axis liftRobot1 = new Axis(driver, Logitech310Axis.RightTrigger, 0.1);
        private static Axis liftRobot2 = new Axis(driver, Logitech310Axis.LeftTrigger, 0.1);

        // Requires at least one trigger to lift robot and both triggers released to drop
        public static boolean LiftRobot() { return (liftRobot1.isPressed() || liftRobot2.isPressed()); }
    }

    public static class ElevatorSystem
    {
        private static Axis raise = new Axis(operator, Logitech310Axis.RightTrigger, 0.1);
        private static Axis lower = new Axis(operator, Logitech310Axis.LeftTrigger, 0.1);

        private static Button quickMoveTest = new Button(operator, Logitech310Button.Up, IButton.ButtonType.Toggle);

        public static boolean Raise() { return raise.isPressed(); }
        public static boolean Lower() { return lower.isPressed(); }
        public static boolean quickMoveTest() { return quickMoveTest.isPressed(); }
    }

    public static class CargoManipulator
    {
        private static Button intake = new Button(operator, Logitech310Button.Left, ButtonType.Hold);
        private static Button shoot = new Button(operator, Logitech310Button.Right, ButtonType.Hold);
        private static Button eject = new Button(operator, Logitech310Button.Down, ButtonType.Hold);
        
        public static boolean intake() { return intake.isPressed(); }
        public static boolean shoot() { return shoot.isPressed(); }
        public static boolean eject() { return eject.isPressed(); }
    }

    public static class HatchManipulator
    {
        // Holder
        private static Button hatchRock = new Button(operator, Logitech310Button.A, ButtonType.Hold);
        private static Button hatchShoot = new Button(operator, Logitech310Button.B, ButtonType.Hold);

        public static boolean hatchRock() { return hatchRock.isPressed(); }
        public static boolean hatchShoot() { return hatchShoot.isPressed(); }

        // Arm
        private static Button hatchForward = new Button(operator, Logitech310Button.LeftBumper, ButtonType.Hold);
        private static Button hatchRelease = new Button(operator, Logitech310Button.RightBumper, ButtonType.Hold);
        private static Button hatchPull = new Button(operator, Logitech310Button.Start, ButtonType.Hold);
        private static Button hatchPush = new Button(operator, Logitech310Button.Back, ButtonType.Hold);
        private static Axis hatchArm = new Axis(operator, Logitech310Axis.LeftStickY, 0.1);

        public static boolean HatchForward() { return hatchForward.isPressed(); }
        public static boolean HatchRelease() { return hatchRelease.isPressed(); }
        public static boolean HatchPull() { return hatchPull.isPressed(); }
        public static boolean HatchPush() { return hatchPush.isPressed(); }
        public static boolean ManualHatchArm() { return hatchArm.isPressed(); }
        public static double GetHatchArmSpeed() { return hatchArm.getValue(); }
    }
}