package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.Robot;
import frc.robot.autonomous.routines.*;

public class AutoManager {
    private final Robot robot;
    private final SendableChooser<String> _chooser;
    private AutoRoutine currentRoutine;

    public AutoManager(Robot robot)
    {
        this.robot = robot;
        _chooser = new SendableChooser<>();

        _chooser.setDefaultOption("No Auto Selected", "NoAutoRoutine");
        _chooser.addOption("TestAutoRoutine", "TestAutoRoutine");
        SmartDashboard.putData("Auto choices", _chooser);
    }

    public void startAutonomous()
    {
        // If there is already a routine running we stop it.
        stopAutonomous();

        String selectedAuto = _chooser.getSelected();
        currentRoutine = createAutoRoutine(selectedAuto);
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

    private AutoRoutine createAutoRoutine(String selectedAuto) {
        // TODO: Implment the logic that chooses the routine basd on a the switch value.
        System.out.println("Selected auto routine: " + selectedAuto);
        return new RightRocketAutoRoutine(robot);
    }
}