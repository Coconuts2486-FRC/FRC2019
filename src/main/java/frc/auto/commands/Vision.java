package frc.auto.commands;

import java.util.HashMap;

import frc.auto.commands.utils.Command;
import frc.auto.commands.utils.ICommand;

/**
 * Navigate to a vision target. Returns when in the specified tolerance.
 */

@Command(name = "Vision")
public class Vision extends ICommand {
    /**
     * Constructor for use in serialization. Do not add anything to this!
     * Do not call past midnight.
     */
    public Vision(HashMap<String, Double> map) { super(map); }

    /**
     * Creates a new instance of Vision with the specified tolerance.
     * When run() is called, the robot will move to the left and right using the Limelight
     * camera as the sensor.
     * @param tolerance How close to approach the desired vision setpoints.
     * @param timeout Amount of time before forcefully returning.
     */
    public Vision(double tolerance, double timeout) {
        super();
        this.parameters.put("tolerance", tolerance);
    }
}