//Based on Code from Team364 - BaseFalconSwerve
//https://github.com/Team364/BaseFalconSwerve/tree/338c0278cb63714a617f1601a6b9648c64ee78d1

package frc.robot.subsystems.Swerve;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;

import frc.robot.Constants;

public final class CTREConfigs {
    public static TalonFXConfiguration swerveAngleFXConfig;
    public static TalonFXConfiguration swerveDriveFXConfig;
    public static CANCoderConfiguration swerveCanCoderConfig;

    private static final CTREConfigs instance = new CTREConfigs();

    private CTREConfigs(){
        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANCoderConfiguration();

        /* Swerve Angle Motor Configurations */
        SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.SwerveConstants.angleEnableCurrentLimit, 
            Constants.SwerveConstants.angleContinuousCurrentLimit, 
            Constants.SwerveConstants.anglePeakCurrentLimit, 
            Constants.SwerveConstants.anglePeakCurrentDuration);

        swerveAngleFXConfig.slot0.kP = Constants.SwerveConstants.angleKP;
        swerveAngleFXConfig.slot0.kI = Constants.SwerveConstants.angleKI;
        swerveAngleFXConfig.slot0.kD = Constants.SwerveConstants.angleKD;
        swerveAngleFXConfig.slot0.kF = Constants.SwerveConstants.angleKF;
        swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;
        swerveAngleFXConfig.initializationStrategy = SensorInitializationStrategy.BootToZero;


        /* Swerve Drive Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.SwerveConstants.driveEnableCurrentLimit, 
            Constants.SwerveConstants.driveContinuousCurrentLimit, 
            Constants.SwerveConstants.drivePeakCurrentLimit, 
            Constants.SwerveConstants.drivePeakCurrentDuration);

        swerveDriveFXConfig.slot0.kP = Constants.SwerveConstants.driveKP;
        swerveDriveFXConfig.slot0.kI = Constants.SwerveConstants.driveKI;
        swerveDriveFXConfig.slot0.kD = Constants.SwerveConstants.driveKD;
        swerveDriveFXConfig.slot0.kF = Constants.SwerveConstants.driveKF;        
        swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        swerveDriveFXConfig.initializationStrategy = SensorInitializationStrategy.BootToZero;
        swerveDriveFXConfig.openloopRamp = Constants.SwerveConstants.openLoopRamp;
        swerveDriveFXConfig.closedloopRamp = Constants.SwerveConstants.closedLoopRamp;

        
        /* Swerve CANCoder Configuration */
        swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = Constants.SwerveConstants.canCoderInvert;
        swerveCanCoderConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
    }

    public static CTREConfigs getConfigs(){
        return instance;
    }
}