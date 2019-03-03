package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class ElevatorPositionHolder extends PIDController {
    private static final double LoopIntervalMs = 50.0;
    private static final double LoopInterval = LoopIntervalMs / 1000.0;

    // Output/Input Units: power per inch
    private static final double Kp = 0.22;
    private static final double Ki = 0.04 / (1000.0 / LoopIntervalMs);
    private static final double Kd = 0.005;

    private final PIDSource _source;
    private final GravityAdjustedPercentOutput _output;

    public ElevatorPositionHolder(PIDSource source, GravityAdjustedPercentOutput output) {
        super(Kp, Ki, Kd, source, output, LoopInterval);
        _source = source;
        _output = output;

        this.setOutputRange(-0.4, 0.5);
    }

    @Override
    protected void calculate() {
        // If we're at the floor we don't need to power up the motors.
        if (isBottomSetpoint() && isAtBottom()) {
            _output.disable();
            return;
        }

        super.calculate();
    }

    private boolean isBottomSetpoint() {
        return getSetpoint() < 1.0;
    }

    private boolean isAtBottom() {
        return _source.pidGet() < 1.0;
    }

    public void diagnosticPeriodic() {
        SmartDashboard.putBoolean("Elevator-Holder-Enabled", this.isEnabled());
        SmartDashboard.putNumber("Elevator-Holder-Setpoint", this.getSetpoint());
        SmartDashboard.putNumber("Elevator-Holder-Error", this.getError());
        SmartDashboard.putNumber("Elevator-Holder-Result", this.get());
    }
}