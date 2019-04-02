package frc.robot.subsystems.climber;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Components;

public class Climber
{
    private final WPI_TalonSRX climberMaster;

    private final WPI_VictorSPX climberVacuum;
    private final DoubleSolenoid lockSolenoid;

    public Climber()
    {
        this.climberMaster = Components.Climber.climber1;
        this.climberMaster.setInverted(true);
        final WPI_TalonSRX climberSlave = Components.Climber.climber2;
        climberSlave.setInverted(true);
        climberSlave.follow(this.climberMaster);

        climberMaster.configContinuousCurrentLimit(25);
        climberMaster.enableCurrentLimit(true);
        climberSlave.configContinuousCurrentLimit(25);
        climberSlave.enableCurrentLimit(true);
    
        this.climberVacuum = Components.Climber.climberVacuum;

        this.lockSolenoid = Components.Climber.lockSolenoid;
        lockSolenoid.set(Value.kReverse);
    }

    public void diagnosticPeriodic() {
        SmartDashboard.putNumber("Climber-Power", climberMaster.get());
        SmartDashboard.putNumber("Climber-Current", climberMaster.getOutputCurrent());
        SmartDashboard.putNumber("Climber-Voltage", climberMaster.getMotorOutputVoltage());
    }

    public void move(double power)
    {
        //power = Doubles.constrainToRange(power, -0.5, 0.5);
        climberMaster.set(power);
    }

    public void vacumeStart() 
    {
        this.climberVacuum.set(1.0);
    }

    public void vacumeStop()
    {
        this.climberVacuum.set(0.0);
    }

    public void setLock(boolean isLocked)
    {
        this.lockSolenoid.set(isLocked ? Value.kForward : Value.kReverse);
    }
}