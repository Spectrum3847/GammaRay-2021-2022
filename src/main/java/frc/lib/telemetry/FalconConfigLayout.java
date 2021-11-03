//Created by Spectrum3847
package frc.lib.telemetry;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class FalconConfigLayout extends CustomLayout{
    private TalonFX talon;
    private TalonFXConfiguration configs;
    public NetworkTableEntry kPEntry;
    public NetworkTableEntry kDEntry;
    public NetworkTableEntry kFEntry;
    public NetworkTableEntry kIEntry;
    public NetworkTableEntry kIzoneEntry;
    public NetworkTableEntry motionCruiseVelocityEntry;
    public NetworkTableEntry motionAccelEntry;
    public NetworkTableEntry openLoopRampRateEntry;
    public NetworkTableEntry closedLoopRampRateEntry;

    public FalconConfigLayout(String name, ShuffleboardTab tab, TalonFX talon, TalonFXConfiguration dafaultConfigs){
        super(name, tab);
        this.talon = talon;
        this.configs = dafaultConfigs;
        setColumnsAndRows(2, 6);
        setSize(2, 4);
    }

    //-----------//
    // initialize //
    public void initialize(){
        kPEntry = quickAddPersistentWidget("kP", configs.slot0.kP, 0, 0);
        kDEntry = quickAddPersistentWidget("kD", configs.slot0.kD, 0, 1);
        kFEntry = quickAddPersistentWidget("kF", configs.slot0.kF, 0, 2);
        kIEntry = quickAddPersistentWidget("kI", configs.slot0.kI, 0, 3);
        kIzoneEntry = quickAddPersistentWidget("kIzone", configs.slot0.integralZone, 0, 4);
        motionCruiseVelocityEntry = quickAddPersistentWidget("CruiseVelocity", configs.motionCruiseVelocity, 1, 0);
        motionAccelEntry = quickAddPersistentWidget("Acceleration", configs.motionAcceleration, 1, 1);
        openLoopRampRateEntry = quickAddPersistentWidget("Open Loop Ramp Rate", configs.openloopRamp, 1, 2);
        closedLoopRampRateEntry = quickAddPersistentWidget("Closed Loop Ramp Rate", configs.closedloopRamp, 1, 3);
    }


    public void update(){
        talon.config_kP(0, kPEntry.getDouble(0));
        talon.config_kD(0, kDEntry.getDouble(0));
        talon.config_kI(0, kIEntry.getDouble(0));
        talon.config_IntegralZone(0, kIzoneEntry.getDouble(0));
        talon.configMotionCruiseVelocity(motionCruiseVelocityEntry.getDouble(0));
        talon.configMotionAcceleration(motionAccelEntry.getDouble(0));
        talon.configOpenloopRamp(openLoopRampRateEntry.getDouble(0));
        talon.configClosedloopRamp(closedLoopRampRateEntry.getDouble(0));
    }
    
}
