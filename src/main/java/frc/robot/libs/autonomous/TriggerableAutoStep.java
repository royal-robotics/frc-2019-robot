package frc.robot.libs.autonomous;

import java.util.*;

public abstract class TriggerableAutoStep<TParentStep extends AutoStepGroup<TParentStep>> {
    protected final TParentStep autoStepParent;
    public final AutoStep autoStep;

    public TriggerableAutoStep(TParentStep parentStep, AutoStep autoStep) {
        this.autoStepParent = parentStep;
        this.autoStep = autoStep;
    }

    public final boolean isTriggered() { return autoStep.hasStarted(); }

    public final boolean isCompleted() { return autoStep.hasCompleted(); }

    public final void trigger() { autoStep.start(); }

    public final List<TriggerableAutoStep<TParentStep>> getSiblingSteps() {
        return autoStepParent.getChildAutoSteps();
    }

    public final int getAutoStepIndex() {
        for(int i = 0; i < getSiblingSteps().size(); i++)
        {
            if (getSiblingSteps().get(i) == this)
                return i;
        }

        throw new IllegalStateException("The autoStep isn't in its AutoStepGroup parents list");
    }

    public abstract boolean shouldTrigger();
}