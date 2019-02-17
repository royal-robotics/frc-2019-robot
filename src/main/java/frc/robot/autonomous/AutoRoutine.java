package frc.robot.autonomous;

import java.util.*;

import javax.crypto.SealedObject;

import edu.wpi.first.wpilibj.*;

/**
 * Top level "step"
 * 
 * TODO: AutoRoutine is really a special case of AutoStepGroup where each steps trigger is the
 *       previous step completing.
 */
public abstract class AutoRoutine extends AutoStepGroup
{
    public AutoRoutine(List<AutoStep> autoSteps)
    {
        super(null);
        //super(setupProceduralAutoSteps(autoSteps));
    }

    // public AutoRoutine(List<TriggerableAutoStep<AutoRoutine>> childSteps)
    // {
    //     super(childSteps);
    // }

    private static List<TriggerableAutoStep<AutoRoutine>> setupProceduralAutoSteps(List<AutoStep> autoSteps) {
        List<TriggerableAutoStep<AutoRoutine>> proceduralAutoSteps = new ArrayList<>(autoSteps.size());

        for (AutoStep autoStep : autoSteps) {
            // TODO: Figure out how to pass a reference to `this` as part of a constructor.
            ProceduralAutoStep proceduralAutoStep = new ProceduralAutoStep(autoRoutine, autoStep);
            // ProceduralAutoStep proceduralAutoStep = null;
            TriggerableAutoStep<AutoRoutine> asdf = proceduralAutoStep;

            proceduralAutoSteps.add(asdf);
        }

        return null;
    }

    private class ProceduralAutoStep extends TriggerableAutoStep<AutoRoutine>
    {
        public ProceduralAutoStep(AutoRoutine autoRoutine, AutoStep autoStep) {
            super(autoRoutine, autoStep);
        }

        @Override
        public boolean shouldTrigger() {
            //TODO: The step should be triggered if its previous sibling completed.
            // Traverse this._autoStepParent._childSteps and look for the autoStep sibling
            // if this step has been completed, then this step should be triggered.
            return false;
        }
    }
}