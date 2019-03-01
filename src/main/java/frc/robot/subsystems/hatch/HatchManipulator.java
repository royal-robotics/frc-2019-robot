package frc.robot.subsystems.hatch;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.*;
import frc.robot.Components;

public class HatchManipulator
{
    private final SpeedController _hatchArm;
    private final SpeedController _hatchRoller;
    private final DoubleSolenoid _carriageRock;
    private final DoubleSolenoid _carriageShoot;

    public HatchManipulator()
    {
        // Setup hatch intake
        _hatchArm = Components.HatchManipulator.hatchArm;
        _hatchRoller = Components.HatchManipulator.hatchRoller;

        // Setup hatch shooter
        _carriageRock = Components.HatchManipulator.carriageRock;
        _carriageRock.set(Value.kForward);
        _carriageShoot = Components.HatchManipulator.carriageShoot;
        _carriageShoot.set(Value.kForward);
    }

    public void enableHatchRoller()
    {
        _hatchRoller.set(0.5);
    }

    public void reverseHatchRoller()
    {
        _hatchRoller.set(-0.5);
    }

    public void stopHatchRoller()
    {
        _hatchRoller.set(0.0);
    }

    public void hatchArmDown()
    {
        _hatchArm.set(-0.5);
    }

    public void hatchArmUp()
    {
        _hatchArm.set(0.5);
    }

    public void hatchArmStop()
    {
        _hatchArm.set(0.0);
    }

    public void hatchForward()
    {
        _carriageRock.set(Value.kForward);
    }

    public void hatchReverse()
    {
        _carriageRock.set(Value.kReverse);
    }

    public void hatchRelease()
    {
        _carriageShoot.set(Value.kForward);
    }

    public void hatchGrab()
    {
        _carriageShoot.set(Value.kReverse);
    }
}