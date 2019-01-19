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
 * Dictates how ICommand subclasses are deserialized from JSON.
 */
public class ICommandDeserializer implements JsonDeserializer<ICommand> {

	@Override
	public ICommand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Get the list of classes that inherit from ICommand and their respective annotations.
        CommandFactory commandFactory = CommandFactory.getInstance();
        
        // Get the JSON element and its Type. The type comes from the annotation!
        JsonObject jObject = json.getAsJsonObject();
        String type = jObject.get("Type").getAsString();

        // Get the appropriate class with the given type from the command factory.
        // For example, if type is "Drive", it will get the Class representation of "Drive.class".
        Class<? extends ICommand> clazz = commandFactory.commands.get(type);

        // Creates a new Constructor. This part is magic.
        Constructor<?> constructor;
        try {
            // Parameter of getConstructor() are the constructor's parameters.
            constructor = clazz.getConstructor(HashMap.class);

            // Creates a type token for what a HashMap is for casting the serialized array.
            Type token = new TypeToken<HashMap<String, Double>>(){
                private static final long serialVersionUID = 3909772501481775418L;
            }.getType();
            Gson gson = new Gson();
            HashMap<String, Double> map = gson.fromJson(jObject.get("Data").getAsJsonObject().get("parameters"), token);

            return (ICommand) constructor.newInstance(map);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}