/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.*;
import frc.libs.autonomous.LoggingContext;
import frc.robot.autonomous.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.cargo.CargoController;
import frc.robot.subsystems.drivebase.*;
import frc.robot.subsystems.elevator.ElevatorController;
import frc.robot.subsystems.hatch.HatchController;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private final AutoManager _autoManager;
  private final Compressor _compressor = new Compressor();
  public final DriveController _driveController = new DriveController();
  public final ElevatorController _elevatorController = new ElevatorController();
  public final HatchController _hatchController = new HatchController();
  public final CargoController _cargoController = new CargoController();
  private final List<IRobotController> _robotControllers = new LinkedList<>();
  private boolean manualAuto;
  static {
    // Load classes eagerly when the robot is constructed.
    LoggingContext.poke();
  }

  public Robot() {
    _robotControllers.add(_driveController);
    _robotControllers.add(_elevatorController);
    _robotControllers.add(_hatchController);
    _robotControllers.add(_cargoController);
    _autoManager = new AutoManager(this);
  }

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    for (IRobotController robotController : _robotControllers)
      robotController.init(false);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    for (IRobotController robotController : _robotControllers) {
      robotController.diagnosticPeriodic();
    }
    _autoManager.loadSelectedRoutine();
    _autoManager.printCurrentAutoRoutine();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    for (IRobotController robotController : _robotControllers)
      robotController.init(false);

    _driveController.setNeutralMode(NeutralMode.Brake);
    _compressor.stop();

    // If there isn't an auto routine selected then
    // the routine will complete immediately and set
    // this to true.
    manualAuto = false;

    _autoManager.startAutonomous(() -> {
      manualAuto = true;
    });
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    if (manualAuto) {
      teleopPeriodic();
    }
  }

  @Override
  public void disabledInit() {
    _autoManager.stopAutonomous();
    _driveController.setNeutralMode(NeutralMode.Coast);
    _compressor.start();
  }

  /**
   * This function is called once when the robot enters operator control.
   */
  @Override
  public void teleopInit() {
    _autoManager.stopAutonomous();
    _driveController.setNeutralMode(NeutralMode.Coast);
    _compressor.start();

    for (IRobotController robotController : _robotControllers)
      robotController.init(true);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    for (IRobotController robotController : _robotControllers)
      robotController.teleopPeriodic();
  }
}
