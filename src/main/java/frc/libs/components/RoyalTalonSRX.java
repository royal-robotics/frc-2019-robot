package frc.libs.components;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;

/**
 * Extends TalonSRX so that it can be used as a PIDOuput.
 */
public class RoyalTalonSRX extends TalonSRX implements PIDOutput {
    public RoyalTalonSRX(int deviceNumber) {
        super(deviceNumber);
    }

    @Override
    public void pidWrite(double output) {
        this.set(ControlMode.PercentOutput, output);
    }
}