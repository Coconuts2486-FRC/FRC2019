package frc.opmode;

import frc.robot.RobotMap;

/**
 * Disabled
 */
public class Disabled extends OpMode {

    public Disabled() {
        super();
    }

    @Override
    public void init() {
        RobotMap.limelight.setLights(false);
    }

    @Override
    public void loop() {

    }

    
}