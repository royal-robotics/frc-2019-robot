package frc.robot.subsystems.elevator;

import frc.robot.Controls;
import frc.robot.subsystems.*;

public class ElevatorController implements IRobotController
{
    private final Elevator _elevator = new Elevator();

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