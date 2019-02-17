package frc.robot.autonomous;

import java.util.*;

/**
 * Top level "step"
 */
public abstract class AutoRoutine extends AutoStepGroup<AutoRoutine>
{
    public AutoRoutine(List<AutoStep> autoSteps)
    {
        super(autoSteps);
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
                List<TriggerableAutoStep<AutoRoutine>> autoStepChildern = _autoStepParent.getChildAutoSteps();
                TriggerableAutoStep<AutoRoutine> previous = autoStepChildern.get(autoStepIndex - 1);
                return previous._autoStep.hasCompleted();
            }
        }
    }
}