package frc.enums;

/**
 * Provides the IDs for CAN devices and joysticks.
 */
public enum IDs {
    // Talon SRX IDs
    LEFT(1), LEFT_FOLLOWER(3), RIGHT(2), RIGHT_FOLLOWER(4),
    ELEVATOR_INNER(10), ELEVATOR_OUTER(11), ROLLERS(12),
    CLIMB_ROLLERS(30), CLIMB_FOLLOWER(16), CLIMB_ARM(33),

    // Pneumatic IDs
    DRIVE_SHIFTER(4), PCM(21), ELEVATOR_PISTONS(5), VACUUM_ENABLE(6),
    VACUUM_DISABLE(7), CLIMB_PISTON(3),

    // Sensor IDs
    ELEVATOR_ULTRASONIC(3), ELEVATOR_DIGITAL(0), ELEVATOR_LIGHT(2), 
    PIGEON(31),

    // Input Device IDs
    LEFT_JOYSTICK(0), RIGHT_JOYSTICK(1), SECONDARY_OPERATOR(2);
    
    private final int value;
    private IDs(int value) {
        this.value = value;
    }
    /**
     * Get the numerical value of Log.
     * @return
     */
    public int getValue() {
        return value;
    }
}