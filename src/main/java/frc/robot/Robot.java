/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.networktables.NetworkTableEntry;
//import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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

  Command m_autonomousCommand, autoRightStartCommand, autoLeftStartCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  UsbCamera camera1;
  UsbCamera camera2;

  VideoSink server;
  
  MecanumDrive robotDrive;
  Joystick stick;
  XboxController xbox;

  Victor frontLeft, frontRight, backLeft, backRight;
  Victor intake;
  Victor shooter;
  Victor conveyer;
  Timer t;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    camera1 = CameraServer.getInstance().startAutomaticCapture(0);
    camera2 = CameraServer.getInstance().startAutomaticCapture(1);
    server = CameraServer.getInstance().getServer();

    camera1.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    camera2.setConnectionStrategy(ConnectionStrategy.kKeepOpen);

    stick = new Joystick(RobotMap.joystickDrive);
    xbox = new XboxController(RobotMap.xboxController);

    frontLeft = new Victor(RobotMap.leftFrontMotor);
    frontRight = new Victor(RobotMap.rightFrontMotor);
    backLeft = new Victor(RobotMap.leftBackMotor);
    backRight = new Victor(RobotMap.rightBackMotor);

    intake = new Victor(RobotMap.intakeMotor);
    
    
    shooter = new Victor(RobotMap.shooterMotor);
    
    
    conveyer = new Victor(RobotMap.conveyerMotor);

    robotDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
    //robotDrive.setMaxOutput(.25);

    // Instantiate our input/output.
    m_oi = new OI();

    // chooser.addOption("My Auto", new MyAutoCommand());
    autoRightStartCommand = new Command(){
    
      @Override
      protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      protected void execute() {
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
    };

    autoLeftStartCommand = new Command(){
    
      @Override
      protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      protected void execute() {
        double cT = t.get();
        if (cT < 1) {
          robotDrive.driveCartesian(0, 0.25, 0);
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
    };

    m_chooser.addOption("Auto Right Start", autoRightStartCommand);
    m_chooser.addOption("Auto Left Start", autoLeftStartCommand);

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

  // A shorter and easier move function to not worry about inverting (NEEDS TESTING)
  public void setMove(double forward, double right, double twistRight) {
    robotDrive.driveCartesian(right, -forward, twistRight);
  }

  public enum directions {
    Forward, Reverse, Left, Right
  }

  public void moveDir(directions dir, double speed) {
    switch (dir) {
      case Forward:
        setMove(0, speed, 0);
        break;
      case Reverse:
      setMove(0, -speed, 0);
        break;
      case Left:
      setMove(-speed, 0, 0);
        break;
      case Right:
        setMove(speed, 0, 0);
        break;
      default:
        break;
    }
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

    /*
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
    */
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
    //double stickSlider = stick.getThrottle();
    double speedCap = 1;
    double spinCap = .69;
    //double speedAdj = speedCap * (1 - ((stickSlider + 1) / 2));

    // For precise movement: if trigger is pressed, spinning is blocked
   /*
    if (stick.getTrigger()) {
      spinCap = 0;
    }
    else {
      spinCap = 0.69;
    }

    // For precise movement: if thumb button is presses, moving is blocked
    if (stick.getRawButton(2)) {
      speedAdj = 0;
    }
*/
    //System.out.println(speedAdj);
    robotDrive.driveCartesian(speedCap*xbox.getRawAxis(0), -speedCap*xbox.getRawAxis(1), spinCap*xbox.getRawAxis(4));

    // Xbox controller A button runs the intake
    if (xbox.getAButton())
    {
      intake.setSpeed(-0.5);
    }
    // Xbox controller b Button reverses intake (in case ball gets stuck in intake)
    else if (xbox.getBButton())
    {
      intake.setSpeed(0.5);
    }
    else
    {
      intake.setSpeed(0);
    }

    // Xbox controller left stick up&down controls the Conveyer 
    if (xbox.getXButton()) 
    {
      conveyer.setSpeed(-1);
    }
    else if (xbox.getYButton())
    {
      conveyer.setSpeed(1);
    }
    else
    {
      conveyer.setSpeed(0);
    }

    // Xbox controller left bumper runs the shooter
    if (xbox.getBumper(Hand.kLeft)) {
      shooter.setSpeed(-1);
    }
    
    if (xbox.getBumper(Hand.kRight))
    {
      shooter.setSpeed(0);
    }

    if (stick.getRawButton(4)) {
      System.out.println("Setting camera 2");
      server.setSource(camera1);
  } else if (stick.getRawButton(5)) {
      System.out.println("Setting camera 1");
      server.setSource(camera2);
  }

  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    
  }
}
