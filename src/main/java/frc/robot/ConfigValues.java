package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class ConfigValues {
    private static DigitalInput compJumper = Components.compJumper;
    private static boolean isPracticeRobot() { return ! compJumper.get(); }

    public static double pnumaticalWheelKludgeLeft() {
        if (isPracticeRobot()) {
            return 0.96994;
        }
        else {
            return 0.97502;
        }
    }

    public static double pnumaticalWheelKludgeRight() {
        if (isPracticeRobot()) {
            return 0.97845;
        }
        else {
            return 0.97630;
        }
    }
}