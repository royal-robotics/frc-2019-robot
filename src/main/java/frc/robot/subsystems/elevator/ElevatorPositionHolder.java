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

    public ElevatorPositionHolder(PIDSource source, PIDOutput output) {
        super(Kp, Ki, Kd, source, output, LoopInterval);

        this.setOutputRange(-0.4, 0.5);
        //this.setPercentTolerance(0.05);
    }

    public void diagnosticPeriodic() {
        SmartDashboard.putBoolean("Elevator-Holder-Enabled", this.isEnabled());
        SmartDashboard.putNumber("Elevator-Holder-Setpoint", this.getSetpoint());
        SmartDashboard.putNumber("Elevator-Holder-Error", this.getError());
        SmartDashboard.putNumber("Elevator-Holder-Result", this.get());
    }
}