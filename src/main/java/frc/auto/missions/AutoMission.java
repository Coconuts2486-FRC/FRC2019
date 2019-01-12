package frc.auto.missions;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import frc.auto.commands.Drive;
import frc.auto.commands.utils.ICommand;
import frc.auto.commands.utils.ICommandDeserializer;
import frc.auto.commands.utils.ICommandSerializer;
import frc.opmode.OpMode;
import frc.robot.RobotMap;

public class AutoMission extends OpMode {
    
    @SerializedName("Command List")
    @Expose(serialize = true, deserialize = true)
    public ArrayList<ICommand> commandList = new ArrayList<ICommand>();

    private transient Boolean _hasLooped = false;

    /**
     * Creates a new instance of AutoMission.
     */
    public AutoMission() {
        super();
    }

    /**
     * {@inheritDoc}
     * This will run the commands once.
     */
    @Override
    public void init() {
        for(ICommand i : commandList) {
            i.run();
        }
        _hasLooped = true;
    }

    /**
     * {@inheritDoc}
     * This will run the commands once.
     */
    @Override
    public void loop() {
        if(!_hasLooped)
            init();
    }

    /**
     * Serialize the AutoMission for saving or modifying.
     * Pretty printing is disabled.
     * @return Serialized string of the instance.
     */
    public String serialize() {
        return serialize(false);
    }

    /**
     * Serialize the RobotConfig instance for saving or modifying.
     * @param setPrettyPrinting {@code}true{@code} to enable pretty printing.
     * @return Serialized string of the instance.
     */
    public String serialize(boolean setPrettyPrinting) {
        Gson gson = (setPrettyPrinting) ? new GsonBuilder().registerTypeAdapter(ICommand.class, new ICommandSerializer()).setPrettyPrinting().create() 
            : new GsonBuilder().registerTypeAdapter(ICommand.class, new ICommandSerializer()).create();
        return gson.toJson(this);
    }

    /**
     * Deserialize a JSON string into a valid AutoMission instance.
     * @param s String to deserialize.
     * @return AutoMission instance from JSON data.
     */
    public static AutoMission deserialize(String s) {
        Gson gson = new GsonBuilder().registerTypeAdapter(ICommand.class, new ICommandDeserializer()).create();
        return gson.fromJson(s, AutoMission.class);
    }

    public class Test123 {
        public void testCommand() {
            Drive drive = new Drive(0.5, 0.5, 1000);
            // Sleep sleep = new Sleep(2000);

            Gson gson = new GsonBuilder().registerTypeAdapter(Drive.class, new ICommandSerializer()).setPrettyPrinting().create();
            // Gson gson2 = new GsonBuilder().registerTypeAdapter(Sleep.class, new ICommandSerializer()).setPrettyPrinting().create();
            String json = gson.toJson(drive);
            RobotMap.logger.printDebug(json);
            // String json2 = gson2.toJson(drive);
            // logger.printDebug(json2);

            Gson gsonDeserialized = new GsonBuilder().registerTypeAdapter(ICommand.class, new ICommandDeserializer()).create();
            ICommand driveDeserialized = gsonDeserialized.fromJson(json, ICommand.class);
            driveDeserialized.run();
        }

        public void testMission() {
            Center center = new Center();
            String data = center.serialize(true);
            RobotMap.logger.printDebug(data);

            AutoMission m = AutoMission.deserialize(data);
            m.init();
        }
    }

    // public class AutoMissionDeserializer implements JsonDeserializer<AutoMission> {
    //     @Override
	// 	public AutoMission deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    //         AutoMission mission = new AutoMission();
	// 	}
    // }
}