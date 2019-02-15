package frc.robot.autonomous;

import java.util.*;
import frc.robot.libs.triggers.*;

public abstract class AutoStep {
    private List<AbstractMap.SimpleEntry<Trigger, AutoStep>> childSteps = new ArrayList<>();
    private boolean started = false;

    public void start() {
        started = true;
        initialize();
    }

    protected abstract void initialize();

    public boolean isStarted() { return started; }

    public void addChildStep(Trigger stepTrigger, AutoStep step) {
        childSteps.add(new AbstractMap.SimpleEntry<Trigger,AutoStep>(stepTrigger, step));
    }

    public void periodic() {
        for (AbstractMap.SimpleEntry<Trigger, AutoStep> step : childSteps) {
            if (step.getKey().isTriggered()) {
                if (!step.getValue().isStarted()) {
                    step.getValue().start();
                }

                step.getValue().periodic();
            }
        }
    }

    public boolean isCompleted() {
        for (AbstractMap.SimpleEntry<Trigger, AutoStep> step : childSteps) {
            if (!step.getValue().isCompleted()) {
                return false;
            }
        }

        return true;
    }

    public void stop() {
        for (AbstractMap.SimpleEntry<Trigger, AutoStep> step : childSteps) {
            step.getValue().stop();
        }
    }
}