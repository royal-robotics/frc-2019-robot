package frc.robot.subsystems.hatch;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Controls;
import frc.robot.subsystems.*;

public class HatchController implements IRobotController
{
    private final HatchManipulator _hatchManipulator = new HatchManipulator();

    @Override
    public void init()
    {
        _hatchManipulator.resetArmPosition();
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

        if (Controls.HatchManipulator.ManualHatchArm())
            _hatchManipulator.moveHatchArm(Controls.HatchManipulator.GetHatchArmSpeed());
        else
            _hatchManipulator.stopHatchArm();

        if (Controls.HatchManipulator.HatchPull())
            _hatchManipulator.pullHatchIn();
        else if (Controls.HatchManipulator.HatchPush())
            _hatchManipulator.pushHatchOut();
        else
            _hatchManipulator.stopHatchRoller();
            
    }

    @Override
    public void diagnosticPeriodic()
    {
        _hatchManipulator.diagnostics();
    }
}