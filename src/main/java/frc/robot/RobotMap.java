package frc.robot;

import frc.utils.DataPublisher;
import frc.utils.FileHandler;
import frc.utils.Logger;

import java.io.File;
import java.nio.file.Paths;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import frc.subsystem.DriveTrain;
import frc.subsystem.Limelight;
import frc.subsystem.Pigeon;

/**
 * Provides an interface for accessing the subsystems in a SAFE manner.
 */
public class RobotMap
{
    public static RobotConfig config;
    public static DriveTrain driveTrain;
    public static Limelight limelight;
    public static Pigeon pigeon;

    public static Logger logger = Logger.getInstance();

    public static void init() {
        logger.printStatus("Initializing robot.");
    
        initRobotMap();

        // Publish data to NetworkTables every 0.1 seconds.
        Notifier notifier = new Notifier(new DataPublisher());
        notifier.startPeriodic(0.1);

        CameraServer.getInstance().startAutomaticCapture();
    }

    private static void initRobotMap() {
        // Tests if the program is deployed to the robot.
        if(new File("/home/lvuser/README_File_Paths.txt").exists()) {
            FileHandler.loadConfig(); // Loads config file.
            DriverStation ds = DriverStation.getInstance();

            if(ds.isFMSAttached())
                logger.printStatus(String.format("You are playing %s match %s at %s.", ds.getMatchType(), ds.getMatchNumber(), ds.getLocation()));

            RobotMap.driveTrain = DriveTrain.getInstance();
        }

        // Program is run locally. Load the config file elsewhere.
        else {
            // Gets the config file from the robot directory path.
            String s = Paths.get("").toAbsolutePath().toString() + "\\usb\\config\\config.txt";
            FileHandler.loadConfig(s);
        }

        // Initialize the RobotMap.
        driveTrain.init();
        limelight = Limelight.getInstance();
        pigeon = Pigeon.getInstance();
    }
}