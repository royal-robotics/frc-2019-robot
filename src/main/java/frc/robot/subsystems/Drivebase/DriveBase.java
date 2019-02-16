package frc.robot.subsystems.Drivebase;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Victor;
import frc.robot.Components;

public class DriveBase {

    private TalonSRX _leftDrive1 = Components.DriveBase.leftDrive1;
    private Victor _leftDrive2 = Components.DriveBase.leftDrive2;
    private Victor _leftDrive3 = Components.DriveBase.leftDrive3;

    public void driveTank(double left, double right) {
        throw new UnsupportedOperationException("TODO: Implement this");
    }

    public void setLiftMode() {
        throw new UnsupportedOperationException("TODO: Implement this");
    }

    public void getLiftMode() {
        throw new UnsupportedOperationException("TODO: Implement this");
    }
}