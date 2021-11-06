package frc.robot.commands.ballpath;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.constants.TowerConstants;
public class runLauncherRPMwithTower extends CommandBase {
  private double rpm_;

  /** Creates a new launcherVel. */
  public runLauncherRPMwithTower(double rpm) {
    // Use addRequirements() here to declare subsystem dependencies.
    rpm_ = rpm;
    addRequirements(Robot.launcher, Robot.tower);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.launcher.setRPM(rpm_);
    Robot.tower.setRPM(TowerConstants.TOWER_RPM);
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
