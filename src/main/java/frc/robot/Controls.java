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
    private static Joystick autoSelector = new Joystick(2);

    public static class DriveSystem {

        public static Class<DifferentialDrive> ControlMode = DifferentialDrive.class;

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

        private static Button autoTargetTest = new Button(driver, Logitech310Button.RightBumper, IButton.ButtonType.Hold);
        public static boolean autoTargetTest() { return autoTargetTest.isPressed(); }

        private static Button autoTestBackward = new Button(driver, Logitech310Button.A, IButton.ButtonType.Toggle);
        public static boolean autoTestBackward() { 
            //return autoTestBackward.isPressed(); 
            return false;
        }

        private static Button autoTestForward = new Button(driver, Logitech310Button.Y, IButton.ButtonType.Toggle);
        public static boolean autoTestForward() { return autoTestForward.isPressed(); }


        //private static Axis liftRobot1 = new Axis(driver, Logitech310Axis.RightTrigger, 0.1);
        //private static Axis liftRobot2 = new Axis(driver, Logitech310Axis.LeftTrigger, 0.1);

        // Requires at least one trigger to lift robot and both triggers released to drop
        public static boolean LiftRobotFront() { 
            //return (liftRobot1.isPressed()); 
            return false;
        }
        public static boolean LiftRobotBack() {
            //return(liftRobot2.isPressed());
            return false;
        }
    }

    public static class ElevatorSystem
    {
        private static Axis power = new Axis(operator, Logitech310Axis.RightStickY, 0.2);
        private static Button moveBottom = new Button(operator, Logitech310Button.Down, IButton.ButtonType.Hold);
        private static Button moveMiddle = new Button(operator, Logitech310Button.Left, IButton.ButtonType.Hold);
        private static Button moveCargoPickup = new Button(operator, Logitech310Button.Right, IButton.ButtonType.Hold);
        private static Button moveTop = new Button(operator, Logitech310Button.Up, IButton.ButtonType.Hold);
 
        public static boolean moveBottom() { return moveBottom.isPressed(); }
        public static boolean moveMiddle() { return moveMiddle.isPressed(); }
        public static boolean moveCargoPickup() { return moveCargoPickup.isPressed(); }
        public static boolean moveTop() { return moveTop.isPressed(); }

        public static boolean ManualControl() { return power.isPressed(); }
        public static double GetPower() { return -power.getValue() * 0.5; }
    }

    public static class Climber
    {
        private static Axis raiseClimber = new Axis(driver, Logitech310Axis.RightTrigger, 0.1);
        private static Axis lowerClimber = new Axis(driver, Logitech310Axis.LeftTrigger, 0.1);

        private static Button vacumeButton = new Button(driver, Logitech310Button.A, ButtonType.Hold);

        public static double getPower()
        {
            if (raiseClimber.getValue() > 0.0) {
                return raiseClimber.getValue();
            }
            else if (lowerClimber.getValue() > 0.0) {
                return -lowerClimber.getValue();
            }

            return 0.0;
        }

        public static boolean runVacume() {
            return vacumeButton.isPressed();
        }
    }

    public static class CargoManipulator
    {
        private static Button shoot = new Button(operator, Logitech310Button.RightBumper, ButtonType.Hold);
        private static Button eject = new Button(operator, Logitech310Button.LeftBumper, ButtonType.Hold);
        private static Button forceMaxPower = new Button(operator, Logitech310Button.Back, ButtonType.Hold);

        public static boolean shoot() { return shoot.isPressed(); }
        public static boolean eject() { return eject.isPressed(); }
        public static boolean forceMaxPower() { return forceMaxPower.isPressed(); }
    }

    public static class HatchManipulator
    {
        // Holder
        private static Axis hatchPusher = new Axis(operator, Logitech310Axis.RightTrigger, 0.1);
        private static Axis hatchFingers = new Axis(operator, Logitech310Axis.LeftTrigger, 0.1);

        public static boolean hatchPusher() { return hatchPusher.isPressed(0.1); }
        public static boolean hatchFingers() { return hatchFingers.isPressed(0.1); }

        // Arm
       
        private static Button hatchIntake = new Button(operator, Logitech310Button.A, ButtonType.Hold);
        private static Button hatchArmHome = new Button(operator, Logitech310Button.X, ButtonType.Hold);
        private static Button hatchArmFloor = new Button(operator, Logitech310Button.B, ButtonType.Hold);
        private static Button hatchArmStick = new Button(operator, Logitech310Button.Y, ButtonType.Hold);
        private static Axis power = new Axis(operator, Logitech310Axis.LeftStickY, 0.1);

        public static boolean HatchIntake() { return hatchIntake.isPressed(); }
        public static boolean HatchArmHome() { return hatchArmHome.isPressed(); }
        public static boolean HatchArmFloor() { return hatchArmFloor.isPressed(); }
        public static boolean HatchArmStick() { return hatchArmStick.isPressed(); }
        public static boolean ManualControl() { return power.isPressed(); }
        public static double GetPower() { return power.getValue() * 20.0; }

        
    }

    public static int getFieldStartPosition()

    {
        int result = 0;

        result += autoSelector.getRawButton(2) ? 1 : 0;
        result += autoSelector.getRawButton(3) ? 2 : 0;
        result += autoSelector.getRawButton(4) ? 4 : 0;
        result += autoSelector.getRawButton(5) ? 8 : 0;

        return result + 1;
    }
    
    public static int getAutoRoutineId()

    {
        int autoValue = 0;

        autoValue += autoSelector.getRawButton(13) ? 1 : 0;
        autoValue += autoSelector.getRawButton(14) ? 2 : 0;
        autoValue += autoSelector.getRawButton(15) ? 4 : 0;
        autoValue += autoSelector.getRawButton(16) ? 8 : 0;

        return autoValue + 1;
    }

}