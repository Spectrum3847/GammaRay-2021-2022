package frc.robot.subsystems;

import frc.robot.Gamepads;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.telemetry.Log;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.Logger;
import frc.lib.util.TalonFXSetup;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class Climber extends SubsystemBase{
    public static final String name = Log._climber;
    
    public final WPI_TalonFX motor;

    public Climber(){
        setName(name);
        motor = new WPI_TalonFX(CanIDs.kClimberMotor);
        TalonFXSetup.defaultSetup(motor, false, 40);
        motor.configReverseSoftLimitThreshold(0);
        motor.configForwardSoftLimitThreshold(115000);
        motor.configReverseSoftLimitEnable(true);
        motor.configForwardSoftLimitEnable(true);
        motor.configMotionCruiseVelocity(8000);
        motor.configMotionAcceleration(4000);
        //150000 velcoity, at 100% F-gain = (100% X 1023) / 15000 F-gain = 0.068
        motor.config_kF(0, 0.07);
        motor.config_kP(0, 0.051);
        
        setDefaultCommand(new RunCommand(() -> setManualOutput(Gamepads.getClimberJoystick()), this));
    }

    //Runs once per robot loop
    public void periodic(){
    }

    public void goToPosotion(double pos){
        motor.set(ControlMode.MotionMagic, pos);
    }


    public void LowerWithCurrentLimiting(){
        if (motor.getSupplyCurrent() < 20){
            motor.configReverseSoftLimitEnable(false);
            setManualOutput(-.2);
        } else{       
            motor.configReverseSoftLimitEnable(true);
        }
    }

    public void zeroPosition(){
        motor.setSelectedSensorPosition(0);
        motor.configReverseSoftLimitEnable(true);
    }

    public void stopMotor(){
        motor.stopMotor();
    }

    public void setManualOutput(double speed){
        motor.set(ControlMode.PercentOutput,speed);
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
