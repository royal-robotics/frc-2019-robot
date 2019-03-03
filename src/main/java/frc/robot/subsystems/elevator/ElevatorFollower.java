package frc.robot.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import frc.libs.motionprofile.*;

public class ElevatorFollower extends SimpleFollower {
    public ElevatorFollower(IMotionProfile motionProfile, TalonSRX motor, Encoder encoder) {
        super(motionProfile, motor, encoder);
    }
}