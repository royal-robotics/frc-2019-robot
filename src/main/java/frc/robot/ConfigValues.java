package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class ConfigValues {
    private static DigitalInput compJumper = Components.compJumper;

    static {
        
    }

    private static boolean isPracticeRobot() { return ! compJumper.get(); }

    public static double pnumaticalWheelKludgeLeft() {
        if (isPracticeRobot()) {
            return 0.96502;
        }
        else {
            return 0.9700;
        }
    }

    public static double pnumaticalWheelKludgeRight() {
        if (isPracticeRobot()) {
            return 0.97630;
        }
        else {
            return 0.9720;
        }
    }
}