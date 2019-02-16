package frc.robot.autonomous;

public abstract class AutoRoutine {
    protected AutoManager manager;

    public void stopRoutine() {
        manager.stop();
    }
}