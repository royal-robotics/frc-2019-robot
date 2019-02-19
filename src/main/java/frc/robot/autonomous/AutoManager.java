package frc.robot.autonomous;

import frc.robot.autonomous.routines.*;
import frc.libs.autonomous.LoggingContext;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.subsystems.drivebase.DriveController;

public class AutoManager {
    private final DriveController driveController;
    private AutoRoutine currentRoutine;

    public AutoManager(DriveController driveController)
    {
        this.driveController = driveController;
    }

    public void startAutonomous()
    {
        // If there is already a routine running we stop it.
        stopAutonomous();

        currentRoutine = createAutoRoutine();
        currentRoutine.start();
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

    private AutoRoutine createAutoRoutine() {
        // TODO: Implment the logic that chooses the routine basd on a the switch value.
        return new TestAuto(driveController);
    }
}