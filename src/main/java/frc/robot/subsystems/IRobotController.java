package frc.robot.subsystems;

public interface IRobotController {
    /**
     * 
     */
    public void init(boolean isTeleop);
    public void teleopPeriodic();
    public void diagnosticPeriodic();
}