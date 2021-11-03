/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.util.net.PortForwarder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.drivers.LimeLight;
import frc.lib.drivers.LimeLightControlModes.LedMode;
import frc.lib.util.Logger;
import frc.robot.Robot;
import frc.robot.Robot.RobotState;
import frc.robot.telemetry.Log;

public class VisionLL extends SubsystemBase {
  public static final String name = Log._visionLL;

  public final LimeLight limelight;
  private boolean LEDState = true;

  private final double TargetHeight = 89.75;// in
  private final double LLHeight = 34.25;// in
  private final double LLAngle = 10; //deg
  private double TargetAngle = 0;
  private double Distance = 0;

  /**
   * Creates a new VisionLL.
   */
  public VisionLL() {
    setName(name);
    limelight = new LimeLight();
    limeLightLEDOn();
  }

  public void forwardLimeLightPorts(){
    //Forward the Limelight Ports
    PortForwarder.add(5800, "10.85.15.22", 5800);
    PortForwarder.add(5801, "10.85.15.22", 5801);

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //If disabled and LED-Toggle is false, than leave lights off, else they should be on
    if(Robot.s_robot_state == RobotState.DISABLED && !SmartDashboard.getBoolean("Limelight-LED Toggle", false) && !DriverStation.isFMSAttached()){
      if (LEDState == true) {
        //limeLightLEDOff();
        LEDState = false;
      }
    } else {
      if (LEDState == false) {
        limeLightLEDOn();
        LEDState = true;
      }
    }

    TargetAngle = limelight.getdegVerticalToTarget();
    Distance = ((TargetHeight - LLHeight) / Math.tan(Math.toRadians(LLAngle + TargetAngle)));
    SmartDashboard.putNumber("LL/LLDistance",Distance);
    SmartDashboard.putNumber("LL/Distance", getActualDistance()); 
  }

  public double getLLDistance(){
    return Distance;
  }
  public double getActualDistance(){
    return (Distance/12);
  }
  public double getRPM(){
    if(Distance> 15.71){
      return ((Distance/12) * 17.28) + 3200;
    }

    else{
      return 3200;
    }
    
  }

  public void limeLightLEDOff(){
    limelight.setLEDMode(LedMode.kforceOff);
  }

  public void limeLightLEDOn(){
    limelight.setLEDMode(LedMode.kforceOn);
  }

  public void setLimeLightLED(boolean b){
    if (b){
        limeLightLEDOn();
    } else{
        limeLightLEDOff();
    }
  }

  public double getLLDegToTarget(){
    return limelight.getdegRotationToTarget()  * -1;
}

public boolean getLLIsTargetFound(){
    return limelight.getIsTargetFound();
}

public double getLLTargetArea(){
    return limelight.getTargetArea();
}

public boolean getLimelightHasValidTarget(){
    return limelight.getIsTargetFound();
}

public void setLimeLightPipeline(int i) {
  setLimeLightPipeline(i);
}

public static void printDebug(String msg){
  Log.println(msg, name, Log.low1);
}

public static void printInfo(String msg){
  Log.println(msg, name, Log.normal2);
}

public static void printWarning(String msg) {
  Log.println(msg, name, Log.high3);
}

public static void printError(String msg) {
  Log.println(msg, name, Log.critical4);
}

}