package frc.opmode;

import static frc.robot.RobotMap.*;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.RobotMap;

/**
 * Autonomous
 */
public class Autonomous extends OpMode {

    NetworkTable instance;

    public Autonomous() {
        super();
    }

    @Override
    public void init() {
        Shuffleboard.selectTab("Autonomous");
        RobotMap.compressor.set(false);
        RobotMap.driveTrain.zeroSensors();
        driveTrain.configPeakOutput(0.3);
        RobotMap.elevator.elevatorForward();

        instance = NetworkTableInstance.getDefault().getTable("parameters");
        instance.getEntry("innerSpeed").setDouble(0);
        instance.getEntry("outerSpeed").setDouble(0);
        instance.getEntry("innerSetpoint").setDouble(-22014);
        instance.getEntry("outerSetpoint").setDouble(0);
    }

    @Override
    public void loop() {
        // RobotMap.driveTrain.set(1, 1);
        // sleep(5000);
        // RobotMap.driveTrain.set(-1, -1);
        // sleep(5000);

        //limelight.drive();
        
        // double innerSpeed = instance.getEntry("innerSpeed").getDouble(0);
        // double outerSpeed = instance.getEntry("outerSpeed").getDouble(0);

        // elevator.setInnerSpeed(innerSpeed);
        // elevator.setOuterSpeed(outerSpeed);

        // double innerSetpoint = instance.getEntry("innerSetpoint").getDouble(0);
        // double outerSetpoint = instance.getEntry("outerSetpoint").getDouble(0);

        // elevator.innerStage.set(ControlMode.Position, innerSetpoint);
        // elevator.outerStage.set(ControlMode.Position, outerSetpoint);

        
    }

    @SuppressWarnings("unused")
    private void sleep(long duration) {
        Stopwatch sw = Stopwatch.createStarted();
        while(sw.elapsed(TimeUnit.MILLISECONDS) <= duration);
    }
}