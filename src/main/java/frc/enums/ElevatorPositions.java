package frc.enums;

/**
 * ElevatorPositions
 */
public enum ElevatorPositions {
    CARGO_LOW(15837, 3382), CARGO_MEDIUM(4500, -3554), CARGO_HIGH(26331, -19072),
    HATCH_LOW(0, 0), HATCH_MEDIUM(0, 0), HATCH_HIGH(0, 0),
    NEUTRAL(704, 3378);

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