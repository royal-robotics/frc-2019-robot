package frc.libs.controls;

import edu.wpi.first.wpilibj.*;
import static frc.libs.controls.Controllers.*;

public class Axis implements IButton {
    private Joystick joystick;
    private Logitech310Axis axisIndex;
    private double deadband;

    private double lastReading = 0.0;

    public Axis(Joystick joystick, Logitech310Axis axisIndex, double deadband) {
        this.joystick = joystick;
        this.axisIndex = axisIndex;
        this.deadband = deadband;
    }

    public double getValue() {
        double value = joystick.getRawAxis(axisIndex.id);
        if (value < deadband && value > -deadband) {
            value = 0.0;
        }
        lastReading = value;
        return lastReading;
    }

    public boolean isPressed() {
        return isPressed(deadband);
    }

    public boolean isPressed(double edge) {
        return Math.abs(getValue()) > edge;
    }

    public boolean isToggled(double edge) {
        double previousValue = lastReading;
        double currentValue = getValue();
        boolean value = previousValue < edge && currentValue > edge;
        return value;
    }
}