package frc.subsystem;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

/**
 * Defines methods that all subsystems will inherit from.
 */
public abstract class ISubsystem {
    /**
     * Assigns all devices in the subsystem.
     */
    protected abstract void assign();

    /**
     * Sleeps for the specified duration in milliseconds.
     * @param duration Time to sleep for.
     */
    protected void sleep(long duration) {
        Stopwatch sw = Stopwatch.createStarted();
        while(sw.elapsed(TimeUnit.MILLISECONDS) <= duration);
    }
}