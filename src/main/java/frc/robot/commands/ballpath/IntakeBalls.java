// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ballpath;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class IntakeBalls extends CommandBase {
  /** Creates a new IntakeBalls. */
  public IntakeBalls() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.intake.sol.down();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.intake.intakeOn();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //intake stops and goes up using default command
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}