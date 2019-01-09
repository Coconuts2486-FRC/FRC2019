package frc.opmode;

import com.google.gson.annotations.Expose;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Base OpMode class. Provides resources for game data.
 */
public abstract class OpMode {

    /**
     * Game-specific message from the Driver Station or Field Management System, depending on which is connected.
     * This is set once the constructor is called. If a null pointer exception occurs when accessing, the constructor was not called.
     * The {@code}@Expose{@code} annotation is set to false.
     */
    @Expose(serialize = false, deserialize = false)
    protected String gameData;

    /**
     * Create a new instance of OpMode.
     * Initializes the game-specific message provided to OpModes.
     */
    public OpMode()
    {
        gameData = DriverStation.getInstance().getGameSpecificMessage();
    }

    /**
     * Initialization code goes in this method.
     * This will only run once before {@code}loop(){@code} is called.
     */
    public abstract void init();
      /**
     * Loop code goes in this method.
     * This will run repeatedly until interrupted after {@code}init(){@code} is called.
     */
    public abstract void loop();
}