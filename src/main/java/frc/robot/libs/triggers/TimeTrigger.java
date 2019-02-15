package frc.robot.libs.triggers;

import java.util.*;

public class TimeTrigger extends Trigger {
    private Timer timer;

    public TimeTrigger(long msDelay) {
        triggered = false;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                triggered = true;
                timer.cancel();
                timer.purge();
                timer = null;
            }
        }, msDelay);
    }
}