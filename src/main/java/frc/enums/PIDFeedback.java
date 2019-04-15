package frc.enums;

/**
 * Provides PIDF values for different subsystems.
 */
public enum PIDFeedback {
    INNER_ELEVATOR(0.1, 0, 0, 0), OUTER_ELEVATOR(0.35, 0, 0, 0);

    private final double kP;
    private final double kI;
    private final double kD;
    private final double kF;

    private PIDFeedback(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    /**
     * Get the kP value of the given PIDFeedback enum.
     * @return A double representing the kP.
     */
    public double getkP() {
        return kP;
    }

    /**
     * Get the kI value of the given PIDFeedback enum.
     * @return A double representing the kI.
     */
    public double getkI() {
        return kI;
    }

    /**
     * Get the kD value of the given PIDFeedback enum.
     * @return A double representing the kD.
     */
    public double getkD() {
        return kD;
    }

    /**
     * Get the kF value of the given PIDFeedback enum.
     * @return A double representing the kF.
     */
    public double getkF() {
        return kF;
    }

    /**
     * For the given PIDFeedback enum, returns each of the feedback values: index 0 is kP, index 1 is kI,
     * index 2 is kD, and index 3 is kF.
     * @return Array of doubles representing the feedback values.
     */
    public double[] getValue() {
        return new double[] { kP, kI, kD, kF };
    }
}