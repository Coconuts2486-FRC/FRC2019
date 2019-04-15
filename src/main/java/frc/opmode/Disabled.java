package frc.opmode;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.RobotMap;

/**
 * Disabled
 */
public class Disabled extends OpMode {

    public Disabled() {
        super();
    }

    @Override
    public void init() {
        RobotMap.elevator.innerStage.setNeutralMode(NeutralMode.Coast);
        RobotMap.limelight.setLights(false);
        RobotMap.limelight.setMode(true);
    }

    @Override
    public void loop() {
        RobotMap.elevator.zeroEncoder();
        RobotMap.climber.zeroEncoder();        
    }
}