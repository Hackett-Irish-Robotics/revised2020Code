/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
 
    // Input and Ouput
    public static OI m_oi;

    Command m_autonomousCommand;
    SendableChooser<Command> m_chooser = new SendableChooser<>();


    MecanumDrive robotDrive;
    Joystick stick;
    
  
    Victor frontLeft, frontRight, backLeft, backRight;
    Victor intake;
    Victor shooter;

    Timer t;

/**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
   @Override
  public void robotInit() {

    stick = new Joystick(RobotMap.joystickDrive);

    frontLeft = new Victor(RobotMap.leftFrontMotor);
    frontRight = new Victor(RobotMap.rightFrontMotor);
    backLeft = new Victor(RobotMap.leftBackMotor);
    backRight = new Victor(RobotMap.rightBackMotor);

    intake = new Victor(RobotMap.intakeMotor);
    //shooter = new Victor(RobotMap.shooterMotor);

    robotDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
    //robotDrive.setMaxOutput(.25);

    CameraServer.getInstance().startAutomaticCapture();

    // Instantiate our input/output.
    m_oi = new OI();

    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */

  public void setMove(double forward, double right, double twistRight) {
    robotDrive.driveCartesian(right, -forward, twistRight);
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    robotDrive.setSafetyEnabled(false);

    t = new Timer();

    t.reset();
    t.start();

    /*
    robotDrive.driveCartesian(0, -0.25, 0);
    Timer.delay(1);
    robotDrive.driveCartesian(0, 0, 0);
    /*

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();

    double cT = t.get();
    if (cT < 1) {
      robotDrive.driveCartesian(0, -0.25, 0);
    }
    else if (cT > 1 && cT < 3) {
      robotDrive.driveCartesian(0, 0, 0);
    }
    else if (cT > 3 && cT < 5) {
      robotDrive.driveCartesian(0.25, 0, 0);
    }
    else if (cT > 5 && cT < 6) {
      robotDrive.driveCartesian(0, -0.25, 0);
    }
    else if (cT > 6 && cT < 8) {
      robotDrive.driveCartesian(0, 0, -0.25);
    }
    else if (cT > 8 && cT < 8.25) {
      robotDrive.driveCartesian(0, 0.75, 0);
    }
    else if (cT > 8.25 && cT < 9) {
      robotDrive.driveCartesian(-0.25, 0, 0);
    }
    else if (cT > 9 && cT < 10) {
      robotDrive.driveCartesian(0, -0.25, 0);
    }
    else {
      robotDrive.driveCartesian(0, 0, 0);
    }
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    // the slider (throttle) on the joystick sets speedAdj, which allows for real time speed limiting
    // speed cap is an extra option for a hard-coded speed limit that is applied to the throttle
    double stickSlider = stick.getThrottle();
    double speedCap = 1;
    double speedAdj = speedCap * (1 - ((stickSlider + 1) / 2));
    //System.out.println(speedAdj);
    robotDrive.driveCartesian(-speedAdj*stick.getX(), speedAdj*stick.getY(), -speedAdj*stick.getZ());

    // Joystick trigger controls the intake
    if (stick.getTrigger())
    {
      intake.setSpeed(1);
    }
    // Joystick side thumb button reverses intake (in case ball gets stuck in intake)
    else if (stick.getRawButton(2))
    {
      intake.setSpeed(-0.4);
    }
    else
    {
      intake.setSpeed(0);
    }

    

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    
  }
}
