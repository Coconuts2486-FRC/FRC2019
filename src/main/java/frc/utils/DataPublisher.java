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

        //instance.getEntry("Heading").setDouble(RobotMap.pigeon.getHeading());

        instance.getEntry("Shifters").setString(RobotMap.driveTrain.getShifterState().name());

        instance.getEntry("Elevator IR Beam").setBoolean(RobotMap.elevator.getIRBeam());
        instance.getEntry("Elevator Inner Position").setDouble(RobotMap.elevator.getInnerEncoder());
        instance.getEntry("Elevator Outer Position").setDouble(RobotMap.elevator.getOuterEncoder());
        instance.getEntry("Climb Arm Position").setDouble(RobotMap.climber.climbArm.getSelectedSensorPosition());
        instance.getEntry("Elevator Button").setBoolean(RobotMap.elevator.elevatorZero.get());
    }
}