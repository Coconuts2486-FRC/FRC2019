package frc.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
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

    public Solenoid driveShifters = null;
    public Compressor compressor  = null;

    public Joystick joystick1 = null;

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
        String[] keys = {"left", "right", "leftFollower", "rightFollower", "driveShifter", "pcm", "joystick1"};
        
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

        if(RobotMap.config.pneumaticIDs.containsKey(keys[4]) && RobotMap.config.pneumaticIDs.containsKey(keys[5])) {
            driveShifters = new Solenoid(RobotMap.config.pneumaticIDs.get(keys[5]), RobotMap.config.pneumaticIDs.get(keys[4]));
            compressor = new Compressor(RobotMap.config.pneumaticIDs.get(keys[5]));
        }
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[4]));
            
        if(RobotMap.config.inputDeviceIDs.containsKey(keys[6]))
            joystick1 = new Joystick(RobotMap.config.inputDeviceIDs.get(keys[6]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[6]));
    }

    /**
     * Stop the motors from running.
     */
    public void stop() {
        left.set(ControlMode.PercentOutput, 0);
        right.set(ControlMode.PercentOutput, 0);
    }

    /**
     * Set the left and right motors to a specified speed.
     * @param speed Desired speed from -1 to 1.
     */
    public void set(double speed) {
        left.set(ControlMode.PercentOutput, speed);
        right.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Sets the left and right motors independently to a specified speed.
     * @param left Desired speed for the left from -1 to 1.
     * @param right Desired speed for the right from -1 to 1.
     */
    public void set(double left, double right) {
        this.left.set(ControlMode.PercentOutput, left);
        this.right.set(ControlMode.PercentOutput, right);
        
    }

    public void setShifter(boolean shifterOnOff) {
        driveShifters.set(shifterOnOff);
    }

    public boolean getShifterState() {
        return
        driveShifters.get();
    }

    public double getJoystickX() {
        return joystick1.getX();
    }

    public double getJoystickY() {
        return
        joystick1.getY();
    }

    public boolean isTriggerPressed() {
        return
        joystick1.getTriggerPressed();
    }

    public void init() {
        leftFollower.set(ControlMode.Follower, left.getDeviceID());
        rightFollower.set(ControlMode.Follower, right.getDeviceID());
        RobotMap.driveTrain.right.setInverted(true);
        RobotMap.driveTrain.rightFollower.setInverted(true);
    }
}