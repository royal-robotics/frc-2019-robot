package frc.robot.subsystems.drivebase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.libs.components.Limelight;
import frc.libs.utils.RobotModels.TankThrottleValues;

public class DriveVisionRotater extends PIDController {
    private final Limelight _limelight;
    private final DriveBase _drivebase;

    private static final double LoopIntervalMs = 20.0;
    private static final double LoopInterval = LoopIntervalMs / 1000.0;

    // Output/Input Units: power per inch
    private static final double Kp = 0.04;
    private static final double Ki = 0.05 / (1000.0 / LoopIntervalMs);
    private static final double Kd = 0.05;

    public DriveVisionRotater(Limelight limelight, DriveBase drivebase) {
        super(Kp, Ki, Kd, new LimelightSource(limelight), new DriveBaseOutput(drivebase), LoopInterval);
        _limelight = limelight;
        _drivebase = drivebase;

        this.setAbsoluteTolerance(1.0);
    }

    @Override
    protected void calculate() {
        if (!this.isEnabled())
            return;

        super.calculate();
    }

    @Override
    public void disable() {
        if (this.isEnabled())
            super.disable();
    }

    @Override
    public void enable() {
        if (!this.isEnabled())
            this.reset();

        super.enable();
    }

    private static class LimelightSource implements PIDSource {
        private final Limelight _limelight;

        public LimelightSource(Limelight limelight) {
            _limelight = limelight;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) { }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return _limelight.xTarget();
        }
    }

    private static class DriveBaseOutput implements PIDOutput {
        
        private final DriveBase _drivebase;

        public DriveBaseOutput(DriveBase drivebase) {
            _drivebase = drivebase;
        }

        @Override
        public void pidWrite(double output) {
            _drivebase.driveTank(new TankThrottleValues(-output, output));
        }
    }
}