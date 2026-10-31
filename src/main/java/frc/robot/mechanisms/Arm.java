package frc.robot.mechanisms;

import org.team4159.commandsv3backport.command3.Command;
import org.team4159.commandsv3backport.command3.Mechanism;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

public class Arm implements Mechanism {
    private final SparkMax motor = new SparkMax(3, MotorType.kBrushless);
    private final SparkMaxConfig motorConfig = new SparkMaxConfig();

    public Arm() {
        motorConfig.inverted(false);
        motorConfig.smartCurrentLimit(30);
        motorConfig.secondaryCurrentLimit(80);
        motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    private void lift(double speed) {
        motor.set(speed);
    }

    private void stop() {
        motor.stopMotor();
    }

    public Command liftCommand(double speed) {
        return run(coroutine -> {
            lift(speed);
            coroutine.park();
        })
            .whenExited(this::stop)
            .named("Lift");
    }
}
