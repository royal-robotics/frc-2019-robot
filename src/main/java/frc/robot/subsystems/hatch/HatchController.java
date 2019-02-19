package frc.robot.subsystems.hatch;

import frc.robot.Controls;
import frc.robot.subsystems.IRobotController;

public class HatchController implements IRobotController
{
    private final Hatch _hatch = new Hatch();

    @Override
    public void init()
    {

    }

    @Override
    public void teleopPeriodic()
    {
        /*
        public static boolean HatchForward() { return hatchForward.isPressed(); }
        public static boolean HatchRelease() { return hatchRelease.isPressed(); }
        public static boolean HatchPull() { return hatchPull.isPressed(); }
        public static boolean HatchPush() { return hatchPush.isPressed(); }
        */
        if (Controls.HatchSystem.HatchForward())
        {
            _hatch.HatchForward();
        }
        else
        {
            _hatch.HatchReverse();
        }

        if (Controls.HatchSystem.HatchRelease())
        {
            _hatch.HatchRelease();
        }
        else
        {
            _hatch.HatchGrab();
        }

        if (Controls.HatchSystem.HatchPull())
        {
            _hatch.EnableHatchRoller();
        }
        else if (Controls.HatchSystem.HatchPush())
        {
            _hatch.ReverseHatchRoller();
        }
        else
        {
            _hatch.StopHatchRoller();
        }
    }

    @Override
    public void diagnosticPeriodic()
    {

    }
}