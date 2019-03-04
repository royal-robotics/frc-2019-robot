package frc.robot.subsystems.hatch;

import frc.robot.Controls;
import frc.robot.subsystems.*;

public class HatchController implements IRobotController
{
    private final HatchManipulator _hatchManipulator = new HatchManipulator();

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

        if (Controls.HatchManipulator.HatchArmHome())
            _hatchManipulator.moveHatchArmHome();
        else if (Controls.HatchManipulator.HatchArmFloor())
            _hatchManipulator.moveHatchArmFloor();
        else if (Controls.HatchManipulator.HatchArmStick())
            _hatchManipulator.moveHatchArmStick();
        else
            _hatchManipulator.stopHatchArm();

        if (Controls.HatchManipulator.HatchIntake())
            _hatchManipulator.pullHatchIn();
        else
            _hatchManipulator.stopHatchRoller();
            
    }

    @Override
    public void diagnosticPeriodic()
    {
        _hatchManipulator.diagnostics();
    }
}