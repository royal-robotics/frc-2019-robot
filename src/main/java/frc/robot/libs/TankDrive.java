package frc.robot.libs;

import com.ctre.phoenix.motorcontrol.*;

public class TankDrive
{
    private IMotorController _left;
    private IMotorController _right;

    public TankDrive(IMotorController left, IMotorController right)
    {
        _left = left;
        _right = right;
    }

    public void set(ControlMode controlMode, double left, double right)
    {
        switch (controlMode)
        {
            case PercentOutput:
                _left.enableVoltageCompensation(false);
                _right.enableVoltageCompensation(false);
                _left.set(ControlMode.PercentOutput, left);
                _right.set(ControlMode.PercentOutput, right);
        }
    }
}