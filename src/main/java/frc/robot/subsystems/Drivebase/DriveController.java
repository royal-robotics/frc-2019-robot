package frc.robot.subsystems.Drivebase;

import frc.robot.Controls;
import frc.robot.subsystems.IRobotController;

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
        double leftThrottle = Controls.DriveSystem.TankDrive.getLeftThrottleValue();
        double rightThrottle = Controls.DriveSystem.TankDrive.getRightThrottleValue();
        _driveBase.driveTank(leftThrottle, rightThrottle);
    }

    public void followMotionProfile(String profileParameter) {

    }

    public void drive(double left, double right) {
        _driveBase.driveTank(left, right);
    }
}