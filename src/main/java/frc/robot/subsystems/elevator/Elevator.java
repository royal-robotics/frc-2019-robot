package frc.robot.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    public Elevator()
    {
        // Setup the elevator motors
        _elevatorSlave.follow(_elevator);

        // Total travel: 60.75 inches
        // Setup elevator encoder
        final double PulsesPerRotation = 256.0;
        final double MeasuredTravelPerRotation = 5.875;
        _encoder.setDistancePerPulse(MeasuredTravelPerRotation / PulsesPerRotation);
        _encoder.setReverseDirection(true);
        _encoder.reset();

        _elevatorPositionHolder = new ElevatorPositionHolder(_encoder, _elevator, ControlLoopIntervalMs);
    }

    public void reset() {
        _encoder.reset();
        _elevatorPositionHolder.reset();
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
            System.out.println("Enabling Holder: " + currentHeight);
            _elevatorPositionHolder.setSetpoint(currentHeight);
        }

        _elevatorPositionHolder.enable();
    }

    public void diagnosticPeriodic() {
        SmartDashboard.putNumber("Elevator-Component-Power", _elevator.get());
        SmartDashboard.putNumber("Elevator-Component-Encoder", _encoder.getDistance());

        SmartDashboard.putNumber("Elevator-Holder-Setpoint", _elevatorPositionHolder.getSetpoint());
        SmartDashboard.putBoolean("Elevator-Holder-Enabled", _elevatorPositionHolder.isEnabled());
    }

    public void quickMove(double height) {
        // TODO: Implement dynamic motion profile follower
    }

    private class ElevatorPositionHolder extends PIDController {
        private static final double Kp = -0.05; // power per inch
        private static final double Ki = 0.0;
        private static final double Kd = 0.0;

        private static final double loopIntervalMs = 50.0;

        private final Encoder _encoder;

        public ElevatorPositionHolder(Encoder source, WPI_TalonSRX output, double period) {
            super(Kp, Ki, Kd, source, output, loopIntervalMs / 1000.0);

            _encoder = source;
            this.setOutputRange(-0.4, 0.1);
            
            // this.setAbsoluteTolerance(absvalue);
            // this.
        }

        @Override
        protected void calculate() {
            super.calculate();

            double input = this.m_pidInput.pidGet();
            double setpoint = this.getSetpoint();
            double error = (setpoint - input);
            double result = this.getP() * (setpoint - input);
            SmartDashboard.putNumber("Elevator-Control-Error", error);
            SmartDashboard.putNumber("Elevator-Control-Result", result);
        }

        private class ElevatorOutput implements PIDOutput
        {
            private final PIDOutput _motor;

            public ElevatorOutput(PIDOutput motor)
            {
                _motor = motor;
            }

            private boolean isUp(double output) {
                return output < 0.0;
            }

            @Override
            public void pidWrite(double output) {
                if (isUp(output)) {
                    _motor.pidWrite(output);
                } else {
                    _motor.pidWrite(output);
                }
            }
        }
    }

    private class ElevatorLinearFollower {

    }
}