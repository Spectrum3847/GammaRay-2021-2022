// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.Logger;
import frc.robot.Constants;
import frc.robot.Gamepads;
import frc.robot.telemetry.Log;

public class Climber extends SubsystemBase {
  public static final String name = Log._climber;

  public final WPI_TalonFX motor;

  /** Creates a new Climber. */
  public Climber() {  
    setName(name);
    motor = new WPI_TalonFX(Constants.ClimberConstants.kClimberMotor);
    motor.setInverted(false);
    SupplyCurrentLimitConfiguration supplyCurrentLimit = new SupplyCurrentLimitConfiguration(false, 160, 1600, 10);
    motor.configSupplyCurrentLimit(supplyCurrentLimit);
    motor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 170, 170, 10));
    motor.setNeutralMode(NeutralMode.Brake);
    motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    //climber assumes it starts fully down, then it will try to prevent driving into the hard stops
    motor.setSelectedSensorPosition(0);
    motor.configReverseSoftLimitThreshold(1000);
    motor.configReverseSoftLimitEnable(false);

    motor.configForwardSoftLimitThreshold(120000); //true hard limit is ~120900
    motor.configForwardSoftLimitEnable(false);

    //int time = 255;
    /*motor.setStatusFramePeriod(StatusFrame.Status_6_Misc, time);
    motor.setStatusFramePeriod(StatusFrame.Status_7_CommStatus, time);
    motor.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, time);
    motor.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, time);
    motor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, time);
    motor.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, time);
    motor.setStatusFramePeriod(StatusFrame.Status_17_Targets1, time);*/

    //Setup Default Command
    this.setDefaultCommand(new RunCommand(() -> setManualOutput(Gamepads.operator.rightStick.getY()), this));

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setManualOutput(double speed){
    motor.set(ControlMode.PercentOutput,speed);
  }

  public void stop(){
    motor.set(ControlMode.PercentOutput, 0);
  }

  public void dashboard() {
    SmartDashboard.putNumber("Climber/Output", motor.getMotorOutputPercent());
    SmartDashboard.putNumber("Climber/Current", motor.getSupplyCurrent());
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
