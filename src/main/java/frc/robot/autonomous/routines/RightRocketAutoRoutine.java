package frc.robot.autonomous.routines;

import java.util.*;
import org.apache.logging.log4j.*;
import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.Robot;
import frc.robot.autonomous.steps.*;
import frc.robot.subsystems.drivebase.DriveController;
import frc.robot.subsystems.elevator.ElevatorController;
import frc.robot.subsystems.hatch.HatchController;

public class RightRocketAutoRoutine extends AutoRoutine {
    private final Marker _parent;
    private final DriveController _driveController;
    private final ElevatorController _elevatorController;
    private final HatchController _hatchController;

    public RightRocketAutoRoutine(Robot robot) {
        super();

        _parent = this.logger.marker;
        _driveController = robot._driveController;
        _elevatorController = robot._elevatorController;
        _hatchController = robot._hatchController;
    }

    @Override
    protected List<AutoStep> createRoutine() {
        AutoStep moveFromPlatform = new TrajectoryDriveStep(_parent, _driveController, "rightRocket", true);
        AutoStep moveToRocket = new DriveStraightStep(_parent, _driveController, 74.0);
        return Arrays.asList(moveFromPlatform, moveToRocket);

        // AutoStep raiseElevator = new ElevatorMoveStep(_parent, _elevatorController, 20);
        // AutoStep shootHatch = new HatchShootStep(_parent, _hatchController);
        // return Arrays.asList(raiseElevator, shootHatch);
    }
}