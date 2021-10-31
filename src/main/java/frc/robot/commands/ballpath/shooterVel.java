// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ballpath;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
public class shooterVel extends CommandBase {
  private double _rpm;

  /** Creates a new launcherVel. */
  public shooterVel(double rpm) {
    // Use addRequirements() here to declare subsystem dependencies.
    _rpm = rpm;
    addRequirements(Robot.launcher, Robot.tower);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.launcher.setLauncherVelocity(_rpm);
    Robot.tower.setTowerVelocity(Robot.tower.towerRPM);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.launcher.stop();
    Robot.tower.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
