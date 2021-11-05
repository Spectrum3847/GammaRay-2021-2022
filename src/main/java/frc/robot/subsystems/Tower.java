//Created by Spectrum3847

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.Logger;
import frc.lib.util.SpectrumPreferences;
import frc.lib.util.TalonFXSetup;
import frc.robot.constants.TowerFalconConfig;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.telemetry.Log;


public class Tower extends SubsystemBase {
  public static final String name = Log._tower;

  public final WPI_TalonFX motorFront;
  public final WPI_TalonFX motorRear;

  private double kP, kI, kD, kF;
  private int iZone;

  public final double towerRPM = 1700;

  /**
   * Creates a new Intake.
   */
  public Tower() {
    setName(name);
    //Pid

    kP = SpectrumPreferences.getInstance().getNumber("Tower kP",0.0465);
    kI = SpectrumPreferences.getInstance().getNumber("Tower kI",0.0005);
    kD = SpectrumPreferences.getInstance().getNumber("Tower kD",0.0);
    kF = SpectrumPreferences.getInstance().getNumber("Tower kF",0.048);
    iZone = (int) SpectrumPreferences.getInstance().getNumber("Tower I-Zone", 150);

    
    motorFront = new WPI_TalonFX(CanIDs.kTowerMotorFront);
    TalonFXSetup.defaultSetup(motorFront, TowerFalconConfig.kInverted, 60);
    motorFront.configSupplyCurrentLimit(TowerFalconConfig.supplyLimit);
    motorFront.setNeutralMode(TowerFalconConfig.kNeutralMode);

    motorFront.config_kP(0, kP);
    motorFront.config_kI(0, kI);   
    motorFront.config_kD(0, kD);  
    motorFront.config_kF(0, kF);  
    motorFront.config_IntegralZone(0, iZone);
   // motorFront.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10);

    motorFront.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    motorRear = new WPI_TalonFX(CanIDs.kTowerMotorRear);
    TalonFXSetup.defaultSetup(motorRear, !TowerFalconConfig.kInverted, 60); //should be inverse of motorFront
    motorRear.configSupplyCurrentLimit(TowerFalconConfig.supplyLimit);
    motorRear.setNeutralMode(TowerFalconConfig.kNeutralMode);
    motorRear.follow(motorFront);

    SpectrumPreferences.getInstance().getNumber("Tower Setpoint", 1000);

    //Set Dafault Command to be driven by the operator left stick and divide by 1.75 to reduce speed
    this.setDefaultCommand(new RunCommand(() -> stop() , this));
  }

  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setManualOutput(double speed){
    motorFront.set(ControlMode.PercentOutput, speed);
  }

  public void setVelocity( double velocity){
    motorFront.set(ControlMode.Velocity, velocity);
  }

  public void DashboardVelocity(){
    double wheelRpm = SpectrumPreferences.getInstance().getNumber("Tower Setpoint", 1000);
    double motorVelocity = (wheelRpm * 30 / 8);
    motorFront.set(ControlMode.Velocity, motorVelocity);
  }

  public void setRPM(double wheelRPM){
    //Sensor Velocity in ticks per 100ms / Sensor Ticks per Rev * 600 (ms to min) * 1.5 gear ratio to shooter
    //Motor Velocity in RPM / 600 (ms to min) * Sensor ticks per rev / Gear Ratio 16 to 40
    double motorVelocity = (wheelRPM / 600 * 2048) / 0.4;
    setVelocity(motorVelocity);
  }

  public double getWheelRPM() {
    return (motorFront.getSelectedSensorVelocity() * 0.4) / 2048 * 600;
  }

  public void stop(){
    motorFront.set(ControlMode.PercentOutput,0);
  }

  public void dashboard() {
  SmartDashboard.putNumber("Tower/Velocity", motorFront.getSelectedSensorVelocity());
  SmartDashboard.putNumber("Tower/WheelRPM", getWheelRPM());
  SmartDashboard.putNumber("Tower/OutputPercentage", motorFront.getMotorOutputPercent());
  SmartDashboard.putNumber("Tower/FrontCurrent", motorFront.getSupplyCurrent());
  SmartDashboard.putNumber("Tower/RearCurrent", motorRear.getSupplyCurrent());
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
