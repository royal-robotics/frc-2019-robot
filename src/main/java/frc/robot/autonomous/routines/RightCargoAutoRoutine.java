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

public class RightCargoAutoRoutine extends AutoRoutine {
    private final Marker _parent;
    private final DriveController _driveController;
    private final ElevatorController _elevatorController;
    private final HatchController _hatchController;

    public RightCargoAutoRoutine(Robot robot) {
        super();

        _parent = this.logger.marker;
        _driveController = robot._driveController;
        _elevatorController = robot._elevatorController;
        _hatchController = robot._hatchController;
    }

    @Override
    protected List<AutoStep> createRoutine() {
        return Arrays.asList(
            new TrajectoryDriveStep(_parent, _driveController, "rightCargo", false),
            new ElevatorMoveStep(_parent, _elevatorController, 17),
            new HatchShootStep(_parent, _hatchController),
            new ElevatorMoveStep(_parent, _elevatorController, 5),
            new ElevatorMoveStep(_parent, _elevatorController, 0)
        );
    }
}