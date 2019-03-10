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
        if (Controls.HatchManipulator.hatchRock()) {
            _hatchManipulator.rockForward();
            
            if (Controls.HatchManipulator.hatchPush())
                _hatchManipulator.shootHatchOut();
            else
                _hatchManipulator.shootHatchIn();
        }
        else {
            _hatchManipulator.rockBackwards();
            _hatchManipulator.shootHatchIn();
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

    public void setRock(boolean setRockOut) {
        if (setRockOut)
            _hatchManipulator.rockForward();
        else
            _hatchManipulator.rockBackwards();
    }

    public void setShoot(boolean setShooterOut) {
        if (setShooterOut)
            _hatchManipulator.shootHatchOut();
        else
            _hatchManipulator.shootHatchIn();
    }

    @Override
    public void diagnosticPeriodic()
    {
        _hatchManipulator.diagnostics();
    }
}