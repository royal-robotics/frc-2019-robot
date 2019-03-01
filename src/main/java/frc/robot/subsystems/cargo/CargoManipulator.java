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
        _cargoIntakeArm.set(Value.kForward);

        // Setup cargo shooter motor
        _cargoShooter = Components.CargoManipulator.cargoCarriageShooter;

        // Setup cargo shooter shifter
        _cargoShifter = Components.CargoManipulator.carriageShooterShifter;
        _cargoShifter.set(Value.kForward);
    }

    public void intakeCargo()
    {
        _cargoIntake.set(0.5);
    }

    public void ejectCargo()
    {
        _cargoIntake.set(-0.5);
    }

    public void stopCargoIntake()
    {
        _cargoIntake.set(0.0);
    }

    public void extendIntake()
    {
        _cargoIntakeArm.set(Value.kReverse);
    }

    public void retractIntake()
    {
        _cargoIntakeArm.set(Value.kForward);
    }
    
    public void runCargoShooter()
    {
        _cargoShooter.set(0.5);
    }

    public void reverseCargoShooter()
    {
        _cargoShooter.set(-0.5);
    }

    public void stopCargoShooter()
    {
        _cargoShooter.set(0.0);
    }
}