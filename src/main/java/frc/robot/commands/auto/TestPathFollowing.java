// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.SwerveConstants;
import frc.robot.Constants.AutoConstants;

//Need to work on setting an intial position for the field2D map to work properly.
public class TestPathFollowing extends SequentialCommandGroup {
  /** Creates a new TestPathFollowing. */
  public TestPathFollowing() {
    TrajectoryConfig config =
    new TrajectoryConfig(
            AutoConstants.kMaxSpeedMetersPerSecond,
            AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        .setKinematics(SwerveConstants.swerveKinematics);

// An example trajectory to follow.  All units in meters.
Trajectory exampleTrajectory =
    TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(new Translation2d(1, -0.5), new Translation2d(2, -0.5)),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3.0, -0.5, new Rotation2d(0)),
        config);


    addCommands(
      new SwerveTrajectoryFollow(exampleTrajectory, this::finalRotation)
    );
  }

  Rotation2d finalRotation(){
    return new Rotation2d(Math.PI);
  }
}