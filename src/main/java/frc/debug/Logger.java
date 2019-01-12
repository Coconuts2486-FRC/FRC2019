package frc.debug;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import frc.enums.Log;

import org.fusesource.jansi.AnsiConsole;

import edu.wpi.first.wpilibj.DriverStation;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

/**
 * Provides basic logging functions.
 * The stopwatch is started once the instance is called.
 */
public class Logger
{
    private Stopwatch _stopwatch = Stopwatch.createUnstarted();
    private boolean _printTrace = false;
    private Log _verbosity = Log.DEBUG;

    private static Logger instance = null;
    
    private Logger() {
        _stopwatch.start();
        AnsiConsole.systemInstall();
    }

    public static Logger getInstance() {
        if(instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    /**
     * Prints a message to the console.
     * The color will be blue.
     * The message will be timestamped.
     * @param msg Message to print.
     */
    public void printDebug(String msg) {
        if(_verbosity.getValue() > Log.DEBUG.getValue())
            return;
        
        System.out.print(ansi().fg(BLUE).a(String.format("[%s] DEBUG: %s\n", getTimestamp(), msg)).reset().reset());
    }

    /**
     * Prints a message to the console.
     * The color will be green.
     * The message will be timestamped.
     * @param msg Message to print.
     */
    public void printStatus(String msg) {
        if(_verbosity.getValue() > Log.STATUS.getValue())
            return;

        System.out.print(ansi().fg(GREEN).a(String.format("[%s] STATUS: %s\n", getTimestamp(), msg)).reset());
    }

    /**
     * Prints a message to the console.
     * The color will be yellow.
     * The message will be timestamped.
     * The stack trace may be printed if the verbosity is set.
     * @param msg Message to print.
     * @see Logger.setVerbosity().
     */
    public void printWarning(String msg) {
        if(_verbosity.getValue() > Log.WARNING.getValue())
            return;
        
        if(_printTrace) {
            String fileCaller = Thread.currentThread().getStackTrace()[4].getClassName();
            String methodCaller = Thread.currentThread().getStackTrace()[4].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[4].getLineNumber();
            System.out.print(ansi().fg(YELLOW).a(String.format("[%s] WARNING: %s at %s() in %s:%s\n", getTimestamp(), msg, methodCaller, fileCaller, lineNumber)).reset());
        }
        else {
            System.out.print(ansi().fg(YELLOW).a(String.format("[%s] WARNING: %s\n", getTimestamp(), msg)).reset());
        }
    }

    
    /**
     * Prints a message to the console.
     * The color will be red.
     * The message will be timestamped.
     * The stack trace may be printed if the verbosity is set.
     * This will not throw an exception nor crash the robot.
     * @param msg Message to print.
     * @see Logger.setVerbosity().
     */
    public void printError(String msg) {
        if(_verbosity.getValue() > Log.ERROR.getValue())
            return;

        if(_printTrace) {
            String fileCaller = Thread.currentThread().getStackTrace()[4].getClassName();
            String methodCaller = Thread.currentThread().getStackTrace()[4].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[4].getLineNumber();
            System.out.print(ansi().fg(RED).a(String.format("[%s] ERROR: %s at %s() in %s:%s\n", getTimestamp(), msg, methodCaller, fileCaller, lineNumber)).reset());
        }
        else {
            System.out.print(ansi().fg(RED).a(String.format("[%s] ERROR: %s\n", getTimestamp(), msg)).reset());
        }
    }

    /**
     * Set the minimum verbosity setting.
     * For example, {@code}Log.DEBUG{@code} will cause all messages to be shown,
     * while {@code}Log.ERROR{@code} will only show errors.
     * @param verbosity Level of verbosity.
     * @see Log
     */
    public void setVerbosity(Log verbosity) {
        _verbosity = verbosity;
    }

    /**
     * Get the current verbosity setting.
     * @return Instance of {@code}Log{@code}.
     */
    public Log getVerbosity() {
        return _verbosity;
    }

    /**
     * Set whether to print stack traces when calling {@code}printWarning{@code} or {@code}printError{@code}.
     * For example, {@code}true{@code} will cause stack traces to be shown.
     * @param verbosity Level of verbosity.
     * @see Log
     */
    public void setStackTrace(boolean state) {
        _printTrace = state;
    }

    /**
     * Get the current stack trace setting.
     * @return {@code}true{@code} if printing stack traces.
     */
    public boolean getStackTrace(boolean state) {
        return _printTrace;
    }

    private String getTimestamp() {
        return String.format("%6d", _stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public void test()
    {
        printDebug("Debug.");
        printStatus("Status.");
        printWarning("Warning.");
        printError("Error.");
    }
}