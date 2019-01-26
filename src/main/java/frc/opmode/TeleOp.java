package frc.opmode;

import static frc.robot.RobotMap.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * TeleOp
 */
public class TeleOp extends OpMode {

    public TeleOp() {
        super();
    }

    @Override
    public void init() {
        Shuffleboard.selectTab("TeleOp");
        driveTrain.compressor.setClosedLoopControl(true);
    }

    @Override
    public void loop() {

        if(driveTrain.joystick1.getRawButton(2))
        {
            double[] speeds = limelight.getValue();
            driveTrain.set(speeds[0], speeds[1]);
        }
        else
        {
            driveTrain.set(-driveTrain.getJoystickY() + driveTrain.getJoystickX(), -driveTrain.getJoystickY() - driveTrain.getJoystickX());
            
            if (driveTrain.isTriggerPressed())
                driveTrain.setShifter(!driveTrain.getShifterState());
        }
    }
}