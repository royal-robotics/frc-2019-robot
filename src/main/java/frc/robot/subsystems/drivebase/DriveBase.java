package frc.robot.subsystems.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
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

    private final TalonSRX _leftDrive1 = Components.DriveBase.leftDrive1;
    private final VictorSPX _leftDrive2 = Components.DriveBase.leftDrive2;
    private final VictorSPX _leftDrive3 = Components.DriveBase.leftDrive3;
    private final TalonSRX _rightDrive1 = Components.DriveBase.rightDrive1;
    private final VictorSPX _rightDrive2 = Components.DriveBase.rightDrive2;
    private final VictorSPX _rightDrive3 = Components.DriveBase.rightDrive3;
    private final Solenoid _lift = Components.DriveBase.lift;

    private final Encoder _leftEncoder = Components.DriveBase.leftEncoder;
    private final Encoder _rightEncoder = Components.DriveBase.rightEncoder;

    public DriveBase() {
        _leftDrive2.follow(_leftDrive1);
        _leftDrive3.follow(_leftDrive1);

        _rightDrive2.follow(_rightDrive1);
        _rightDrive3.follow(_rightDrive1);

        _leftEncoder.setDistancePerPulse(InchesPerPulse);
        _leftEncoder.reset();
        _rightEncoder.setDistancePerPulse(InchesPerPulse);
        _rightEncoder.reset();
    }

    public void driveTank(TankThrottleValues throttleValues) {
        _leftDrive1.set(ControlMode.PercentOutput, throttleValues.left);
        _rightDrive1.set(ControlMode.PercentOutput, -throttleValues.right);
    }

    public void EnableLift()
    {
        _lift.set(true);
    }

    public void DisableLift()
    {
        _lift.set(false);
    }
}