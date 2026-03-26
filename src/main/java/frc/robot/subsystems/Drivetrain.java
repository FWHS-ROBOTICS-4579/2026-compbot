// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private final SparkMax LeftLeader = new SparkMax(3, MotorType.kBrushed);
  // private final SparkMax LeftFollower = new SparkMax(2, MotorType.kBrushed);
  private final SparkMax RightLeader = new SparkMax(1, MotorType.kBrushed);
  // private final SparkMax RightFollower = new SparkMax(4, MotorType.kBrushed);

  private final DifferentialDrive differentialDrive = new DifferentialDrive(LeftLeader, RightLeader);

  private final SlewRateLimiter driveSpeedLimiter = new SlewRateLimiter(2);

  /** Creates a new ExampleSubsystem. */
  public Drivetrain() {
    SparkBaseConfig config = new SparkMaxConfig();
    
    config.voltageCompensation(12);
    config.smartCurrentLimit(60);

    // config.follow(LeftLeader);
    // config.inverted(true);
    // LeftFollower.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);

    // config.follow(RightLeader);
    // config.inverted(false);
    // RightFollower.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);

    // config.disableFollowerMode();
    config.inverted(false);
    RightLeader.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    config.inverted(true);
    LeftLeader.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
  }

  public Command ArcadeDrive(DoubleSupplier x, DoubleSupplier y) {
    return this.run(
      () -> differentialDrive.arcadeDrive(driveSpeedLimiter.calculate(x.getAsDouble()), y.getAsDouble())
    );
  }
}
