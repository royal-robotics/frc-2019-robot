package frc.robot.autonomous;

public abstract class TriggerableAutoStep<TParentStep extends AutoStepGroup> {
    protected final TParentStep _autoStepParent;
    private final AutoStep _autoStep;

    public TriggerableAutoStep(TParentStep parentStep, AutoStep autoStep) {
        _autoStepParent = parentStep;
        _autoStep = autoStep;
    }

    public final boolean isTriggered() { return _autoStep.hasStarted(); }

    public final boolean isCompleted() { return _autoStep.isCompleted(); }

    public final void trigger() { _autoStep.start(); }

    public abstract boolean shouldTrigger();
}