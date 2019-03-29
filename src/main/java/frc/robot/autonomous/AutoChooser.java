package frc.robot.autonomous;

import java.lang.reflect.Constructor;
import frc.libs.autonomous.buildingblocks.AutoRoutine;
import frc.robot.Robot;
import frc.robot.autonomous.routines.*;
import frc.robot.Controls;

public class AutoChooser {
    private final Robot _robot;

    public AutoChooser(Robot robot) {
        _robot = robot;
    }

    public Class<?> getSelectedRoutine() {
        switch (Controls.getFieldStartPosition()) {
        case 3: { // Right side of platform
            switch (Controls.getAutoRoutineId()) {
            case 1: {
                return RightFrontHatchAutoRoutine.class;
            }
            case 2: {
                return RightRocketAutoRoutine.class;
            }
            case 3: {
                return RightPlatformCargoAutoRoutine.class;
            }
            }
            break;
        }

        case 2: {// Right side of platform center.
            return CenterRightFrontHatchAutoRoutine.class;
        }

        case 10: {// Left side of platform center
            return CenterLeftFrontHatchAutoRoutine.class;
        }

        case 9: {// Left side of platform
            switch (Controls.getAutoRoutineId()) {
            case 1: {
                return LeftFrontHatchAutoRoutine.class;
            }
            case 2: {
                return LeftRocketAutoRoutine.class;
            }
            case 3: {
                return LeftPlatformCargoAutoRoutine.class;
            }
            }
            break;
        }
        }

        return null;
    }

    public AutoRoutine constructAutoRoutine() {
        Class<?> routine = this.getSelectedRoutine();
        if (routine == null)
            return null;

        try {
            Constructor<?> constructor = routine.getConstructor(Robot.class);
            AutoRoutine autoRoutine = (AutoRoutine) constructor.newInstance(_robot);
            return autoRoutine;
        } catch (Exception e) {
            System.out.println("Unable to selected: " + routine.getSimpleName());
            System.err.println(e);
        }

        return null;
    }
}