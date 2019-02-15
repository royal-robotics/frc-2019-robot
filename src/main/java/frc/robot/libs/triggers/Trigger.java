package frc.robot.libs.triggers;

public class Trigger {
    protected boolean triggered;

    public Trigger() {
        triggered = true;
    }
    
    public boolean isTriggered() { return triggered; }
}