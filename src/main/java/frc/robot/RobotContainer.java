// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;

// WPI Dependencies
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

// Team 8626 Dependencies

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.BalanceCommand;
import frc.robot.Constants.Controller;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final static DriveSubsystem m_drivetrain = new DriveSubsystem();

  // private final ArcadeDriveCommand m_autoCommand = new ArcadeDriveCommand(m_DriveSubsystem);
 
  // Define controllers
  private final Joystick m_flightJoystick = new Joystick(Controller.kJoystickPort);
  private final XboxController m_gameController = new XboxController(Controller.kGamepadPort); 

  // Autonomous Mode
  private final static DashBoard m_dashboard = new DashBoard();
  private final static Autonomous m_autoControl = new Autonomous(m_dashboard, m_drivetrain);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();
    configureDefaultCommands();
  }

  /**
   * Set Default Commands for Subsystems if required...
   */
  private void configureDefaultCommands(){


    // Always Read Joystick and control the drivetrain
    m_drivetrain.setDefaultCommand(    
    new ArcadeDriveCommand(
      () -> m_flightJoystick.getX(), 
      () -> m_flightJoystick.getY(),
      // () -> m_gameController.getRightY(), 
      // () -> m_gameController.getRightX(),
      m_drivetrain));

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Use "JoystickButton"

  /* 
  (new JoystickButton(m_gameController, Button.kB.value))
  .toggleWhenPressed(new BalanceCommand(m_drivetrain, false));
*/

  // Toggle balancing (No Abort!)
  (new Trigger(() -> m_gameController.getBButtonPressed())).toggleOnTrue(new BalanceCommand(m_drivetrain, false));


  /**
   * Get Start command from the autonomous controller (Dashboard)
   */
  }


  public Command getAutonomousCommand() {
    Command retval = null;
     try {
       retval = m_autoControl.getStartCommand();
     } catch (IOException e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
     }
    return retval;
  }
}