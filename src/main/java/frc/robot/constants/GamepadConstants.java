package frc.robot.constants;

import frc.lib.gamepads.GamepadConfiguration;
import frc.lib.gamepads.XboxGamepad;

//Used to set the default deadband. expotential, and scalr values for the gamepads
public final class GamepadConstants {
    public static GamepadConfiguration driverDefaultValues = new GamepadConfiguration();
    public static GamepadConfiguration operatorDefaultValues = new GamepadConfiguration();

    protected GamepadConstants instance = new GamepadConstants();

    private GamepadConstants(){
        driverDefaultValues.LEFT_Y_DEADBAND = 0.5;
        driverDefaultValues.LEFT_X_DEADBAND = 0.5;
        driverDefaultValues.RIGHT_Y_DEADBAND = 0.5;
        driverDefaultValues.RIGHT_X_DEADBAND = 0.5;
        driverDefaultValues.LEFT_Y_EXP_VAL = 1.0;
        driverDefaultValues.LEFT_X_EXP_VAL = 1.0;
        driverDefaultValues.RIGHT_Y_EXP_VAL = 1.0;
        driverDefaultValues.RIGHT_X_EXP_VAL = 1.0;
        driverDefaultValues.LEFT_Y_SCALAR_VAL = 1.0;
        driverDefaultValues.LEFT_X_SCALAR_VAL = 1.0;
        driverDefaultValues.RIGHT_Y_SCALAR_VAL = 1.0;
        driverDefaultValues.RIGHT_X_SCALAR_VAL = 1.0;
        
        operatorDefaultValues.LEFT_Y_DEADBAND = 0.5;
        operatorDefaultValues.LEFT_X_DEADBAND = 0.5;
        operatorDefaultValues.RIGHT_Y_DEADBAND = 0.5;
        operatorDefaultValues.RIGHT_X_DEADBAND = 0.5;
        operatorDefaultValues.LEFT_Y_EXP_VAL = 1.0;
        operatorDefaultValues.LEFT_X_EXP_VAL = 1.0;
        operatorDefaultValues.RIGHT_Y_EXP_VAL = 1.0;
        operatorDefaultValues.RIGHT_X_EXP_VAL = 1.0;
        operatorDefaultValues.LEFT_Y_SCALAR_VAL = 1.0;
        operatorDefaultValues.LEFT_X_SCALAR_VAL = 1.0;
        operatorDefaultValues.RIGHT_Y_SCALAR_VAL = 1.0;
        operatorDefaultValues.RIGHT_X_SCALAR_VAL = 1.0;
    }

    public static XboxGamepad setDefaults(XboxGamepad xboxGamepad, GamepadConfiguration gamepadConfiguration){
        xboxGamepad.leftStick.expYCurve.setDeadzone(gamepadConfiguration.LEFT_Y_DEADBAND);  
        xboxGamepad.leftStick.expXCurve.setDeadzone(gamepadConfiguration.LEFT_X_DEADBAND);
        xboxGamepad.rightStick.expYCurve.setDeadzone(gamepadConfiguration.RIGHT_Y_DEADBAND);
        xboxGamepad.rightStick.expXCurve.setDeadzone(gamepadConfiguration.RIGHT_X_DEADBAND);

        xboxGamepad.leftStick.expYCurve.setExpVal(gamepadConfiguration.LEFT_Y_EXP_VAL);
        xboxGamepad.leftStick.expXCurve.setExpVal(gamepadConfiguration.LEFT_X_EXP_VAL);
        xboxGamepad.rightStick.expYCurve.setExpVal(gamepadConfiguration.RIGHT_Y_EXP_VAL);
        xboxGamepad.rightStick.expXCurve.setExpVal(gamepadConfiguration.RIGHT_X_EXP_VAL);

        xboxGamepad.leftStick.expYCurve.setScalar(gamepadConfiguration.LEFT_Y_SCALAR_VAL);
        xboxGamepad.leftStick.expXCurve.setScalar(gamepadConfiguration.LEFT_X_SCALAR_VAL);
        xboxGamepad.rightStick.expYCurve.setScalar(gamepadConfiguration.RIGHT_Y_SCALAR_VAL);
        xboxGamepad.rightStick.expXCurve.setScalar(gamepadConfiguration.RIGHT_X_SCALAR_VAL);
        return xboxGamepad;
    }
}
