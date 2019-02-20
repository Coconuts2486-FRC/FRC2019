package frc.auto.commands.utils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.google.gson.annotations.Expose;

import frc.auto.missions.AutoMission;
import frc.robot.RobotMap;

/**
 * Abstract class for commands. Defines that run() must be available for AutoMissions to call.
 * @see AutoMission
 */
public class ICommand {

    /**
     * Map containing the name of a variable and its value.
     * When {@code}run(){@code} is called, it will access this to determine
     * the appropriate value.
     */
    @Expose(serialize = true, deserialize = true)
    public HashMap<String, Double> parameters;

    public ICommand() { parameters = new HashMap<>(4, 1); }
    public ICommand(HashMap<String, Double> parameters) { this.parameters = parameters; }

    /**
     * Defines what action to perform when running.
     * Must be overwritten.
     */
    public void run()
    {
        RobotMap.logger.printWarning("Default run() command in ICommand. Overload me!");
    }

    /**
     * Sleeps for the specified duration in milliseconds.
     * @param duration Time to sleep for.
     */
    protected void sleep(long duration) {
        Stopwatch sw = Stopwatch.createStarted();
        while(sw.elapsed(TimeUnit.MILLISECONDS) <= duration);
    }
}