//Created by Spectrum3847
//Based on Code from FRC# 4141 
package frc.robot.telemetry;

import frc.lib.util.Logger;
import frc.robot.telemetry.shuffleboard.IntakeTelemetry;
import frc.robot.telemetry.shuffleboard.MainTelemetry;
import frc.robot.telemetry.shuffleboard.SwerveTelemetry;

// Class that wraps all of the interaction with the Shuffleboard

// All decisions about content and layout of the Shuffleboard are consolidated in this file
// to make it easier to change things rather than having to look throughout all of the
// classes for subsystems, commands, etc.

// The Shuffler class knows about the subsystems, commands, etc. but generally not vice versa.
public class Shuffler {

    // Tabs
    private MainTelemetry m_mainTelemetry;
    private SwerveTelemetry m_swerveTelemetry;
    public IntakeTelemetry m_IntakeTelemetry;

    public Shuffler() {
        printLow("Constructing Shuffler...");

        m_mainTelemetry = new MainTelemetry();
        m_swerveTelemetry = new SwerveTelemetry();
        m_IntakeTelemetry = new IntakeTelemetry();
    }

    public void initialize() {
        printLow("Initializing Shuffler...");

        m_swerveTelemetry.initializeViewable();
        m_IntakeTelemetry.initializeViewable();
        m_mainTelemetry.initializeViewable();
    }

    public void update() {
        m_mainTelemetry.update();
        m_swerveTelemetry.update();
        m_IntakeTelemetry.update();
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
