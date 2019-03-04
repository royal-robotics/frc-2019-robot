package frc.robot.subsystems.drivebase;

import java.time.*;
import frc.libs.motionprofile.IMotionProfile.*;

public interface IFollowerError {
    
    double getError(Segment segment, Duration duration);
}