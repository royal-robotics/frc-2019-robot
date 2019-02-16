package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

public final class Components {
    public static class DriveBase {
        public static TalonSRX leftDrive1 = new TalonSRX(7);
        public static Victor leftDrive2 = new Victor(5);
        public static Victor leftDrive3 = new Victor(3);
        public static Talon rightDrive1 = new Talon(8);
        public static Victor rightDrive2 = new Victor(6);
        public static Victor rightDrive3 = new Victor(4);
        public static DoubleSolenoid climb = new DoubleSolenoid(0, 0, 7);
        public static Encoder driveLeft = new Encoder(10, 11, true);
        public static Encoder driveRight = new Encoder(12, 13, false);
    }

    public static class Elevator
    {
        public static Talon elevator1 = new Talon(9);
        public static Talon elevator2 = new Talon(10);
        public static Talon carriage = new Talon(11);
        public static Solenoid lift = new Solenoid(0, 10);
        public static DoubleSolenoid carriageShift = new DoubleSolenoid(1, 0, 7);
        public static DoubleSolenoid carriageRock = new DoubleSolenoid(1, 0, 7);
        public static DoubleSolenoid carriageShoot = new DoubleSolenoid(1, 0, 7);   
        public static Encoder elevatorEncoder = new Encoder(12, 13, true);
    }


    public static class Hatch {
        public static Talon hatchArm = new Talon(1);
        public static Victor hatchRoller = new Victor(2);
    }

    public static class BallIntake {
        public static Talon ballIntake1 = new Talon(12);
        public static Talon ballIntake2 = new Talon(13);
        public static DoubleSolenoid ballIntake4 = new DoubleSolenoid(0, 0, 7);
    }
}