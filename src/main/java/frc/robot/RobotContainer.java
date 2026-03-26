// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.CenterAuto;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import static frc.robot.Constants.FuelConstants.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {
  private final ShooterSubsystem m_ShooterSubsystem = new ShooterSubsystem();
  private final Drivetrain m_Drivetrain = new Drivetrain();

  private final CommandPS4Controller m_driverController =
      new CommandPS4Controller(OperatorConstants.kDriverControllerPort);

  private final SendableChooser<Command> m_autoChooser = new SendableChooser<>();
  private final Command m_centerAuto = new CenterAuto(m_Drivetrain, m_ShooterSubsystem);

  public RobotContainer() {
    configureBindings();

    m_autoChooser.setDefaultOption("Center Auto", m_centerAuto);
    m_autoChooser.addOption("None", null);
    SmartDashboard.putData(m_autoChooser);
  }


  private void configureBindings() {

    // Drivetrain
    m_Drivetrain.setDefaultCommand(
        m_Drivetrain.ArcadeDrive(
          () -> -m_driverController.getLeftY(),
          () -> -m_driverController.getRightX()
          ));
    
    // Intake Fuel
    m_driverController.L2().whileTrue(
        m_ShooterSubsystem.runEnd(() -> m_ShooterSubsystem.Intake(), () -> m_ShooterSubsystem.Stop()));

    // Shooting
    m_driverController.R2().whileTrue(m_ShooterSubsystem.SpinUpShooterCommand().withTimeout(SPIN_UP_SECONDS)
        .andThen(m_ShooterSubsystem.ShootCommand())
        .finallyDo(() -> m_ShooterSubsystem.Stop())); 

    // Eject
    m_driverController.R1().whileTrue(m_ShooterSubsystem.runEnd(() -> m_ShooterSubsystem.Eject(), () -> m_ShooterSubsystem.Stop()));
  }

  public Command getAutoCommand() {
    return m_autoChooser.getSelected();
  }
}
