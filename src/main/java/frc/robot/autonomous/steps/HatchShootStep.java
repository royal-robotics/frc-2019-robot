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
        _scheduler = new Notifier(() -> rockOutStep());
    }

    @Override
    protected void initialize() {
        _scheduler.setHandler(() -> rockOutStep());
        _scheduler.startSingle(0.25);
    }

    private void rockOutStep() {
        _hatchController.setRock(true);

        _scheduler.setHandler(() -> shootOutStep());
        _scheduler.startSingle(0.50);
    }

    private void shootOutStep() {
        _hatchController.setShoot(true);

        _scheduler.setHandler(() -> rockInStep());
        _scheduler.startSingle(0.50);
    }

    private void rockInStep() {
        _hatchController.setRock(false);
        _hatchController.setShoot(false);

        _scheduler.setHandler(() -> complete());
        _scheduler.startSingle(0.50);
    }

    @Override
    public void stop() {
        _scheduler.close();

        _hatchController.setRock(false);
        _hatchController.setShoot(false);
    }
}