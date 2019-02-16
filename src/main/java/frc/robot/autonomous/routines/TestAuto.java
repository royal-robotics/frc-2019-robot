package frc.robot.autonomous.routines;

import frc.robot.autonomous.*;
import frc.robot.autonomous.steps.*;
import frc.robot.subsystems.Drivebase.DriveController;

import java.util.*;

public class TestAuto extends AutoRoutine {
    public TestAuto(DriveController driveController) {
        List<AutoStep> autoSteps = new ArrayList<>();
        autoSteps.add(new DriveStep(driveController, 0.5, 1000));
        autoSteps.add(new DriveStep(driveController, 0.0, 0));
    }
}