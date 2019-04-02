package frc.robot.subsystems.climber;

import frc.robot.Controls;
import frc.robot.subsystems.*;

public class ClimberController implements IRobotController
{
    public static final Climber climber = new Climber();

    @Override
    public void init(boolean isTeleop) {
  
        climber.move(0.0);
        climber.vacumeStop();
    }

    @Override
    public void teleopPeriodic() {

        climber.move(Controls.Climber.getPower());

        if (Controls.Climber.runVacuum())
        {
            climber.vacumeStart();
        }
        else
        {
            climber.vacumeStop();
        }

        if (Controls.Climber.lockButton())
        {
            climber.setLock(!climber.isLocked());
        }
    }


    @Override
    public void diagnosticPeriodic() {
        climber.diagnosticPeriodic();
    }
}