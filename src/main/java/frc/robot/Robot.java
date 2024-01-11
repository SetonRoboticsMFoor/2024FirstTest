// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.StatusSignal;
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
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


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
  private CANSparkMax driveMotor;
  private RelativeEncoder driveEncoder;

  private CANSparkMax turnMotor;
  private RelativeEncoder turnEncoder;

  private CANcoder absTurnEncoder;

  private Joystick driveStick;
  private JoystickButton driveFwdButton;
  private JoystickButton driveRvsButton;
  private JoystickButton turnFwdButton;
  private JoystickButton turnRvsButton;
  private AHRS navX;
 

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

    //Drive contols
    driveMotor = new CANSparkMax(11, MotorType.kBrushless);
    driveEncoder = driveMotor.getEncoder();

    //Turn controls
    turnMotor = new CANSparkMax(13, MotorType.kBrushless);
    turnEncoder = turnMotor.getEncoder();

    //Cancoder
    absTurnEncoder = new CANcoder(21);


    
    //OI
    driveStick = new Joystick(0);
    driveFwdButton = new JoystickButton(driveStick, 1);
    driveRvsButton = new JoystickButton(driveStick, 2);
    turnFwdButton = new JoystickButton(driveStick, 3);
    turnRvsButton = new JoystickButton(driveStick, 4);

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
    //absEncoderPosition = absTurnEncoder.getAbsolutePosition();

    SmartDashboard.putNumber("Drive Encoder Position: ", driveEncoder.getPosition());
    SmartDashboard.putNumber("Turn Encoder Position: ", turnEncoder.getPosition());
    SmartDashboard.putNumber("NavX Gyro Heading: ", navX.getAngle());

    //FIXME!!!!!!!!!!!!!! putNumber does not work for absolute encoder
    //SmartDashboard.putNumber("Absolute Encoder Position: ", absTurnEncoder.getAbsolutePosition());
 
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
    if (driveFwdButton.getAsBoolean() == true) {
      driveMotor.set(.25);
    } else if (driveRvsButton.getAsBoolean() == true) {
      driveMotor.set(-.25);
    } else {
      driveMotor.set(0);
    }

    //Move turn motors forward or reverse
     if (turnFwdButton.getAsBoolean() == true) {
      turnMotor.set(.25);
    } else if (turnRvsButton.getAsBoolean() == true) {
      turnMotor.set(-.25);
    } else {
      turnMotor.set(0);
    }

  

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
