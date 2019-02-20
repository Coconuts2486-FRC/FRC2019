package frc.subsystem;

import frc.enums.IDs;
import frc.robot.RobotMap;

/**
 * Compressor
 */
public class Compressor {
    private static Compressor instance = null;
    
    private edu.wpi.first.wpilibj.Compressor compressor = null;
    private Compressor() {
        assign();
    }

    public static Compressor getInstance() {
        if(instance == null) {
            instance = new Compressor();
        }

        return instance;
    }

    private void assign() {
        compressor = new edu.wpi.first.wpilibj.Compressor(IDs.PCM.getValue());
    }

    @SuppressWarnings("unused")
    private void assignKeys() {
        String[] keys = {"pcm"};

        if(RobotMap.config.pneumaticIDs.containsKey(keys[5]))
            compressor = new edu.wpi.first.wpilibj.Compressor(RobotMap.config.pneumaticIDs.get(keys[5]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[5]));
    }

    public void toggle() {
        compressor.setClosedLoopControl(!compressor.getClosedLoopControl());
    }

    public void enable() {
        compressor.setClosedLoopControl(true);
    }

    public void disable() {
        compressor.setClosedLoopControl(false);
    }

    public void set(boolean value) {
        compressor.setClosedLoopControl(value);
    }
}