package frc.auto.commands;

import java.util.HashMap;

import frc.auto.commands.utils.Command;
import frc.auto.commands.utils.ICommand;
import frc.robot.RobotMap;

/**
 * Drives the left and right motors a specified distance for a specified time.
 */
@Command(name = "Drive")
public class Drive extends ICommand {
    /**
     * Empty constructor for use in serialization. Do not add anything to this!
     * Do not call past midnight.
     */
    public Drive(HashMap<String, Double> map) { 
        super(map);
    }

    /**
     * Creates a new instance of Drive with the following parameters.
     * When run() is called, the robot will apply the left and right speeds to the left and right motors respectively.
     * Once the duration has exceeded the runtime, the command will return.
     * @param speedLeft Left speed to apply to the motors.
     * @param speedRight Right speed to apply to the motors.
     * @param duration Duration to apply speed for.
     */
    public Drive(double speedLeft, double speedRight, double duration) {
        super();
        this.parameters.put("speedLeft",  speedLeft);
        this.parameters.put("speedRight", speedRight);
        this.parameters.put("duration",   duration);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        RobotMap.logger.printStatus(String.format("Driving left with speed %s and right with %s for %s ms.", parameters.get("speedLeft"), parameters.get("speedRight"), parameters.get("duration")));
        RobotMap.driveTrain.set(parameters.get("speedLeft"), parameters.get("speedRight"));
        super.sleep(Double.valueOf(parameters.get("duration")).longValue());
    }
}