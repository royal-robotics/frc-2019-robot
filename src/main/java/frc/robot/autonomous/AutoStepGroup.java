package frc.robot.autonomous;

import java.util.*;
import java.util.function.*;
import edu.wpi.first.wpilibj.*;
import frc.robot.libs.utils.*;

public abstract class AutoStepGroup<TParent extends AutoStepGroup<TParent>> extends AutoStep {
    private static final double TriggerInterval = 0.050;

    private Supplier<List<TriggerableAutoStep<TParent>>> _childStepTriggers;
    private final Notifier _periodicNotifier;

    public AutoStepGroup(List<AutoStep> childSteps) {
        _periodicNotifier = new Notifier(() -> periodicRun());

        _childStepTriggers = Lazy.lazily(() -> {
            List<TriggerableAutoStep<TParent>> childStepTriggers = new ArrayList<>();

            for (AutoStep autoStep : childSteps) {
                childStepTriggers.add(createTrigger(autoStep));
            }
            return _childStepTriggers = Lazy.value(childStepTriggers);
        });
    }

    protected abstract TriggerableAutoStep<TParent> createTrigger(AutoStep autoStep);

    @Override
    protected final void initialize() {
        _periodicNotifier.startPeriodic(TriggerInterval);

        // Check triggers immediately instead of waiting for the first interval.
        periodicRun();
    }

    @Override
    protected boolean isCompleted() {
        for (TriggerableAutoStep<TParent> triggerableAutoStep : _childStepTriggers.get()) {
            if (!triggerableAutoStep.isCompleted())
                return false;
        }

        return true;
    }

    @Override
    protected void stop() {
        _periodicNotifier.stop();
        
        for (TriggerableAutoStep<TParent> triggerableAutoStep : _childStepTriggers.get()) {
            // if the step hasn't been triggered it doesn't need to stop.
            if (!triggerableAutoStep.isTriggered())
                continue;

            // TODO: fix this (should actually stop)
            //triggerableAutoStep._autoStep.stop();
        }
    }

    private final void periodicRun() {
        for (TriggerableAutoStep<TParent> triggerableAutoStep : _childStepTriggers.get()) {
            // check if the step has already been triggered.
            if (triggerableAutoStep.isTriggered())
                continue;

            if (triggerableAutoStep.shouldTrigger())
                triggerableAutoStep.trigger();
        }
    }

    protected final List<TriggerableAutoStep<TParent>> getChildAutoSteps()
    {
        return _childStepTriggers.get();
    }
}