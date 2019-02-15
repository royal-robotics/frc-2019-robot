package frc.robot.autonomous;

import java.util.*;

// Auto manager / top level step
public class AutoManager {
    private final long msPeriodic = 50;
    private final int firstStep = 0;

    private List<AutoStep> autoSteps;

    private int currentStep = firstStep;
    private Timer timer = new Timer();

    public AutoManager(List<AutoStep> autoSteps) {
        this.autoSteps = autoSteps;

        if(autoSteps.size() <= firstStep)
            return;

        autoSteps.get(firstStep).start();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                AutoStep currentAutoStep = autoSteps.get(currentStep);
                if(currentAutoStep.isCompleted()) {
                    if(autoSteps.size() == currentStep + 1) {
                        timer.cancel();
                        timer.purge();
                        timer = null;
                        return; //Auto completed
                    } else {
                        currentAutoStep = autoSteps.get(++currentStep);
                        currentAutoStep.start();
                    }
                }

                currentAutoStep.periodic();
            }
        }, 0, msPeriodic);
    }

    public void stop() {
        for(AutoStep step : autoSteps) {
            step.stop();
        }
    }
}