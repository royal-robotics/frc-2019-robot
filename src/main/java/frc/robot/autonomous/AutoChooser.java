package frc.robot.autonomous;

import java.lang.reflect.Constructor;
import java.util.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.libs.autonomous.buildingblocks.AutoRoutine;
import frc.robot.Robot;
import frc.robot.autonomous.routines.*;
import frc.robot.Controls;


public class AutoChooser {
    private final Robot _robot;

    public AutoChooser(Robot robot) {
        _robot = robot;
    }

    public AutoRoutine getSelectedRoutine() {
        AutoRoutine routine = null;

        switch(Controls.getFieldStartPosition()) {
            case 3: {   // Right side of platform
                switch(Controls.getAutoRoutineId()) {
                    case 1: {
                        routine = new RightFrontHatchAutoRoutine(_robot);
                        break;
                    }
                    case 2: {
                        routine = new RightRocketAutoRoutine(_robot);
                        break;
                    }
                    case 3: {
                        routine = new RightCargoAutoRoutine(_robot);
                        break;
                    }
                    case 4: {
                        routine = new RightPlatformCargoAutoRoutine(_robot);
                        break;
                    }
                }
                break;
            }

            case 2: {// Right side of platform center.
                routine = new CenterRightFrontHatchAutoRoutine(_robot);
                break;
            }

            case 10: {// Left side of platform center
                routine = new CenterLeftFrontHatchAutoRoutine(_robot);
                break;
            }

            case 9: {// Left side of platform
                switch(Controls.getAutoRoutineId()){
                    case 1: {
                        routine = new LeftFrontHatchAutoRoutine(_robot);
                        break;
                    }
                    case 2: {
                        routine = new LeftRocketAutoRoutine(_robot);
                        break;
                    }
                    case 3: {
                        routine = new LeftCargoAutoRoutine(_robot);
                        break;
                    }
                    case 4: {
                        routine = new LeftPlatformCargoAutoRoutine(_robot);
                        break;
                    }
                }
                break;
            }
        }

        return routine;
    }
}