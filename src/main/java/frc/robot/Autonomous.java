// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;

// WPI Libraries
import edu.wpi.first.wpilibj2.command.Command;


// Team8626 Libraries
import frc.robot.DashBoard.AutoSelec;
import frc.robot.DashBoard.StartPosition;
import frc.robot.subsystems.DriveSubsystem;

public class Autonomous {

    private final DashBoard m_dashboard;
    private final DriveSubsystem m_drivetrain;

    public StartPosition m_startPosition;
    public AutoSelec m_autoStart;

    public Autonomous(DashBoard dashboard, DriveSubsystem drivetrain){
        m_dashboard = dashboard;
        m_drivetrain = drivetrain;
       
    }

    public Command getStartCommand() throws IOException {
        Command startCommand = null;
        m_startPosition = m_dashboard.getStartPosition(); 
        m_autoStart = m_dashboard.getAutoMode();
         
        switch (m_autoStart) {
            default: 
            //startCommand =
           break;
        }
        return startCommand;
    }  
}
