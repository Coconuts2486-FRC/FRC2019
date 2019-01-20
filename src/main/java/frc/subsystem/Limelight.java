package frc.subsystem;

import java.text.DecimalFormat;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.RobotMap;

/**
 * Limelight
 */
public class Limelight {
    private static Limelight instance = null;

    NetworkTable limelight;
    NetworkTable limelightConfig;

    String sY = "kY";
    String sVelocity = "kVelocity";
    String sAmplitude = "kAmplitude";
    String sPeriod = "kPeriod";

    double kYConstant;
    double kVelocity;
    double kAmplitude;
    double kPeriod;

    double xError;
    double yError;

    private Limelight() {
        NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
        limelight = tableInstance.getTable("limelight");
        limelightConfig = tableInstance.getTable("limelightConfig");

        limelightConfig.getEntry(sY).setDouble(0.1);
        limelightConfig.getEntry(sVelocity).setDouble(0.3);
        limelightConfig.getEntry(sAmplitude).setDouble(5);
        limelightConfig.getEntry(sPeriod).setDouble(0.013);

        RobotMap.logger.printStatus("Limelight initialized.");
    }

    public static Limelight getInstance() {
        if(instance == null)
            instance = new Limelight();

        return instance;
    }
    
    /**
     * Gets the calibrated values for the Limelight offsets.
     * Uses trigonometric equations to calculate the variances.
     * @return An array for the desired left and right speeds. [0] is the left, and [1] is the right.
     */
    public double[] getValue() {
        kYConstant = limelightConfig.getEntry(sY).getDouble(0.05);
        kVelocity  = limelightConfig.getEntry(sVelocity).getDouble(0.05);
        kAmplitude = limelightConfig.getEntry(sAmplitude).getDouble(0.05);
        kPeriod    = limelightConfig.getEntry(sPeriod).getDouble(0.25);

        xError = limelight.getEntry("tx").getDouble(0);
        yError = limelight.getEntry("ty").getDouble(0);

        double aLeft  = kVelocity * (Math.cos(kPeriod * xError) + kAmplitude * Math.sin(kPeriod * xError));
        double aRight = kVelocity * (Math.cos(kPeriod * xError) - kAmplitude * Math.sin(kPeriod * xError));

        double leftOutput  = yError * kYConstant * aLeft;
        double rightOutput = yError * kYConstant * aRight;

        RobotMap.logger.printStatus(String.format("Limelight was calculated. Left: %s; Right: %s", 
            new DecimalFormat("#.####").format(leftOutput), new DecimalFormat("#.####").format(rightOutput)));

        return new double[] { leftOutput, rightOutput };
    }

    public void setLights(boolean state) {
        double value;
        value = state ? 0.0 : 1.0;
        limelightConfig.getEntry("ledMode").setDouble(value);
    }
}