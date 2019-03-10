package frc.robot.autonomous.routines;

import java.util.*;
import org.apache.logging.log4j.*;
import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.Robot;
import frc.robot.autonomous.steps.*;
import frc.robot.subsystems.drivebase.DriveController;

public class RightRocketAutoRoutine extends AutoRoutine {
    private final Marker _parent;
    private final DriveController _driveController;

    public RightRocketAutoRoutine(Robot robot) {
        super();

        _parent = this.logger.marker;
        _driveController = robot._driveController;
    }

    @Override
    protected List<AutoStep> createRoutine() {
        AutoStep moveToRocket = new TrajectoryDriveStep(_parent, _driveController, "rightRocket", true);
        return Arrays.asList(moveToRocket);
    }
}