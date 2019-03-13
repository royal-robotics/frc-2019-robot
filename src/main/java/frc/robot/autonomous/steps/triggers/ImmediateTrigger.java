package frc.robot.autonomous.steps.triggers;

import frc.libs.autonomous.*;

public class ImmediateTrigger<TParent extends AutoStepGroup<TParent>> extends TriggerableAutoStep<TParent> {
    public ImmediateTrigger(AutoStepGroup<TParent> autoGroup, AutoStep autoStep) {
        super(autoGroup, autoStep);
    }

    @Override
    public boolean shouldTrigger() {
        return true;
    }
}