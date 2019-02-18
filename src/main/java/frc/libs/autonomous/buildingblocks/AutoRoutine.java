package frc.libs.autonomous.buildingblocks;

import java.util.*;
import frc.libs.autonomous.*;

/**
 * Top level "step"
 */
public abstract class AutoRoutine extends AutoStepGroup<AutoRoutine>
{
    public AutoRoutine(AutoLogger logger, List<AutoStep> autoSteps)
    {
        super(logger, autoSteps);
        logger.LogInformation("Auto Steps in autoroutine " + autoSteps.size());
    }

    @Override
    protected final TriggerableAutoStep<AutoRoutine> createTrigger(AutoStep autoStep) {

        return new ProceduralAutoStep(this, autoStep);
    }

    private class ProceduralAutoStep extends TriggerableAutoStep<AutoRoutine>
    {
        public ProceduralAutoStep(AutoRoutine autoRoutine, AutoStep autoStep) {
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
                List<TriggerableAutoStep<AutoRoutine>> autoStepChildern = autoStepParent.getChildAutoSteps();
                TriggerableAutoStep<AutoRoutine> previous = autoStepChildern.get(autoStepIndex - 1);
                return previous.autoStep.hasCompleted();
            }
        }
    }
}