// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashBoard {

    enum StartPosition {
        LEFT_SIDE, RIGHT_SIDE, MIDDLE_SIDE
    }

    enum AutoSelec {
       EXIT_BALANCE, NODE_EXIT_BALANCE
    }

    private static final Notifier m_thread = new Notifier(new dashboardThread());
	
	public static final boolean kEnableDashBoard = true;
	
    // private DesiredMode mCachedDesiredMode = null;
    // private StartingPosition mCachedStartingPosition = null;
    SendableChooser<StartPosition> m_startPositionChooser = new SendableChooser<>();
    SendableChooser<AutoSelec> m_autonomousModeChooser = new SendableChooser<>();

    static final double kShortInterval = .02;
    static final double kLongInterval  = .5;
    
    static double m_shortOldTime = 0.0;
    static double m_longOldTime  = 0.0;   



    /**
     * Class Constructor
     * Initialize the Dashboard with defaul values of "settable" inputs.
     */
    public DashBoard() {
        if(kEnableDashBoard){
            //SmartDashboard.putBoolean("Compressor ENABLE", true);
            //SmartDashboard.putBoolean("Limelight-LED Toggle", false);

            initAutonomousStrategy();
            initStartupPostion();
        }
        m_thread.startPeriodic(kShortInterval);

    

        initSubsystems();
    }

    /** 
     * Initialize Robot Autonomous Strategy 
     */
    private void initAutonomousStrategy(){
        

        m_autonomousModeChooser.setDefaultOption("Exit community zone and balance on recharge station", AutoSelec.EXIT_BALANCE);
        m_autonomousModeChooser.addOption("Deliver cone or block, exit community zone, and balance on recharge station", AutoSelec.NODE_EXIT_BALANCE);
 

        SmartDashboard.putData("Auto Mode", m_autonomousModeChooser);
    }
    
    /** 
     * Initialize Robot Starting Position
     */
    private void initStartupPostion(){
       
        
        m_startPositionChooser.setDefaultOption("Middle Side", StartPosition.MIDDLE_SIDE);
        m_startPositionChooser.addOption("Left Side", StartPosition.LEFT_SIDE);
        m_startPositionChooser.addOption("Right Side", StartPosition.RIGHT_SIDE);

        SmartDashboard.putData("Starting Position", m_startPositionChooser);
    }

    /** 
     * Returns Selected Robot Start Position
     */
    public StartPosition getStartPosition() {
        return m_startPositionChooser.getSelected();
    }
 
    /** 
     * Returns Selected Robot Autonomous Startup Mode
     */  
    public AutoSelec getAutoMode() {
        return m_autonomousModeChooser.getSelected();
    }

    private static void updateDashboard() {
        double time = Timer.getFPGATimestamp();
        if (kEnableDashBoard) {
            if ((time - m_shortOldTime) > kShortInterval) {
                m_shortOldTime = time;
                updateShortInterval();
            }
            if ((time - m_longOldTime) > kLongInterval) {
                //Thing that should be updated every LONG_DELAY
                m_longOldTime = time;
                updateShortInterval();
                updateLongInterval();
            }
        }
    }

    // Initialize Dashboard for all subsystems.
    private static void initSubsystems() {
    
       
    }

    // Update values that need high frequency refresh.
    private static void updateShortInterval() {
    

         // Pulsing to indicate Dashboard is updated
         dashboardFlash();
    }

    // Update values that need low frequency refresh.
    private static void updateLongInterval(){
     
    }


    //Flash a light on the dashboard so that you know that the dashboard is refreshing.
    static int t = 0;
    static boolean b = true;
    public static void dashboardFlash(){
        if (t > 20) {
            t = 0;
            b = !b;
            SmartDashboard.putBoolean("Pulse", b);
        }
        t++;
    }

    private static class dashboardThread implements Runnable {  
        @Override
        public void run() {
            updateDashboard();
        }
    }
}
