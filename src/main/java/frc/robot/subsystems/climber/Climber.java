package frc.robot.subsystems.climber;

import com.ctre.phoenix.motorcontrol.can.*;
import com.google.common.primitives.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Components;

public class Climber
{
    private final WPI_TalonSRX climberMaster;
    private final WPI_VictorSPX climberVacume;

    public Climber()
    {
        this.climberMaster = Components.Climber.climber1;
        this.climberMaster.setInverted(true);

        final WPI_TalonSRX climberSlave = Components.Climber.climber2;
        climberSlave.setInverted(true);
        climberSlave.follow(this.climberMaster);
    
        this.climberVacume = Components.Climber.climberVacume;
    }

    public void diagnosticPeriodic() {
        SmartDashboard.putNumber("Climber-Power", climberMaster.get());
    }

    public void move(double power)
    {
        //power = Doubles.constrainToRange(power, -0.5, 0.5);
        climberMaster.set(power);

    }

    public void vacumeStart() 
    {
        this.climberVacume.set(1.0);
    }

    public void vacumeStop()
    {
        this.climberVacume.set(0.0);
    }
}