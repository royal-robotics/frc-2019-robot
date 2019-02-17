package frc.robot.autonomous;

import java.util.*;
import java.util.function.*;
import frc.robot.libs.utils.*;
import edu.wpi.first.wpilibj.*;

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

    @Override
    protected final void initialize() {
        _periodicNotifier.startPeriodic(TriggerInterval);

        // Check triggers immediately instead of waiting for the first interval.
        periodicRun();
    }

    @Override
    protected final void stop() {
        _periodicNotifier.stop();
        
        for (TriggerableAutoStep<TParent> triggerableAutoStep : _childStepTriggers.get()) {
            // if the step hasn't been triggered it doesn't need to stop.
            if (!triggerableAutoStep.isTriggered())
                continue;

            triggerableAutoStep._autoStep.stop();
        }
    }

    private final void periodicRun() {
        // Check for child steps that should be triggered
        for (TriggerableAutoStep<TParent> triggerableAutoStep : _childStepTriggers.get()) {
            // check if the step has already been triggered.
            if (triggerableAutoStep.isTriggered())
                continue;

            if (triggerableAutoStep.shouldTrigger())
                triggerableAutoStep.trigger();
        }

        // Check if all steps have been completed.
        boolean allCompleted = true;
        for (TriggerableAutoStep<TParent> triggerableAutoStep : _childStepTriggers.get()) {
            // check if the step has already been triggered.
            if (!triggerableAutoStep._autoStep.hasCompleted()) {
                allCompleted = false;
                break;
            }
        }

        if (allCompleted) {
            Complete();

            // We don't need to check on the progress of child steps once they're all completed.
            _periodicNotifier.stop();
        }
    }

    protected abstract TriggerableAutoStep<TParent> createTrigger(AutoStep autoStep);

    protected final List<TriggerableAutoStep<TParent>> getChildAutoSteps()
    {
        return _childStepTriggers.get();
    }
}