// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.util.SpectrumPreferences;
import frc.lib.sim.PhysicsSim;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Tower;
import frc.robot.subsystems.VisionLL;
import frc.robot.subsystems.Swerve.Swerve;
import frc.robot.telemetry.Dashboard;
import frc.robot.telemetry.Log;
import frc.robot.telemetry.ShuffleboardTabs;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //subsystems and hardware are defined here
  public static final Swerve swerve = new Swerve();
  public static final Intake intake = new Intake();
  public static final Indexer indexer = new Indexer();
  public static final Tower tower = new Tower();
  public static final Launcher launcher = new Launcher();
  public static final Climber climber = new Climber();
  public static final VisionLL visionLL = new VisionLL(); 
  public static final Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);
  public static PowerDistribution pdp = new PowerDistribution(0, ModuleType.kCTRE);
  public static SpectrumPreferences prefs = SpectrumPreferences.getInstance();
  public static final ShuffleboardTabs shuffleboardTabs = new ShuffleboardTabs();

  //AutonCommand
  private Command m_autonomousCommand;

  /**
   * Robot State Tracking Setup
   */
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
    printNormal("Start robotInit()");
    Dashboard.intializeDashboard();
    shuffleboardTabs.initialize();
    Gamepads.resetConfig(); //Reset Gamepad Configs
    Log.initLog(); //Config the Debugger based on FMS state
    compressor.start();
    visionLL.forwardLimeLightPorts();
    printNormal("End robotInit()");
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
    printNormal("Start disabledInit()");
    Log.initLog(); //Config the Debugger based on FMS state
    Gamepads.resetConfig();; //Reset Gamepad Configs
    CommandScheduler.getInstance().cancelAll(); //Disable any currently running commands
    LiveWindow.setEnabled(false);     //Disable Live Window we don't need that data being sent
		LiveWindow.disableAllTelemetry();
    printNormal("End disabledInit()");
  }

  @Override
  public void disabledPeriodic() {
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    setState(RobotState.AUTONOMOUS);
    printNormal("Start autonomousInit()");
    Log.initLog(); //Config the Debugger based on FMS state
    CommandScheduler.getInstance().cancelAll(); //Disable any currently running commands
    LiveWindow.setEnabled(false);     //Disable Live Window we don't need that data being sent
		LiveWindow.disableAllTelemetry();

    // schedule the autonomous command (example)
    m_autonomousCommand = AutonSetup.getAutonomousCommand();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }

    printNormal("End autonomousInit()");
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    setState(RobotState.TELEOP);
    printNormal("Start teleopInit()");
    CommandScheduler.getInstance().cancelAll(); //Disable any currently running commands
    Log.initLog(); //Config the Debugger based on FMS state
    Gamepads.resetConfig();; //Reset Gamepad Configs
		LiveWindow.setEnabled(false);     //Disable Live Window we don't need that data being sent
		LiveWindow.disableAllTelemetry();
    printNormal("End teleopInit()");
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
    Log.initLog(); //Config the Debugger based on FMS state
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  public void simulationInit() {
		Sim.intialization();
	}
	
	public void simulationPeriodic() {
		PhysicsSim.getInstance().run();
	}

  //---------------//
  // Print Methods //
  //---------------//
  public static void printLow(String msg) {
    Log.println(msg, Log._general, Log.low1);
  }

  public static void printNormal(String msg) {
    Log.println(msg, Log._general, Log.normal2);
  }

  public static void printHigh(String msg) {
    Log.println(msg, Log._general, Log.high3);
  }
}
