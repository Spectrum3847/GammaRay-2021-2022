package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.auto.TestPathPlanner;

public class AutonSetup {

    /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public static Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new TestPathPlanner();
  }
}
