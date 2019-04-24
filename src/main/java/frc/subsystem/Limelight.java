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

    double kVelocity;
    double kAmplitude;
    double kPeriod;

    double xError;
    double yError;
    double area;

    double m;
    double b;

    private Limelight() {
        NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
        limelight = tableInstance.getTable("limelight");
        limelightConfig = tableInstance.getTable("limelightConfig");

        limelightConfig.getEntry(sVelocity).setDouble(0.35);
        limelightConfig.getEntry(sAmplitude).setDouble(1.75);
        limelightConfig.getEntry(sPeriod).setDouble(0.2);

        // x: stopping point, y: output speed
        double[] point1 = new double[] { 14, 0.2 };
        double[] point2 = new double[] { 1.2, 1 };

        // Calculate slope.
        m = (point2[1] - point1[1])/(point2[0] - point1[0]);
        b = (point1[1]) - (m * point1[0]);

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
        kVelocity  = limelightConfig.getEntry(sVelocity).getDouble(0.05);
        kAmplitude = limelightConfig.getEntry(sAmplitude).getDouble(0.05);
        kPeriod    = limelightConfig.getEntry(sPeriod).getDouble(0.25);

        xError = limelight.getEntry("tx").getDouble(0);
        yError = limelight.getEntry("ty").getDouble(0);
        area   = limelight.getEntry("ta").getDouble(0);

        double aLeft  = kVelocity * (Math.cos(kPeriod * xError) + kAmplitude * Math.sin(kPeriod * xError));
        double aRight = kVelocity * (Math.cos(kPeriod * xError) - kAmplitude * Math.sin(kPeriod * xError));

        double result = (m * area + b);

        double leftOutput  = result * aLeft;
        double rightOutput = result * aRight;

        // RobotMap.logger.printStatus(String.format("Limelight was calculated. Left: %s; Right: %s", 
        //     new DecimalFormat("#.####").format(leftOutput), new DecimalFormat("#.####").format(rightOutput)));

        return new double[] { leftOutput, rightOutput };
    }

    /**
     * Drive the robot to the vision target.
     */
    public void drive() {
        if(getLight() == false)
            setLights(true);

        area   = limelight.getEntry("ta").getDouble(0);
        if(area == 0)
            return;
        
        double[] speeds = getValue();
        RobotMap.driveTrain.set(speeds[0], speeds[1]);
    }

    /**
     * Sets the current state of the LEDs to be either on or off.
     * @param state True if on.
     */
    public void setLights(boolean state) {
        double value;
        value = state ? 0.0 : 1.0;
        limelight.getEntry("ledMode").setDouble(value); // Do nothing now. It doesn't work.
    }

    /**
     * Gets the current state of the LEDs.
     * @return True if on.
     */
    public boolean getLight() {
        boolean value;
        value = limelight.getEntry("ledMode").getDouble(0) == 0 ? true : false;
        return value;
    }

    /**
     * Sets whether the robot should be in drive mode or targetting mode.
     */
    public void setMode(boolean isDrive) {
        if(isDrive)
            limelight.getEntry("pipeline").setDouble(1);
        else
            limelight.getEntry("pipeline").setDouble(0);
    }

    public void setPipeline(int pipeline) {
        limelight.getEntry("pipeline").setNumber(pipeline);
    }
}