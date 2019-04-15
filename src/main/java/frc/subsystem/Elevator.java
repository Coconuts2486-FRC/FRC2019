package frc.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import frc.enums.ElevatorPositions;
import frc.enums.IDs;
import frc.enums.PIDFeedback;
import frc.robot.RobotMap;

/**
 * Handles the elevator and its relevant systems.
 */
public class Elevator extends ISubsystem {
    public TalonSRX innerStage;
    public TalonSRX outerStage;
    private TalonSRX rollers;
    //public TalonSRX vacuumPump;

    public DigitalInput elevatorZero;

    private Solenoid elevator;
    private Solenoid vacuumEnable;
    private Solenoid vacuumRelease;
    //public Solenoid  climbing;

    //private boolean hasZeroed = false;

    public DigitalInput IRBeam;

    private static Elevator instance = null;
    private Elevator() {
        assign();
        init();
    }

    public static Elevator getInstance() {
        if(instance == null)
            instance = new Elevator();
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void assign() {
        innerStage   = new TalonSRX(IDs.ELEVATOR_INNER.getValue());
        outerStage   = new TalonSRX(IDs.ELEVATOR_OUTER.getValue());
        rollers      = new TalonSRX(IDs.ROLLERS.getValue());
        //vacuumPump = new TalonSRX(13);

        elevatorZero = new DigitalInput(IDs.ELEVATOR_DIGITAL.getValue());

        elevator      = new Solenoid(IDs.PCM.getValue(), IDs.ELEVATOR_PISTONS.getValue());
        vacuumEnable  = new Solenoid(IDs.PCM.getValue(), IDs.VACUUM_ENABLE.getValue());
        vacuumRelease = new Solenoid(IDs.PCM.getValue(), IDs.VACUUM_DISABLE.getValue());
        //climbing      = new Solenoid(IDs.PCM.getValue(), 3);

        IRBeam = new DigitalInput(IDs.ELEVATOR_LIGHT.getValue());
    }

    /**
     * Initializes the encoders and PID loops.
     */
    private void init() {
        //innerStage.setInverted(InvertType.InvertMotorOutput);
        innerStage.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        outerStage.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        innerStage.setSensorPhase(false);
        outerStage.setSensorPhase(true);

        outerStage.configClosedLoopPeakOutput(0, 1);
        outerStage.configClosedloopRamp(0.2);

        configPIDF(innerStage, PIDFeedback.INNER_ELEVATOR);
        configPIDF(outerStage, PIDFeedback.OUTER_ELEVATOR);
    }

    /**
     * Zero the encoder's relative sensor position to its absolute. This ensures the elevator
     * initializes relative to where it starts.
     * @param talon Talon instance to zero.
     */
    @Deprecated
    @SuppressWarnings("unused")
    private void zeroEncoder(TalonSRX talon) {
        int absolutePosition = talon.getSensorCollection().getPulseWidthPosition();
		/* mask out overflows, keep bottom 12 bits */
		absolutePosition &= 0xFFF;
		/* set the quadrature (relative) sensor to match absolute */
        talon.setSelectedSensorPosition(absolutePosition, 0, 10);
    }

    /**
     * Zeroes the encoders by running the inner stage downwards until the limit switch is pressed.
     * Once the limit switch is pressed, it will set the encoders of the elevator to zero.
     */
    public void zeroEncoder() {
        //innerStage.set(ControlMode.PercentOutput, 0.15);
        //while(!elevatorZero.get()) ; // Wait until the elevator's sensor is pressed
        
        // Set the encoders to zero.
        if(elevatorZero.get() == true) {
            innerStage.setSelectedSensorPosition(0);
            outerStage.setSelectedSensorPosition(0);
            RobotMap.elevator.innerStage.setNeutralMode(NeutralMode.Brake);
        }

        //innerStage.set(ControlMode.PercentOutput, 0);
    }

    /**
     * Configures the selected Talon's PID parameters in slot 0.
     * Used in closed-loop control mode.
     * @param talon Talon to configure.
     * @param pidFeedback PIDF Parameters.
     */
    private void configPIDF(TalonSRX talon, PIDFeedback pidFeedback) {
        talon.config_kP(0, pidFeedback.getkP());
        talon.config_kI(0, pidFeedback.getkI());
        talon.config_kD(0, pidFeedback.getkD());
        talon.config_kF(0, pidFeedback.getkF());
    }

    /**
     * Set the elevator to the desired position and place the object.
     * First, it raises the elevators to their desired positions.
     * Then, it determines whether it needs to place cargo or the hatch panel.
     * @param pos
     */
    public void set(ElevatorPositions pos) {
        innerStage.set(ControlMode.Position, pos.getInner());
        outerStage.set(ControlMode.Position, pos.getOuter());
        
        // while(!onTarget());

        // switch(pos) {
        //     case CARGO_LOW:
        //     case CARGO_MEDIUM:
        //     case CARGO_HIGH:
        //         // Spit out the ball.
        //         setRollers(-1);
        //         super.sleep(1000);
        //         break;
        //     case HATCH_LOW:
        //     case HATCH_MEDIUM:
        //     case HATCH_HIGH:
        //         // Disable the vacuum.
        //         releaseVacuum(true);
        //         super.sleep(100);
        //         releaseVacuum(false);
        //     case NEUTRAL:
        //         // Do nothing. We are already in neutral.
        //         break;
        // }

        // innerStage.set(ControlMode.Position, ElevatorPositions.NEUTRAL.getInner());
        // outerStage.set(ControlMode.Position, ElevatorPositions.NEUTRAL.getOuter());
        // while(!onTarget());
        // innerStage.set(ControlMode.PercentOutput, 0);
        // outerStage.set(ControlMode.PercentOutput, 0);
    }

    public void disablePower() {
        innerStage.set(ControlMode.PercentOutput, 0);
        outerStage.set(ControlMode.PercentOutput, 0);
    }

    /**
     * Set the rollers on the elevator to a specific speed.
     * @param speed Amount of speed to intake with.
     */
    public void setRollers(double speed) {
        rollers.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Cause the rollers on the elevator to take in a cargo ball.
     */
    public void intakeRollers() {
        setRollers(1);
    }

    /**
     * Cause the rollers on the elevator to spit out a cargo ball.
     */
    public void outtakeRollers() {
        setRollers(-1);
    }

    /**
     * Returns true if both stages are in the correct position.
     */
    public boolean onTarget() {
        double tolerance = 5;
        boolean innerElevatorTarget = Math.abs(innerStage.getClosedLoopError()) < tolerance;
        boolean outerElevatorTarget = Math.abs(outerStage.getClosedLoopError()) < tolerance;
        return (innerElevatorTarget && outerElevatorTarget);
    }

    /**
     * Set the elevators to the forward position.
     */
    public void elevatorForward() {
        elevator.set(false);
    }

    /**
     * Set the elevators to the back position.
     * This is what the robot defaults to.
     */
    public void elevatorBackward() {
        elevator.set(true);
    }

    /**
     * Toggles the current elevator position by applying the NOT operator to the
     * elevator's current position.
     */
    public void elevatorToggle() {
        elevator.set(!elevator.get());
    }

    /**
     * Gets the current position of the inner encoder.
     * @return The scaled position sensor.
     */
    public double getInnerEncoder() {
        return innerStage.getSelectedSensorPosition();
    }

    /**
     * Gets the current position of the outer encoder.
     * @return The scaled position sensor.
     */
    public double getOuterEncoder() {
        return outerStage.getSelectedSensorPosition();
    }

    /**
     * Sets the raw speed of the inner elevator.
     * @param speed Positive values will cause the elevator to raise, while negative
     * causes the elevator to lower.
     */
    public void setInnerSpeed(double speed) {
        innerStage.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Sets the raw speed of the outer elevator.
     * @param speed Positive values will cause the elevator to raise, while negative
     * causes the elevator to lower.
     */
    public void setOuterSpeed(double speed) {
        outerStage.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Enables the vacuum pump to clamp the hatch panels.
     * @param value True to enable the vacuum pump; false to disable.
     */
    public void enableVacuum(boolean value) {
        vacuumEnable.set(value);
    }

    /**
     * Releases the vacuum to toss the hatch panels.
     * @param value True to release the vacuum; false to disable.
     */
    public void releaseVacuum(boolean value) {
        vacuumRelease.set(value);
    }

    public boolean getIRBeam()
    {
        return IRBeam.get();
    }

}