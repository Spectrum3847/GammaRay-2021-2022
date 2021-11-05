//Created by Spectrum3847
//Based on Code from FRC# 4141 
package frc.robot.telemetry.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.Robot;
import frc.robot.telemetry.Log;
import frc.lib.telemetry.WidgetsAndLayouts;
import frc.lib.util.Logger;

// The Shuffleboard Main tab.
public class IntakeTelemetry {
    //---------------------//
    // NetworkTableEntries //

    //----------------//
    // Tab & Layouts  //
    private static ShuffleboardTab m_tab;

    //---------//
    // Widgets //

    //--------------//
    // Constructor  //
    public IntakeTelemetry() {
        printLow("Constructing IntakeTab...");
        m_tab = Shuffleboard.getTab("Intake");
    }

    //---------------------//
    // initialize //
    // Create all View Widgets, ones you can't edit, created after subsystem instances are made
    public void initialize() {
        //intakeLayout(m_tab).withPosition(0, 0);
        WidgetsAndLayouts.TalonFXLayout("Motor", m_tab, Robot.intake.motor).withPosition(0, 0);
        WidgetsAndLayouts.SolenoidWidget("Solenoid", m_tab, Robot.intake.sol.solDown).withPosition(1, 0).withSize(1, 1);
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
