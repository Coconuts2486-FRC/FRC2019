package frc.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.enums.IDs;
import frc.robot.RobotMap;

/**
 * Climber
 */
public class Climber {
    private static Climber instance = null;

    public TalonSRX climbRollers;
    public TalonSRX climbRollersFollower;
    public TalonSRX climbArm;
    public boolean  isNeutral = true;

    //public Solenoid climbPistons;
    
    private Climber() {
        init();
    }

    public static Climber getInstance() {
        if(instance == null)
            instance = new Climber();
        return instance;
    }

    public void init() {
        climbRollers = new TalonSRX(IDs.CLIMB_ROLLERS.getValue());
        climbRollers.setInverted(InvertType.InvertMotorOutput);

        climbArm     = new TalonSRX(IDs.CLIMB_ARM.getValue());
        climbArm.setSensorPhase(true);
        climbArm.configClosedLoopPeakOutput(0, 1.0f);
        climbArm.configClosedloopRamp(0.2);

        //climbPistons = new Solenoid(IDs.PCM.getValue(), IDs.CLIMB_PISTON.getValue());

        climbArm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        climbArm.config_kP(0, 0.35);
        climbArm.config_kI(0, 0);
        climbArm.config_kD(0, 0);
        climbArm.config_kF(0, 0);
    }

    public void enableArm() {
        climbArm.set(ControlMode.PercentOutput, -1.0);
        isNeutral = false;
        //DriverStation.reportWarning("Arm enabled.", false);
    }

    public void disableArm() {
        climbArm.set(ControlMode.PercentOutput, 1.0);
        isNeutral = true;
        //DriverStation.reportWarning("Arm DISABLED.", false);
    }

    public void zeroPower() {
        climbArm.set(ControlMode.PercentOutput, 0.0);
    }

    public void enableWheels() {
        climbRollers.set(ControlMode.PercentOutput, 0.5);
    }

    public void disableWheels() {
        climbRollers.set(ControlMode.PercentOutput, 0);
    }

    public void zeroEncoder() {
        if(RobotMap.elevator.elevatorZero.get() == true) {
            climbArm.setSelectedSensorPosition(0);
        }
    }
}