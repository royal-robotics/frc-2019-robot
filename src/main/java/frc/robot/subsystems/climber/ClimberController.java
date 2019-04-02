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

        if (Controls.Climber.isPowered())
        {
            climber.setLock(false);
            climber.move(Controls.Climber.getPower());
        }
        else
        {
            climber.setLock(true);
            climber.move(0.0);
        }
        
        if (Controls.Climber.runVacuum())
        {
            climber.vacumeStart();
        }
        else
        {
            climber.vacumeStop();
        }
    }


    @Override
    public void diagnosticPeriodic() {
        climber.diagnosticPeriodic();
    }
}