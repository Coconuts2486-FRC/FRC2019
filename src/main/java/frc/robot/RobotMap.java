package frc.robot;

import frc.utils.DataPublisher;
import frc.utils.Logger;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import frc.auto.AutoFactory;
import frc.subsystem.CameraSystem;
import frc.subsystem.Climber;
import frc.subsystem.Compressor;
import frc.subsystem.DriveTrain;
import frc.subsystem.Elevator;
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
    public static Compressor compressor;
    public static CameraSystem cameraSystem;
    public static AutoFactory autoFactory;
    public static Elevator elevator;
    public static Climber climber;

    public static Logger logger = Logger.getInstance();

    @SuppressWarnings("resource")
    public static void init() {
        logger.printStatus("Initializing robot.");
    
        initRobotMap();

        // Publish data to NetworkTables every 0.1 seconds.
        Notifier notifier = new Notifier(new DataPublisher());
        notifier.startPeriodic(0.1);

        CameraServer.getInstance().startAutomaticCapture(0);
        //CameraServer.getInstance().startAutomaticCapture(1); // Enable for a second USB camera.

        DriverStation ds = DriverStation.getInstance();

        if(ds.isFMSAttached())
        logger.printWarning(String.format("You are playing %s match %s at %s.", ds.getMatchType(), ds.getMatchNumber(), ds.getLocation()));
        logger.printWarning("Good luck and have fun! :)");
    }

    private static void initRobotMap() {
        // // Tests if the program is deployed to the robot.
        // if(new File("/home/lvuser/README_File_Paths.txt").exists()) {
        //     FileHandler.loadConfig(); // Loads config file.
        //     DriverStation ds = DriverStation.getInstance();

        //     if(ds.isFMSAttached())
        //         logger.printStatus(String.format("You are playing %s match %s at %s.", ds.getMatchType(), ds.getMatchNumber(), ds.getLocation()));

        //     RobotMap.driveTrain = DriveTrain.getInstance();
        // }

        // // Program is run locally. Load the config file elsewhere.
        // else {
        //     // Gets the config file from the robot directory path.
        //     String s = Paths.get("").toAbsolutePath().toString() + "\\usb\\config\\config.txt";
        //     FileHandler.loadConfig(s);
        // }

        // Initialize the RobotMap.
        RobotMap.driveTrain = DriveTrain.getInstance();
        driveTrain.init();
        limelight = Limelight.getInstance();
        pigeon = Pigeon.getInstance();
        compressor = Compressor.getInstance();
        //cameraSystem = CameraSystem.getInstance();
        elevator = Elevator.getInstance();
        climber = Climber.getInstance();

        // autoFactory = AutoFactory.getInstance();
    }
}