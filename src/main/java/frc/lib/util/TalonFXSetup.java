package frc.lib.util;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class TalonFXSetup {

    public static void defaultSetup(TalonFX motor){
        motor.configFactoryDefault();
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        simpleCurrentLimit(motor, 40);
        defaultStatusFrames(motor);
    }

    //Talon FX motor
    //Limit in Amps
    public static void simpleCurrentLimit(TalonFX motor, double limit){
        SupplyCurrentLimitConfiguration supplyCurrentLimit = new SupplyCurrentLimitConfiguration(true, limit, limit, 0.5);
        motor.configSupplyCurrentLimit(supplyCurrentLimit);
    }

    public static void defaultStatusFrames(TalonFX motor){
        int time = 255;
        //motor.setStatusFramePeriod(StatusFrame.Status_1_General, 100);
        motor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, time);
        motor.setStatusFramePeriod(StatusFrame.Status_6_Misc, time);
        motor.setStatusFramePeriod(StatusFrame.Status_7_CommStatus, time);
        motor.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, time);
        motor.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, time);
        motor.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, time);
        motor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, time);
        motor.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, time);
        motor.setStatusFramePeriod(StatusFrame.Status_17_Targets1, time);
    }
}
