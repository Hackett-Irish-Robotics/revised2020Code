/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;



public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // Here we are creating the Victor variables to
  // control the drive.

  // PWM IDs
  PWMVictorSPX leftFrontVictorSPX = null;
  PWMVictorSPX leftBackVictorSPX = null;
  PWMVictorSPX rightFrontVictorSPX = null;
  PWMVictorSPX rightBackVictorSPX = null;

  // Creating our Arcade drive.
  DifferentialDrive differentialDrive = null;

  public Drivetrain() {


  // PWM Objects
  //  leftFrontVictorSPX = new PWMVictorSPX(RobotMap.DRIVETRAIN_LEFT_FRONT_VICTOR);
  //  leftBackVictorSPX = new PWMVictorSPX(RobotMap.DRIVETRAIN_LEFT_BACK_VICTOR);
  //  rightFrontVictorSPX = new PWMVictorSPX(RobotMap.DRIVETRAIN_RIGHT_FRONT_VICTOR);
  //  rightBackVictorSPX = new PWMVictorSPX(RobotMap.DRIVETRAIN_RIGHT_BACK_VICTOR);

  
  // Time to group up our motors so they can work together.
  //SpeedControllerGroup leftMotorsSPX = new SpeedControllerGroup(leftFrontVictorSPX, leftBackVictorSPX);
  //SpeedControllerGroup rightMotorsSPX = new SpeedControllerGroup(rightFrontVictorSPX, rightBackVictorSPX);

  // Attempt to slow motors down.
  //leftMotorsSPX.set(0.5);
  //rightMotorsSPX.set(0.5);

  //differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
  //differentialDrive = new DifferentialDrive(leftMotorsSPX, rightMotorsSPX);

  }
 
  // Full speed for the drive. Had issues when tried to slow it
  // down when moving forward and backward.
  public void arcadeDrive(double moveSpeed, double rotateSpeed) {
   // differentialDrive.arcadeDrive(moveSpeed, rotateSpeed * 0.69);

  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

    // We want to make sure we have the robot drive running.
  //  setDefaultCommand(new DriveArcade());
  }
}
    