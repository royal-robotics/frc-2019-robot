package frc.robot.autonomous;

import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class AutoManager {
    private final Robot robot;
    private final AutoChooser autoChooser;

    private AutoRoutine currentRoutine = null;

    public AutoManager(Robot robot)
    {
        this.robot = robot;
        this.autoChooser = new AutoChooser(robot);
    }

    public void loadSelectedRoutine() {
        if (currentRoutine == null) {
            currentRoutine = autoChooser.constructAutoRoutine();
            return;
        }

        Class<?> selectedRoutine = autoChooser.getSelectedRoutine();
        if (selectedRoutine == null) {
            if (currentRoutine != null)
                currentRoutine.stop();

            currentRoutine = null;
            return;
        }

        if (currentRoutine.getClass() != selectedRoutine) {
            if (currentRoutine != null)
                currentRoutine.stop();

            currentRoutine = autoChooser.constructAutoRoutine();
            return;
        }
    }

    public void startAutonomous(Runnable onComplete)
    {
        // If there is already a routine running we stop it.
        stopAutonomous();

        // Make sure the correct auto routine is loaded.
        loadSelectedRoutine();

        if (currentRoutine != null) {
            currentRoutine.start(onComplete);
        } else {
            // If there isn't a selected routine then autonomous completes immediately.
            onComplete.run();
        }
    }

    public void stopAutonomous()
    {
        if (currentRoutine != null)
        {
            currentRoutine.stop();
            currentRoutine = null;
            LoggingContext.init();
        }
    }

    public void printCurrentAutoRoutine() {
        String position = "Bad Setting";
        String routine = "Manual";

        switch(Controls.getFieldStartPosition()) {
            case 3: {
                position = "Right";
                switch(Controls.getAutoRoutineId()) {
                    case 1: {
                        routine = "RightFrontHatchAutoRoutine";
                        break;
                    }
                    case 2: {
                        routine = "RightRocketAutoRoutine";
                        break;
                    }
                    case 3: {
                        routine = "RightPlatformCargoAutoRoutine";
                        break;
                    }
                }
                break;
            }
            case 2: {
                position = "Center Right";
                routine = "CenterRightFrontHatchAutoRoutine";
                break;
            }
            case 1: {
                position = "Manual";
                break;
            }
            case 10: {
                position = "Center Left";
                routine = "CenterLeftFrontHatchAutoRoutine";
                break;
            }
            case 9: {
                position = "Left";
                switch(Controls.getAutoRoutineId()) {
                    case 1: {
                        routine = "LeftFrontHatchAutoRoutine";
                        break;
                    }
                    case 2: {
                        routine = "LeftRocketAutoRoutine";
                        break;
                    }
                    case 3: {
                        routine = "LeftPlatformCargoAutoRoutine";
                        break;
                    }
                }
                break;
            }
        }

        SmartDashboard.putString("AutoFieldStartPosition", position);
        SmartDashboard.putString("AutoRoutineName", routine);
    }
}