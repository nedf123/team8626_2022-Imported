package frc.robot.subsystems;
  
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.DriveTrain;

/* Main class for handling Drivetrain 
**
**
*/

 public class DriveSubsystem extends SubsystemBase {
    private final CANSparkMax m_motorFrontLeft  = new CANSparkMax(DriveTrain.kCANMotorFL, MotorType.kBrushed);
    private final CANSparkMax m_motorRearLeft   = new CANSparkMax(DriveTrain.kCANMotorRL, MotorType.kBrushed);
    private final CANSparkMax m_motorFrontRight = new CANSparkMax(DriveTrain.kCANMotorFR, MotorType.kBrushed);
    private final CANSparkMax m_motorRearRight  = new CANSparkMax(DriveTrain.kCANMotorRR, MotorType.kBrushed);

    private final MotorControllerGroup m_motorControllerLeft = new MotorControllerGroup(m_motorFrontLeft, m_motorRearLeft);
    private final MotorControllerGroup m_motorControllerRight = new MotorControllerGroup(m_motorFrontRight, m_motorRearRight);
  
    private final DifferentialDrive m_drive = new DifferentialDrive(m_motorControllerLeft, m_motorControllerRight);
    
    // The left-side drive encoder
    private final Encoder m_leftEncoder =
      new Encoder(
          DriveTrain.kLeftEncoderPorts[0],
          DriveTrain.kLeftEncoderPorts[1],
          DriveTrain.kLeftEncoderReversed);

    // The right-side drive encoder
    private final Encoder m_rightEncoder =
      new Encoder(
        DriveTrain.kRightEncoderPorts[0],
        DriveTrain.kRightEncoderPorts[1],
        DriveTrain.kRightEncoderReversed);

      //  Gyro Sensor (Unsing NAVx Module)
      private final AHRS m_gyro = new AHRS();
      
      // Odometry class for tracking robot location
      private final DifferentialDriveOdometry m_odometry;

      public DriveSubsystem() {
        // Sets distance for per pulse for the encoders
        m_leftEncoder.setDistancePerPulse(DriveTrain.kEncoderMetersPerPulse);
        m_rightEncoder.setDistancePerPulse(DriveTrain.kEncoderMetersPerPulse);  

        resetGyro();

        m_gyro.calibrate();

        resetEncoders();
       
        m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d(), m_leftEncoder.get(),m_rightEncoder.get());

        // Set initial Power for the drivetrain
        this.setHighSpeed();

        // Disable motor Safety for Simulation (Avoid Watchdog timeopiut on motors)
        if(RobotBase.isSimulation()){
          m_drive.setSafetyEnabled(false);
        } 
      }  
      
      public void resetEncoders() {
        m_leftEncoder.reset();
        m_rightEncoder.reset();  
      }
  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  /**   
   * Drives the robot using tank controls.
   *
   * @param leftSpeed  Left Side Speed
   * @param RightSpeed Right Side Speed
   */
  public void tankDrive(double leftSpeed, double RightSpeed) {
    m_drive.tankDrive(leftSpeed, RightSpeed);
  }


  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_motorControllerLeft.setVoltage(leftVolts);
    m_motorControllerRight.setVoltage(rightVolts);
    m_drive.feed();
  }


  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    updateDriveDash();
    m_odometry.update(
        m_gyro.getRotation2d(), m_leftEncoder.getDistance(), m_rightEncoder.getDistance());
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();

  }

 public AHRS getGyro() {
  return m_gyro;
 }

  public float getPitch() {
    // Clement Gyroscope
return m_gyro.getRoll();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(m_leftEncoder.getRate(), m_rightEncoder.getRate());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_odometry.resetPosition(m_gyro.getRotation2d(), m_leftEncoder.getDistance(), m_rightEncoder.getDistance(), pose);

  }
 /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2.0;
  }
  public double getEncoderDistanceLeft() {
    return m_leftEncoder.getDistance();
  }
  public double getEncoderDistanceRight() {
    return m_rightEncoder.getDistance();
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public Encoder getLeftEncoder() {
    return m_leftEncoder;
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public Encoder getRightEncoder() {
    return m_rightEncoder;
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return m_gyro.getRotation2d().getDegrees();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return -m_gyro.getRate();
  }

public double getPitchRate() {
  return m_gyro.getRawGyroY();
}

  /**
   * Set Low Speed Power for the drivetrain.
   */
  public void setLowSpeed() {
    m_drive.setMaxOutput(DriveTrain.kPowerRatioLowSpeed);
  }

  /**
   * Set High Speed Power for the drivetrain.
   */
  public void setHighSpeed() {
    m_drive.setMaxOutput(DriveTrain.kPowerRatioHighSpeed);
  }
public void resetGyro() {
m_gyro.reset();
}

  public void updateDriveDash() {
SmartDashboard.putNumber("Pitch Angle", getPitch());
SmartDashboard.putNumber("Pitch Rate", getPitchRate());
  }
}
