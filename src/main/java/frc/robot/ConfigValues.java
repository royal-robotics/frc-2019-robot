package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class ConfigValues {
    private static DigitalInput compJumper = Components.compJumper;
    private static boolean isPracticeRobot() { return ! compJumper.get(); }

    public static double pnumaticalWheelKludgeLeft() {
        if (isPracticeRobot())
            return 0.96994;
        else
            return 0.96994 * 1.0052356;
            //return 0.991472668 * 0.96087 * 0.921739;
    }

    public static double pnumaticalWheelKludgeRight() {
        if (isPracticeRobot()) {
            System.out.println("IsPractice.");
            return 0.97845;
        }
        else {
            System.out.println("IsComp.");
            return 0.97629741;
        }
    }
}