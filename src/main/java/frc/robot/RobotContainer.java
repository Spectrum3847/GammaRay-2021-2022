// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.drivers.EForwardableConnections;
import frc.lib.util.Logger;
import frc.robot.commands.auto.ThreeBall;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Tower;
import frc.robot.subsystems.VisionLL;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  
  // The robot's subsystems are defined here...
  public static final Swerve swerve = new Swerve();
  public static final Intake intake = new Intake();
  public static final Indexer indexer = new Indexer();
  public static final Tower tower = new Tower();
  public static final Launcher launcher = new Launcher();
  public static final Climber climber = new Climber();
  public static final VisionLL visionLL = new VisionLL(); 
  public static final Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);

  public static DriverStation DS;
  public static PowerDistribution pdp = new PowerDistribution(0, ModuleType.kCTRE);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    portForwarding();
    printInfo("Start robotInit()");
    Dashboard.intializeDashboard();
    compressor.start();
    printInfo("End robotInit()");
  }

  //foward limelight ports
  private void portForwarding() {
    EForwardableConnections.addPortForwarding(EForwardableConnections.LIMELIGHT_CAMERA_FEED);
    EForwardableConnections.addPortForwarding(EForwardableConnections.LIMELIGHT_WEB_VIEW);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new ThreeBall();
  }

  public static void printDebug(String msg){
    Logger.println(msg, Robot._general, Logger.debug1);
  }
  
  public static void printInfo(String msg){
    Logger.println(msg, Robot._general, Logger.info2);
  }
  
  public static void printWarning(String msg) {
    Logger.println(msg, Robot._general, Logger.warning3);
  }
}
