package frc.robot.autonomous.routines;

import frc.robot.autonomous.steps.*;
import frc.robot.libs.autonomous.*;
import frc.robot.libs.autonomous.buildingblocks.*;
import frc.robot.subsystems.Drivebase.DriveController;
import java.util.*;

public class TestAuto extends AutoRoutine {
    public TestAuto(DriveController driveController) {
        super(createRoutine(driveController));
    }

    @SuppressWarnings("serial")
    private static List<AutoStep> createRoutine(DriveController driveController) {
        return new ArrayList<AutoStep>() {{
            new DriveStep(driveController, 0.5, 1000);
            new DriveStep(driveController, 0.0, 0);
        }};
    }
}