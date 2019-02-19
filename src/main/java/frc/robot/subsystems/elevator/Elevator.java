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
    private final Encoder _elevatorEncoder = Components.Elevator.elevatorEncoder;
    private final TalonSRX _carriage = Components.Elevator.carriage;
    private final DoubleSolenoid _carriageShift = Components.Elevator.carriageShift;

    // TODO Elevator encoder
    public Elevator()
    {
        
    }

    public void Raise()
    {
        _elevator1.set(ControlMode.PercentOutput, 0.5);
        _elevator2.set(ControlMode.PercentOutput, 0.5);
    }

    public void Lower()
    {
        _elevator1.set(ControlMode.PercentOutput, -0.5);
        _elevator2.set(ControlMode.PercentOutput, -0.5);
    }

    public void Stop()
    {
        _elevator1.set(ControlMode.PercentOutput, 0);
        _elevator2.set(ControlMode.PercentOutput, 0);
    }
    

    public void StartCarriageRollers()
    {
        _carriage.set(ControlMode.PercentOutput, 0.5);
    }

    public void ReverseCarriageRollers()
    {
        _carriage.set(ControlMode.PercentOutput, -0.5);
    }

    public void StopCarriageRollers()
    {
        _carriage.set(ControlMode.PercentOutput, 0);
    }

    public void ShootModeOn()
    {
        _carriageShift.set(Value.kForward);
    }

    public void ShootModeOff()
    {
        _carriageShift.set(Value.kReverse);
    }
}