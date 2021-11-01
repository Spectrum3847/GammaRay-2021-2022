//Based on Code from Team364 - BaseFalconSwerve
//https://github.com/Team364/BaseFalconSwerve/tree/338c0278cb63714a617f1601a6b9648c64ee78d1

package frc.robot.subsystems.Swerve;

import com.ctre.phoenix.sensors.PigeonIMU;

import frc.lib.util.Logger;
import frc.lib.util.SpectrumPreferences;
import frc.robot.Constants;
import frc.robot.commands.TeleopSwerve;
import frc.robot.telemetry.Log;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {
    public static final String name = Log._drive;
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    public PigeonIMU gyro;
    public double pidTurn = 0;
    public boolean limelightAim = false;
    public double drive_x = 0;
    public double drive_y = 0;
    public double drive_rotation = 0;
    private final Field2d m_field = new Field2d();
    public ProfiledPIDController thetaController = new ProfiledPIDController(Constants.AutoConstants.kPThetaController, 0, 0,
                                                            Constants.AutoConstants.kThetaControllerConstraints);;
    public PIDController xController = new PIDController(Constants.AutoConstants.kPXController, 0, 0);
    public PIDController yController = new PIDController(Constants.AutoConstants.kPYController, 0, 0);


    public Swerve() {
        setName(name);
        gyro = new PigeonIMU(Constants.Swerve.pigeonID);
        gyro.configFactoryDefault();
        zeroGyro();
        
        swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getYaw());

        mSwerveMods = new SwerveModule[] {
            new SwerveModule(0, Constants.Swerve.Mod0.constants),
            new SwerveModule(1, Constants.Swerve.Mod1.constants),
            new SwerveModule(2, Constants.Swerve.Mod2.constants),
            new SwerveModule(3, Constants.Swerve.Mod3.constants)
        };

        //Setup thetaController used for auton and automatic turns
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        setDefaultCommand(new TeleopSwerve(this, true, false));
    }    

    @Override
    public void periodic(){
        swerveOdometry.update(getYaw(), getStates());   

        //------ Update PD values -------//
        double xkP = SpectrumPreferences.getInstance().getNumber("Swerve: X kP", Constants.AutoConstants.kPXController)/100;
        double xkD = SpectrumPreferences.getInstance().getNumber("Swerve: X kD", 0.0)/100;
        double ykP = SpectrumPreferences.getInstance().getNumber("Swerve: Y kP", Constants.AutoConstants.kPYController)/100;
        double ykD = SpectrumPreferences.getInstance().getNumber("Swerve: Y kD", 0.0)/100;
        double thetakP = SpectrumPreferences.getInstance().getNumber("Swerve: Theta kP", Constants.AutoConstants.kPThetaController)/100;
        double thetakD = SpectrumPreferences.getInstance().getNumber("Swerve: Theta kD", 0.0)/100;        
        xController.setPID(xkP, 0, xkD);
        yController.setPID(ykP, 0, ykD);
        thetaController.setPID(thetakP, 0, thetakD);

        m_field.setRobotPose(swerveOdometry.getPoseMeters()); //Field is used for simulation and testing
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        //If pidTurn is getting a value override the drivers steering control
        if (pidTurn != 0) {
            rotation = pidTurn;
        }
        
        if (Math.abs(rotation) < 0.03){
            rotation = 0;
        }
        SwerveModuleState[] swerveModuleStates =
            Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation)
                                );
        SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
        drive_y = translation.getY();
        drive_x = translation.getX();
        drive_rotation = rotation;
    }

    public void useOutput(double output) {
        pidTurn = output * Constants.Swerve.maxAngularVelocity;
    }

    //Used for control loops that give a rotational velocity directly
    public void setRotationalVelocity(double rotationalVelocity){
        pidTurn = rotationalVelocity;
    }

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.normalizeWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }    

    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(pose, getYaw());
    }

    public SwerveModuleState[] getStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public void zeroGyro(){
        gyro.setYaw(0);
    }

    //Set gyro to a specific value
    public void setGyro(double value){
        gyro.setYaw(value);
    }

    public Rotation2d getYaw() {
        double[] ypr = new double[3];
        gyro.getYawPitchRoll(ypr);
        return (Constants.Swerve.invertGyro) ? Rotation2d.fromDegrees(360 - ypr[0]) : Rotation2d.fromDegrees(ypr[0]);
    }

    public double getDegrees() {
        return getYaw().getDegrees();
    }

    public double getRadians() {
        return getYaw().getRadians();
    }

    public void dashboard(){
        for(SwerveModule mod : mSwerveMods){
            SmartDashboard.putNumber("Swerve/Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
            SmartDashboard.putNumber("Swerve/Mod " + mod.moduleNumber + " Integrated", mod.getState().angle.getDegrees());
            SmartDashboard.putNumber("Swerve/Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);    
        }
        SmartDashboard.putNumber("Swerve/Gyro Yaw", getYaw().getDegrees());
        SmartDashboard.putNumber("Swerve/Drive vY", drive_y);
        SmartDashboard.putNumber("Swerve/Drive vX", drive_x);
        SmartDashboard.putNumber("Swerve/Drive vRotation", drive_rotation);

        SmartDashboard.putNumber("Swerve/Drive pY", this.getPose().getY());
        SmartDashboard.putNumber("Swerve/Drive pX", this.getPose().getX());

        SmartDashboard.putData("Field", m_field);
    }


    public static void printDebug(String msg){
        Logger.println(msg, name, Logger.low1);
      }
      
      public static void printInfo(String msg){
        Logger.println(msg, name, Logger.normal2);
      }
      
      public static void printWarning(String msg) {
        Logger.println(msg, name, Logger.high3);
      }

      public static void printError(String msg) {
        Logger.println(msg, name, Logger.critical4);
      }
}