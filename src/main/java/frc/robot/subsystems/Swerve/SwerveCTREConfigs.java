//Created by Spectrum3847
//Based on Code from Team364 - BaseFalconSwerve
//https://github.com/Team364/BaseFalconSwerve/tree/338c0278cb63714a617f1601a6b9648c64ee78d1

package frc.robot.subsystems.Swerve;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;

import frc.robot.constants.SwerveConstants;

public final class SwerveCTREConfigs {
    public static TalonFXConfiguration swerveAngleFXConfig;
    public static TalonFXConfiguration swerveDriveFXConfig;
    public static CANCoderConfiguration swerveCanCoderConfig;

    private static final SwerveCTREConfigs instance = new SwerveCTREConfigs();

    private SwerveCTREConfigs(){
        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANCoderConfiguration();

        /* Swerve Angle Motor Configurations */
        SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
            SwerveConstants.angleEnableCurrentLimit, 
            SwerveConstants.angleContinuousCurrentLimit, 
            SwerveConstants.anglePeakCurrentLimit, 
            SwerveConstants.anglePeakCurrentDuration);

        swerveAngleFXConfig.slot0.kP = SwerveConstants.angleKP;
        swerveAngleFXConfig.slot0.kI = SwerveConstants.angleKI;
        swerveAngleFXConfig.slot0.kD = SwerveConstants.angleKD;
        swerveAngleFXConfig.slot0.kF = SwerveConstants.angleKF;
        swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;
        swerveAngleFXConfig.initializationStrategy = SensorInitializationStrategy.BootToZero;


        /* Swerve Drive Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
            SwerveConstants.driveEnableCurrentLimit, 
            SwerveConstants.driveContinuousCurrentLimit, 
            SwerveConstants.drivePeakCurrentLimit, 
            SwerveConstants.drivePeakCurrentDuration);

        swerveDriveFXConfig.slot0.kP = SwerveConstants.driveKP;
        swerveDriveFXConfig.slot0.kI = SwerveConstants.driveKI;
        swerveDriveFXConfig.slot0.kD = SwerveConstants.driveKD;
        swerveDriveFXConfig.slot0.kF = SwerveConstants.driveKF;        
        swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        swerveDriveFXConfig.initializationStrategy = SensorInitializationStrategy.BootToZero;
        swerveDriveFXConfig.openloopRamp = SwerveConstants.openLoopRamp;
        swerveDriveFXConfig.closedloopRamp = SwerveConstants.closedLoopRamp;

        
        /* Swerve CANCoder Configuration */
        swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = SwerveConstants.canCoderInvert;
        swerveCanCoderConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
    }

    public static SwerveCTREConfigs getConfigs(){
        return instance;
    }
}