package frc.libs.autonomous;

import java.util.*;
import java.util.function.*;
import frc.libs.utils.*;
import edu.wpi.first.wpilibj.*;

public abstract class AutoStepGroup<TParent extends AutoStepGroup<TParent>> extends AutoStep {
    private static final double TriggerCheckInterval = 0.050;

    //private Supplier<List<TriggerableAutoStep<TParent>>> _childStepTriggers;
    private List<TriggerableAutoStep<TParent>> _childStepTriggers;
    private final Notifier _periodicNotifier;

    public AutoStepGroup(AutoLogger logger, List<Function<AutoStepGroup<TParent>, TriggerableAutoStep<TParent>>> childStepTriggerThunks) {
        super(logger);

        _periodicNotifier = new Notifier(() -> periodicRun());

        List<TriggerableAutoStep<TParent>> childStepTriggers = new ArrayList<>(childStepTriggerThunks.size());
        for (Function<AutoStepGroup<TParent>, TriggerableAutoStep<TParent>> childStepTriggerThunk : childStepTriggerThunks) {
            childStepTriggers.add(childStepTriggerThunk.apply(this));
        }
        _childStepTriggers = childStepTriggers;
    }

    @Override
    protected final void initialize() {
        _periodicNotifier.startPeriodic(TriggerCheckInterval);

        // Check triggers immediately instead of waiting for the first interval.
        periodicRun();
    }

    @Override
    public final void stop() {
        _periodicNotifier.stop();
        
        for (TriggerableAutoStep<TParent> triggerableAutoStep : _childStepTriggers) {
            // if the step hasn't been triggered it doesn't need to stop.
            if (!triggerableAutoStep.isTriggered())
                continue;

            triggerableAutoStep.autoStep.stop();
        }
    }

    private final void periodicRun() {
        // Check for child steps that should be triggered
        for (TriggerableAutoStep<TParent> triggerableAutoStep : _childStepTriggers) {
            // check if the step has already been triggered.
            if (triggerableAutoStep.isTriggered())
                continue;

            if (triggerableAutoStep.shouldTrigger())
            {
                triggerableAutoStep.trigger();
            }
        }

        // Check if all steps have been completed.
        boolean allCompleted = true;
        for (TriggerableAutoStep<TParent> triggerableAutoStep : _childStepTriggers) {
            // check if the step has already been triggered.
            if (!triggerableAutoStep.autoStep.hasCompleted()) {
                allCompleted = false;
                break;
            }
        }

        if (allCompleted) {
            complete();

            // We don't need to check on the progress of child steps once they're all completed.
            _periodicNotifier.stop();
        }
    }

    protected final List<TriggerableAutoStep<TParent>> getChildAutoSteps()
    {
        return _childStepTriggers;
    }
}