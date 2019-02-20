package frc.subsystem;

import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.RobotMap;

/**
 * Pigeon
 */
public class Pigeon {
    private static Pigeon instance = null;
    PigeonIMU pigeonIMU = null;

    private Pigeon() {
        pigeonIMU = new PigeonIMU(RobotMap.driveTrain.leftFollower);
        pigeonIMU.setYaw(0);
    }

    public static Pigeon getInstance() {
        if(instance == null) {
            instance = new Pigeon();
        }

        return instance;
    }

    public double getHeading() {
        double[] ypr = new double[3];
        pigeonIMU.getYawPitchRoll(ypr);
        return ypr[0];
    }
}