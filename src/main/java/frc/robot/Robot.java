// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.CANcoder;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // Front left motors
  private CANSparkMax frontLeftDriveMotor;
  private RelativeEncoder frontLeftDriveEncoder;
  private CANSparkMax frontLeftTurnMotor;
  private RelativeEncoder frontLeftTurnEncoder;
  private CANcoder frontLeftAbsTurnEncoder;

  // Rear left motors
  private CANSparkMax rearLeftDriveMotor;
  private RelativeEncoder rearLeftDriveEncoder;
  private CANSparkMax rearLeftTurnMotor;
  private RelativeEncoder rearLeftTurnEncoder;
  private CANcoder rearLeftAbsTurnEncoder;

  // rear right motors
  private CANSparkMax frontRightDriveMotor;
  private RelativeEncoder frontRightDriveEncoder;
  private CANSparkMax frontRightTurnMotor;
  private RelativeEncoder frontRightTurnEncoder;
  private CANcoder frontRightAbsTurnEncoder;

  // Rear right motors
  private CANSparkMax rearRightDriveMotor;
  private RelativeEncoder rearRightDriveEncoder;
  private CANSparkMax rearRightTurnMotor;
  private RelativeEncoder rearRightTurnEncoder;
  private CANcoder rearRightAbsTurnEncoder;

  private Joystick driveStick;

  private AHRS navX;

  private double driveSpeed;
  private double turnSpeed;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // Front left
    frontLeftDriveMotor = new CANSparkMax(11, MotorType.kBrushless);
    frontLeftDriveEncoder = frontLeftDriveMotor.getEncoder();
    frontLeftTurnMotor = new CANSparkMax(13, MotorType.kBrushless);
    frontLeftTurnEncoder = frontLeftTurnMotor.getEncoder();
    frontLeftAbsTurnEncoder = new CANcoder(21);

    // Rear left
    rearLeftDriveMotor = new CANSparkMax(3, MotorType.kBrushless);
    rearLeftDriveEncoder = frontLeftDriveMotor.getEncoder();
    rearLeftTurnMotor = new CANSparkMax(5, MotorType.kBrushless);
    rearLeftTurnEncoder = frontLeftTurnMotor.getEncoder();
    rearLeftAbsTurnEncoder = new CANcoder(20);

    // Front Right
    frontRightDriveMotor = new CANSparkMax(4, MotorType.kBrushless);
    frontRightDriveEncoder = frontRightDriveMotor.getEncoder();
    frontRightTurnMotor = new CANSparkMax(14, MotorType.kBrushless);
    frontRightTurnEncoder = frontRightTurnMotor.getEncoder();
    frontRightAbsTurnEncoder = new CANcoder(23);

    // Rear Right
    rearRightDriveMotor = new CANSparkMax(10, MotorType.kBrushless);
    rearRightDriveEncoder = frontRightDriveMotor.getEncoder();
    rearRightTurnMotor = new CANSparkMax(8, MotorType.kBrushless);
    rearRightTurnEncoder = frontRightTurnMotor.getEncoder();
    rearRightAbsTurnEncoder = new CANcoder(22);

    // OI
    driveStick = new Joystick(0);

    navX = new AHRS(SPI.Port.kMXP);

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    // Publish encoder values to the dashboard
    // absEncoderPosition = absTurnEncoder.getAbsolutePosition();

    SmartDashboard.putNumber("Front Left Drive Encoder Position: ",
        frontLeftDriveEncoder.getPosition());
    SmartDashboard.putNumber("Rear Left Drive Encoder Position: ",
        rearLeftDriveEncoder.getPosition());
    SmartDashboard.putNumber("Front Right Drive Encoder Position: ",
        frontRightDriveEncoder.getPosition());
    SmartDashboard.putNumber("Rear Right Drive Encoder Position: ",
        rearRightDriveEncoder.getPosition());

    SmartDashboard.putNumber("Front Left Turn Encoder Position: ",
        frontLeftTurnEncoder.getPosition());
    SmartDashboard.putNumber("Rear Left Turn Encoder Position: ",
        rearLeftTurnEncoder.getPosition());
    SmartDashboard.putNumber("Front Right Turn Encoder Position: ",
        frontRightTurnEncoder.getPosition());
    SmartDashboard.putNumber("Rear Right Turn Encoder Position: ",
        rearRightTurnEncoder.getPosition());

    SmartDashboard.putNumber("NavX Gyro Heading: ", navX.getAngle());

    // Encoder works!!!
    SmartDashboard.putNumber("Front Left Absolute Encoder Position: ",
        frontLeftAbsTurnEncoder.getAbsolutePosition().getValueAsDouble());
    SmartDashboard.putNumber("Rear Left Absolute Encoder Position: ",
        rearLeftAbsTurnEncoder.getAbsolutePosition().getValueAsDouble());
    SmartDashboard.putNumber("Front Right Absolute Encoder Position: ",
        frontRightAbsTurnEncoder.getAbsolutePosition().getValueAsDouble());
    SmartDashboard.putNumber("Rear Right Absolute Encoder Position: ",
        rearRightAbsTurnEncoder.getAbsolutePosition().getValueAsDouble());

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different
   * autonomous modes using the dashboard. The sendable chooser code works with
   * the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the
   * chooser code and
   * uncomment the getString line to get the auto name from the text box below the
   * Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure
   * below with additional strings. If using the SendableChooser make sure to add
   * them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // Move drive motors forward or reverse
    driveSpeed = driveStick.getRawAxis(1);

    // Apply deadband
    if (driveSpeed > 0 && driveSpeed < 0.1) {
      driveSpeed = 0.0;
    }

    if (driveSpeed < 0 && driveSpeed > -0.1) {
      driveSpeed = 0.0;
    }

    frontLeftDriveMotor.set(driveSpeed);
    rearLeftDriveMotor.set(driveSpeed);
    frontRightDriveMotor.set(driveSpeed);
    rearRightDriveMotor.set(driveSpeed);

    turnSpeed = driveStick.getRawAxis(4);

    // Apply deadband
    if (turnSpeed > 0 && turnSpeed < 0.1) {
      turnSpeed = 0.0;
    }

    if (turnSpeed < 0 && turnSpeed > -0.1) {
      turnSpeed = 0.0;
    }

    frontLeftTurnMotor.set(turnSpeed);
    rearLeftTurnMotor.set(turnSpeed);
    frontRightTurnMotor.set(turnSpeed);
    rearRightTurnMotor.set(turnSpeed);

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
