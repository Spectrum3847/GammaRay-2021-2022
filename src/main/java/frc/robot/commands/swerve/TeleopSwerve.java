//Created by Spectrum3847
//Based on Code from Team364 - BaseFalconSwerve
//https://github.com/Team364/BaseFalconSwerve/tree/338c0278cb63714a617f1601a6b9648c64ee78d1

package frc.robot.commands.swerve;

import frc.robot.Gamepads;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.Swerve.Swerve;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TeleopSwerve extends CommandBase {

    private double rotation;
    private Translation2d translation;
    private boolean fieldRelative;
    private boolean openLoop;
    
    private Swerve s_Swerve;

    /**
     * Driver control
     */
    public TeleopSwerve(Swerve s_Swerve, boolean fieldRelative, boolean openLoop) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);
        this.fieldRelative = fieldRelative;
        this.openLoop = openLoop;
    }

    @Override
    public void execute() {
        double yAxis = Gamepads.driver.leftStick.getY();
        double xAxis = Gamepads.driver.leftStick.getX() * -1;
        double rAxis = Gamepads.driver.triggers.getTwist() * -0.75;

        
        translation = new Translation2d(yAxis, xAxis).times(SwerveConstants.maxSpeed);
        rotation = rAxis * SwerveConstants.maxAngularVelocity;
        s_Swerve.drive(translation, rotation, fieldRelative, openLoop);
    }
}
