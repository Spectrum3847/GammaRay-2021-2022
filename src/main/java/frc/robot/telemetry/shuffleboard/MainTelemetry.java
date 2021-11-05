//Created by Spectrum3847
//Based on Code from FRC# 4141 
package frc.robot.telemetry.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import java.util.Map;

import frc.robot.Robot;
import frc.robot.telemetry.Log;
import frc.lib.util.Logger;

// The Shuffleboard Main tab.
public class MainTelemetry {

     //----------------//
    // Default Values //
    //----------------//

    //---------------------//
    // NetworkTableEntries //
    //---------------------//
    public static NetworkTableEntry flashEntry;

    //----------------//
    // Tab & Layouts  //
    //----------------//
    private ShuffleboardTab m_tab;

    //---------//
    // Widgets //
    //---------//
    public static SimpleWidget m_flashWidget;

    //--------------//
    // Constructor  //
    //--------------//
    public MainTelemetry() {
        printLow("Constructing MainTab...");
        m_tab = Shuffleboard.getTab("Main");
    }

    //---------------------//
    // initialize //
    //---------------------//
    // Create all View Widgets, ones you can't edit, created after subsystem instances are made
    public void initialize() {
        matchTimeWidget().withPosition(0, 1);
        flashWidget().withPosition(0, 0);
        m_tab.addNumber("FPGA timestamp", () -> Timer.getFPGATimestamp()).withPosition(0, 4);

    }

    // Match Time
    public SuppliedValueWidget<Double> matchTimeWidget(){
        SuppliedValueWidget<Double> m_matchTimeWidget = m_tab.addNumber("Match Time", () -> DriverStation.getMatchTime());
        m_matchTimeWidget.withWidget(BuiltInWidgets.kDial);
        m_matchTimeWidget.withProperties(Map.of("min", -1, "max", 135));
        return m_matchTimeWidget;
    }

    //Flash
    public SimpleWidget flashWidget(){
        m_flashWidget = m_tab.add("Flash", false);
        flashEntry = m_flashWidget.getEntry();
        m_flashWidget.withWidget(BuiltInWidgets.kBooleanBox);
        m_flashWidget.withProperties(Map.of("Color when true", "#1a0068", "Color when false", "#FFFFFF"));
        return m_flashWidget;
    }

    //--------//
    // Update //
    //--------//
    static boolean b = true;

    public void update() {     // This will be called at a rate setup in ShufflbeboardTabs
        b = !b;
        flashEntry.setBoolean(b);
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
