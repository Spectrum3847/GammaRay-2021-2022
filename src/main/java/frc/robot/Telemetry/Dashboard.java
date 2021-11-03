//Created by Spectrum3847
package frc.robot.telemetry;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.Util;
import frc.robot.Robot;

//Smart Dashboard Values should mostly be used for quick debugging, we will disable if FMS is connected
public class Dashboard {

    private static final Notifier dashFastThread = new Notifier(new dashboardFastThread());
    private static final Notifier dashSlowThread = new Notifier(new dashboardSlowThread());
	
	public static final boolean ENABLE_DASHBOARD = true;
	
	static final double FAST_DELAY = .02;
    static final double SLOW_DELAY = .5;

    //Put values that you want to use as use inputs here and set their default state
    public static void intializeDashboard() {
    	if(ENABLE_DASHBOARD && !DriverStation.isFMSAttached()){
            SmartDashboard.putBoolean("Limelight-LED Toggle", false);
            dashFastThread.startPeriodic(FAST_DELAY);
            dashSlowThread.startPeriodic(SLOW_DELAY);
        }
    }

    //Check each subsystems dashboard values and update them
    private static void updatePutFast() {
        Robot.swerve.dashboard();
        Robot.launcher.dashboard();
        Robot.tower.dashboard();
        Robot.intake.dashboard();
        Robot.indexer.dashboard();
        Robot.climber.dashboard();
        SmartDashboard.putBoolean("Pressure SW", Robot.compressor.getPressureSwitchValue());
    }

    //Things that don't need to be sent out each cycle
    static boolean b = true;
    private static void updatePutSlow(){
    	//SmartDashboard.putBoolean("Compressor On?", Robot.pneumatics.compressor.enabled());
		
		//Can change to show a different message than "Yes" and "No"
        SmartDashboard.putBoolean("Change Battery", Util.changeBattery());
        b = !b;
        SmartDashboard.putBoolean("Disabled Toggle", b);
    }

    private static class dashboardFastThread implements Runnable {    
		@Override
		public void run() {
			updatePutFast();
		}
	}

    private static class dashboardSlowThread implements Runnable {    
		@Override
		public void run() {
			updatePutSlow();
		}
	}

    //Old way we used to update dashboard can be deleted.
    /*public static void updateDashboard() {
        double time = Timer.getFPGATimestamp();
    	if (ENABLE_DASHBOARD) {
            if ((time - shortOldTime) > SHORT_DELAY) {
                shortOldTime = time;
                updatePutShort();
            }
            if ((time - longOldTime) > LONG_DELAY) {
                //Thing that should be updated every LONG_DELAY
                longOldTime = time;
                updatePutLong();
            }
        }
    }*/
}
