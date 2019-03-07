package frc.robot.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.can.*;
import com.google.common.primitives.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.libs.components.RoyalEncoder;
import frc.robot.Components;

public class Elevator
{
    private final GravityAdjustedPercentOutput _elevator;
    private final RoyalEncoder _royalEncoder;
    private final ElevatorPositionHolder _elevatorPositionHolder;

    private boolean _isStopped = true;

    public Elevator()
    {
        // Setup the elevator motors
        final WPI_TalonSRX _elevatorMaster = Components.Elevator.elevator1;
        final TalonSRX _elevatorSlave = Components.Elevator.elevator2;
        _elevatorMaster.setInverted(true);
        _elevatorSlave.setInverted(true);
        _elevatorSlave.follow(_elevatorMaster);
        _elevator = new GravityAdjustedPercentOutput(_elevatorMaster, 0.15);

        // Setup elevator encoder
        // Total travel: 60.75 inches
        final double PulsesPerRotation = 256.0;
        final double MeasuredTravelPerRotation = 5.875;
        final double DistancePerPulse = MeasuredTravelPerRotation / PulsesPerRotation;
        final Encoder encoder = Components.Elevator.elevatorEncoder;
        _royalEncoder = new RoyalEncoder(encoder, DistancePerPulse, true);

        // Setup control followers
        _elevatorPositionHolder = new ElevatorPositionHolder(encoder, _elevator);
    }

    public void reset() {
        _royalEncoder.reset();
        _elevatorPositionHolder.reset();
        _elevatorPositionHolder.setSetpoint(0.0);
    }

    public void move(double power) {
        _isStopped = false;
        _elevatorPositionHolder.disable();

        power = Doubles.constrainToRange(power, -0.5, 0.5);
        _elevator.set(power);
    }

    public void stop() {
        if (!_isStopped) {
            final double currentHeight = _royalEncoder.getDistance();
            _elevatorPositionHolder.setSetpoint(currentHeight);
        }

        _isStopped = true;
        _elevatorPositionHolder.enable();
    }

    public void quickMove(double height) {
        _isStopped = false;
        _elevatorPositionHolder.setSetpoint(height);
        _elevatorPositionHolder.enable();
    }

    public void diagnosticPeriodic() {
        SmartDashboard.putNumber("Elevator-Component-Power", _elevator.get());
        SmartDashboard.putNumber("Elevator-Component-Encoder", _royalEncoder.getDistance());
        SmartDashboard.putNumber("Elevator-Component-Velocity", _royalEncoder.getVelocity());
        SmartDashboard.putNumber("Elevator-Component-VelocityMax", _royalEncoder.velocityMax);
        SmartDashboard.putNumber("Elevator-Component-VelocityMin", _royalEncoder.velocityMin);
        
        _elevatorPositionHolder.diagnosticPeriodic();
    }
    public double getElevatorHeight(){
        return _royalEncoder.getDistance();

    }
}