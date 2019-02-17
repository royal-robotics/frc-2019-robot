package frc.robot.subsystems;

public interface IRobotController {
    /**
     * 
     */
    public void init();
    public void teleopPeriodic();
    public void diagnosticPeriodic();
}