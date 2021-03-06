
package frc.robot.subsystems.cargo;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.*;
import frc.robot.Components;

public class CargoManipulator
{
    private final SpeedController _cargoIntake;
    private final SpeedController _cargoShooter;

    public CargoManipulator()
    {
        // Setup cargo intake motors
        final WPI_TalonSRX ballIntakeMaster = Components.CargoManipulator.cargoIntake1;
        final WPI_TalonSRX ballIntakeSlave = Components.CargoManipulator.cargoIntake2;
        ballIntakeSlave.follow(ballIntakeMaster);
        _cargoIntake = ballIntakeMaster;

        // Setup cargo shooter motor
        _cargoShooter = Components.CargoManipulator.cargoCarriageShooter;
    }

    public void intake() {
        _cargoIntake.set(1.0);
        _cargoShooter.set(1.0);
    }

    public void eject() {
        _cargoIntake.set(-1.0);
        _cargoShooter.set(-1.0);
    }

    public void shoot(boolean useMaxPower) {
        _cargoIntake.set(0.0);
        _cargoShooter.set(useMaxPower ? 1.0 : 0.80);
    }

    public void shoot() {
        this.shoot(false);
    }

    public void stop() {
        _cargoIntake.set(0.0);
        _cargoShooter.set(0.0);
    }
}