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
    }

    @Override
    public void diagnosticPeriodic() {
    }
}