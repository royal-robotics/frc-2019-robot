
package frc.robot.subsystems.cargo;

import edu.wpi.first.wpilibj.*;
import frc.robot.Components;

public class CargoManipulator
{
    private final SpeedController _cargoShooter;

    public CargoManipulator()
    {
        // Setup cargo shooter motor
        _cargoShooter = Components.CargoManipulator.cargoCarriageShooter;
    }

    public void intake() {
        _cargoShooter.set(1.0);
    }

    public void eject() {
        _cargoShooter.set(-1.0);
    }

    public void shoot(boolean useMaxPower) {
        _cargoShooter.set(useMaxPower ? 1.0 : 0.80);
    }

    public void shoot() {
        this.shoot(false);
    }

    public void stop() {
        _cargoShooter.set(0.0);
    }
}