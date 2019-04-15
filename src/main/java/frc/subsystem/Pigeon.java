package frc.subsystem;

import com.ctre.phoenix.sensors.PigeonIMU;

import frc.enums.IDs;
import frc.robot.RobotMap;

/**
 * Pigeon
 */
public class Pigeon {
    private static Pigeon instance = null;
    PigeonIMU pigeonIMU = null;

    double[] ypr;

    private Pigeon() {
        pigeonIMU = new PigeonIMU(IDs.PIGEON.getValue());
        pigeonIMU.setYaw(0);
    }

    public static Pigeon getInstance() {
        if(instance == null) {
            instance = new Pigeon();
        }

        return instance;
    }

    public double getHeading() {
        ypr = new double[3];
        pigeonIMU.getYawPitchRoll(ypr);
        return ypr[0];
    }

    public double getPitch() {
        pigeonIMU.getYawPitchRoll(ypr);
        return ypr[1];
    }

    public double getRoll() {
        pigeonIMU.getYawPitchRoll(ypr);
        return ypr[2];
    }
}