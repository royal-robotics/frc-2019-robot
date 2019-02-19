package frc.robot.subsystems.ballintake;

import frc.robot.Controls;
import frc.robot.subsystems.*;

public class BallIntakeController implements IRobotController
{
    private final BallIntake _ballIntake = new BallIntake();

    @Override
    public void init()
    {

    }

    @Override
    public void teleopPeriodic()
    {
        if (Controls.BallIntakeSystem.Intake())
        {
            _ballIntake.RollUpBall();
        }
        else if (Controls.BallIntakeSystem.Outtake())
        {
            _ballIntake.RollDownBall();
        }
        else
        {
            _ballIntake.StopRollers();
        }

        if(Controls.BallIntakeSystem.ArmToggle())
        {
            _ballIntake.ChangeArmPosition();
        }
        
    }

    @Override
    public void diagnosticPeriodic()
    {

    }
}