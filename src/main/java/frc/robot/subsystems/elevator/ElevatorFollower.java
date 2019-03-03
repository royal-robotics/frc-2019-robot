package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj.*;
import frc.libs.motionprofile.*;

public class ElevatorFollower extends SimpleFollower {
    public ElevatorFollower(Encoder encoder, SpeedController motor) {
        super(encoder, motor);
    }
}