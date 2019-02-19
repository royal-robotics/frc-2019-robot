package frc.libs.autonomous.buildingblocks;

import java.util.*;
import java.util.function.*;
import org.apache.logging.log4j.*;
import frc.libs.autonomous.*;

/**
 * Top level "step"
 */
public abstract class AutoRoutine extends AutoStepGroup<AutoRoutine>
{
    public AutoRoutine()
    {
        // Routines are top level groups, they shouldn't have a parent marker.
        super(null);
    }

    protected abstract List<AutoStep> createRoutine();

    @Override
    protected final List<TriggerableAutoStep<AutoRoutine>> createGroup()
    {
        List<AutoStep> autoSteps = createRoutine();
        List<TriggerableAutoStep<AutoRoutine>> triggerableAutoSteps = new ArrayList<>(autoSteps.size());
        for (AutoStep autoStep : autoSteps)
        {
            triggerableAutoSteps.add(new ProceduralAutoStep(this, autoStep));
        }

        return triggerableAutoSteps;
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