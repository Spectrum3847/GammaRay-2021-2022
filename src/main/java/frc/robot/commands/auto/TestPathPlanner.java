//Created by Spectrum3847
package frc.robot.commands.auto;

import java.util.List;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.SwerveConstants;

//Need to work on setting an intial position for the field2D map to work properly.
public class TestPathPlanner extends SequentialCommandGroup {
  /** Creates a new TestPathFollowing. */
  public TestPathPlanner() {
    TrajectoryConfig config =
    new TrajectoryConfig(
            AutoConstants.kMaxSpeedMetersPerSecond,
            AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        .setKinematics(SwerveConstants.swerveKinematics);

// An example trajectory to follow.  All units in meters.
PathPlannerTrajectory exampleTrajectory = PathPlanner.loadPath("New New New Path", 3, 3);

    addCommands(
      new SwerveTrajectoryFollow(exampleTrajectory, this::finalRotation)
    );
  }

  Rotation2d finalRotation(){
    return new Rotation2d(Math.PI);
  }
}
