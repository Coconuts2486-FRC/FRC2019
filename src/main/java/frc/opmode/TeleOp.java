package frc.opmode;

import frc.robot.RobotMap;
import frc.subsystem.DriveTrain;

/**
 * TeleOp
 */
public class TeleOp extends OpMode {

    public TeleOp() {
        super();
        
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        RobotMap.driveTrain.set((RobotMap.driveTrain.getJoystickY() - -RobotMap.driveTrain.getJoystickX()), (RobotMap.driveTrain.getJoystickY() + -RobotMap.driveTrain.getJoystickX()));
        if (RobotMap.driveTrain.isTriggerPressed()){
            RobotMap.driveTrain.setShifter(!RobotMap.driveTrain.getShifterState());
        }
    }

    
}