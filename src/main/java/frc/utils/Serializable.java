package frc.utils;

/**
 * Provides the default methods necessary for serialization.
 */
public abstract class Serializable {
    public String type;
    /**
     * Serialize the current instance into a string.
     * @return Serialized data.
     */
    public abstract String serialize();
}