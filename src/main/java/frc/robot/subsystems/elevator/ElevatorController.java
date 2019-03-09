package frc.robot.subsystems.elevator;

import frc.robot.Controls;
import frc.robot.subsystems.*;

public class ElevatorController implements IRobotController
{
    public static final Elevator _elevator = new Elevator();

    @Override
    public void init() {
        _elevator.reset();
    }

    @Override
    public void teleopPeriodic() {
        if (Controls.ElevatorSystem.ManualControl())
        {
            _elevator.move(Controls.ElevatorSystem.GetPower());
        }
        else if (Controls.ElevatorSystem.quickMoveTest())
        {
            _elevator.quickMove(30.0);
        }
        else if (Controls.ElevatorSystem.moveBottom())
        {
            _elevator.quickMove(0.0);
        }
        else if (Controls.ElevatorSystem.moveHatchLow())
        {
            _elevator.quickMove(9.5);
        }
        else if (Controls.ElevatorSystem.moveHatchMid())
        {
            _elevator.quickMove(38.0);
        }
        else if (Controls.ElevatorSystem.moveHatchHigh())
        {
            _elevator.quickMove(60.0);
        }
        else if (Controls.ElevatorSystem.moveCargoLow())
        {
            _elevator.quickMove(17.75);
        }
        else if (Controls.ElevatorSystem.moveCargoMid())
        {
            _elevator.quickMove(30.5);
        }
        else if (Controls.ElevatorSystem.moveCargoHigh())
        {
            _elevator.quickMove(58.5);
        }
        else if (Controls.ElevatorSystem.moveTop())
        {
            _elevator.quickMove(60.0);
        }
        else
        {
            _elevator.stop();
        }
    }

    @Override
    public void diagnosticPeriodic() {
        _elevator.diagnosticPeriodic();
    }
}