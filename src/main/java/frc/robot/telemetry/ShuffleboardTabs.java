//Created by Spectrum3847
//Based on Code from FRC# 4141 
package frc.robot.telemetry;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.telemetry.shuffleboard.GamepadsTelemetry;
import frc.robot.telemetry.shuffleboard.IntakeTelemetry;
import frc.robot.telemetry.shuffleboard.MainTelemetry;
import frc.robot.telemetry.shuffleboard.SwerveTelemetry;
import frc.robot.telemetry.shuffleboard.TowerTelemetry;

// Class that wraps all of the interaction with the Shuffleboard

// All decisions about content and layout of the Shuffleboard are consolidated in this file
// to make it easier to change things rather than having to look throughout all of the
// classes for subsystems, commands, etc.

// The ShuffleboardTabs class knows about the subsystems, commands, etc. but generally not vice versa.
public class ShuffleboardTabs {

    private double _heartBeatPeriod = 1;     //How fast we should run the update methods, most values are set by suppliers so they update quickly

    // Tabs
    private MainTelemetry m_mainTelemetry;
    private SwerveTelemetry m_swerveTelemetry;
    public IntakeTelemetry m_IntakeTelemetry;
    public GamepadsTelemetry m_GamepadsTelemetry;
    private TowerTelemetry m_towerTelemetry;

    public ShuffleboardTabs() {
        printLow("Constructing ShuffleboardTabs...");

        m_mainTelemetry = new MainTelemetry();
        m_swerveTelemetry = new SwerveTelemetry();
        m_IntakeTelemetry = new IntakeTelemetry(); 
        m_GamepadsTelemetry = new GamepadsTelemetry();
        m_towerTelemetry = new TowerTelemetry();
    }

    public void initialize() {
        printLow("Initializing ShuffleboardTabs...");

        m_swerveTelemetry.initialize();
        m_IntakeTelemetry.initialize();
        m_mainTelemetry.initialize();
        m_GamepadsTelemetry.initialize();
        m_towerTelemetry.initialize();
        
        _heartBeat.startPeriodic(_heartBeatPeriod);
    }

    //Update values from Shuffleboard, this is run at the _heartbeatperiod
    //We don't need to assign values every program cycle
    private void update() {
            m_mainTelemetry.update();
            m_swerveTelemetry.update();
            m_IntakeTelemetry.update();
            m_GamepadsTelemetry.update();
            m_towerTelemetry.update();
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

    //Controls how often we update values based on shuffleboard not how often data changes are sent to shuffleboard
    class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                update();
        }
    }
    Notifier _heartBeat = new Notifier(new PeriodicRunnable());
}
