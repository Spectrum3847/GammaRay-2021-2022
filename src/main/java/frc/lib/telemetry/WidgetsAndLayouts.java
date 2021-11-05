package frc.lib.telemetry;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import frc.lib.drivers.SpectrumSolenoid;

public class WidgetsAndLayouts {

    //Standard TalonFX Layout
    public static ShuffleboardLayout TalonFXLayout (String name, ShuffleboardTab tab, TalonFX motor){
        ShuffleboardLayout layout = tab.getLayout(name, BuiltInLayouts.kGrid);
        layout.withProperties(Map.of("Label position", "TOP"));

        SuppliedValueWidget<Double> outputPercentWidget = layout.addNumber("Output Percent", () -> motor.getMotorOutputPercent());
         outputPercentWidget.withPosition(0, 0);
 
         SuppliedValueWidget<Double> supplyCurrentWidget = layout.addNumber("Supply Current", () -> motor.getSupplyCurrent());
         supplyCurrentWidget.withPosition(0, 1);

         SuppliedValueWidget<Double>  velocityWidget = layout.addNumber("Velocity", ()-> motor.getSelectedSensorVelocity());
         velocityWidget.withPosition(0, 3);

         SuppliedValueWidget<Double>  positionWidget = layout.addNumber("Position", ()-> motor.getSelectedSensorPosition());
         positionWidget.withPosition(0, 4);

         return layout;
    }

    public static ShuffleboardLayout TalonFXLayout (String name, ShuffleboardTab tab, WPI_TalonFX motor){
        return TalonFXLayout(name, tab, (TalonFX) motor);
    }

    //Solenoid Widget
    public static SuppliedValueWidget<Boolean> SolenoidWidget (String name, ShuffleboardTab tab, SpectrumSolenoid sol){
        return tab.addBoolean(name, ()-> sol.get());
    }

    public static SuppliedValueWidget<Boolean> SolenoidWidget (String name, ShuffleboardLayout layout, SpectrumSolenoid sol){
        return layout.addBoolean(name, ()-> sol.get());
    }
    
}
