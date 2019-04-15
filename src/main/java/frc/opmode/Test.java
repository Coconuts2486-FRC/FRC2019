package frc.opmode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import static frc.robot.RobotMap.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * Test
 */
public class Test extends OpMode {
    Joystick gamepad;

    public Test() {
        super();
    }

    @Override
    public void init() {
        Shuffleboard.selectTab("Test");
        gamepad = new Joystick(4);
        elevator.innerStage.setNeutralMode(NeutralMode.Brake);
        //elevator.elevatorBackward();
    }

    boolean isHatch = false;

    @Override
    public void loop() {
        // Hatch positions
        // if (elevator.getIRBeam() == true) { // Cargo
        //     isHatch = false;
        // }
        // else if (elevator.getIRBeam() == false) {
        //     isHatch = true;
        // }

        // if (isHatch == true) {
        //     // HATCHES
        //     if (gamepad.getRawButton(1)) // Low
        //         elevator.set(ElevatorPositions.HATCH_LOW);
        //     else if (gamepad.getRawButton(2) || gamepad.getRawButton(3)) // Medium
        //         elevator.set(ElevatorPositions.HATCH_MEDIUM);
        //     else if (gamepad.getRawButton(4)) // High
        //         elevator.set(ElevatorPositions.HATCH_HIGH);
        //     else
        //         elevator.disablePower();
        // }
        // else if (isHatch == false) {
        //     // CARGO
        //     if (gamepad.getRawButton(1))
        //         elevator.set(ElevatorPositions.CARGO_LOW);
        //     else if (gamepad.getRawButton(2) || gamepad.getRawButton(3))
        //         elevator.set(ElevatorPositions.CARGO_MEDIUM);
        //     else if (gamepad.getRawButton(4))
        //         elevator.set(ElevatorPositions.CARGO_HIGH);
        //     else
        //         elevator.disablePower();
        // }

        // elevator.setRollers(gamepad.getRawAxis(2) - gamepad.getRawAxis(3));

        //RobotMap.limelight.drive();
    }

}