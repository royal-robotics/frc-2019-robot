package frc.robot.subsystems.ballintake;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Components;

public class BallIntake
{
    public BallIntake()
    {

    }

    public void RollUpBall()
    {
        Components.BallIntake.ballIntake1.set(ControlMode.PercentOutput, 0.5);
        Components.BallIntake.ballIntake2.set(ControlMode.PercentOutput, 0.5);
    }

    public void RollDownBall()
    {
        Components.BallIntake.ballIntake1.set(ControlMode.PercentOutput, -0.5);
        Components.BallIntake.ballIntake2.set(ControlMode.PercentOutput, -0.5);
    }

    public void StopRollers()
    {
        Components.BallIntake.ballIntake1.set(ControlMode.PercentOutput, 0);
        Components.BallIntake.ballIntake2.set(ControlMode.PercentOutput, 0);
    }

    public void ChangeArmPosition()
    {
        if (Components.BallIntake.ballArm.get() == Value.kReverse)
        {
            Components.BallIntake.ballArm.set(Value.kForward);
        }
        else
        {
            Components.BallIntake.ballArm.set(Value.kReverse);
        }
    }
}