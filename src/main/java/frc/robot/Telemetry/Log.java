package frc.robot.telemetry;

import edu.wpi.first.wpilibj.DriverStation;
import frc.lib.util.Logger;

public class Log {
  private static int FMS_LOG_LEVEL = Logger.high3;        //Log level when FMS attached
  private static int PRACTICE_LOG_LEVEL = Logger.normal2; //Log level any other time

  // Add Debug flags
  // You can have a flag for each subsystem, etc
  public static final String _general = "GENERAL";
  public static final String _telemetry = "TELEMETRY";
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
      Logger.setLevel(FMS_LOG_LEVEL);
    } else {
      Logger.setLevel(PRACTICE_LOG_LEVEL);
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
