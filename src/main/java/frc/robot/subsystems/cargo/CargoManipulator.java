
package frc.robot.subsystems.cargo;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.*;
import frc.robot.Components;

public class CargoManipulator
{
    private final SpeedController _cargoIntake;
    private final DoubleSolenoid _cargoIntakeArm;
    private final SpeedController _cargoShooter;
    private final DoubleSolenoid _cargoShifter;

    public CargoManipulator()
    {
        // Setup cargo intake motors
        final WPI_TalonSRX ballIntakeMaster = Components.CargoManipulator.cargoIntake1;
        final WPI_TalonSRX ballIntakeSlave = Components.CargoManipulator.cargoIntake2;
        ballIntakeSlave.follow(ballIntakeMaster);
        _cargoIntake = ballIntakeMaster;
        
        // Setup cargo intake arm
        _cargoIntakeArm = Components.CargoManipulator.cargoArm;
        _cargoIntakeArm.set(Value.kReverse);

        // Setup cargo shooter motor
        _cargoShooter = Components.CargoManipulator.cargoCarriageShooter;

        // Setup cargo shooter shifter
        _cargoShifter = Components.CargoManipulator.carriageShooterShifter;
        _cargoShifter.set(Value.kForward);
    }

    public void intake() {
        _cargoIntakeArm.set(Value.kForward);
        _cargoIntake.set(1.0);
        _cargoShifter.set(Value.kReverse);
        _cargoShooter.set(1.0);
    }

    public void eject() {
        _cargoIntakeArm.set(Value.kForward);
        _cargoIntake.set(-1.0);
        _cargoShifter.set(Value.kForward);
        _cargoShooter.set(-1.0);
    }

    public void shoot() {
        _cargoIntakeArm.set(Value.kReverse);
        _cargoIntake.set(0.0);
        _cargoShifter.set(Value.kForward);
        _cargoShooter.set(0.75);
    }

    public void stop() {

        _cargoIntakeArm.set(Value.kReverse);
        _cargoIntake.set(0.0);
        _cargoShifter.set(Value.kForward);
        _cargoShooter.set(0.0);
    }
}