package frc.robot.autonomous.routines;

import java.util.*;
import org.apache.logging.log4j.*;
import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.libs.components.Limelight;
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
    private final Limelight _limelight;

    public RightRocketAutoRoutine(Robot robot) {
        super();

        _parent = this.logger.marker;
        _driveController = robot._driveController;
        _elevatorController = robot._elevatorController;
        _hatchController = robot._hatchController;
        _limelight = robot._driveController.limelight;
    }

    @Override
    protected List<AutoStep> createRoutine() {
        return Arrays.asList(
            new DriveStraightStep(_parent, _driveController, -100.0, 40.0, 60),
            new TrajectoryDriveStep(_parent, _driveController, "rightRocket1", true),
            new DriveStraightTrackStep(_parent, _driveController, _limelight, 40.0, 25.0, 40),
            new ElevatorMoveStep(_parent, _elevatorController, 54)
            //new HatchShootStep(_parent, _hatchController),
            //new DriveStraightStep(_parent, _driveController, -5.0, 20.0, 150),
            //new ElevatorMoveStep(_parent, _elevatorController, 5),
            //new ElevatorMoveStep(_parent, _elevatorController, 0)
            // new TrajectoryDriveStep(_parent, _driveController, "rightRocket2", true)
            // new TrajectoryDriveStep(_parent, _driveController, "rightRocket3", false)
        );
    }
}