//Created by Spectrum3847
//Based on code by FRC4141
package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.gamepads.AndButton;
import frc.lib.gamepads.XboxGamepad;
import frc.lib.util.Logger;
import frc.robot.Robot.RobotState;
import frc.robot.commands.ResetGyro;
import frc.robot.commands.climberCommands;
import frc.robot.commands.ballpath.BallPath;
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
		operator.leftTriggerButton.whileHeld(BallPath.intakeBalls());

		// Indexer
		operator.selectButton.whileHeld(BallPath.feed());

		// Tower
		operator.startButton.whileHeld(BallPath.runTower(0.5));

		// Trench
		new AndButton(operator.rightTriggerButton, operator.bButton)
				.whileHeld(BallPath.setRPMs(5000, 1700));

		// Intiantion line
		new AndButton(operator.rightTriggerButton, operator.xButton)
				.whileHeld(BallPath.setRPMs(4500, 1700));

		//Climber Position Test
		operator.aButton.whileHeld(climberCommands.goToPosition(10000));
		operator.yButton.whileHeld(climberCommands.goToPosition(90000));
		new AndButton(operator.rightTriggerButton, operator.aButton)
			.whileHeld(climberCommands.lowerWithCurrent())
			.whenReleased(climberCommands.zeroPosition());

		// Hood
		operator.Dpad.Up.whenPressed(BallPath.setHood(1.0));
		operator.Dpad.Down.whenPressed(BallPath.setHood(0));
		operator.Dpad.Left.whenPressed(BallPath.setHood(0.33));
		operator.Dpad.Right.whenPressed(BallPath.setHood(0.66));

		// Unjam all the things
		operator.leftBumper.whileHeld(BallPath.unJamAll());
	}

	// Configure the button bindings for the driver control in Test Mode
	public static void driverTestBindings() {

	}

	// Configure the button bindings for the driver control in Test Mode
	public static void operatorTestBindings() {

	}

	public static double getClimberJoystick(){
		return operator.leftStick.getY();
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
