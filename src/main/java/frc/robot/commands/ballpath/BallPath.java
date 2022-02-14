package frc.robot.commands.ballpath;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;

public class BallPath {
    
    public static Command feed(){
        return new FeedBalls().alongWith(runIntake(0.3));
    }

    //Deploy Intake
    public static Command intakeDown(){
        return new RunCommand(() -> Robot.intake.sol.down(), Robot.intake.sol);
    }

    //Run intake motor
    public static Command runIntake(double speed){
        return new RunCommand(() -> Robot.intake.setManualOutput(speed), Robot.intake);
    }

    //Run indexer motor
    public static Command runIndexer(double speed){
        return new RunCommand(() -> Robot.indexer.setMotorOutput(speed), Robot.indexer);
    }

    //Run launcher motor
    public static Command runLauncher(double speed){
        return new RunCommand(() -> Robot.launcher.setManualOutput(speed), Robot.launcher);
    }

    //Run tower motor
    public static Command runTower(double speed){
        return new RunCommand(() -> Robot.tower.setManualOutput(speed), Robot.tower);
    }

    //Set Launcher RPM
    public static Command setLauncherRPM(double speed){
        return new RunCommand(() -> Robot.launcher.setRPM(speed), Robot.launcher);
    }

    //Set Tower RPM
    public static Command setTowerRPM(double speed){
        return new RunCommand(() -> Robot.tower.setRPM(speed), Robot.tower);
    }

    //Set Both launcher and tower RPMs
    public static Command setRPMs(double launcherRPM, double towerRPM){
        return setLauncherRPM(launcherRPM).alongWith(setTowerRPM(towerRPM));
    }

    public static Command setHood(double position){
        return new RunCommand(() -> Robot.launcher.setHood(position));
    }

    public static Command unJamAll(){
        return runIntake(-0.5).alongWith(runIndexer(-Robot.indexer.feedSpeed))
            .alongWith(runTower(-0.3)).alongWith(runLauncher(-0.5)
            .alongWith(intakeDown()));
    }
}
