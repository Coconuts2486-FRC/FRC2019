package frc.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import frc.enums.ElevatorPositions;
import frc.enums.IDs;
import frc.enums.Shifter;
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

    public Joystick joystick1 = null;
    public Joystick joystick2 = null;
    public Joystick secondaryOperator = null;

    private DriveTrain() {
        assign();
    }

    public static DriveTrain getInstance() {
        if(instance == null) {
            instance = new DriveTrain();
        }

        return instance;
    }

    private void assign() {
        left  = new TalonSRX(IDs.LEFT.getValue());
        right = new TalonSRX(IDs.RIGHT.getValue());
        leftFollower  = new TalonSRX(IDs.LEFT_FOLLOWER.getValue());
        rightFollower = new TalonSRX(IDs.RIGHT_FOLLOWER.getValue());

        driveShifters = new Solenoid(IDs.PCM.getValue(), IDs.DRIVE_SHIFTER.getValue());

        joystick1 = new Joystick(IDs.LEFT_JOYSTICK.getValue());
        joystick2 = new Joystick(IDs.RIGHT_JOYSTICK.getValue());
        secondaryOperator = new Joystick(IDs.SECONDARY_OPERATOR.getValue());
    }

    @SuppressWarnings("unused")
    private void assignKeys() {
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

        if(RobotMap.config.pneumaticIDs.containsKey(keys[4]) && RobotMap.config.pneumaticIDs.containsKey(keys[5]))
            driveShifters = new Solenoid(RobotMap.config.pneumaticIDs.get(keys[5]), RobotMap.config.pneumaticIDs.get(keys[4]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[4]));
            
        if(RobotMap.config.inputDeviceIDs.containsKey(keys[6]))
            joystick1 = new Joystick(RobotMap.config.inputDeviceIDs.get(keys[6]));
        else
            RobotMap.logger.printError(String.format("Key %s could not be found.", keys[6]));
    }

    public void init() {
        // Set the left and right followers based on their master IDs.
        leftFollower.set(ControlMode.Follower, left.getDeviceID());
        rightFollower.set(ControlMode.Follower, right.getDeviceID());
        // Invert the motor outputs to set forward in the right direction.
        left.setInverted(InvertType.InvertMotorOutput);
        leftFollower.setInverted(InvertType.FollowMaster);
        right.setInverted(InvertType.None);
        rightFollower.setInverted(InvertType.FollowMaster);

        left.setNeutralMode(NeutralMode.Brake);
        leftFollower.setNeutralMode(NeutralMode.Brake);
        right.setNeutralMode(NeutralMode.Brake);
        rightFollower.setNeutralMode(NeutralMode.Brake);

        initMotionProfiling();
        zeroSensors();
    }

    public void zeroSensors() {
        // int absolutePositionLeft = RobotMap.driveTrain.left.getSensorCollection().getPulseWidthPosition();
		/* mask out overflows, keep bottom 12 bits */
		// absolutePositionLeft &= 0xFFF;
		/* set the quadrature (relative) sensor to match absolute */
        RobotMap.driveTrain.left.setSelectedSensorPosition(0, 0, 10);

        // int absolutePositionLeft = RobotMap.driveTrain.left.getSensorCollection().getPulseWidthPosition();
		/* mask out overflows, keep bottom 12 bits */
		// absolutePositionLeft &= 0xFFF;
		/* set the quadrature (relative) sensor to match absolute */
        RobotMap.driveTrain.right.setSelectedSensorPosition(0, 0, 10);
    }

    private void initMotionProfiling() {
        RobotMap.driveTrain.left. configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        RobotMap.driveTrain.right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        RobotMap.driveTrain.left. setSensorPhase(false);
        RobotMap.driveTrain.right.setSensorPhase(false);

        //RobotMap.driveTrain.left. configAllowableClosedloopError(0, 20);
        //RobotMap.driveTrain.right.configAllowableClosedloopError(0, 20);

        // double kP = 0.25/4096;
        // double kI = kP/100;
        // double kD = kP * 10;
        // double kF = 0.171;

        double kP = 0.1;
        double kI = 0;
        double kD = 0;
        double kF = 0;

        setPID(left,  kP, kI, kD, kF);
        setPID(right, kP, kI, kD, kF);
    }

    private void setPID(TalonSRX talon, double kP, double kI, double kD, double kF) {
        talon.config_kP(0, kP);
        talon.config_kI(0, kI);
        talon.config_kD(0, kD);
        talon.config_kF(0, kF);
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

    public void setShifter(Shifter value) {
        driveShifters.set(value.getValue());
    }

    public void setShifter(boolean value) {
        driveShifters.set(value);
    }

    public Shifter getShifterState() {
        return Shifter.valueOf(driveShifters.get());
    }

    /**
     * Gets the speeds from the encoders attached to the drivetrain.
     * @return An array of speeds. Index of 0 is the left, and 1 is the right.
     */
    public double[] getSpeeds() {
        return new double[] { left.getSelectedSensorVelocity(), right.getSelectedSensorVelocity() };
    }

    public double getJoystickX() {
        return joystick1.getX();
    }

    public double getJoystickY() {
        return
        joystick1.getY();
    }

    public double getJoystick2X()
    {
        return
        joystick2.getX();
    }

    public double getJoystick2Y()
    {
        return
        joystick2.getY();
    }

    public boolean isTriggerPressed() {
        return
        joystick1.getTriggerPressed();
    }

    public void configPeakOutput(double maxSpeed) {
        left.configPeakOutputForward(maxSpeed);
        right.configPeakOutputForward(maxSpeed);

        left.configPeakOutputReverse(-maxSpeed);
        right.configPeakOutputReverse(-maxSpeed);
    }

    public ElevatorPositions getDesiredElevator() {
        int low = 4;
        int med = 5;
        int high = 6;

        if(secondaryOperator.getRawButtonPressed(low))
            return ElevatorPositions.CARGO_LOW;
        else if(secondaryOperator.getRawButtonPressed(med))
            return ElevatorPositions.CARGO_MEDIUM;
        else if(secondaryOperator.getRawButtonPressed(high))
            return ElevatorPositions.CARGO_HIGH;
        else
            return ElevatorPositions.NEUTRAL;
    }
}