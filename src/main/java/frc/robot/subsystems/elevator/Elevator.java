package frc.robot.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Components;

public class Elevator
{
    private final GravityAdjustedPercentOutput _elevator;
    private final Encoder _encoder;
    
    private final ElevatorPositionHolder _elevatorPositionHolder;
    private final ElevatorFollower _elevatorFollower;

    public Elevator()
    {
        // Setup the elevator motors
        final WPI_TalonSRX _elevatorMaster = Components.Elevator.elevator1;
        final TalonSRX _elevatorSlave = Components.Elevator.elevator2;
        _elevatorMaster.setInverted(true);
        _elevatorSlave.setInverted(true);
        _elevatorSlave.follow(_elevatorMaster);
        _elevator = new GravityAdjustedPercentOutput(_elevatorMaster, 0.12);

        // Setup elevator encoder
        // Total travel: 60.75 inches
        final double PulsesPerRotation = 256.0;
        final double MeasuredTravelPerRotation = 5.875;
        _encoder = Components.Elevator.elevatorEncoder;
        _encoder.setDistancePerPulse(MeasuredTravelPerRotation / PulsesPerRotation);
        _encoder.setReverseDirection(true);
        _encoder.reset();

        // Setup control followers
        _elevatorPositionHolder = new ElevatorPositionHolder(_encoder, _elevator);
        _elevatorFollower = new ElevatorFollower();
    }

    public void reset() {
        _encoder.reset();
        _elevatorPositionHolder.reset();
    }

    public void raise() {
        _elevatorPositionHolder.disable();
        _elevator.set(0.4);
    }

    public void lower() {
        _elevatorPositionHolder.disable();
        _elevator.set(-0.4);
    }

    public void stop() {
        if (!_elevatorPositionHolder.isEnabled()) {
            final double currentHeight = _encoder.getDistance();
            _elevatorPositionHolder.setSetpoint(currentHeight);
        }

        _elevatorPositionHolder.enable();
    }

    public void quickMove(double height) {
        // TODO: Implement dynamic motion profile follower
    }

    public void diagnosticPeriodic() {
        SmartDashboard.putNumber("Elevator-Component-Power", _elevator.get());
        SmartDashboard.putNumber("Elevator-Component-Encoder", _encoder.getDistance());
        _elevatorPositionHolder.diagnosticPeriodic();
    }
}