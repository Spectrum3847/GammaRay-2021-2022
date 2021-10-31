// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.util.Logger;
import frc.lib.util.SpectrumPreferences;
import frc.robot.Telemetry.Log;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  public static SpectrumPreferences prefs = SpectrumPreferences.getInstance();
  private RobotContainer m_robotContainer;




  public enum RobotState {
    DISABLED, AUTONOMOUS, TELEOP, TEST
  }

  public static RobotState s_robot_state = RobotState.DISABLED;

  public static RobotState getState() {
    return s_robot_state;
  }

  public static void setState(final RobotState state) {
    s_robot_state = state;
  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    Gamepads.resetConfig(); //Reset Gamepad Configs
    Log.initDebugger(); //Config the Debugger based on FMS state
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  
    //Ensure the controllers are always configured
    Gamepads.configure();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    setState(RobotState.DISABLED);
    printInfo("Start disabledInit()");
    Gamepads.resetConfig();; //Reset Gamepad Configs
    CommandScheduler.getInstance().cancelAll(); //Disable any currently running commands
    LiveWindow.setEnabled(false);
    LiveWindow.disableAllTelemetry();
    printInfo("End disabledInit()");
  }

  @Override
  public void disabledPeriodic() {
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    printInfo("Start autonomousInit()");
    Log.initDebugger(); //Config the Debugger based on FMS state
    CommandScheduler.getInstance().cancelAll(); //Disable any currently running commands
    LiveWindow.setEnabled(false);
		LiveWindow.disableAllTelemetry();
    setState(RobotState.AUTONOMOUS);

    // schedule the autonomous command (example)
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }

    printInfo("End autonomousInit()");
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    setState(RobotState.TELEOP);
    printInfo("Start teleopInit()");
    Gamepads.resetConfig();; //Reset Gamepad Configs
    CommandScheduler.getInstance().cancelAll(); //Disable any currently running commands
		LiveWindow.setEnabled(false);
		LiveWindow.disableAllTelemetry();

    //Backup code to make sure the autonmous command is actually cancelled
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    Log.initDebugger(); //Config the Debugger based on FMS state
    printInfo("End teleopInit()");
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    setState(RobotState.TEST);
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    Gamepads.resetConfig();; //Reset Gamepad Configs


  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}



  public static void printDebug(String msg) {
    Logger.println(msg, Log._general, Logger.low1);
  }

  public static void printInfo(String msg) {
    Logger.println(msg, Log._general, Logger.normal2);
  }

  public static void printWarning(String msg) {
    Logger.println(msg, Log._general, Logger.high3);
  }
}
