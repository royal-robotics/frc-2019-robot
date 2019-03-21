  package frc.robot.subsystems.hatch;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.*;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Components;
import frc.libs.utils.Util;

public class HatchManipulator
{
    private final TalonSRX _hatchArm;
    private final SpeedController _hatchRoller;
    private final DoubleSolenoid _carriageFingers;
    private final DoubleSolenoid _carriagePusher;

    private double currentAngle;

    private final double HomeAngle = -72;
    private final double StickAngle = -35.5;
    private final double FloorAngle = 80;

    public HatchManipulator()
    {
        // Setup hatch intake
        _hatchArm = Components.HatchManipulator.hatchArm;
        _hatchRoller = Components.HatchManipulator.hatchRoller;

        // Setup hatch shooter
        _carriageFingers = Components.HatchManipulator.carriageFingers;
        _carriageFingers.set(Value.kForward);
        _carriagePusher = Components.HatchManipulator.carriagePusher;
        _carriagePusher.set(Value.kReverse);

        _hatchRoller.setInverted(true);

        // Set up hatch arm PID
        resetArm();
        _hatchArm.setInverted(true);
        _hatchArm.setSensorPhase(false);

        _hatchArm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        _hatchArm.configClosedLoopPeakOutput(0, 1.0); 
        _hatchArm.config_kP(0, 3.5);
        _hatchArm.config_kI(0, 0);
        _hatchArm.config_kD(0, 0);

        TalonSRXPIDSetConfiguration pidConfig = new TalonSRXPIDSetConfiguration();
        pidConfig.selectedFeedbackCoefficient = 1.0;
        pidConfig.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Absolute;
        Components.HatchManipulator.hatchArm.configurePID(pidConfig);
    }

    public void resetArm()
    {
        int encoderHome = Util.angleToEncoder(HomeAngle);
        _hatchArm.setSelectedSensorPosition(encoderHome);
        _hatchArm.set(ControlMode.Position, encoderHome);
        currentAngle = HomeAngle;
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

    public void moveHatchArmHome()
    {
        if (currentAngle != HomeAngle)
        {
            currentAngle = HomeAngle;
            setArmAngle(currentAngle);
        }
    }

    public void moveHatchArmFloor()
    {
        if (currentAngle != FloorAngle)
        {
            currentAngle = FloorAngle;
            setArmAngle(currentAngle);
        }
    }

    public void moveHatchArmStick()
    {
        if (currentAngle != StickAngle)
        {
            currentAngle = StickAngle;
            setArmAngle(currentAngle);
        }
    }

    public void openFingers()
    {
        _carriageFingers.set(Value.kForward);
    }

    public void closeFingers()
    {
        _carriageFingers.set(Value.kReverse);
    }

    public void pusherOut()
    {
        _carriagePusher.set(Value.kForward);
    }

    public void pusherIn()
    {
        _carriagePusher.set(Value.kReverse);
    }

    public void diagnostics()
    {
        SmartDashboard.putNumber("HatchArmPosition", getArmAngle());
        SmartDashboard.putNumber("HatchArmPower", _hatchArm.getMotorOutputPercent());
        SmartDashboard.putNumber("HatchArmTarget", Util.encoderToAngle((int)_hatchArm.getClosedLoopTarget()));
        SmartDashboard.putNumber("HatchArmError", Util.encoderToAngle((int)_hatchArm.getClosedLoopError()));
    }

    public void manualHatchArm(double hatchValue)
    {
        currentAngle = getArmAngle();
        double newArmAngle = currentAngle + hatchValue;
        setArmAngle(newArmAngle);
    }

    private double getArmAngle()
    {
        return Util.encoderToAngle(_hatchArm.getSelectedSensorPosition());
    }

    private void setArmAngle(double angle)
    {
        _hatchArm.set(ControlMode.Position, Util.angleToEncoder(angle));
    }
}