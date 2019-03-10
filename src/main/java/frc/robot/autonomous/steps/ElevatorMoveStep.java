package frc.robot.autonomous.steps;

import org.apache.logging.log4j.*;
import frc.libs.autonomous.*;
import frc.robot.subsystems.elevator.*;

public class ElevatorMoveStep extends AutoStep {
    private final ElevatorController _elevatorController;
    private final double _height;

    public ElevatorMoveStep(Marker parent, ElevatorController elevatorController, double height) {
        super(parent);

        _elevatorController = elevatorController;
        _height = height;
    }

    @Override
    protected void initialize() {
        _elevatorController.quickMove(_height, () -> {
            complete();
        });
    }

    @Override
    public void stop() {
        _elevatorController.stop();
    }
}