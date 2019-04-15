package frc.opmode;

import static frc.robot.RobotMap.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.google.common.base.Stopwatch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.auto.commands.Climb;
import frc.enums.ElevatorPositions;
import frc.enums.Shifter;

/**
 * TeleOp
 */
public class TeleOp extends OpMode {

    DriverStation ds = DriverStation.getInstance();
    Climb climbCommand;

    public TeleOp() {
        super();
    }

    @Override
    public void init() {
        Shuffleboard.selectTab("TeleOp");
        compressor.set(true);
        driveTrain.configPeakOutput(1);
        driveTrain.setShifter(Shifter.LOW);
        elevator.innerStage.setNeutralMode(NeutralMode.Brake);
        elevator.outerStage.setNeutralMode(NeutralMode.Brake);

        climber.disableArm();
        climbCommand = new Climb(20, -10, 0);
    }

    double[] speeds = new double[2];
    double sum = 0;

    @Override
    public void loop() {
        // Limelight control.
        if (driveTrain.leftJoystick.getRawButton(4)) {
            // limelight.setMode(false);
            limelight.setLights(true);
            limelightControl();
        } else {
            limelight.setMode(true);
            limelight.setLights(true);
            operatorControl();
        }
    }

    public void limelightControl() {
        if (elevator.getIRBeam() == true) // Cargo
            isHatch = false;
        else if (elevator.getIRBeam() == false)
            isHatch = true;

        if (ds.isFMSAttached()) {
            // Pipeline 2 is hatches, pipeline 3 is cargo
            if (isHatch == true)
                limelight.setPipeline(2);
            else
                limelight.setPipeline(3);
        } else {
            if (isHatch == true)
                limelight.setPipeline(5);
            else
                limelight.setPipeline(4);
        }

        limelight.drive();
    }

    Stopwatch sw = Stopwatch.createUnstarted();

    boolean elevatorHeld = false;
    boolean isHatch = true;
    boolean climbHeld = false;
    boolean temp = false;
    final double ultrasonicTolerance = 0.32;

    public void operatorControl() {
        /* Driving code. */
        driveTrain.set(-driveTrain.leftJoystick.getY(), -driveTrain.rightJoystick.getY());

        if (driveTrain.leftJoystick.getRawButtonPressed(3) || driveTrain.rightJoystick.getRawButtonPressed(3))
            driveTrain.setShifter(!driveTrain.getShifterState().getValue());

        /* Elevator code. */
        // Toggles the elevator from the down position to the up.
        boolean elevatorPressed = driveTrain.leftJoystick.getRawButton(8);
        if (elevatorHeld == false)
            if (elevatorPressed)
                elevator.elevatorToggle();
        elevatorHeld = elevatorPressed ? true : false;

        if (elevator.getIRBeam() == true) // Cargo
            isHatch = false;
        else if (elevator.getIRBeam() == false)
            isHatch = true;

        if (!driveTrain.secondaryOperator.getRawButton(9)) {
            if (isHatch) {
                // HATCHES
                if (driveTrain.secondaryOperator.getRawButton(4)) // Low
                    elevator.set(ElevatorPositions.HATCH_LOW);
                else if (driveTrain.secondaryOperator.getRawButton(5)) // Medium
                    elevator.set(ElevatorPositions.HATCH_MEDIUM);
                else if (driveTrain.secondaryOperator.getRawButton(6)) // High
                    elevator.set(ElevatorPositions.HATCH_HIGH);
                else
                    elevator.disablePower();
            } else if (!isHatch) {
                // CARGO
                if (driveTrain.secondaryOperator.getRawButton(3))
                    elevator.set(ElevatorPositions.CARGO_SHIP);
                else if (driveTrain.secondaryOperator.getRawButton(4))
                    elevator.set(ElevatorPositions.CARGO_LOW);
                else if (driveTrain.secondaryOperator.getRawButton(5))
                    elevator.set(ElevatorPositions.CARGO_MEDIUM);
                else if (driveTrain.secondaryOperator.getRawButton(6))
                    elevator.set(ElevatorPositions.CARGO_HIGH);
                else
                    elevator.disablePower();
            }
        } else {
            elevator.outerStage.set(ControlMode.Position, 0);

            if (false/** driveTrain.secondaryOperator.getRawButton(3) */) {
                climbCommand.run();
            } else {
                if (driveTrain.secondaryOperator.getRawButton(4))
                    elevator.setInnerSpeed(-0.85);
                else if (driveTrain.secondaryOperator.getRawButton(5))
                    elevator.setInnerSpeed(0.55);
                else
                    elevator.setInnerSpeed(0);

                // boolean climbPressed = driveTrain.secondaryOperator.getRawButton(7);
                // if(climbHeld == false)
                // if(climbPressed)
                // climber.climbPistons.set(!climber.climbPistons.get());
                // climbHeld = climbPressed ? true : false;

                // boolean climbPressed = driveTrain.secondaryOperator.getRawButton(7);
                // if(climbHeld == false)
                // if(climbPressed)
                // if(climber.isNeutral == true) {
                // climber.enableArm();
                // //elevator.set(ElevatorPositions.CLIMB_MAIN);
                // }
                // else {
                // climber.disableArm();
                // elevator.disablePower();
                // }
                // climbHeld = climbPressed ? true : false;

                if (driveTrain.secondaryOperator.getRawButton(6))
                    climber.enableArm();
                else if (driveTrain.secondaryOperator.getRawButton(7))
                    climber.disableArm();
                else
                    climber.zeroPower();

                climber.climbRollers.set(ControlMode.PercentOutput, -driveTrain.getJoystickY());
            }
        }

        // Elevator manual override. Positive is up.
        // if(driveTrain.secondaryOperator.getRawButton(3))
        // elevator.setInnerSpeed(0.55);
        // else if(driveTrain.secondaryOperator.getRawButton(7))
        // elevator.setInnerSpeed(-0.55);
        // else
        // elevator.setInnerSpeed(0);

        // Sets the intake rollers. Positive is in.
        if (driveTrain.leftJoystick.getTrigger())
            elevator.intakeRollers();
        else if (driveTrain.rightJoystick.getTrigger())
            elevator.outtakeRollers();
        else
            elevator.setRollers(0);

        // Vacuum pump code.
        boolean vacuumEnable = driveTrain.leftJoystick.getRawButton(5);
        boolean vacuumRelease = driveTrain.rightJoystick.getRawButton(5);
        if (!(vacuumEnable && vacuumRelease)) {
            if (vacuumEnable)
                elevator.enableVacuum(true);
            else
                elevator.enableVacuum(false);

            if (vacuumRelease)
                elevator.releaseVacuum(true);
            else
                elevator.releaseVacuum(false);
        }

        // if(driveTrain.leftJoystick.getRawButton(5) ||
        // driveTrain.rightJoystick.getRawButton(5))
        // elevator.vacuumPump.set(ControlMode.PercentOutput, 1);
        // else
        // elevator.vacuumPump.set(ControlMode.PercentOutput, 0)
    }
}