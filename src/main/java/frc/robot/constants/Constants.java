//Created by Spectrum3847
package frc.robot.constants;

public final class Constants {
    public static int CANconfigTimeOut = 0;
    public static final int minBatteryVoltage = 12;

    public static final class CanIDs{

        public static final int pigeonID = 0;

        public static final int driveMotor0 = 1;
        public static final int angleMotor0 = 2;
        public static final int driveMotor1 = 11;
        public static final int angleMotor1 = 12;
        public static final int driveMotor2 = 21;
        public static final int angleMotor2 = 22;
        public static final int driveMotor3 = 31;
        public static final int angleMotor3 = 32;

        public static final int kIntakeMotor = 40;
        public static final int kIndexerMotor = 41;
        public static final int kTowerMotorFront = 42;
        public static final int kTowerMotorRear = 43;
        
        public static final int kLauncherMotorLeft = 50;
        public static final int kFollowerMotorRight = 51;

        public static final int kClimberMotor = 60;
    }

    public static final class SolenoidPorts{
        public static final int kIntakeDown = 7;
    }

    public static final class PWMPorts{   
        public static final int kHoodServoLeft = 0;
        public static final int kHoodServoRight = 1;
    }
}
