package frc.robot.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.libs.components.RoyalEncoder;
import frc.robot.Components;

public class Elevator
{
    private final GravityAdjustedPercentOutput _elevator;
    private final RoyalEncoder _royalEncoder;

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
        final double DistancePerPulse = MeasuredTravelPerRotation / PulsesPerRotation;
        final Encoder encoder = Components.Elevator.elevatorEncoder;
        _royalEncoder = new RoyalEncoder(encoder, DistancePerPulse, true);

        // Setup control followers
        _elevatorPositionHolder = new ElevatorPositionHolder(encoder, _elevator);
        _elevatorFollower = new ElevatorFollower();
    }

    public void reset() {
        _royalEncoder.reset();
        _elevatorPositionHolder.reset();
    }

    public void move(double power) {
        _elevatorPositionHolder.disable();
        if (power > 0.4) {
            power = 0.4;
        } else if (power < -0.4) {
            power = -0.4;
        }
        _elevator.set(power);
    }

    public void stop() {
        if (!_elevatorPositionHolder.isEnabled()) {
            final double currentHeight = _royalEncoder.getDistance();
            _elevatorPositionHolder.setSetpoint(currentHeight);
        }

        _elevatorPositionHolder.enable();
    }

    public void quickMove(double height) {
        // TODO: Implement dynamic motion profile follower
    }

    public void diagnosticPeriodic() {
        SmartDashboard.putNumber("Elevator-Component-Power", _elevator.get());
        SmartDashboard.putNumber("Elevator-Component-Encoder", _royalEncoder.getDistance());
        SmartDashboard.putNumber("Elevator-Component-Velocity", _royalEncoder.getVelocity());
        SmartDashboard.putNumber("Elevator-Component-VelocityMax", _royalEncoder.velocityMax);
        SmartDashboard.putNumber("Elevator-Component-VelocityMin", _royalEncoder.velocityMin);
        
        _elevatorPositionHolder.diagnosticPeriodic();
    }
}