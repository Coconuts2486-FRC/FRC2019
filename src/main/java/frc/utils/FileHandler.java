package frc.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;

import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

import frc.robot.RobotConfig;
import frc.robot.RobotMap;

/**
 * Provides basic utilities for writing to and reading from files.
 */
public class FileHandler {
    /**
     * Writes to a file at the specified location.
     * Ensure that the location exists on the drive.
     * @param data String to write to the file.
     * @param location File location to write to.
     */
    public static void WriteToFile(String data, String location) {
        File file = new File(location);
        CharSink sink = Files.asCharSink(file, StandardCharsets.UTF_8);
        try {
            sink.write(data);
        }
        catch(Exception ex)
        {
            RobotMap.logger.printError(ex.getMessage());
        }
    }

    public static String ReadFromFile(String location) {
        File file = new File(location);
        CharSource source = Files.asCharSource(file, StandardCharsets.UTF_8);
        try {
            return source.read();
        }
        catch(Exception ex)
        {
            RobotMap.logger.printError(ex.getMessage());
            return "";
        }
    }

    /**
     * Load the config file from /U/config/config.txt.
     * This will overwrite the config file located in RobotMap.
     * Only works when deployed to the roboRIO; otherwise, use {@code}loadConfig(path){@code}.
     */
    public static void loadConfig() {
        String dir = "/U/config/";
        String file = "config.txt";

        loadConfig(dir + file);
    }

    /**
     * Load the config file from the specified path.
     * This will overwrite the config file located in RobotMap.
     * @param path The path to load the config file from
     */
    public static void loadConfig(String path) {
        RobotMap.config = RobotConfig.deserialize(FileHandler.ReadFromFile(path));
        RobotMap.logger.printDebug(RobotMap.config.toString());
    }
}