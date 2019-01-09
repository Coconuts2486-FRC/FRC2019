package frc.subsystem;

import edu.wpi.first.wpilibj.Talon;
import frc.robot.RobotMap;

/**
 * DriveTrain
 */
public class DriveTrain
{
    private static DriveTrain instance = null;

    public Talon left  = null;
    public Talon right = null;
    public Talon leftFollower  = null;
    public Talon rightFollower = null;

    private DriveTrain() {
        assign();

        left.setName("Drive Train", "Left");
        right.setName("Drive Train", "Right");
        leftFollower.setName("Drive Train", "Left Follower");
        rightFollower.setName("Drive Train", "Right Follower");
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
            left = new Talon(RobotMap.config.motorControllerIDs.get(keys[0]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[0]));

        if(RobotMap.config.motorControllerIDs.containsKey(keys[1]))
            right = new Talon(RobotMap.config.motorControllerIDs.get(keys[1]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[1]));

        if(RobotMap.config.motorControllerIDs.containsKey(keys[2]))
            leftFollower = new Talon(RobotMap.config.motorControllerIDs.get(keys[2]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[2]));

        if(RobotMap.config.motorControllerIDs.containsKey(keys[3]))
            rightFollower = new Talon(RobotMap.config.motorControllerIDs.get(keys[3]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[3]));
    }

    public void stop()
    {
        left.set(0);
        right.set(0);
    }
}