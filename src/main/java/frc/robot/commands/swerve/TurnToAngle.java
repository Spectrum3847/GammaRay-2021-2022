//Created by Spectrum3847
package frc.robot.commands.swerve;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Robot;

public class TurnToAngle extends ProfiledPIDCommand {

  public static double kP = 0.005;
  public static double kI = 0; // 0.00015
  public static double kD = 0.00025; // 0.0005

  boolean hasTarget = false;

  public TurnToAngle(double angle) {
    super(
        // The ProfiledPIDController used by the command
        new ProfiledPIDController(
            // The PID gainss
            kP, kI, kD,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(360, 360)),
        // This should return the measurement
        Robot.swerve::getDegrees,
        // This should return the goal (can also be a constant)
        angle,
        // This uses the output
        (output, setpoint) -> Robot.swerve.useOutput(output));
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(0.2);
    double differance = angle - Robot.swerve.getDegrees();
    if (differance > 180) {
      differance = (360 - differance) * -1;
    }
    getController().setGoal(differance);
  }

  @Override
  public void initialize() {
    super.initialize();
  }

  @Override
  public void execute() {
    super.execute();
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    Robot.swerve.useOutput(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
