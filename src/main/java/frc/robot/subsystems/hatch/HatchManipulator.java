package frc.robot.subsystems.hatch;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.*;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Components;

public class HatchManipulator
{
    private final TalonSRX _hatchArm;
    private final SpeedController _hatchRoller;
    private final Solenoid _carriageRock;
    private final DoubleSolenoid _carriageShoot;

    private int currentPosition;

    public HatchManipulator()
    {
        // Setup hatch intake
        _hatchArm = Components.HatchManipulator.hatchArm;
        _hatchRoller = Components.HatchManipulator.hatchRoller;

        // Setup hatch shooter
        _carriageRock = Components.HatchManipulator.carriageRock;
        _carriageRock.set(false);
        _carriageShoot = Components.HatchManipulator.carriageShoot;
        _carriageShoot.set(Value.kReverse);

        _hatchRoller.setInverted(true);

        // Set up hatch arm PID
        resetArmPosition();

        _hatchArm.setInverted(true);
        _hatchArm.setSensorPhase(true);

        _hatchArm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        _hatchArm.config_kP(0, 1.0);
        _hatchArm.config_kI(0, 0);
        _hatchArm.config_kD(0, 0);
        _hatchArm.configClosedLoopPeakOutput(0, 0.3);

        TalonSRXPIDSetConfiguration pidConfig = new TalonSRXPIDSetConfiguration();
        pidConfig.selectedFeedbackCoefficient = 1.0;
        pidConfig.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Absolute;
        Components.HatchManipulator.hatchArm.configurePID(pidConfig);
    }

    public void resetArmPosition()
    {
        _hatchArm.setSelectedSensorPosition(0);
        currentPosition = 0;
    }

    public void pullHatchIn()
    {
        _hatchRoller.set(0.5);
    }

    public void pushHatchOut()
    {
        _hatchRoller.set(-0.5);
    }

    public void stopHatchRoller()
    {
        _hatchRoller.set(0.0);
    }

    public void moveHatchArm(double speed)
    {
        currentPosition = _hatchArm.getSelectedSensorPosition();

        // Move hatch arm at 30% speed
        double output = speed * 0.3;
        _hatchArm.set(ControlMode.PercentOutput, output);
    }

    public void stopHatchArm()
    {
        _hatchArm.set(ControlMode.Position, currentPosition);
    }

    public void rockForward()
    {
        _carriageRock.set(true);
    }

    public void rockBackwards()
    {
        _carriageRock.set(false);
    }

    public void shootHatchOut()
    {
        _carriageShoot.set(Value.kForward);
    }

    public void shootHatchIn()
    {
        _carriageShoot.set(Value.kReverse);
    }

    public void diagnostics()
    {
        SmartDashboard.putNumber("HatchArmPosition", _hatchArm.getSelectedSensorPosition());
        SmartDashboard.putNumber("HatchArmPower", _hatchArm.getMotorOutputPercent());
        SmartDashboard.putString("HatchArmControlMode", _hatchArm.getControlMode().toString());

        if (_hatchArm.getControlMode() == ControlMode.Position) {
            SmartDashboard.putNumber("HatchArmTarget", _hatchArm.getClosedLoopTarget());
            SmartDashboard.putNumber("HatchArmError", _hatchArm.getClosedLoopError());
        }
    }
}