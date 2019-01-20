package frc.opmode;

import static frc.robot.RobotMap.*;

/**
 * Autonomous
 */
public class Autonomous extends OpMode {

    public Autonomous() {
        super();
    }

    @Override
    public void init() {
        driveTrain.compressor.setClosedLoopControl(false);
    }

    @Override
    public void loop() {
        double[] speeds = limelight.getValue();
        driveTrain.set(speeds[0], speeds[1]);
    }

}