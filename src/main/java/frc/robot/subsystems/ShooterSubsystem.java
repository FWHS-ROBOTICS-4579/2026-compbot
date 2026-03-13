// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  private final SparkMax feederMotor = new SparkMax(5, MotorType.kBrushed);
  private final SparkMax intakeAndShooterMotor = new SparkMax(6, MotorType.kBrushed);

  /** Creates a new ExampleSubsystem. */
  public ShooterSubsystem() {
    SparkBaseConfig launcherConfig = new SparkMaxConfig();
    launcherConfig.voltageCompensation(12);
    launcherConfig.smartCurrentLimit(60);
    launcherConfig.inverted(true);
    intakeAndShooterMotor.configure(launcherConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);


    SparkBaseConfig feederConfig = new SparkMaxConfig();
    feederConfig.voltageCompensation(12);
    feederConfig.smartCurrentLimit(60);
    feederMotor.configure(feederConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
  }

  public void StartShooter() {
    intakeAndShooterMotor.setVoltage(10);
    feederMotor.setVoltage(10);
  }

  public void StopShooter() {
    intakeAndShooterMotor.setVoltage(0);
    feederMotor.setVoltage(0);
  }

  public void StartIntake() {
    intakeAndShooterMotor.setVoltage(-10);
    feederMotor.setVoltage(-10);
  }

  public void StopIntake() {
    intakeAndShooterMotor.setVoltage(0);
    feederMotor.setVoltage(0);
  }
}
