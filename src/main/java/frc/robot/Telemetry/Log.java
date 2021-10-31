package frc.robot.Telemetry;

import edu.wpi.first.wpilibj.DriverStation;
import frc.lib.util.Logger;

public class Log {
      // Add Debug flags
  // You can have a flag for each subsystem, etc
  public static final String _general = "GENERAL";
  public static final String _controls = "CONTROL";
  public static final String _auton = "AUTON";
  public static final String _drive = "DRIVE";
  public static final String _indexer = "INDEXER";
  public static final String _intake = "INTAKE";
  public static final String _launcher = "LAUNCHER";
  public static final String _tower = "TOWER";
  public static final String _climber = "CLIMBER";
  public static final String _visionLL = "LIMELIGHT";

  public static void initDebugger(){
    if(DriverStation.isFMSAttached()) {
      Logger.setLevel(Logger.high3);
    } else {
      Logger.setLevel(Logger.normal2);
    }
    Logger.flagOn(_general); //Set all the flags on, comment out ones you want off
    Logger.flagOn(_controls);
    Logger.flagOn(_auton);
    Logger.flagOn(_drive);
    Logger.flagOn(_indexer);
    Logger.flagOn(_intake);
    Logger.flagOn(_launcher);
    Logger.flagOn(_tower);
    Logger.flagOn(_climber);
    Logger.flagOn(_visionLL);
  }
}
