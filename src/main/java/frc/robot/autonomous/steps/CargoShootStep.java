package frc.robot.autonomous.steps;

import org.apache.logging.log4j.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.autonomous.*;
import frc.robot.subsystems.cargo.*;

public class CargoShootStep extends AutoStep {

    private final CargoController _cargoController;
    private final Notifier _scheduler;

    public CargoShootStep(Marker parent, CargoController cargoController)
    {
        super(parent);
        _cargoController = cargoController;
        _scheduler = new Notifier(() -> shootStop());
    }

    @Override
    protected void initialize() {
        _cargoController.shoot();
        _scheduler.setHandler(() -> shootStop());
        _scheduler.startSingle(0.50);
    }

    private void shootStop() {
        _cargoController.stop();
        this.complete();
    }

    @Override
    public void stop() {
        _scheduler.close();
        _cargoController.stop();
    }
}