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
    public static class DriveBase {

        public static TalonSRX leftDrive1 = new TalonSRX(7);
        public static VictorSPX leftDrive2 = new VictorSPX(5);
        public static VictorSPX leftDrive3 = new VictorSPX(3);
        public static TalonSRX rightDrive1 = new TalonSRX(8);
        public static VictorSPX rightDrive2 = new VictorSPX(6);
        public static VictorSPX rightDrive3 = new VictorSPX(4);

        public static DoubleSolenoid climb = new DoubleSolenoid(0, 0, 7);
        public static Encoder leftEncoder = new Encoder(10, 11, true);
        public static Encoder rightEncoder = new Encoder(12, 13, false);
    }

    public static class Elevator
    {
        public static TalonSRX elevator1 = new TalonSRX(9);
        public static TalonSRX elevator2 = new TalonSRX(10);
        public static TalonSRX carriage = new TalonSRX(11);
        public static Solenoid lift = new Solenoid(0, 10);
        public static DoubleSolenoid carriageShift = new DoubleSolenoid(1, 0, 7);
        public static DoubleSolenoid carriageRock = new DoubleSolenoid(1, 0, 7);
        public static DoubleSolenoid carriageShoot = new DoubleSolenoid(1, 0, 7);   
        public static Encoder elevatorEncoder = new Encoder(12, 13, true);
    }


    public static class Hatch {
        public static TalonSRX hatchArm = new TalonSRX(1);
        public static VictorSPX hatchRoller = new VictorSPX(2);
    }

    public static class BallIntake {
        public static TalonSRX ballIntake1 = new TalonSRX(12);
        public static TalonSRX ballIntake2 = new TalonSRX(13);
        public static DoubleSolenoid ballIntake4 = new DoubleSolenoid(0, 0, 7);
    }
}