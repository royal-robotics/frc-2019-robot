package frc.robot.subsystems.Drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.Components;
import frc.robot.libs.utils.RobotModels.*;

public class DriveBase {
    private TalonSRX _leftDrive1 = Components.DriveBase.leftDrive1;
    private VictorSPX _leftDrive2 = Components.DriveBase.leftDrive2;
    private VictorSPX _leftDrive3 = Components.DriveBase.leftDrive3;
    private TalonSRX _rightDrive1 = Components.DriveBase.rightDrive1;
    private VictorSPX _rightDrive2 = Components.DriveBase.rightDrive2;
    private VictorSPX _rightDrive3 = Components.DriveBase.rightDrive3;

    public DriveBase() {
        _leftDrive2.follow(_leftDrive1);
        _leftDrive3.follow(_leftDrive1);

        _rightDrive2.follow(_rightDrive1);
        _rightDrive3.follow(_rightDrive1);
    }

    public void driveTank(TankThrottleValues throttleValues) {
        _leftDrive1.set(ControlMode.PercentOutput, throttleValues.left);
        _rightDrive1.set(ControlMode.PercentOutput, throttleValues.right);
    }

    public void setLiftMode() {
        throw new UnsupportedOperationException("TODO: Implement this");
    }

    public void getLiftMode() {
        throw new UnsupportedOperationException("TODO: Implement this");
    }
}