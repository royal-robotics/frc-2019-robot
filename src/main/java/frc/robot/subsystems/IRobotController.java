package frc.robot.subsystems;

public interface IRobotController {
    public void componentInit();
    public void componentPeriodic();

    public void teleopInit();
    public void teleopPeriodic();
}