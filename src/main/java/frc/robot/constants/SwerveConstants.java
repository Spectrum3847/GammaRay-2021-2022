//Created by Spectrum3847
package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.lib.swerve.SwerveModuleConstants;
import frc.robot.constants.Constants.CanIDs;

public final class SwerveConstants {
    public static final boolean isInvertGyro = false; // Always ensure Gyro is CCW+ CW-

    /* Drivetrain Constants */
    public static final double trackWidth = Units.inchesToMeters(17.5);
    public static final double wheelBase = Units.inchesToMeters(20.5);
    public static final double wheelDiameter = Units.inchesToMeters(3.94);
    public static final double wheelCircumference = wheelDiameter * Math.PI;

    public static final double openLoopRamp = 0.25;
    public static final double closedLoopRamp = 0.0;

    public static final double driveGearRatio = (8.16 / 1.0); //8.16:1
    public static final double angleGearRatio = (12.8 / 1.0); //12.8:1

    public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

    /* Swerve Current Limiting */
    public static final int angleContinuousCurrentLimit = 25;
    public static final int anglePeakCurrentLimit = 40;
    public static final double anglePeakCurrentDuration = 0.1;
    public static final boolean isAngleEnableCurrentLimit = true;

    public static final int driveContinuousCurrentLimit = 35;
    public static final int drivePeakCurrentLimit = 60;
    public static final double drivePeakCurrentDuration = 0.1;
    public static final boolean isDriveEnableCurrentLimit = true;

    /* Angle Motor PID Values */
    public static final double angleKP = 0.6;
    public static final double angleKI = 0.0;
    public static final double angleKD = 12.0;
    public static final double angleKF = 0.0;

    /* Drive Motor PID Values */
    public static final double driveKP = 0.10;
    public static final double driveKI = 0.0;
    public static final double driveKD = 0.0;
    public static final double driveKF = 0.0;

    /* Drive Motor Characterization Values */
    public static final double driveKS = (0.605 / 12); // (0.667 / 12); //divide by 12 to convert from volts to percent output for CTRE
    public static final double driveKV = (1.72 / 12); //(2.44 / 12);
    public static final double driveKA = (0.193/12); //(0.27 / 12);

    /* Swerve Profiling Values */
    public static final double maxSpeed = 4.5; //meters per second
    public static final double maxAngularVelocity = 11.5;

    /* Neutral Modes */
    public static final NeutralMode angleNeutralMode = NeutralMode.Coast;
    public static final NeutralMode driveNeutralMode = NeutralMode.Brake;

    /* Motor Inverts */
    public static final boolean isDriveMotorInvert = false;
    public static final boolean isAngleMotorInvert = false;

    /* Angle Encoder Invert */
    public static final boolean isCanCoderInvert = false;

    /* Module Specific Constants */
    /* Front Left Module - Module 0 */
    public static final class Mod0 {
        public static final int driveMotorID = CanIDs.driveMotor0;
        public static final int angleMotorID = CanIDs.angleMotor0;
        public static final int canCoderID = 3;
        public static final double angleOffset = 222.099; //PRACTICE ROBOT 183.8;
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
    }

    /* Front Right Module - Module 1 */
    public static final class Mod1 {
        public static final int driveMotorID = CanIDs.driveMotor1;
        public static final int angleMotorID = CanIDs.angleMotor1;
        public static final int canCoderID = 13;
        public static final double angleOffset = 148.9; //practice robot 174.6;
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
    }
    
    /* Back Left Module - Module 2 */
    public static final class Mod2 {
        public static final int driveMotorID = CanIDs.driveMotor2;
        public static final int angleMotorID = CanIDs.angleMotor2;
        public static final int canCoderID = 23;
        public static final double angleOffset = 257.6; //practice robot 275.1;
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
    }

    /* Back Right Module - Module 3 */
    public static final class Mod3 {
        public static final int driveMotorID = CanIDs.driveMotor3;
        public static final int angleMotorID = CanIDs.angleMotor3;
        public static final int canCoderID = 33;
        public static final double angleOffset = 227.72; // practice robot 341.2;
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
    }
}
