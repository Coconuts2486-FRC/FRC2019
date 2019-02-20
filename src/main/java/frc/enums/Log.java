package frc.enums;

import frc.utils.Logger;

/**
 * Defines the four debugging levels: debug, status, warning, and error.
 * @see Logger
 */
public enum Log {
    DEBUG(0), STATUS(1), WARNING(2), ERROR(3);

    private final int value;
    private Log(int value) {
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