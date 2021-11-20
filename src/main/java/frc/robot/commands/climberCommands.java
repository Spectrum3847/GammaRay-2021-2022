package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;

public class climberCommands {
    //Have climber go to position
    public static Command goToPosition(double pos){
        return new RunCommand(() -> Robot.climber.goToPosotion(pos), Robot.climber);
    }

    public static Command lowerWithCurrent(){
        return new RunCommand(() -> Robot.climber.LowerWithCurrentLimiting(), Robot.climber);
    }

    public static Command zeroPosition(){
        return new RunCommand(() -> Robot.climber.zeroPosition(), Robot.climber);
    }
}
