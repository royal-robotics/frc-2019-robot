package frc.libs.autonomous.buildingblocks;

import java.util.*;
import java.util.function.*;
import frc.libs.autonomous.*;

/**
 * Top level "step"
 */
public abstract class AutoRoutine extends AutoStepGroup<AutoRoutine>
{
    public AutoRoutine(AutoLogger logger, List<AutoStep> autoSteps)
    {
        super(logger, createTriggers(logger, autoSteps));

        logger.LogInformation("Auto Steps in autoroutine " + autoSteps.size());
    }

    public static List<Function<AutoStepGroup<AutoRoutine>, TriggerableAutoStep<AutoRoutine>>> createTriggers(AutoLogger logger, List<AutoStep> autoSteps) {
        List<Function<AutoStepGroup<AutoRoutine>, TriggerableAutoStep<AutoRoutine>>> triggerableAutoStepThunks = new ArrayList<>(autoSteps.size());
        for (AutoStep autoStep : autoSteps)
        {
            triggerableAutoStepThunks.add((parentStep) -> {
                return new ProceduralAutoStep(parentStep, autoStep);
            });
        }

        return triggerableAutoStepThunks;
    }

    private static class ProceduralAutoStep extends TriggerableAutoStep<AutoRoutine>
    {
        public ProceduralAutoStep(AutoStepGroup<AutoRoutine> autoRoutine, AutoStep autoStep) {
            super(autoRoutine, autoStep);
        }

        @Override
        public boolean shouldTrigger() {
            int autoStepIndex = getAutoStepIndex();
            if (autoStepIndex == 0) {
                // If this is the first step it should be triggered immediately.
                return true;
            }
            else {
                // Trigger the autoStep if its previous sibling has completed.
                List<TriggerableAutoStep<AutoRoutine>> autoStepChildern = this.getSiblingSteps();
                TriggerableAutoStep<AutoRoutine> previous = autoStepChildern.get(autoStepIndex - 1);
                return previous.autoStep.hasCompleted();
            }
        }
    }
}