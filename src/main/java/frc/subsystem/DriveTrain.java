package frc.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.RobotMap;

/**
 * DriveTrain
 */
public class DriveTrain
{
    private static DriveTrain instance = null;

    public TalonSRX left  = null;
    public TalonSRX right = null;
    public TalonSRX leftFollower  = null;
    public TalonSRX rightFollower = null;

    private DriveTrain() {
        assign();
    }

    public static DriveTrain getInstance() {
        if(instance == null) {
            instance = new DriveTrain();
        }

        return instance;
    }

    private void assign()
    {
        String[] keys = {"left", "right", "leftFollower", "rightFollower"};
        
        if(RobotMap.config.motorControllerIDs.containsKey(keys[0]))
            left = new TalonSRX(RobotMap.config.motorControllerIDs.get(keys[0]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[0]));

        if(RobotMap.config.motorControllerIDs.containsKey(keys[1]))
            right = new TalonSRX(RobotMap.config.motorControllerIDs.get(keys[1]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[1]));

        if(RobotMap.config.motorControllerIDs.containsKey(keys[2]))
            leftFollower = new TalonSRX(RobotMap.config.motorControllerIDs.get(keys[2]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[2]));

        if(RobotMap.config.motorControllerIDs.containsKey(keys[3]))
            rightFollower = new TalonSRX(RobotMap.config.motorControllerIDs.get(keys[3]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[3]));
    }

    public void stop() {
        left.set(ControlMode.PercentOutput, 0);
        right.set(ControlMode.PercentOutput, 0);
    }

    public void set(double speed) {
        left.set(ControlMode.PercentOutput, speed);
        right.set(ControlMode.PercentOutput, speed);
    }

    public void set(double left, double right) {
        this.left.set(ControlMode.PercentOutput, left);
        this.right.set(ControlMode.PercentOutput, right);
    }
}