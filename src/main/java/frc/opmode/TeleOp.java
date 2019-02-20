package frc.opmode;

import static frc.robot.RobotMap.*;

import com.google.common.base.Stopwatch;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.enums.ElevatorPositions;
import frc.enums.Shifter;

/**
 * TeleOp
 */
public class TeleOp extends OpMode {

    public TeleOp() {
        super();
    }

    @Override
    public void init() {
        Shuffleboard.selectTab("TeleOp");
        compressor.set(true);
        driveTrain.configPeakOutput(1);
        driveTrain.setShifter(Shifter.LOW);
    }

    double[] speeds = new double[2];
    double sum      = 0;

    @Override
    public void loop() {
        speeds  = driveTrain.getSpeeds();
        sum     = speeds[0] + speeds[1];

        //Limelight control.
        if(driveTrain.joystick1.getRawButton(4))
            limelightControl();
        else   
            operatorControl();

        //if(driveTrain.joystick1.getRawButtonPressed(3))
            //cameraSystem.toggle();
    }

    public void limelightControl() {
        limelight.drive();
    }

    Stopwatch sw = Stopwatch.createUnstarted();

    boolean elevatorHeld = false;
    public void operatorControl() {
        // Driving code.
        //this is one joystick
        //driveTrain.set(-driveTrain.getJoystickY() + driveTrain.getJoystickX(), -driveTrain.getJoystickY() - driveTrain.getJoystickX());
        //this is two joysticks
        driveTrain.set(-driveTrain.getJoystickY(), - driveTrain.getJoystick2Y());
        if (driveTrain.isTriggerPressed())
            driveTrain.setShifter(!driveTrain.getShifterState().getValue());

        // Elevator code.
        boolean elevatorPressed = driveTrain.joystick1.getRawButton(8);
        if(elevatorHeld == false)
            if(elevatorPressed)
                elevator.elevatorToggle();
        elevatorHeld = elevatorPressed ? true : false;

        ElevatorPositions desiredPos = driveTrain.getDesiredElevator();
        if(desiredPos != ElevatorPositions.NEUTRAL)
            elevator.set(desiredPos);

        // Elevator manual override. Positive is up.
        if(driveTrain.secondaryOperator.getRawButton(3))
            elevator.setInnerSpeed(0.55);
        else if(driveTrain.secondaryOperator.getRawButton(7))
            elevator.setInnerSpeed(-0.55);
        else
            elevator.setInnerSpeed(0);
        
        // Positive is in.
        if(driveTrain.joystick1.getRawButton(3) || driveTrain.joystick2.getRawButton(3))
            elevator.intakeRollers();
        else if(driveTrain.joystick1.getRawButton(2) || driveTrain.joystick2.getRawButton(2))
            elevator.outtakeRollers();
        else
            elevator.setRollers(0);

        // Vacuum code.
        boolean vacuumEnable  = driveTrain.joystick1.getRawButton(5);
        boolean vacuumRelease = driveTrain.joystick2.getRawButton(5);
        if(!(vacuumEnable && vacuumRelease)) {
            if(vacuumEnable)
                elevator.enableVacuum(true);
            else
                elevator.enableVacuum(false);
                
            if(vacuumRelease)
                elevator.releaseVacuum(true);
            else
                elevator.releaseVacuum(false);
        }
        
        /** Automatic shifting code. It doesn't work. :( */
        
        // if(Math.abs(driveTrain.left.getSelectedSensorVelocity() + driveTrain.right.getSelectedSensorVelocity()) > 10000) {
        //     driveTrain.setShifter(true);
        // }
        // else {
        //     driveTrain.setShifter(false);
        // }

        // switch(driveTrain.getShifterState()) {
        //     // 20,000 is the max velocity for one side.
        //     case HIGH:
        //         if(sum < 10000 && driveTrain.getShifterState() == Shifter.HIGH)
        //             driveTrain.setShifter(Shifter.LOW);
        //         break;
        //     // 10,000 is the max velocity for one side.
        //     case LOW:
        //         if(sum > 10000 && driveTrain.getShifterState() == Shifter.LOW)
        //             driveTrain.setShifter(Shifter.HIGH);
        //         break;
        // }

        if(Math.abs(sum) > 24000) {
            if(sum < 0 && cameraSystem.getStatus())
                cameraSystem.setRear();
            else if(sum > 0 && !cameraSystem.getStatus())
                cameraSystem.setFront();
        }
    }
}