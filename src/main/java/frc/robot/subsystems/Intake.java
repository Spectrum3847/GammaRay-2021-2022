// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.drivers.SpectrumSolenoid;
import frc.lib.util.Logger;
import frc.lib.util.TalonFXSetup;
import frc.robot.Constants;
import frc.robot.telemetry.Log;

public class Intake extends SubsystemBase{
  public static final String name = Log._intake;

  public final double intakeSpeed = 0.75;

  public final WPI_TalonFX motor;
  public final SpectrumSolenoid solDown;
  

  /** Creates a new Intake. */
  public Intake() {  
    setName(name);
    motor = new WPI_TalonFX(Constants.IntakeConstants.kIntakeMotor);
    TalonFXSetup.defaultSetup(motor);
    
    solDown = new SpectrumSolenoid(Constants.IntakeConstants.kIntakeDown);

    this.setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setManualOutput(double speed){
    motor.set(ControlMode.PercentOutput,speed);
  }

  public void intakeOn(){
    setManualOutput(intakeSpeed);
  }

  public void stop(){
    motor.set(ControlMode.PercentOutput, 0);
  }
  
 public void up(){
    solDown.set(false);
  }

  public void down(){
    solDown.set(true);
  }

  public void dashboard() {
    SmartDashboard.putNumber("Intake/Output", motor.getMotorOutputPercent());
    SmartDashboard.putNumber("Intake/Current", motor.getSupplyCurrent());
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
