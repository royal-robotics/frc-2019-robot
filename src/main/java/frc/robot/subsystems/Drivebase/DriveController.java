package frc.robot.subsystems.Drivebase;

import frc.robot.Controls;
import frc.robot.libs.utils.RobotModels.*;
import frc.robot.subsystems.*;

public class DriveController implements IRobotController {
    private DriveBase _driveBase = new DriveBase();

    @Override
    public void componentInit() {

    }

    @Override
    public void componentPeriodic() {

    }

    @Override
    public void teleopInit() {
        
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
    }

    public void followMotionProfile(String profileParameter) {

    }

    public void drive(double left, double right) {
        _driveBase.driveTank(new TankThrottleValues(left, right));
    }
}