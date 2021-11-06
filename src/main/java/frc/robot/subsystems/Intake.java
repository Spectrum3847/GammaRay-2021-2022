//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.drivers.SpectrumSolenoid;
import frc.lib.util.TalonFXSetup;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.constants.Constants.SolenoidPorts;
import frc.robot.telemetry.Log;

public class Intake extends SubsystemBase{
  public static final String name = Log._intake;

  public final WPI_TalonFX motor;
  public final solenoid sol;
  

  /** Creates a new Intake. */
  public Intake() {  
    setName(name);
    motor = new WPI_TalonFX(CanIDs.kIntakeMotor);
    TalonFXSetup.defaultSetup(motor, false, 40);
    sol = new solenoid();

    setDefaultCommand(new RunCommand(() -> stopMotor(), this));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setManualOutput(double speed){
    motor.set(ControlMode.PercentOutput,speed);
  }

  public void intakeOn(){
    setManualOutput(IntakeConstants.DEFAULT_INTAKE_SPEED);
  }

  public void stopMotor(){
    motor.stopMotor();
  }

  public void dashboard() {
  }

  public static void printLow(String msg){
    Log.println(msg, name, Log.low1);
  }
  
  public static void printInfo(String msg){
    Log.println(msg, name, Log.normal2);
  }
  
  public static void printHigh(String msg) {
    Log.println(msg, name, Log.high3);
  }

  public static void printCritical(String msg) {
    Log.println(msg, name, Log.critical4);
  }

  public class solenoid extends SubsystemBase{
    public final SpectrumSolenoid solDown;

    public solenoid(){
      setName(name + " solenoid");
      solDown = new SpectrumSolenoid(SolenoidPorts.kIntakeDown);

      this.setDefaultCommand(new RunCommand(() -> up(), this));
    }

    public void up(){
      solDown.set(false);
    }
  
    public void down(){
      solDown.set(true);
    }
  }
}
