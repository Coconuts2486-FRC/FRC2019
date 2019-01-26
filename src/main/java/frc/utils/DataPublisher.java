package frc.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.RobotMap;

/**
 * Publishes data about the robot to NetworkTables.
 */
public class DataPublisher implements java.lang.Runnable {
    NetworkTable instance = NetworkTableInstance.getDefault().getTable("datapublisher");
    public void run() {
        instance.getEntry("Left Speed").setDouble(RobotMap.driveTrain.left.getSelectedSensorVelocity(0));
        instance.getEntry("Right Speed").setDouble(RobotMap.driveTrain.right.getSelectedSensorVelocity(0));

        instance.getEntry("Left Position").setDouble(RobotMap.driveTrain.left.getSelectedSensorPosition(0));
        instance.getEntry("Right Position").setDouble(RobotMap.driveTrain.right.getSelectedSensorPosition(0));
        
        instance.getEntry("Left Error").setDouble(RobotMap.driveTrain.left.getClosedLoopError(0));
        instance.getEntry("Right Error").setDouble(RobotMap.driveTrain.right.getClosedLoopError(0));

        instance.getEntry("Heading").setDouble(RobotMap.pigeon.getHeading());
    }
}