//Created by Spectrum3847

package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

public final class TowerFalconConfig{
    public static TalonFXConfiguration config = new TalonFXConfiguration();

    /* Inverted */
    public static final boolean kInverted = true;

    /* Neutral Modes */
    public static final NeutralMode kNeutralMode = NeutralMode.Coast;

    /* Control Loop Constants */
    public static final double kP = 0.0465;
    public static final double kI = 0.0005;
    public static final double kD = 0;
    public static final double kF = 0.048;
    public static final double kIz = 150;
    public static final double motionCruiseVelocity = 0;
    public static final double motionAcceleration = 0;

    /* Current Limiting */
    public static final int currentLimit = 60;
    public static final int tirggerThresholdLimit = 65;
    public static final double PeakCurrentDuration = 0.5;
    public static final boolean EnableCurrentLimit = true;
    public static final SupplyCurrentLimitConfiguration supplyLimit = new SupplyCurrentLimitConfiguration(
        EnableCurrentLimit, currentLimit, tirggerThresholdLimit, PeakCurrentDuration);

    /* Voltage Compensation */
    public static final double voltageCompSaturation = 12;

    /* Ramp Rate */
    public static final double openLoopRamp = 0;
    public static final double closedLoopRamp = 0;

    /* Motor Characterization Values */
    public static final double kS = 0;
    public static final double kV = 0; 
    public static final double kA = 0;

    /* Intialization Strategy */
    public static final SensorInitializationStrategy sensorStrat = SensorInitializationStrategy.BootToZero;

    /* getConfig */
    private static final TowerFalconConfig instance = new TowerFalconConfig();
    public static TowerFalconConfig getInstance(){
        return instance;
    }

    private TowerFalconConfig(){
        config.slot0.kP = kP;
        config.slot0.kI = kI;
        config.slot0.kD = kD;
        config.slot0.kF = kF;
        config.slot0.integralZone = kIz;
        config.motionCruiseVelocity = motionCruiseVelocity;
        config.motionAcceleration = motionAcceleration;
        
        config.supplyCurrLimit = supplyLimit;
        config.openloopRamp = openLoopRamp;
        config.closedloopRamp = closedLoopRamp;
        config.voltageCompSaturation = voltageCompSaturation;
    }
}
