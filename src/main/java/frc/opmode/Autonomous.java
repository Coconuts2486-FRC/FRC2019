package frc.opmode;

import static frc.robot.RobotMap.*;

import com.ctre.phoenix.motorcontrol.ControlMode;

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
        driveTrain.compressor.setClosedLoopControl(false);
        RobotMap.driveTrain.zeroSensors();
        driveTrain.configPeakOutput(0.3);
    }

    @Override
    public void loop() {
        driveTrain.left. set(ControlMode.Position, 37696);
        driveTrain.right.set(ControlMode.Position, 37696);
    }

}