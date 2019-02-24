package frc.robot.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Components;

public class Elevator
{
    private final TalonSRX _elevator1 = Components.Elevator.elevator1;
    private final TalonSRX _elevator2 = Components.Elevator.elevator2;
    // private final Encoder _elevatorEncoder = Components.Elevator.elevatorEncoder;
    // private final TalonSRX _carriage = Components.Elevator.carriage;
    // private final DoubleSolenoid _carriageShift = Components.Elevator.carriageShift;

    // TODO Elevator encoder
    public Elevator()
    {
        _elevator2.follow(_elevator1);
    }

    public void Raise()
    {
        _elevator1.set(ControlMode.PercentOutput, -0.1);
    }

    public void Lower()
    {
        _elevator1.set(ControlMode.PercentOutput, 0.1);
    }

    public void Stop()
    {
        _elevator1.set(ControlMode.PercentOutput, 0);
    }
}