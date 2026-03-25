package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import static frc.robot.Constants.FuelConstants.*;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ShooterSubsystem;

public class CenterAuto extends SequentialCommandGroup {
    public CenterAuto(Drivetrain drivetrain, ShooterSubsystem shooterSubsystem) {
        addCommands(
            drivetrain.ArcadeDrive(() -> -0.5, () -> 0.0).withTimeout(0.25).andThen(drivetrain.ArcadeDrive(() -> 0.0, () -> 0.0)),

        shooterSubsystem.SpinUpShooterCommand().withTimeout(SPIN_UP_SECONDS)
        .andThen(shooterSubsystem.ShootCommand()).withTimeout(5)
        .finallyDo(() -> shooterSubsystem.Stop())
        );
    }
}
