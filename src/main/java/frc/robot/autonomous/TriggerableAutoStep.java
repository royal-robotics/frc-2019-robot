package frc.robot.autonomous;

import java.util.*;

// TODO: This should be an inner class off `AutoStepGroup`
public abstract class TriggerableAutoStep<TParentStep extends AutoStepGroup<TParentStep>> {
    protected final TParentStep _autoStepParent;
    protected final AutoStep _autoStep;

    public TriggerableAutoStep(TParentStep parentStep, AutoStep autoStep) {
        _autoStepParent = parentStep;
        _autoStep = autoStep;
    }

    public final boolean isTriggered() { return _autoStep.hasStarted(); }

    public final boolean isCompleted() { return _autoStep.hasCompleted(); }

    public final void trigger() { _autoStep.start(); }

    public final List<TriggerableAutoStep<TParentStep>> getSiblingSteps() {
        return _autoStepParent.getChildAutoSteps();
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