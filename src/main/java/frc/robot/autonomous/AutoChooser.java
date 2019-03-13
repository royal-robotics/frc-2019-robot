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
    private final SendableChooser<String> _chooser;
    private final List<Class> _routines;

    public AutoChooser(Robot robot) {
        _robot = robot;
        _chooser = new SendableChooser<>();
        _routines = selectableRoutines();

        _chooser.setDefaultOption("No Auto Selected", _routines.get(0).getSimpleName());
        for (Class routine : _routines) {
            _chooser.addOption(routine.getSimpleName(), routine.getSimpleName());
        }

        SmartDashboard.putData("Auto Routine", _chooser);
    }

    public AutoRoutine getSelectedRoutine() {
        return new RightRocketAutoRoutine(_robot);
        // switch(Controls.getFieldStartPosition()) {
        //     case 3: {
        //         switch(Controls.getAutoRoutineId()) {
        //             case 1: {
        //                 return new RightFrontHatchAutoRoutine(_robot);
        //             }

        //             case 2: {
        //                 return new RightRocketAutoRoutine(_robot);
        //             }

        //             case 3: {
        //                 return new RightCargoAutoRoutine(_robot);
        //             }
        //             default: {
        //                 return null;
        //             }
        //         }
        //     }

        //     case 2: {
        //         return new CenterRightFrontHatchAutoRoutine(_robot);
                
        //     }

        //     case 10: {
        //         return new CenterLeftFrontHatchAutoRoutine(_robot);
        //     }

        //     case 9: {
        //         switch(Controls.getAutoRoutineId()){
        //             case 1: {
        //                 return new LeftFrontHatchAutoRoutine(_robot);
        //             }

        //             case 2: {
        //                 return new LeftRocketAutoRoutine(_robot);
        //             }
        //             default: {
        //                 return null;
        //             }
        //         }
        //     }

        //     default: {
        //         return null;
        //     }
        // }

        // String routineName = _chooser.getSelected();
        // try {
        //     for (Class routine : _routines) {
        //         if (routine.getSimpleName().equals(routineName))
        //         {
        //             Constructor constructor = routine.getConstructor(Robot.class);
        //             return (AutoRoutine)constructor.newInstance(_robot);
        //         }
        //     }
        // }
        // catch (Exception e)
        // {
        //     System.out.println("Unable to selected: " + routineName);
        //     System.err.println(e);
        // }
        // return null;
    }

    private static List<Class> selectableRoutines() {
        return Arrays.asList(RightRocketAutoRoutine.class, 
                             RightFrontHatchAutoRoutine.class);
    }
}