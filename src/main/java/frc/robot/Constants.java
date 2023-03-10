// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // driveTrain subsystem constants
    public final static class DriveTrain {
        // CAN Bus addresses for motors
        public static int kCANMotorFL = 3;  // USING SPARKMAX
        public static int kCANMotorRL = 1;  // USING SPARKMAX
        public static int kCANMotorFR = 4;  // USING SPARKMAX
        public static int kCANMotorRR = 2;  // USING SPARKMAX

        // Encoder Ports
        public static int[] kLeftEncoderPorts = {0,1};
        public static int[] kRightEncoderPorts = {2,3};

        public static boolean kLeftEncoderReversed = true;
        public static boolean kRightEncoderReversed = false;

        // P{ower Moultiplicators
        public static double kPowerRatioLowSpeed = 0.5;
        public static double kPowerRatioHighSpeed = 1.0;

        // Drivetrain Characteristics
        public static double kWheelDiameter = Units.inchesToMeters(6.0) ;
        public static int kEncoderPulsesPerRev = 256;
        public static double kEncoderMetersPerPulse = kWheelDiameter * Math.PI / kEncoderPulsesPerRev;

        public static final double ksVolts = 0.22;
        public static final double kvVoltSecondsPerMeter = 1.98;
        public static final double kaVoltSecondsSquaredPerMeter = 0.2;
        public static final double kMaxAvailableVoltage = 10.5; // Assumes Battery "sag" for PID/Ramsete Controllers

        // TODO update placeholder values _ NEED TO DO CHARACTERIZATION
        // Example value only - as above, this must be tuned for your drive!
        public static final double kPDriveVel = 8.5;

        public static final double kTrackwidthMeters = Units.inchesToMeters(21.75);
        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;

        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
    }
    // Controller station constants
    public final static class Controller {
        public static int kJoystickPort = 1;
        public static int kGamepadPort  = 0;
    }
}