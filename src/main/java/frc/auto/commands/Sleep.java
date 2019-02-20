package frc.auto.commands;

import java.util.HashMap;

import frc.auto.commands.utils.Command;
import frc.auto.commands.utils.ICommand;
import frc.robot.RobotMap;

/**
 * Sleep the robot for a specific amount of time.
 */

@Command(name = "Sleep")
public class Sleep extends ICommand {
    /**
     * Constructor for use in serialization. Do not add anything to this!
     * Do not call past midnight.
     */
    public Sleep(HashMap<String, Double> map) { super(map); }

    /**
     * Creates a new instance of Drive with the following parameters.
     * When run() is called, the robot will apply the left and right speeds to the left and right motors respectively.
     * Once the duration has exceeded the runtime, the command will return.
     * @param speedLeft Left speed to apply to the motors.
     * @param speedRight Right speed to apply to the motors.
     * @param duration Duration to apply speed for.
     */
    public Sleep(double duration) {
        super();
        this.parameters.put("duration", duration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        RobotMap.logger.printStatus(String.format("Sleeping for %s ms.", parameters.get("duration")));
        super.sleep(Double.valueOf(parameters.get("duration")).longValue());
    }
}