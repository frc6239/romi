// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.OnBoardIO;

public class LEDWithGyro extends CommandBase {
  Drivetrain drivetrain;
  OnBoardIO onBoardIO;

  /** Creates a new LEDWithGyro. */
  public LEDWithGyro(Drivetrain drivetrain, OnBoardIO onBoardIO) {
    this.drivetrain = drivetrain;
    this.onBoardIO = onBoardIO;
    addRequirements(drivetrain, onBoardIO);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.onBoardIO.setGreenLed(false);
    this.onBoardIO.setRedLed(false);
    this.drivetrain.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.drivetrain.arcadeDrive(0, 0);

    if (drivetrain.getGyroAngleZ() >= 10) {
      this.onBoardIO.setGreenLed(true);
      this.onBoardIO.setRedLed(false);
    } else if (drivetrain.getGyroAngleZ() <= 10) {
      this.onBoardIO.setRedLed(true);
      this.onBoardIO.setGreenLed(false);
    } else if (drivetrain.getGyroAngleZ() >= 90) {
      this.onBoardIO.setGreenLed(false);
    } else if (drivetrain.getGyroAngleZ() <= 90) {
      this.onBoardIO.setRedLed(false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.onBoardIO.setGreenLed(false);
    this.onBoardIO.setRedLed(false);
    System.out.println("LED command finished");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(this.drivetrain.getGyroAngleZ()) >= 360;
  }
}
