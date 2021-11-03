//Created by Spectrum3847
package frc.robot.telemetry;

import edu.wpi.first.wpilibj.DriverStation;
import frc.lib.util.Logger;

//This class adds robot specfic flags to Logger class
public class Log extends Logger{
  private static int FMS_LOG_LEVEL = high3;        //Log level when FMS attached
  private static int PRACTICE_LOG_LEVEL = normal2; //Log level any other time

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

  public static void initLog(){
    if(DriverStation.isFMSAttached()) {
      setLevel(FMS_LOG_LEVEL);
    } else {
      setLevel(PRACTICE_LOG_LEVEL);
    }
    flagOn(_general); //Set all the flags on, comment out ones you want off
    flagOn(_controls);
    flagOn(_auton);
    flagOn(_drive);
    flagOn(_indexer);
    flagOn(_intake);
    flagOn(_launcher);
    flagOn(_tower);
    flagOn(_climber);
    flagOn(_visionLL);
  }
}
