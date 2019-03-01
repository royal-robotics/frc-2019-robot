package frc.robot.subsystems.drivebase;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import frc.robot.Components;
import frc.libs.utils.RobotModels.*;

public class DriveBase {

    // TODO: Figure out actual encoder gear ratio
    // The ratio that relates the rate the encoder turns vs. the wheel.
    public static final double EncoderGearRatio = 0.3429;
    // The diameter of one of the drivebase wheels
    public static final double WheelDiameterInches = 6.0;
    // The number of pulses per encoder rotation
    public static final double PulsesPerRotation = 256.0;
    public static final double InchesPerPulse = EncoderGearRatio * WheelDiameterInches * Math.PI / PulsesPerRotation;


    private final SpeedController _leftDrive;
    private final SpeedController _rightDrive;
    private final Encoder _leftEncoder;
    private final Encoder _rightEncoder;

    private final Solenoid _lift;
    private final DoubleSolenoid _climb;

    public DriveBase() {
        final WPI_TalonSRX leftDrive1 = Components.DriveBase.leftDrive1;
        Components.DriveBase.leftDrive2.follow(leftDrive1);
        Components.DriveBase.leftDrive3.follow(leftDrive1);
        _leftDrive = leftDrive1;

        final WPI_TalonSRX rightDrive1 = Components.DriveBase.rightDrive1;
        Components.DriveBase.rightDrive2.follow(rightDrive1);
        Components.DriveBase.rightDrive3.follow(rightDrive1);
        _rightDrive = rightDrive1;

        _leftEncoder = Components.DriveBase.leftEncoder;
        _leftEncoder.setDistancePerPulse(InchesPerPulse);
        _leftEncoder.reset();

        _rightEncoder = Components.DriveBase.rightEncoder;
        _rightEncoder.setDistancePerPulse(InchesPerPulse);
        _rightEncoder.reset();

        _lift = Components.DriveBase.lift;
        _climb = Components.DriveBase.climb;
    }

    public void driveTank(TankThrottleValues throttleValues) {
        // TODO: Use `leftDrive1.setInverted(true)` instead of negative value
        _leftDrive.set(throttleValues.left);
        _rightDrive.set(-throttleValues.right);
    }

    public void enableLift()
    {
        _lift.set(true);
    }

    public void disableLift()
    {
        _lift.set(false);
    }
}