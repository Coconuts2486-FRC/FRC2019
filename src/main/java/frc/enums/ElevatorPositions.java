package frc.enums;

/**
 * The main elevator has two values: inner elevator and outer elevator.
 * The climbing elevator has one value: inner elevator. The second value is unused.
 */
public enum ElevatorPositions {
    CARGO_LOW(-9500, 0), CARGO_MEDIUM(-900, 9000), CARGO_HIGH(150, 23500),
    HATCH_LOW(-22014, 0), HATCH_MEDIUM(-4500, 0), HATCH_HIGH(-316, 15302),
    CARGO_SHIP(-2000, 0),
    CLIMB_NEUTRAL(-10, 0), CLIMB_ENABLED(-895668, 0), CLIMB_MAIN(-24000, 0),
    NEUTRAL(-22014, 0);

    private final int innerEncoderPos;
    private final int outerEncoderPos;

    private ElevatorPositions(int innerEncoderPos, int outerEncoderPos) {
        this.innerEncoderPos = innerEncoderPos;
        this.outerEncoderPos = outerEncoderPos;
    }

    public int getInner() {
        return innerEncoderPos;
    }

    public int getOuter() {
        return outerEncoderPos;
    }
}