package frc.auto.commands;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import frc.auto.commands.utils.Command;
import frc.auto.commands.utils.ICommand;
import frc.robot.RobotMap;

/**
 * Sleep
 */

@Command(name = "Sleep")
public class Sleep extends ICommand {
    private long duration;

    public Sleep() { }

    public Sleep(long duration) {
        this.duration = duration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        RobotMap.logger.printStatus(String.format("Sleeping for %s ms.", duration));
        sleep(duration);
    }

    /**
     * Sleeps for the specified duration in milliseconds.
     * @param duration Time to sleep for.
     */
    private void sleep(long duration) {
        Stopwatch sw = Stopwatch.createStarted();
        while(sw.elapsed(TimeUnit.MILLISECONDS) <= duration);
    }
}