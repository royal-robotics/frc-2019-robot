package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Components;
import frc.robot.Controls;
import frc.robot.subsystems.*;

public class ElevatorController implements IRobotController
{
    private final Elevator _elevator = new Elevator();

    private final DoubleSolenoid _carriageShift = Components.Elevator.carriageShift;

    @Override
    public void init() {
        _elevator.reset();
        _carriageShift.set(Value.kForward);
    }

    @Override
    public void teleopPeriodic() {
        if (Controls.ElevatorSystem.Raise())
        {
            _elevator.raise();
        }
        else if (Controls.ElevatorSystem.Lower())
        {
            _elevator.lower();
        }
        else
        {
            _elevator.stop();
        }

        if (Controls.ElevatorSystem.Shift())
        {
            _carriageShift.set(Value.kReverse);
        } else {
            _carriageShift.set(Value.kForward);
        }
    }

    @Override
    public void diagnosticPeriodic() {
        _elevator.diagnosticPeriodic();
    }
}