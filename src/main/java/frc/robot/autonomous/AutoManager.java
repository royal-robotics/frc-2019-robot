package frc.robot.autonomous;

import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class AutoManager {
    private final Robot robot;
    private final AutoChooser autoChooser;
    private AutoRoutine currentRoutine;

    public AutoManager(Robot robot)
    {
        this.robot = robot;
        this.autoChooser = new AutoChooser(robot);
    }

    public void startAutonomous()
    {
        // If there is already a routine running we stop it.
        stopAutonomous();

        currentRoutine = autoChooser.getSelectedRoutine();
        if (currentRoutine != null) {
            currentRoutine.start();
        } 
    }

    public void stopAutonomous()
    {
        if (currentRoutine != null)
        {
            currentRoutine.stop();
            currentRoutine = null;
            LoggingContext.init();
        }
    }

    public void printCurrentAutoRoutine() {
        SmartDashboard.putNumber("getFieldStartPosition", Controls.getFieldStartPosition());
        
        switch(Controls.getFieldStartPosition()) {
            case 3: {
                switch(Controls.getAutoRoutineId()) {
                    case 1: {
                        SmartDashboard.putString("AutoRoutine-Name", "RightFrontHatchAutoRoutine");
                        break;
                    }

                    case 2: {
                        SmartDashboard.putString("AutoRoutine-Name", "RightRocketAutoRoutine");
                        break;
                    }

                    case 3: {
                        SmartDashboard.putString("AutoRoutine-Name", "RightCargoAutoRoutine");
                        break;
                    }

                    default: {
                        SmartDashboard.putString("AutoRoutine-Name", "No Routine");
                        break;
                    }
                }
                break;
            }

            case 2: {
                SmartDashboard.putString("AutoRoutine-Name", "CenterRightFrontHatchAutoRoutine");
                break;    
            }

            case 10: {
                SmartDashboard.putString("AutoRoutine-Name", "CenterLeftFrontHatchAutoRoutine");
                break;
            }

            case 9: {
                switch(Controls.getAutoRoutineId()){
                    case 1: {
                        SmartDashboard.putString("AutoRoutine-Name", "LeftFrontHatchAutoRoutine");
                        break;
                    }

                    case 2: {
                        SmartDashboard.putString("AutoRoutine-Name", "LeftRocketAutoRoutine");
                        break;
                    }

                    default: {
                        SmartDashboard.putString("AutoRoutine-Name", "No Routine");
                        break;
                    }
                }
                break;
            }

            default: {
                SmartDashboard.putString("AutoRoutine-Name", "No Routine");
                break;
            }
        }
    }
}