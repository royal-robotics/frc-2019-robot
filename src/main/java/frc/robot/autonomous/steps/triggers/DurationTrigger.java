package frc.robot.autonomous.steps.triggers;

import frc.libs.autonomous.*;

public class DurationTrigger<TParent extends AutoStepGroup<TParent>> extends TriggerableAutoStep<TParent> {
    public DurationTrigger(AutoStepGroup<TParent> autoGroup, AutoStep autoStep) {
        super(autoGroup, autoStep);
    }

    @Override
    public boolean shouldTrigger() {
        return true;
    }
}