package frc.robot;

import static edu.wpi.first.units.Units.Seconds;

import org.team4159.commandsv3backport.command3.Command;

import frc.robot.mechanisms.Arm;
import frc.robot.mechanisms.Drivetrain;

public class AutoFactory {
    private AutoFactory() {

    }
    public static Command autoCommand(Drivetrain drivetrain, Arm arm) {
        return Command.requiring(drivetrain, arm)
            .executing(coroutine -> {
                //list out all commands you want to run, make an autonomous routine
                //Drivetrain drives forward at 25% speed for 1.5 seconds, inside the parentheses are the speed of each motor
                coroutine.await(drivetrain.autoDriveCommand(0.25, 0.25).withTimeout(Seconds.of(1.5)));
                //turning = one motor is negative speed
                coroutine.await(drivetrain.autoDriveCommand(0.15, -0.15).withTimeout(Seconds.of(0.75)));
                coroutine.await(arm.liftCommand(0.4).withTimeout(Seconds.of(0.5)));
            })
            .named("Auto Drive");
    }
}