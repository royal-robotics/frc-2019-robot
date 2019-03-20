package frc.robot.autonomous.steps;

import org.apache.logging.log4j.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.autonomous.*;
import frc.robot.subsystems.hatch.*;

public class HatchShootStep extends AutoStep {

    private final HatchController _hatchController;
    private final Notifier _scheduler;

    public HatchShootStep(Marker parent, HatchController hatchController)
    {
        super(parent);
        _hatchController = hatchController;
        _scheduler = new Notifier(() -> pusherOutStep());
    }

    @Override
    protected void initialize() {
        _scheduler.setHandler(() -> pusherOutStep());
        _scheduler.startSingle(0.50);
    }

    private void pusherOutStep() {
        _hatchController.setPusher(true);

        _scheduler.setHandler(() -> fingersOutStep());
        _scheduler.startSingle(0.50);
    }

    private void pusherInStep() {
        _hatchController.setPusher(false);
        _hatchController.setFingers(false);

        _scheduler.setHandler(() -> complete());
        _scheduler.startSingle(0.50);
    }

    private void fingersOutStep() {
        _hatchController.setFingers(true);

        _scheduler.setHandler(() -> pusherInStep());
        _scheduler.startSingle(0.50);
    }


    @Override
    public void stop() {
        _scheduler.close();

        _hatchController.setPusher(false);
        _hatchController.setFingers(false);
    }
}