// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import static frc.robot.Constants.FuelConstants.*;

public class RobotContainer {
  private final ShooterSubsystem m_ShooterSubsystem = new ShooterSubsystem();
  private final Drivetrain m_Drivetrain = new Drivetrain();

  private final CommandPS5Controller m_driverController =
      new CommandPS5Controller(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }


  private void configureBindings() {

    // Drivetrain
    m_Drivetrain.setDefaultCommand(
        m_Drivetrain.ArcadeDrive(
          () -> -m_driverController.getLeftY(),
          () -> -m_driverController.getRightX()));

    // Intake Fuel
    m_driverController.L2().whileTrue(
        m_ShooterSubsystem.runEnd(() -> m_ShooterSubsystem.Intake(), () -> m_ShooterSubsystem.Stop()););

    // Shooting
    m_driverController.R2().whileTrue(m_ShooterSubsystem.SpinUpCommand.withTimeout(SPIN_UP_SECONDS))
        .andThen(m_ShooterSubsystem.ShootCommand())
        .finallyDo(() -> m_ShooterSubsystem.Stop());

    // Eject
    m_driverController.R1().whileTrue(m_ShooterSubsystem.runEnd(() -> m_ShooterSubsystem.Eject(), () -> m_ShooterSubsystem.Stop()));
  }
}
