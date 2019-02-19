package frc.robot.subsystems.elevator;

import frc.robot.Controls;
import frc.robot.subsystems.*;

public class ElevatorController implements IRobotController
{
    private final Elevator _elevator = new Elevator();

    @Override
    public void init() {

    }

    @Override
    public void teleopPeriodic() {
        if (Controls.ElevatorSystem.Raise())
        {
            _elevator.Raise();
        }
        else if (Controls.ElevatorSystem.Lower())
        {
            _elevator.Lower();
        }
        else
        {
            _elevator.Stop();
        }

        // Front rollers turn off when recieving ball and turn on when shooting ball
        if (Controls.ElevatorSystem.ReceiveBall())
        {
            _elevator.ShootModeOff();
            _elevator.StartCarriageRollers();
        }
        else if (Controls.ElevatorSystem.ShootBall())
        {
            _elevator.ShootModeOn();
            _elevator.StartCarriageRollers();
        }
        else if (Controls.ElevatorSystem.ReverseBall())
        {
            _elevator.ShootModeOn();
            _elevator.ReverseCarriageRollers();
        }
        else
        {
            _elevator.StopCarriageRollers();
        }
    }

    @Override
    public void diagnosticPeriodic() {

    }
}