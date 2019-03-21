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
        else if (Controls.ElevatorSystem.moveBottom())
        {
            _elevator.quickMove(0.0);
        }
        else if (Controls.ElevatorSystem.moveMiddle())
        {
            _elevator.quickMove(28.0);
        }
        else if (Controls.ElevatorSystem.moveCargoPickup())
        {
            _elevator.quickMove(15.5);
        }
        else if (Controls.ElevatorSystem.moveTop())
        {
            _elevator.quickMove(56.0);
        }
        else
        {
            _elevator.stop();
        }
    }

    public void quickMove(double height, Runnable onComplete) {
        _elevator.quickMove(height, onComplete);
    }

    public void stop() {
        _elevator.stop();
    }

    @Override
    public void diagnosticPeriodic() {
        _elevator.diagnosticPeriodic();
    }
}