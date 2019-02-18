package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.autonomous.routines.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.subsystems.Drivebase.DriveController;

public class AutoManager {
    private final SendableChooser<String> _autoChooser = new SendableChooser<>();
    private final DriveController _driveController;
    private AutoRoutine currentRoutine;

    public AutoManager(DriveController driveController)
    {
        _driveController = driveController;
    }

    public void startAutonomous()
    {
        // If there is already a routine running we stop it.
        stopAutonomous();

        // TODO: Implement dashboard choose routine logic
        currentRoutine = new TestAuto(_driveController);
        currentRoutine.start();
    }

    public void stopAutonomous()
    {
        if (currentRoutine != null)
        {
            currentRoutine.stop();
            currentRoutine = null;
        }
    }
}