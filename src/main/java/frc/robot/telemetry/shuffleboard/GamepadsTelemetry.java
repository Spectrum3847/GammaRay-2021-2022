package frc.robot.telemetry.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import frc.lib.util.Logger;
import frc.robot.Gamepads;
import frc.robot.telemetry.Log;

public class GamepadsTelemetry {
    //---------------------//
    // NetworkTableEntries //
    //---------------------//
    public static NetworkTableEntry yDriveDeadzoneEntry;

    public SimpleWidget yDriveDeadzoneWidget;

    //----------------//
    // Tab & Layouts  //
    private static ShuffleboardTab m_tab;

     //--------------//
    // Constructor  //
    public GamepadsTelemetry() {
        printLow("Constructing IntakeTab...");
        m_tab = Shuffleboard.getTab("Gamepads");
        initializeEditable();
    }

    //---------------------//
    // initializeEditable  //
    //Create all edit widgets, created before subsystem instances are made
    public void initializeEditable(){
        yDriveDeadzoneWidget = m_tab.addPersistent("y Deadband", Gamepads.driver.rightStick.expYCurve.getDeadzone());
        yDriveDeadzoneEntry = yDriveDeadzoneWidget.getEntry();

    }

    //---------------------//
    // initializeViewable  //
    // Create all View Widgets, ones you can't edit, created after subsystem instances are made
    public void initializeViewable() {
    }

    //--------//
    // Update //
    public void update() {     // This will be called in the robotPeriodic
    }

    public static void printLow(String msg) {
        Logger.println(msg, Log._telemetry, Logger.low1);
    }

    public static void printNormal(String msg) {
        Logger.println(msg, Log._telemetry, Logger.normal2);
    }

    public static void printHigh(String msg) {
        Logger.println(msg, Log._telemetry, Logger.high3);
    }
}
