package frc.robot.autonomous.routines;

import frc.robot.autonomous.steps.*;
import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.subsystems.drivebase.DriveController;
import java.util.*;

public class TestAutoRoutine extends AutoRoutine {
    private final DriveController driveController;

    public TestAutoRoutine(DriveController driveController) {
        super();

        this.driveController = driveController;
    }

    @Override
    protected List<AutoStep> createRoutine() {
        // ArrayList<AutoStep> list_of_steps = new ArrayList<AutoStep>() {{
        //     add(new DriveStep(logger, driveController, 0.5, 5000));
        //     add(new DriveStep(logger, driveController, -0.5, 5000));
        // }};

        ArrayList<AutoStep> list_of_steps = new ArrayList<AutoStep>();
        list_of_steps.add(new DriveStep(this.logger.marker, driveController, 0.5, 5000));
        list_of_steps.add(new DriveStep(this.logger.marker, driveController, -0.5, 5000));

        return list_of_steps;
    }
}