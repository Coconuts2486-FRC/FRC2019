package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.auto.commands.Drive;
import frc.auto.commands.utils.ICommand;
import frc.auto.commands.utils.ICommandDeserializer;
import frc.auto.commands.utils.ICommandSerializer;
import frc.debug.FileHandler;
import frc.opmode.Autonomous;
import frc.opmode.Disabled;
import frc.opmode.OpMode;
import frc.opmode.TeleOp;
import frc.opmode.Test;
import frc.subsystem.DriveTrain;

import static frc.robot.RobotMap.*;

import java.io.File;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Robot extends TimedRobot {

  private OpMode _opMode;

  public Robot() {
    // Initialize the RobotMap instances.
    init();

    Drive drive = new Drive(0.5, 0.5, 1000);
    // Sleep sleep = new Sleep(2000);

    Gson gson = new GsonBuilder().registerTypeAdapter(Drive.class, new ICommandSerializer()).setPrettyPrinting().create();
    // Gson gson2 = new GsonBuilder().registerTypeAdapter(Sleep.class, new ICommandSerializer()).setPrettyPrinting().create();
    String json = gson.toJson(drive);
    logger.printDebug(json);
    // String json2 = gson2.toJson(drive);
    // logger.printDebug(json2);

    Gson gsonDeserialized = new GsonBuilder().registerTypeAdapter(ICommand.class, new ICommandDeserializer()).create();
    ICommand driveDeserialized = gsonDeserialized.fromJson(json, ICommand.class);
    driveDeserialized.run();

    // Politely ask the JVM to clean house.
    System.gc();
  }

  @Override
  public void disabledInit() {
    _opMode = new Disabled();
    logger.printStatus("Initializing disabled.");
    _opMode.init();
    logger.printStatus("Looping disabled.");
  }

  @Override
  public void disabledPeriodic() {
    _opMode.loop();
  }

  @Override
  public void autonomousInit() {
    _opMode = new Autonomous();
    logger.printStatus("Initializing autonomous.");
    _opMode.init();
    logger.printStatus("Looping autonomous.");
  }

  @Override
  public void autonomousPeriodic() {
    _opMode.loop();
  }

  @Override
  public void teleopInit() {
    _opMode = new TeleOp();
    logger.printStatus("Initializing teleop.");
    _opMode.init();
    logger.printStatus("Looping teleop.");
  }

  @Override
  public void teleopPeriodic() {
    _opMode.loop();
  }

  @Override
  public void testInit() {
    _opMode = new Test();
    logger.printStatus("Initializing test.");
    _opMode.init();
    logger.printStatus("Looping test.");
  }

  @Override
  public void testPeriodic() {
    _opMode.loop();
  }

  public void init() {
    logger.printStatus("Initializing robot.");

    //TODO: Test to see if the virtualization test works.
    // Tests if the program is deployed to the robot.
    if(new File("/home/lvuser/README_File_Paths.txt").exists()) {
      FileHandler.loadConfig(); // Loads config file.
      DriverStation ds = DriverStation.getInstance();
      logger.printStatus(String.format("You are playing %s match %s at %s.", ds.getMatchType(), ds.getMatchNumber(), ds.getLocation()));
    }
    // Program is run locally. Load the config file elsewhere.
    else {
      // Gets the config file from the robot directory path.
      String s = Paths.get("").toAbsolutePath().toString() + "\\usb\\config\\config.txt";
      FileHandler.loadConfig(s);
    }

    RobotMap.driveTrain = DriveTrain.getInstance();
  }
}