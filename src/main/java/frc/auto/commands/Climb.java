package frc.auto.commands;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.auto.commands.utils.Command;
import frc.auto.commands.utils.ICommand;
import frc.robot.RobotMap;

/**
 * Drives the left and right motors a specified distance for a specified time.
 */
@Command(name = "Climb")
public class Climb extends ICommand {
    NetworkTable nt; // Used for debugging by printing messages.
    boolean isFront; // Switch for whether the front elevator is active.

    /**
     * Empty constructor for use in serialization. Do not add anything to this! ...
     * Do not call past midnight.
     */
    public Climb(HashMap<String, Double> map) {
        super(map);
    }

    /**
     * Creates a new instance of Climb with the following parameters. When run() is
     * called, the robot will wobble itself onto the habitat. Once the duration has
     * exceeded the runtime, the command will return.
     * 
     * @param angleSetpoint Target angle for climbing.
     * @param tiltThreshold Toggle climbing direction when the error exceeds the
     *                      threshold.
     * @param duration      Duration to climb for before timing out. Not yet
     *                      implemented.
     */
    public Climb(double angleSetpoint, double tiltThreshold, double duration) {
        super();
        this.parameters.put("angleSetpoint", angleSetpoint);
        this.parameters.put("tiltThreshold", tiltThreshold);
        this.parameters.put("duration", duration);

        nt = NetworkTableInstance.getDefault().getTable("datapublisher");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        // Prints that the method is actually running.
        RobotMap.logger.printStatus("Attempting to climb. Wish me luck!");

        // Gets the current difference between the desired angle and current angle of
        // the robot.
        // Necessary to ensure the robot "wobbles" up.
        double error = this.parameters.get("angleSetpoint") - RobotMap.pigeon.getPitch();
        nt.getEntry("Climb Error").setNumber(error);

        // If the limit switch is pressed, the robot is at the top.
        if (RobotMap.climber.climbArm.getSensorCollection().isRevLimitSwitchClosed()) {
            RobotMap.logger.printStatus("Reverse limit switch hit!");

            // Drives the robot forward.
            RobotMap.driveTrain.set(0.5, 0.5);
            RobotMap.climber.climbRollers.set(ControlMode.PercentOutput, 0.75);
            // Applies a small amount of power to keep the robot up.
            RobotMap.climber.enableArm();
            RobotMap.elevator.setInnerSpeed(-0.5);
        } else {
            // If the robot is currently pushing the front elevator down.
            // This prevents the robot from wobbling at one point and instead bounces
            // between two points.
            if (isFront == true && error >= this.parameters.get("tiltThreshold")) {
                isFront = !isFront;
                RobotMap.logger.printDebug("Set direction on the climber to FALSE.");
            } else if (error < this.parameters.get("tiltThreshold")) {
                isFront = !isFront;
                RobotMap.logger.printDebug("Set direction on the climber to TRUE.");
            }

            // Based on isFront, the front and back elevator is set to move.
            if (isFront == true) {
                RobotMap.climber.disableArm();
                RobotMap.elevator.setInnerSpeed(-0.85);
            } else {
                RobotMap.climber.enableArm();
                RobotMap.elevator.setInnerSpeed(0);
            }

            // Runs the wheels on the climber forward to push it forward.
            RobotMap.climber.climbRollers.set(ControlMode.PercentOutput, 0.75);
        }
    }
}