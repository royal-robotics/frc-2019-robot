package frc.robot.subsystems.hatch;

import frc.robot.Controls;
import frc.robot.subsystems.*;
import frc.robot.subsystems.elevator.*;

public class HatchController implements IRobotController
{
    private final HatchManipulator _hatchManipulator = new HatchManipulator();

    private final Elevator _elevator = ElevatorController._elevator;

    @Override
    public void init()
    {
        _hatchManipulator.resetArm();
    }

    @Override
    public void teleopPeriodic()
    {
        if (Controls.HatchManipulator.hatchPusher()) {
            _hatchManipulator.pusherOut();
            
            if (Controls.HatchManipulator.hatchFingers())
                _hatchManipulator.openFingers();
            else
                _hatchManipulator.closeFingers();
        }
        else {
            _hatchManipulator.pusherIn();
            _hatchManipulator.closeFingers();
        }

        if(_elevator.getElevatorHeight() < 8.0)
            return;

        if(Controls.HatchManipulator.ManualControl())
            _hatchManipulator.manualHatchArm(Controls.HatchManipulator.GetPower());
        else if (Controls.HatchManipulator.HatchArmHome())
            _hatchManipulator.moveHatchArmHome();
        else if (Controls.HatchManipulator.HatchArmFloor())
            _hatchManipulator.moveHatchArmFloor();
        else if (Controls.HatchManipulator.HatchArmStick())
            _hatchManipulator.moveHatchArmStick();

        if (Controls.HatchManipulator.HatchIntake())
            _hatchManipulator.pullHatchIn();
        else
            _hatchManipulator.stopHatchRoller();
    }

    public void setFingers(boolean setFingers) {
        if (setFingers)
            _hatchManipulator.openFingers();
        else
            _hatchManipulator.closeFingers();
    }

    public void setPusher(boolean setPusher) {
        if (setPusher)
            _hatchManipulator.pusherOut();
        else
            _hatchManipulator.pusherIn();
    }

    @Override
    public void diagnosticPeriodic()
    {
        _hatchManipulator.diagnostics();
    }
}