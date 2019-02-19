package frc.robot.subsystems.hatch;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

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

    // TODO Reverse maybe?
    public void HatchArmDown()
    {
        _hatchArm.set(ControlMode.PercentOutput, -0.5);
    }

    public void HatchArmUp()
    {
        _hatchArm.set(ControlMode.PercentOutput, 0.5);
    }

    public void HatchArmStop()
    {
        _hatchArm.set(ControlMode.PercentOutput, 0);
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