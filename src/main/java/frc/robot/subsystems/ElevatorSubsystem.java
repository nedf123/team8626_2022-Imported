// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {

  // Get correct ports
DigitalInput bottomLimitSwitch = new DigitalInput(0);
DigitalInput topLimitSwitch = new DigitalInput(1);

private final CANSparkMax elevatorMotor = new CANSparkMax(1, MotorType.kBrushed);

private final Encoder elevatorEncoder = new Encoder(1, 2);


  /** Creates a new ElevatorSubsystem. */
  public ElevatorSubsystem() {
    elevatorEncoder.setDistancePerPulse(20);
    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

