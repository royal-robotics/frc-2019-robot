package frc.robot.autonomous;

import java.util.*;
import edu.wpi.first.wpilibj.*;

public class AutoStepGroup extends AutoStep {
    private static final double TriggerInterval = 0.050;

    private final List<TriggerableAutoStep<AutoStepGroup>> _childSteps;
    private final Notifier _periodicNotifier;

    public AutoStepGroup(List<TriggerableAutoStep<AutoStepGroup>> childSteps) {
        _childSteps = childSteps;
        _periodicNotifier = new Notifier(() -> periodicRun());
    }

    // public AutoStepGroup(List<AutoStep> childSteps) {
    //     _childSteps = null;
    //     _periodicNotifier = new Notifier(() -> periodicRun());
    // }

    protected List<TriggerableAutoStep<AutoStepGroup>> setupChildSteps(List<AutoStep> autoSteps) {
        return null;
    }

    @Override
    protected final void initialize() {
        _periodicNotifier.startPeriodic(TriggerInterval);

        // Check triggers immediately instead of waiting for the first interval.
        periodicRun();
    }

    @Override
    protected boolean isCompleted() {
        for (TriggerableAutoStep<AutoStepGroup> triggerableAutoStep : _childSteps) {
            if (!triggerableAutoStep.isCompleted())
                return false;
        }

        return true;
    }

    @Override
    protected void stop() {
        _periodicNotifier.stop();
        
        for (TriggerableAutoStep<AutoStepGroup> triggerableAutoStep : _childSteps) {
            // if the step hasn't been triggered it doesn't need to stop.
            if (!triggerableAutoStep.isTriggered())
                continue;

            // TODO: fix this (should actually stop)
            //triggerableAutoStep._autoStep.stop();
        }
    }

    private final void periodicRun() {
        for (TriggerableAutoStep<AutoStepGroup> triggerableAutoStep : _childSteps) {
            // check if the step has already been triggered.
            if (triggerableAutoStep.isTriggered())
                continue;

            if (triggerableAutoStep.shouldTrigger())
                triggerableAutoStep.trigger();
        }
    }
}