package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Common place for static declarations of all robot
 * components (motors, actuators, sensors, etc.).
 * 
 * Remarks: 
 * Ideally robot components are declared and owned by their
 * subsystem related classes. However, having the declarations
 * in a single place allows their port mappings to be accounted
 * for more easily. A scoped components file is the compromise.
 */
public final class Components {

    // TODO This may or may not be used?
    public static DoubleSolenoid climb = new DoubleSolenoid(0, 0, 7);

    public static class DriveBase {
        public static TalonSRX leftDrive1 = new TalonSRX(7);
        public static VictorSPX leftDrive2 = new VictorSPX(5);
        public static VictorSPX leftDrive3 = new VictorSPX(3);
        public static TalonSRX rightDrive1 = new TalonSRX(8);
        public static VictorSPX rightDrive2 = new VictorSPX(6);
        public static VictorSPX rightDrive3 = new VictorSPX(4);

        public static Solenoid lift = new Solenoid(0, 10); // Lift robot onto platform
        public static Encoder leftEncoder = new Encoder(10, 11, true);
        public static Encoder rightEncoder = new Encoder(12, 13, false);
    }

    public static class Elevator
    {
        public static TalonSRX elevator1 = new TalonSRX(9); // Lift everything
        public static TalonSRX elevator2 = new TalonSRX(10); // Lifts everything
        public static TalonSRX carriage = new TalonSRX(11); // Carriage ball rollers
        public static DoubleSolenoid carriageShift = new DoubleSolenoid(1, 0, 7); // Enables/disables front carriage rollers
        public static Encoder elevatorEncoder = new Encoder(12, 13, true); // Keeps elevator1 and elevator2 
    }

    public static class Hatch {
        public static TalonSRX hatchArm = new TalonSRX(1); // picks up hatches from ground
        public static VictorSPX hatchRoller = new VictorSPX(2); // Rolls and pulls a hatch
        public static DoubleSolenoid carriageRock = new DoubleSolenoid(1, 0, 7); // Pushes forward the hatch
        public static DoubleSolenoid carriageShoot = new DoubleSolenoid(1, 0, 7); // Releases the hatch
    }

    public static class BallIntake {
        // One of the two ballIntake is not needed(?)
        public static TalonSRX ballIntake1 = new TalonSRX(12); // Roller
        public static TalonSRX ballIntake2 = new TalonSRX(13); // Roller

        public static DoubleSolenoid ballArm = new DoubleSolenoid(0, 0, 7); // Arm
    }
}