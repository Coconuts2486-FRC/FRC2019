package frc.robot;

import java.util.HashMap;
import java.util.Set;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import frc.utils.Serializable;

/**
 * Provides the configuration details of the robot, such as ports, PID values, and speeds.
 * Intended to be serialized, providing easily customizable settings.
 */

public class RobotConfig extends Serializable {

    /** Place config data here with @SerializedName tag.
     *  "Name" is not deserialized because it is merely for distinguishing serialized files.
     */

    @SerializedName("Type")
    @Expose(serialize = true, deserialize = false)
    private String name = "RobotConfig";
    @Expose(serialize = true, deserialize = false)
    private float version = 1.0f; // Used to distinguish file versions. See gson's versioning utility for more.

    @SerializedName("Motor Controller IDs")
    public HashMap<String, Byte> motorControllerIDs = new HashMap<>(13, 1); // 13 motor controllers, double at 100% load factor
    @SerializedName("Pneumatic IDs")
    public HashMap<String, Byte> pneumaticIDs = new HashMap<>(5, 1); // 5 pneumatic devices, double at 100% load factor
    @SerializedName("Input Device IDs")
    public HashMap<String, Byte> inputDeviceIDs = new HashMap<>(4, 1); // 4 input devices, double at 100% load factor
    /** Methods for access. */ 

    /**
     * Returns the keys of the HashMaps.
     */
    @Override
    public String toString() {
        Set<String> motorKeys = motorControllerIDs.keySet();
        Set<String> pneumaticKeys = pneumaticIDs.keySet();
        Set<String> inputKeys = inputDeviceIDs.keySet();

        StringBuilder sb = new StringBuilder();

        sb.append("Motor Keys: ");
        for(String i : motorKeys)
        {
            sb.append(i);
            sb.append(", ");
        }

        sb.append("Pneumatic Keys: ");
        for(String i : pneumaticKeys)
        {
            sb.append(i);
            sb.append(", ");
        }

        sb.append("Input Device Keys: ");
        for(String i : inputKeys)
        {
            sb.append(i);
            sb.append(", ");
        }

        return sb.toString();
    }

    /**
     * Generates a sample robot config file.
     * Useful for serialization.
     * @return Pre-configured robot map.
     */
    public static RobotConfig generate()
    {
        RobotConfig config = new RobotConfig();

        // Generate the motor keys.
        for(byte i = 0; i < 13; i++)
        {
            StringBuilder sb = new StringBuilder("motor");
            sb.append(i);

            config.motorControllerIDs.put(sb.toString(), i);
        }

        // Generate the pneumatic keys.
        for(byte i = 0; i < 5; i++)
        {
            StringBuilder sb = new StringBuilder("pneumatic");
            sb.append(i);

            config.pneumaticIDs.put(sb.toString(), i);
        }

        for(byte i = 0; i < 4; i++)
        {
            StringBuilder sb = new StringBuilder("input");
            sb.append(i);

            config.inputDeviceIDs.put(sb.toString(), i);
        }

        return config;
    }

    /**
     * Serialize the RobotConfig instance for saving or modifying.
     * Pretty printing is disabled.
     * @return Serialized string of the instance.
     */
    @Override
    public String serialize() {
        return serialize(false);
    }

    /**
     * Serialize the RobotConfig instance for saving or modifying.
     * @param setPrettyPrinting {@code}true{@code} to enable pretty printing.
     * @return Serialized string of the instance.
     */
    public String serialize(boolean setPrettyPrinting) {
        Gson gson = (setPrettyPrinting) ? new GsonBuilder().setPrettyPrinting().create() : new Gson();
        return gson.toJson(this);
    }

    /**
     * Deserialize a JSON string into a valid RobotConfig instance.
     * @param s String to deserialize.
     * @return RobotConfig instance from JSON data.
     */
    public static RobotConfig deserialize(String s) {
        RobotConfig config = new RobotConfig();

        Type token = new TypeToken<HashMap<String, Byte>>(){}.getType();
        JsonObject o = new JsonParser().parse(s).getAsJsonObject();

        Gson gson = new Gson();
        config.motorControllerIDs = gson.fromJson(o.get("Motor Controller IDs"), token);
        config.pneumaticIDs = gson.fromJson(o.get("Pneumatic IDs"), token);
        config.inputDeviceIDs = gson.fromJson(o.get("Input Device IDs"), token);

        return config;
    }
}