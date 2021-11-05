//Created by Spectrum3847
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.ballpath.FeedBalls;
import frc.robot.commands.ballpath.ShooterVel;
import frc.robot.commands.swerve.SwerveDrive;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ThreeBall extends SequentialCommandGroup {
  /** Creates a new ThreeBall. */
  public ThreeBall() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelCommandGroup(
        new RunCommand(() -> Robot.launcher.setHood(Robot.launcher.intitationLineShot)),
        new ShooterVel(4500),  //Shooter spinup
        new SequentialCommandGroup(
          new WaitCommand(1),
          new FeedBalls().withTimeout(4).alongWith(new RunCommand(() -> Robot.intake.setManualOutput(0.3), Robot.intake)) //tower feed
        )).withTimeout(4),
        new SwerveDrive(false,0.3,0).withTimeout(1.2), //Drive Fwd
        new SwerveDrive(false,0,0).withTimeout(0.5) //stop
        //new SwerveDrive(false,-0.4,0).withTimeout(1.4), //Drive Backwards
        //new SwerveDrive(false,0,0).withTimeout(0.5) //stop
    );
  }
}
