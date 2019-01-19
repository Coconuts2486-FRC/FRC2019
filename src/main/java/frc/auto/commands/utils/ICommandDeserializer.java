package frc.auto.commands.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * ICommandDeserializer
 */
public class ICommandDeserializer implements JsonDeserializer<ICommand> {

	@Override
	public ICommand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        CommandClasses commandClasses = CommandClasses.getInstance();
        
        JsonObject jObject = json.getAsJsonObject();
        String type = jObject.get("Type").getAsString();

        Class<? extends ICommand> clazz = commandClasses.commands.get(type);

        Constructor<?> constructor;
        try {
            // Parameter of getConstructor() are the constructor's parameters.
            constructor = clazz.getConstructor(HashMap.class);

            Type token = new TypeToken<HashMap<String, Double>>(){}.getType();
            Gson gson = new Gson();
            HashMap<String, Double> map = gson.fromJson(jObject.get("Data").getAsJsonObject().get("parameters"), token);

            return (ICommand) constructor.newInstance(map);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;

        // Type t = clazz.getGenericSuperclass();

        // command = (ICommand) t;

        // RobotMap.logger.printDebug(typeOfT.toString());
        // RobotMap.logger.printDebug(type);

        // return command;
    }
}