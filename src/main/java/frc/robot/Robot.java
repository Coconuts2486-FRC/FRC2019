package frc.robot;

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
import static frc.robot.RobotMap.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Robot extends TimedRobot {

  private OpMode _opMode;

  public Robot() {
    logger.printStatus("Initializing robot.");

    //TODO: These need to be replaced with a test for virtualization.
    //FileHandler.loadConfig(); // Use on roboRIO
    FileHandler.loadConfig("C:\\Users\\Zach\\Desktop\\config.txt"); // Use on computers, replace with your config.txt dir

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
}