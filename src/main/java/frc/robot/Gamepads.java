//Created by Spectrum3847
//Based on code by FRC4141
package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.lib.gamepads.AndButton;
import frc.lib.gamepads.XboxController;
import frc.lib.util.Logger;
import frc.robot.Robot.RobotState;
import frc.robot.Telemetry.Log;
import frc.robot.commands.FeedBalls;
import frc.robot.commands.IntakeBalls;
import frc.robot.commands.ResetGyro;
import frc.robot.commands.swerve.ClimberSwerve;
import frc.robot.commands.swerve.LLAim;
import frc.robot.commands.swerve.TurnToAngle;

public class Gamepads {
        // Create Joysticks first so they can be used in defaultCommands
        public static XboxController driver = new XboxController(0, .15, .15);
        public static XboxController operator = new XboxController(1, .06, .05);
        public static boolean driverConfigured = false;
        public static boolean operatorConfigured = false;
        public static String name = Log._controls;

        // Configure all the controllers
        public static void configure() {
                configureDriver();
                configureOperator();
        }

        public static void resetConfig(){
                CommandScheduler.getInstance().clearButtons();
                driverConfigured = false;
                operatorConfigured = false;
               // driver = new SpectrumXboxController(0, .15, .15);  //Set these up to be configured from Dashboard
                //operator = new SpectrumXboxController(1, .06, .05); //Set these up to be configured from Dashboard
                configureDriver();
                configureOperator();
                if (!driverConfigured){
                        Logger.println("##### Driver Controller Not Connected #####");
                }

                if (!operatorConfigured){
                        Logger.println("***** Operator Controller Not Connected *****");
                }
        }

        // Configure the driver controller
        public static void configureDriver() {
                // Detect whether the xbox controller has been plugged in after start-up
                if (!driverConfigured) {
                        boolean isConnected = driver.isConnected();
                        if (!isConnected) return;

                        // Configure button bindings
                        if(Robot.getState() == RobotState.TEST){
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
                        if (!isConnected) return;

                        // Configure button bindings
                        if(Robot.getState() == RobotState.TEST){
                                operatorTestBindings();
                        } else {
                                operatorBindings();
                        }
                        operatorConfigured = true;
                }
        }

        public static void driverBindings(){
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
                operator.selectButton.whileHeld(new RunCommand(() -> RobotContainer.intake.setManualOutput(0.3),
                                RobotContainer.intake));

                // Tower
                operator.startButton.whileHeld(new RunCommand(() -> RobotContainer.tower.setPercentModeOutput(0.5),
                                RobotContainer.tower));

                // Don't use
                new AndButton(operator.rightTriggerButton, operator.aButton).whileHeld(new RunCommand(
                                () -> RobotContainer.launcher.setLauncherVelocity(3500), RobotContainer.launcher)
                                .alongWith(new RunCommand(() -> RobotContainer.tower.setTowerVelocity(1700))));

                // Trench
                new AndButton(operator.rightTriggerButton, operator.bButton).whileHeld(new RunCommand(
                                () -> RobotContainer.launcher.setLauncherVelocity(5000), RobotContainer.launcher)
                                .alongWith(new RunCommand(() -> RobotContainer.tower.setTowerVelocity(1700))));

                // Intiantion line
                new AndButton(operator.rightTriggerButton, operator.xButton).whileHeld(new RunCommand(
                                () -> RobotContainer.launcher.setLauncherVelocity(4500), RobotContainer.launcher)
                                .alongWith(new RunCommand(() -> RobotContainer.tower.setTowerVelocity(1700))));

                // Hood
                operator.Dpad.Up.whenPressed(
                                new RunCommand(() -> RobotContainer.launcher.setHood(1.0), RobotContainer.launcher));
                operator.Dpad.Down.whenPressed(
                                new RunCommand(() -> RobotContainer.launcher.setHood(0), RobotContainer.launcher));
                operator.Dpad.Left.whenPressed(
                                new RunCommand(() -> RobotContainer.launcher.setHood(0.33), RobotContainer.launcher));
                operator.Dpad.Right.whenPressed(
                                new RunCommand(() -> RobotContainer.launcher.setHood(0.66), RobotContainer.launcher));

                // Unjam all the things
                operator.leftBumper.whileHeld(new RunCommand(() -> RobotContainer.intake.setManualOutput(-0.5))
                                .alongWith(new RunCommand(() -> RobotContainer.indexer.setManualOutput(-RobotContainer.indexer.feedSpeed),RobotContainer.indexer)
                                .alongWith(new RunCommand(() -> RobotContainer.tower.setPercentModeOutput(-0.3),RobotContainer.tower)
                                .alongWith(new RunCommand(() -> RobotContainer.launcher.setPercentModeOutput(-0.5),RobotContainer.launcher)
                                .alongWith(new StartEndCommand(() -> RobotContainer.intake.down(),() -> RobotContainer.intake.up())))))
                                );

                operator.yButton.whileHeld(new RunCommand(() -> RobotContainer.climber.setManualOutput(-1),
                                RobotContainer.climber));
        }

        //Configure the button bindings for the driver control in Test Mode
        public static void driverTestBindings(){
        
        }

        //Configure the button bindings for the driver control in Test Mode
        public static void operatorTestBindings(){

        }

        public static void printDebug(String msg){
                Logger.println(msg, name, Logger.low1);
        }
        
        public static void printInfo(String msg){
                Logger.println(msg, name, Logger.normal2);
        }
        
        public static void printWarning(String msg) {
                Logger.println(msg, name, Logger.high3);
        }
        
        public static void printError(String msg) {
                Logger.println(msg, name, Logger.critical4);
        }
}
