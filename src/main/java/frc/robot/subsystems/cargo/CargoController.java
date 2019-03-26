package frc.robot.subsystems.cargo;

import frc.robot.Controls;
import frc.robot.subsystems.*;

public class CargoController implements IRobotController
{
    private final CargoManipulator _cargoManipulator = new CargoManipulator();

    @Override
    public void init(boolean isTeleop)
    {

    }

    @Override
    public void teleopPeriodic()
    {
        boolean useMaxPower = Controls.CargoManipulator.forceMaxPower();
        if (Controls.CargoManipulator.shoot()) {
            _cargoManipulator.shoot(useMaxPower);
        } else if (Controls.CargoManipulator.eject()) {
            _cargoManipulator.eject();
        } else {
            _cargoManipulator.stop();
        }
    }

    @Override
    public void diagnosticPeriodic()
    {

    }

    public void stop() {
        _cargoManipulator.stop();
    }

    public void shoot() {
        _cargoManipulator.shoot();
    }
}