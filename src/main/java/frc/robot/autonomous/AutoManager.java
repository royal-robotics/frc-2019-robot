package frc.robot.autonomous;

import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.Robot;
import frc.robot.autonomous.routines.*;

public class AutoManager {
    private final Robot robot;
    private AutoRoutine currentRoutine;

    public AutoManager(Robot robot)
    {
        this.robot = robot;
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
        return new TestAutoRoutine(robot);
    }
}