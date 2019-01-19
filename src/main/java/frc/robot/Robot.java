package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.auto.missions.AutoMission;
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

public class Robot extends TimedRobot {

  private OpMode _opMode;

  public Robot() {
    String s = RobotConfig.generate().serialize(true);
    logger.printDebug(s);

    // Initialize the RobotMap instances.
    init();

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

    driveTrain.init();

  }

  public void testAuto() {
    AutoMission m = new AutoMission();
    AutoMission.Test test = m.new Test();
    test.testMission();
  }
}