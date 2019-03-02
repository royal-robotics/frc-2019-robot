package frc.robot.subsystems.hatch;

import frc.robot.Controls;
import frc.robot.subsystems.*;

public class HatchController implements IRobotController
{
    private final HatchManipulator _hatchManipulator = new HatchManipulator();

    @Override
    public void init()
    {

    }

    @Override
    public void teleopPeriodic()
    {
        if (Controls.HatchManipulator.hatchRock())
            _hatchManipulator.rockForward();
        else
            _hatchManipulator.rockBackwards();

        if (Controls.HatchManipulator.hatchShoot())
            _hatchManipulator.shootHatchOut();
        else
            _hatchManipulator.shootHatchIn();


            
    }

    @Override
    public void diagnosticPeriodic()
    {

    }
}