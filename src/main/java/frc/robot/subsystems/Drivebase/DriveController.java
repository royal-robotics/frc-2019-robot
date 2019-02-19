package frc.robot.subsystems.drivebase;

import frc.robot.Controls;
import frc.libs.utils.RobotModels.*;
import frc.robot.subsystems.*;

public class DriveController implements IRobotController {
    private final DriveBase _driveBase = new DriveBase();

    @Override
    public void init() {

    }

    @Override
    public void teleopPeriodic() {
        TankThrottleValues throttleValues;
        switch(Controls.DriveSystem.ControlMode.getSimpleName())
        {
            case "TankDrive":
                throttleValues = Controls.DriveSystem.TankDrive.getThrottleValues();
                break;
            case "DifferentialDrive":
                throttleValues = Controls.DriveSystem.DifferentialDrive.getThrottleValues();
                break;
            case "CheesyDrive":
                throttleValues = Controls.DriveSystem.CheesyDrive.getThrottleValues();
                break;
            default:
                throw new UnsupportedOperationException();
        }
        _driveBase.driveTank(throttleValues);

        if (Controls.DriveSystem.LiftRobot())
        {
            _driveBase.EnableLift();
        }
        else
        {
            _driveBase.DisableLift();
        }
    }

    @Override
    public void diagnosticPeriodic() {

    }

    public void followMotionProfile(String profileParameter) {

    }

    public void drive(double left, double right) {
        _driveBase.driveTank(new TankThrottleValues(left, right));
    }
}