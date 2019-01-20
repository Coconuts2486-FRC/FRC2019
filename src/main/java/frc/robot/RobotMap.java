package frc.robot;

import frc.debug.Logger;
import frc.subsystem.DriveTrain;
import frc.subsystem.Limelight;

/**
 * Provides an interface for accessing the subsystems in a SAFE manner.
 */
public class RobotMap
{
    public static RobotConfig config;
    public static DriveTrain driveTrain;
    public static Limelight limelight;

    public static Logger logger = Logger.getInstance();
}