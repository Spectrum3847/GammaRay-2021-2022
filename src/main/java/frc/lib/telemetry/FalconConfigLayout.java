//Created by Spectrum3847
package frc.lib.telemetry;


import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class FalconConfigLayout extends CustomLayout{

    private TalonFX talon;
    private TalonFXConfiguration configs = new TalonFXConfiguration();
    public NetworkTableEntry kPEntry;
    public NetworkTableEntry kDEntry;
    public NetworkTableEntry kFEntry;
    public NetworkTableEntry kIEntry;
    public NetworkTableEntry kIzoneEntry;
    public NetworkTableEntry motionCruiseVelocityEntry;
    public NetworkTableEntry motionAccelEntry;
    public NetworkTableEntry openLoopRampRateEntry;
    public NetworkTableEntry closedLoopRampRateEntry;
    public NetworkTableEntry persistentEntry;

    //Pulls default values from the TalonFX
    public FalconConfigLayout(String name, ShuffleboardTab tab, TalonFX talon, int column, int row) {
        super(name, tab);
        this.talon = talon;
        talon.getAllConfigs(this.configs);
        setColumnsAndRows(2, 6);
        setSize(2, 4);
        layout.withPosition(column, row);
    }

    //-----------//
    // initialize //
    public void initialize(){
        //Column 1
        kPEntry = quickAddWidget("kP", configs.slot0.kP, 0, 0);
        kDEntry = quickAddWidget("kD", configs.slot0.kD, 0, 1);
        kFEntry = quickAddWidget("kF", configs.slot0.kF, 0, 2);
        kIEntry = quickAddWidget("kI", configs.slot0.kI, 0, 3);
        kIzoneEntry = quickAddWidget("kIzone", configs.slot0.integralZone, 0, 4);

        //Column 2
        motionCruiseVelocityEntry = quickAddWidget("CruiseVelocity", configs.motionCruiseVelocity, 1, 0);
        motionAccelEntry = quickAddWidget("Acceleration", configs.motionAcceleration, 1, 1);
        openLoopRampRateEntry = quickAddWidget("Open Loop Ramp Rate", configs.openloopRamp, 1, 2);
        closedLoopRampRateEntry = quickAddWidget("Closed Loop Ramp Rate", configs.closedloopRamp, 1, 3);
        persistentEntry = addPersistentWidget(1, 4);
    }


    public void update(){
        updatePersistent();
        talon.config_kP(0, kPEntry.getDouble(0));
        talon.config_kD(0, kDEntry.getDouble(0));
        talon.config_kI(0, kIEntry.getDouble(0));
        talon.config_IntegralZone(0, kIzoneEntry.getDouble(0));
        talon.configMotionCruiseVelocity(motionCruiseVelocityEntry.getDouble(0));
        talon.configMotionAcceleration(motionAccelEntry.getDouble(0));
        talon.configOpenloopRamp(openLoopRampRateEntry.getDouble(0));
        talon.configClosedloopRamp(closedLoopRampRateEntry.getDouble(0));
    }

    public void updatePersistent(){
        if (persistentEntry.getBoolean(true)){
            kPEntry.setPersistent();
            kDEntry.setPersistent();
            kFEntry.setPersistent();
            kIEntry.setPersistent();
            kIzoneEntry.setPersistent();
            motionCruiseVelocityEntry.setPersistent();
            motionAccelEntry.setPersistent();
            openLoopRampRateEntry.setPersistent();
            closedLoopRampRateEntry.setPersistent();
        } else {
            kPEntry.clearPersistent();
            kDEntry.clearPersistent();
            kFEntry.clearPersistent();
            kIEntry.clearPersistent();
            kIzoneEntry.clearPersistent();
            motionCruiseVelocityEntry.clearPersistent();
            motionAccelEntry.clearPersistent();
            openLoopRampRateEntry.clearPersistent();
            closedLoopRampRateEntry.clearPersistent();
        }
    }
    
}
