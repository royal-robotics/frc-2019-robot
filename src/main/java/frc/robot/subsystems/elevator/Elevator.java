package frc.robot.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import com.fasterxml.jackson.core.io.OutputDecorator;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.PIDBase;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Components;

public class Elevator
{
    private final WPI_TalonSRX _elevator = Components.Elevator.elevator1;
    private final TalonSRX _elevatorSlave = Components.Elevator.elevator2;
    private final Encoder _encoder = Components.Elevator.elevatorEncoder;

    private final double ControlLoopIntervalMs = 50.0;
    private final ElevatorPositionHolder _elevatorPositionHolder;
    
    // TODO: We probably want a reset method in-case the robot is turned on with the lift up.
    private boolean _isMovingByOperator = false;
    private double _targetHeight = 0.0;

    // private final TalonSRX _carriage = Components.Elevator.carriage;
    // private final DoubleSolenoid _carriageShift = Components.Elevator.carriageShift;

    public Elevator()
    {
        // Setup the elevator motors
        _elevatorSlave.follow(_elevator);

        // Setup elevator encoder
        final double PulsesPerRotation = 256.0;
        final double MeasuredTravelPerRotation = 5.875;
        _encoder.setDistancePerPulse(MeasuredTravelPerRotation / PulsesPerRotation);
        _encoder.setReverseDirection(false);
        _encoder.reset();

        _elevatorPositionHolder = new ElevatorPositionHolder(_encoder, _elevator, ControlLoopIntervalMs);
    }

    public void reset() {
        // TODO: Add reset logic
    }

    public void raise() {
        _elevatorPositionHolder.disable();
        _elevator.set(ControlMode.PercentOutput, -0.4);
    }

    public void lower() {
        _elevatorPositionHolder.disable();
        _elevator.set(ControlMode.PercentOutput, 0.1);
    }

    public void stop() {
        if (!_elevatorPositionHolder.isEnabled()) {

            final double currentHeight = _encoder.getDistance();
            _elevatorPositionHolder.setSetpoint(currentHeight);

            _elevatorPositionHolder.enable();
        }
    }

    public void quickMove(double height) {
        // TODO: Implement dynamic motion profile follower
    }

    private class ElevatorPositionHolder extends PIDController {
        private static final double Kp = 15.0;
        private static final double Ki = 0.0;
        private static final double Kd = 6.0;

        private static final double loopIntervalMs = 50.0;

        public ElevatorPositionHolder(Encoder source, WPI_TalonSRX output, double period) {
            super(Kp, Ki, Kd, source, output, loopIntervalMs / 1000.0);
        }
    }
}