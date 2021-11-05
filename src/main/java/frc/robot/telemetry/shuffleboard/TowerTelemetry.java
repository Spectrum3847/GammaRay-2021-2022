package frc.robot.telemetry.shuffleboard;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;
import frc.robot.telemetry.Log;
import frc.lib.telemetry.FalconConfigLayout;
import frc.lib.telemetry.WidgetsAndLayouts;

public class TowerTelemetry {
    // NetworkTableEntries //

    // Tab & Layouts  //
    private static ShuffleboardTab m_tab;
    private FalconConfigLayout towerFrontConfigLayout;
    private FalconConfigLayout towerRearConfigLayout;

    // Widgets //

    // Constructor  //
    public TowerTelemetry() {
        printLow("Constructing TowerTab...");
        m_tab = Shuffleboard.getTab("Tower"); // Create the tab
    }

    // initialize //
    // Create all View Widgets, ones you can't edit, created after subsystem instances are made
    public void initialize() {
        WidgetsAndLayouts.TalonFXLayout("Motor Front", m_tab, Robot.tower.motorFront).withPosition(0, 0);
        WidgetsAndLayouts.TalonFXLayout("Motor Rear", m_tab, Robot.tower.motorRear).withPosition(1, 0);
        towerFrontConfigLayout = new FalconConfigLayout("Motor Front Config", m_tab, Robot.tower.motorFront,2,0);
        towerFrontConfigLayout.initialize();
    }

    // Update //
    public void update() {     // This will be called in the robotPeriodic
        towerFrontConfigLayout.update();
        towerRearConfigLayout.update();
    }

    // UNTESTED!!!
    // Clear Persistent Date // 
    public void clearPersistentData() {
        printNormal("Clearing Tower Tab Persistent");
        NetworkTableInstance.getDefault().getTable("Shuffleboard/Tower").getKeys().forEach(key -> {
            NetworkTableInstance.getDefault().getTable("Shuffleboard/Tower").getEntry(key).clearPersistent();
        });
    }

    public static void printLow(String msg) {
        Log.println(msg, Log._telemetry, Log.low1);
    }

    public static void printNormal(String msg) {
        Log.println(msg, Log._telemetry, Log.normal2);
    }

    public static void printHigh(String msg) {
        Log.println(msg, Log._telemetry, Log.high3);
    }

}
