package frc.robot.autonomous.steps;

import java.util.List;

import org.apache.logging.log4j.*;
import frc.libs.autonomous.*;
import frc.robot.subsystems.drivebase.*;
import frc.robot.subsystems.elevator.ElevatorController;

public class DriveAndElevatorStep extends AutoStepGroup<DriveAndElevatorStep> {
    private final DriveController _driveController;
    private final ElevatorController _elevatorController;

    public DriveAndElevatorStep(
        Marker parent,
        DriveController driveController,
        ElevatorController elevatorController)
    {
        super(parent);

        _driveController = driveController;
        _elevatorController = elevatorController;
    }

    @Override
    protected List<TriggerableAutoStep<DriveAndElevatorStep>> createGroup() {
        AutoStep moveFromPlatform = new DriveStraightStep(parent, _driveController, -60.0, 40.0, 80);
        AutoStep raise = new ElevatorMoveStep(parent, _elevatorController, 10);

        return null;
    }

    private static class DurationTrigger extends TriggerableAutoStep<DriveAndElevatorStep>
    {
        public DurationTrigger(AutoStepGroup<DriveAndElevatorStep> autoRoutine, AutoStep autoStep) {
            super(autoRoutine, autoStep);
        }

        @Override
        public boolean shouldTrigger() {
            return true;
        }
    }
}