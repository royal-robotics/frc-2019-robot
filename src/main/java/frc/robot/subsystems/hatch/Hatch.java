package frc.robot.subsystems.hatch;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Components;

public class Hatch
{
    private static TalonSRX _hatchArm = Components.Hatch.hatchArm;
    private static VictorSPX _hatchRoller = Components.Hatch.hatchRoller;
    private static DoubleSolenoid _carriageRock = Components.Hatch.carriageRock;
    private static DoubleSolenoid _carriageShoot = Components.Hatch.carriageShoot;


    public Hatch()
    {
        // Set up hatch arm PID
        Components.Hatch.hatchArm.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        TalonSRXPIDSetConfiguration pidConfig = new TalonSRXPIDSetConfiguration();
        pidConfig.selectedFeedbackCoefficient = 1.0;
        pidConfig.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
        Components.Hatch.hatchArm.configurePID(pidConfig);
    }

    public void EnableHatchRoller()
    {
        _hatchRoller.set(ControlMode.PercentOutput, 0.5);
    }

    public void ReverseHatchRoller()
    {
        _hatchRoller.set(ControlMode.PercentOutput, -0.5);
    }

    public void StopHatchRoller()
    {
        _hatchRoller.set(ControlMode.PercentOutput, 0);
    }

    public void SetHatchArmSpeed(double value)
    {
        // Set the motor speed to 50% the given value
        double output = value * 0.5;
        _hatchArm.set(ControlMode.PercentOutput, output);
    }

    public void StopHatchArm()
    {
        _hatchArm.set(ControlMode.Position, _hatchArm.getSelectedSensorPosition());
    }

    public void HatchForward()
    {
        _carriageRock.set(Value.kForward);
    }

    public void HatchReverse()
    {
        _carriageRock.set(Value.kReverse);
    }

    public void HatchRelease()
    {
        _carriageShoot.set(Value.kForward);
    }

    public void HatchGrab()
    {
        _carriageShoot.set(Value.kReverse);
    }
}