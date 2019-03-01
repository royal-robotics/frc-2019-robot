package frc.libs.motionprofile;

import java.time.*;

public interface IMotionProfile {
    int size();

    Duration duration();

    Segment getSegment(int index);

    Segment getSegment(Duration index);


    public class Segment {
        public final Duration time;
        public final double x;
        public final double y;
        public final double position;
        public final double velocity;
        public final double acceleration;
        public final double jerk;
        public final double heading;
        
        public Segment(Duration time, double x, double y, double position,
                    double velocity, double acceleration,
                    double jerk, double heading) {
            this.time = time;
            this.x = x;
            this.y = y;
            this.position = position;
            this.velocity = velocity;
            this.acceleration = acceleration;
            this.jerk = jerk;
            this.heading = heading;
        }
    }
}