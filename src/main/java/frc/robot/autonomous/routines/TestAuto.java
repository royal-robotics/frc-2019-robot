package frc.robot.autonomous.routines;

import frc.robot.autonomous.steps.*;
import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.subsystems.drivebase.DriveController;
import java.util.*;

public class TestAuto extends AutoRoutine {
    public TestAuto(AutoLogger logger, DriveController driveController) {
        super(logger, createRoutine(logger, driveController));
    }

    @SuppressWarnings("serial")
    private static List<AutoStep> createRoutine(AutoLogger logger, DriveController driveController) {
        // ArrayList<AutoStep> list_of_steps = new ArrayList<AutoStep>() {{
        //     add(new DriveStep(logger, driveController, 0.5, 5000));
        //     add(new DriveStep(logger, driveController, -0.5, 5000));
        // }};

        ArrayList<AutoStep> list_of_steps = new ArrayList<AutoStep>();
        list_of_steps.add(new DriveStep(logger, driveController, 0.5, 5000));
        list_of_steps.add(new DriveStep(logger, driveController, -0.5, 5000));

        logger.LogInformation("List of steps" + list_of_steps.size());

        return list_of_steps;
    }
}