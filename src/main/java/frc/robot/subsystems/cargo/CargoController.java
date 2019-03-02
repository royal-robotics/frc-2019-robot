package frc.robot.subsystems.cargo;

import frc.robot.Controls;
import frc.robot.subsystems.*;

public class CargoController implements IRobotController
{
    private final CargoManipulator _cargoManipulator = new CargoManipulator();

    @Override
    public void init()
    {

    }

    @Override
    public void teleopPeriodic()
    {
        if (Controls.CargoManipulator.intake()) {
            _cargoManipulator.intake();
        } else if (Controls.CargoManipulator.shoot()) {
            _cargoManipulator.shoot();
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
}