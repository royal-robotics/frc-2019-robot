package frc.robot.subsystems.drivebase;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.interfaces.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Components;
import frc.libs.components.RoyalEncoder;
import frc.libs.utils.RobotModels.*;

public class DriveBase {
    private final SpeedController _leftDrive;
    private final SpeedController _rightDrive;
    public final RoyalEncoder leftEncoder;
    public final RoyalEncoder rightEncoder;
    public final Gyro gyro;

    private final Solenoid _frontLift;
    private final DoubleSolenoid _backLift;

    public static final double WheelDiameter = 6.0;
    public static final double WheelbaseWidth = 25.0;

    public DriveBase() {
        final WPI_TalonSRX leftDrive1 = Components.DriveBase.leftDrive1;
        Components.DriveBase.leftDrive2.follow(leftDrive1);
        Components.DriveBase.leftDrive3.follow(leftDrive1);
        _leftDrive = leftDrive1;
 
        final WPI_TalonSRX rightDrive1 = Components.DriveBase.rightDrive1;
        rightDrive1.setInverted(true);
        Components.DriveBase.rightDrive2.setInverted(true);
        Components.DriveBase.rightDrive3.setInverted(true);
        Components.DriveBase.rightDrive2.follow(rightDrive1);
        Components.DriveBase.rightDrive3.follow(rightDrive1);
        _rightDrive = rightDrive1;

        final double inchesPerPulse = inchesPerPulse();
        Encoder leftEncoder = Components.DriveBase.leftEncoder;
        this.leftEncoder = new RoyalEncoder(leftEncoder, inchesPerPulse, true);

        Encoder rightEncoder = Components.DriveBase.rightEncoder;
        this.rightEncoder = new RoyalEncoder(rightEncoder, inchesPerPulse, false);

        gyro = Components.DriveBase.gyro;
        gyro.reset();

        _frontLift = Components.DriveBase.frontLift;
        _backLift = Components.DriveBase.backLift;
        _backLift.set(Value.kForward);
    }

    public void reset() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public void driveTank(TankThrottleValues throttleValues) {
        _leftDrive.set(throttleValues.left);
        _rightDrive.set(throttleValues.right);
    }

    public void enableFrontLift()
    {
        _frontLift.set(true);
    }

    public void disableFrontLift()
    {
        _frontLift.set(false);
    }

    public void enableBackLift()
    {
        _backLift.set(Value.kReverse);
    }

    public void disableBackLift() 
    {
        _backLift.set(Value.kForward);
    }

    public void diagnosticPeriodic() {
        SmartDashboard.putNumber("Drive-Power-Left", _leftDrive.get());
        SmartDashboard.putNumber("Drive-Power-Right", _rightDrive.get());
        
        SmartDashboard.putNumber("Drive-Angle", gyro.getAngle());

        SmartDashboard.putNumber("Drive-Distance-Left", leftEncoder.getDistance());
        SmartDashboard.putNumber("Drive-Distance-Right", rightEncoder.getDistance());

        SmartDashboard.putNumber("Drive-Velocity-Left", leftEncoder.getVelocity());
        SmartDashboard.putNumber("Drive-Velocity-Right", rightEncoder.getVelocity());
    }

    private static double inchesPerPulse() {
        // The ratio that relates the rate the encoder turns vs. the wheel.
        final double EncoderGearRatio = 1.0;
        
        // The number of pulses per encoder rotation
        final double PulsesPerRotation = 256.0;

        return  EncoderGearRatio * WheelDiameter * Math.PI / PulsesPerRotation;
    }
}   