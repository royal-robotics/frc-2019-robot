package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.libs.controls.*;

public class Controls {
    private static Joystick driver = new Joystick(0);

    //#region Controller Mapping
    public enum Logitech310Button {
        A (1),
        B (2),
        X (3),
        Y (4),
        LeftBumper (5),
        RightBumper (6),
        Back (7),
        Start (8),
        LeftStickPress (9),
        RightStickPress (10);
        public int id;
        Logitech310Button(int id) {
            this.id = id;
        }
    }

    public enum Logitech310Axis {
        LeftStickX (0),
        LeftStickY (1),
        LeftTrigger (2),
        RightTrigger (3),
        RightStickX (4),
        RightStickY (5);
        public int id;
        Logitech310Axis(int id) {
            this.id = id;
        }
    }
    //#endregion

    // Drive input values
    public static class DriveSystem {
        public static class TankDrive {
            private static Axis leftThrottle = new Axis(driver, Logitech310Axis.LeftStickY, 0.1);
            private static Axis rightThrottle = new Axis(driver, Logitech310Axis.RightStickY, 0.1);

            public static double getLeftThrottleValue() { return -leftThrottle.getValue(); }
            public static double getRightThrottleValue() { return -rightThrottle.getValue(); }
        }
    }
}