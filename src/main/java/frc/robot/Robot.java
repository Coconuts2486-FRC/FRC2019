package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.auto.missions.AutoMission;
import frc.opmode.Autonomous;
import frc.opmode.Disabled;
import frc.opmode.OpMode;
import frc.opmode.TeleOp;
import frc.opmode.Test;

import static frc.robot.RobotMap.*;

public class Robot extends TimedRobot {

  private OpMode _opMode;

  public Robot() {
    // Initialize the robot.
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

  public void testAuto() {
    AutoMission m = new AutoMission();
    AutoMission.Test test = m.new Test();
    test.testMission();
  }

  public void testConfig() {
    String s = RobotConfig.generate().serialize(true);
    logger.printDebug(s);
  }
}