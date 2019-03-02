package frc.libs.controls;

public class Controllers {
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
        RightStickPress (10),

        Up(0, true),
        Right(90, true),
        Down(180, true),
        Left(270, true);

        public int id;
        public boolean isPOV;

        Logitech310Button(int id) {
            this(id, false);
        }

        Logitech310Button(int id, boolean isPOV) {
            this.id = id;
            this.isPOV = isPOV;
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
}