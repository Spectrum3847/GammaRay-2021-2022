//Created by Spectrum3847
//Based on code by FRC4141
package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.gamepads.AndButton;
import frc.lib.gamepads.XboxGamepad;
import frc.lib.util.Logger;
import frc.robot.Robot.RobotState;
import frc.robot.commands.ResetGyro;
import frc.robot.commands.ballpath.BallPath;
import frc.robot.commands.swerve.ClimberSwerve;
import frc.robot.commands.swerve.LLAim;
import frc.robot.commands.swerve.TurnToAngle;
import frc.robot.constants.GamepadConstants;
import frc.robot.telemetry.Log;

public class Gamepads {
	public static String name = Log._controls;
	// Create Joysticks first so they can be used in defaultCommands
	public static XboxGamepad driver = GamepadConstants.setDefaults(new XboxGamepad(0), GamepadConstants.driverDefaultValues);
	public static XboxGamepad operator = GamepadConstants.setDefaults(new XboxGamepad(1), GamepadConstants.operatorDefaultValues);
	public static boolean isDriverBound = false;
	public static boolean isOperatorBound = false;

	// Configure all the controllers
	public static void bindGamepads() {
		bindDriver();
		bindOperator();
	}

	public static void resetBindings() {
		CommandScheduler.getInstance().clearButtons();
		isDriverBound = false;
		isOperatorBound = false;
		bindDriver();
		bindOperator();
		if (!isDriverBound) {
			printCritical("##### Driver Controller Not Connected #####");
		}

		if (!isOperatorBound) {
			printCritical("***** Operator Controller Not Connected *****");
		}
	}

	// Bind the driver controller buttons
	public static void bindDriver() {
		// Detect whether the xbox controller has been plugged in after start-up
		if (!isDriverBound) {
			if (!driver.isConnected())
				return;

			// button bindings
			if (Robot.getState() == RobotState.TEST) {
				driverTestBindings();
			} else {
				driverBindings();
			}
			isDriverBound = true;
		}
	}

	// Bind the operator controller buttons
	public static void bindOperator() {
		// Detect whether the xbox controller has been plugged in after start-up
		if (!isOperatorBound) {
			if (!operator.isConnected())
				return;

			// button bindings
			if (Robot.getState() == RobotState.TEST) {
				operatorTestBindings();
			} else {
				operatorBindings();
			}
			isOperatorBound = true;
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

	public static void printLow(String msg) {
		Logger.println(msg, name, Logger.low1);
	}

	public static void printNormal(String msg) {
		Logger.println(msg, name, Logger.normal2);
	}

	public static void printHigh(String msg) {
		Logger.println(msg, name, Logger.high3);
	}

	public static void printCritical(String msg) {
		Logger.println(msg, name, Logger.critical4);
	}
}
