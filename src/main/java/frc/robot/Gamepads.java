//Created by Spectrum3847
//Based on code by FRC4141
package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.lib.gamepads.AndButton;
import frc.lib.gamepads.XboxGamepad;
import frc.lib.util.Logger;
import frc.robot.Robot.RobotState;
import frc.robot.commands.FeedBalls;
import frc.robot.commands.IntakeBalls;
import frc.robot.commands.ResetGyro;
import frc.robot.commands.swerve.ClimberSwerve;
import frc.robot.commands.swerve.LLAim;
import frc.robot.commands.swerve.TurnToAngle;
import frc.robot.telemetry.Log;

public class Gamepads {
	// Create Joysticks first so they can be used in defaultCommands
	public static XboxGamepad driver = new XboxGamepad(0, .15, .15);
	public static XboxGamepad operator = new XboxGamepad(1, .06, .05);
	public static boolean driverConfigured = false;
	public static boolean operatorConfigured = false;
	public static String name = Log._controls;

	// Configure all the controllers
	public static void configure() {
		configureDriver();
		configureOperator();
	}

	public static void resetConfig() {
		CommandScheduler.getInstance().clearButtons();
		driverConfigured = false;
		operatorConfigured = false;
		// driver = new SpectrumXboxController(0, .15, .15); //Set these up to be
		// configured from Dashboard
		// operator = new SpectrumXboxController(1, .06, .05); //Set these up to be
		// configured from Dashboard
		configureDriver();
		configureOperator();
		if (!driverConfigured) {
			Logger.println("##### Driver Controller Not Connected #####");
		}

		if (!operatorConfigured) {
			Logger.println("***** Operator Controller Not Connected *****");
		}
	}

	// Configure the driver controller
	public static void configureDriver() {
		// Detect whether the xbox controller has been plugged in after start-up
		if (!driverConfigured) {
			boolean isConnected = driver.isConnected();
			if (!isConnected)
				return;

			// Configure button bindings
			if (Robot.getState() == RobotState.TEST) {
				driverTestBindings();
			} else {
				driverBindings();
			}
			driverConfigured = true;
		}
	}

	// Configure the operator controller
	public static void configureOperator() {
		// Detect whether the xbox controller has been plugged in after start-up
		if (!operatorConfigured) {
			boolean isConnected = operator.isConnected();
			if (!isConnected)
				return;

			// Configure button bindings
			if (Robot.getState() == RobotState.TEST) {
				operatorTestBindings();
			} else {
				operatorBindings();
			}
			operatorConfigured = true;
		}
	}

	public static void driverBindings() {
		// Driver Controls
		// Reset Gyro based on left bumper and the abxy buttons
		new AndButton(driver.leftBumper, driver.yButton).whileHeld(new ResetGyro(0));
		new AndButton(driver.leftBumper, driver.xButton).whileHeld(new ResetGyro(90));
		new AndButton(driver.leftBumper, driver.aButton).whileHeld(new ResetGyro(180));
		new AndButton(driver.leftBumper, driver.bButton).whileHeld(new ResetGyro(270));

		// turn the robot to a cardinal direction
		driver.yButton.whileHeld(new TurnToAngle(0));
		driver.xButton.whileHeld(new TurnToAngle(90));
		driver.aButton.whileHeld(new TurnToAngle(180));
		driver.bButton.whileHeld(new TurnToAngle(270));

		// Climber mode to disable field relative
		driver.startButton.whileHeld(new ClimberSwerve());

		// Aim with limelight
		driver.rightBumper.whileHeld(new LLAim());
	}

	public static void operatorBindings() {
		// Intake
		operator.leftTriggerButton.whileHeld(new IntakeBalls());

		// Indexer
		operator.selectButton.whileHeld(new FeedBalls());
		operator.selectButton.whileHeld(new RunCommand(() -> Robot.intake.setManualOutput(0.3), Robot.intake));

		// Tower
		operator.startButton.whileHeld(new RunCommand(() -> Robot.tower.setPercentModeOutput(0.5), Robot.tower));

		// Don't use
		new AndButton(operator.rightTriggerButton, operator.aButton)
				.whileHeld(new RunCommand(() -> Robot.launcher.setLauncherVelocity(3500), Robot.launcher)
						.alongWith(new RunCommand(() -> Robot.tower.setTowerVelocity(1700))));

		// Trench
		new AndButton(operator.rightTriggerButton, operator.bButton)
				.whileHeld(new RunCommand(() -> Robot.launcher.setLauncherVelocity(5000), Robot.launcher)
						.alongWith(new RunCommand(() -> Robot.tower.setTowerVelocity(1700))));

		// Intiantion line
		new AndButton(operator.rightTriggerButton, operator.xButton)
				.whileHeld(new RunCommand(() -> Robot.launcher.setLauncherVelocity(4500), Robot.launcher)
						.alongWith(new RunCommand(() -> Robot.tower.setTowerVelocity(1700))));

		// Hood
		operator.Dpad.Up.whenPressed(new RunCommand(() -> Robot.launcher.setHood(1.0), Robot.launcher));
		operator.Dpad.Down.whenPressed(new RunCommand(() -> Robot.launcher.setHood(0), Robot.launcher));
		operator.Dpad.Left.whenPressed(new RunCommand(() -> Robot.launcher.setHood(0.33), Robot.launcher));
		operator.Dpad.Right.whenPressed(new RunCommand(() -> Robot.launcher.setHood(0.66), Robot.launcher));

		// Unjam all the things
		operator.leftBumper.whileHeld(new RunCommand(() -> Robot.intake.setManualOutput(-0.5))
				.alongWith(new RunCommand(() -> Robot.indexer.setManualOutput(-Robot.indexer.feedSpeed), Robot.indexer)
						.alongWith(new RunCommand(() -> Robot.tower.setPercentModeOutput(-0.3), Robot.tower)
							.alongWith(new RunCommand(() -> Robot.launcher.setPercentModeOutput(-0.5), Robot.launcher)
										.alongWith(new StartEndCommand(() -> Robot.intake.down(),() -> Robot.intake.up()
		))))));

		operator.yButton.whileHeld(new RunCommand(() -> Robot.climber.setManualOutput(-1), Robot.climber));
	}

	// Configure the button bindings for the driver control in Test Mode
	public static void driverTestBindings() {

	}

	// Configure the button bindings for the driver control in Test Mode
	public static void operatorTestBindings() {

	}

	public static void printDebug(String msg) {
		Logger.println(msg, name, Logger.low1);
	}

	public static void printInfo(String msg) {
		Logger.println(msg, name, Logger.normal2);
	}

	public static void printWarning(String msg) {
		Logger.println(msg, name, Logger.high3);
	}

	public static void printError(String msg) {
		Logger.println(msg, name, Logger.critical4);
	}
}
