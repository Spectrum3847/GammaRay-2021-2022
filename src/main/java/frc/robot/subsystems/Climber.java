package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.TalonFXSetup;
import frc.robot.Gamepads;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.telemetry.Log;

public class Climber extends SubsystemBase{
    public static final String name = Log._climber;

    public final WPI_TalonFX motor;

    public Climber() {
        setName(name);
        motor = new WPI_TalonFX(CanIDs.kClimberMotor);
        TalonFXSetup.defaultSetup(motor, false, 40);
        motor.setSelectedSensorPosition(0);
        motor.configForwardSoftLimitThreshold(20000);
        motor.configReverseSoftLimitThreshold(0);
        motor.configForwardSoftLimitEnable(true);
        motor.configReverseSoftLimitEnable(true);

        setDefaultCommand(new RunCommand(() -> setManualOutput(Gamepads.getClimberJoystick()), this));
    }

    public void stopMotor (){
        motor.stopMotor();
    }

    public void setManualOutput(double speed){
        motor.set(ControlMode.PercentOutput,speed);
      }
}


    

