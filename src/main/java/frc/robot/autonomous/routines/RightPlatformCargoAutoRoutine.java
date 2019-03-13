package frc.robot.autonomous.routines;

import java.util.*;
import org.apache.logging.log4j.*;
import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.Robot;
import frc.robot.autonomous.steps.*;
import frc.robot.subsystems.drivebase.DriveController;
import frc.robot.subsystems.elevator.ElevatorController;
import frc.robot.subsystems.cargo.CargoController;

public class RightPlatformCargoAutoRoutine extends AutoRoutine {
    private final Marker _parent;
    private final DriveController _driveController;
    private final ElevatorController _elevatorController;
    private final CargoController _cargoController;

    public RightPlatformCargoAutoRoutine(Robot robot) {
        super();

        _parent = this.logger.marker;
        _driveController = robot._driveController;
        _elevatorController = robot._elevatorController;
        _cargoController = robot._cargoController;
    }

    @Override
    protected List<AutoStep> createRoutine() {
        return Arrays.asList(
            new DriveStraightStep(_parent, _driveController, 96, 40, 60),
            new TrajectoryDriveStep(_parent, _driveController, "rightPlatformCargo", false),
            new ElevatorMoveStep(_parent, _elevatorController, 19),
            new CargoShootStep(_parent, _cargoController),
            new ElevatorMoveStep(_parent, _elevatorController, 5),
            new ElevatorMoveStep(_parent, _elevatorController, 0)
        );
    }
}