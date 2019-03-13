package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Gyro;

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

    public static DigitalInput compJumper = new DigitalInput(25);

    public static class DriveBase {
        public static Gyro gyro = new ADXRS450_Gyro();

        public static WPI_TalonSRX leftDrive1 = new WPI_TalonSRX(7);
        public static WPI_VictorSPX leftDrive2 = new WPI_VictorSPX(5);
        public static WPI_VictorSPX leftDrive3 = new WPI_VictorSPX(3);
        public static Encoder leftEncoder = new Encoder(10, 11);

        public static WPI_TalonSRX rightDrive1 = new WPI_TalonSRX(8);
        public static WPI_VictorSPX rightDrive2 = new WPI_VictorSPX(6);
        public static WPI_VictorSPX rightDrive3 = new WPI_VictorSPX(4);
        public static Encoder rightEncoder = new Encoder(12, 13);

        public static Solenoid frontLift = new Solenoid(0, 5);
        public static DoubleSolenoid backLift = new DoubleSolenoid(0, 1, 6);
    }

    public static class Elevator
    {
        public static WPI_TalonSRX elevator1 = new WPI_TalonSRX(9);
        public static WPI_TalonSRX elevator2 = new WPI_TalonSRX(10);
        public static Encoder elevatorEncoder = new Encoder(14, 15, true);
    }

    public static class HatchManipulator {
        // Hatch Intake
        public static WPI_TalonSRX hatchArm = new WPI_TalonSRX(1);
        public static WPI_VictorSPX hatchRoller = new WPI_VictorSPX(2);

        // Hatch Shooter
        public static Solenoid carriageRock = new Solenoid(1, 6);
        public static DoubleSolenoid carriageShoot = new DoubleSolenoid(1, 2, 5);
    }

    public static class CargoManipulator {
        // Cargo Intake
        public static WPI_TalonSRX cargoIntake1 = new WPI_TalonSRX(12);
        public static WPI_TalonSRX cargoIntake2 = new WPI_TalonSRX(13);
        public static DoubleSolenoid cargoArm = new DoubleSolenoid(0, 0, 7);

        // Cargo Shooter
        public static WPI_TalonSRX cargoCarriageShooter = new WPI_TalonSRX(11);
        public static DoubleSolenoid carriageShooterShifter = new DoubleSolenoid(1, 0, 7);
    }
}