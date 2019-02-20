package frc.opmode;

import static frc.robot.RobotMap.*;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.RobotMap;

/**
 * Autonomous
 */
public class Autonomous extends OpMode {

    public Autonomous() {
        super();
    }

    @Override
    public void init() {
        Shuffleboard.selectTab("Autonomous");
        RobotMap.compressor.set(false);
        RobotMap.driveTrain.zeroSensors();
        driveTrain.configPeakOutput(0.3);
    }

    @Override
    public void loop() {
        // RobotMap.driveTrain.set(1, 1);
        // sleep(5000);
        // RobotMap.driveTrain.set(-1, -1);
        // sleep(5000);

        limelight.drive();
    }

    @SuppressWarnings("unused")
    private void sleep(long duration) {
        Stopwatch sw = Stopwatch.createStarted();
        while(sw.elapsed(TimeUnit.MILLISECONDS) <= duration);
    }
}