package frc.auto.commands.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import frc.robot.RobotMap;

/**
 * ICommandSerializer
 */
public class ICommandSerializer implements JsonSerializer<ICommand> {

    @Override
    public JsonElement serialize(ICommand src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        Command[] annotations = src.getClass().getAnnotationsByType(Command.class);
        if(annotations.length == 0)
        {
            RobotMap.logger.printWarning("Could not serialize a command. No annotations exist.");
        }
        else
        {
            String name = src.getClass().getAnnotationsByType(Command.class)[0].name();
            json.addProperty("Type", name);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            json.add("Data", gson.toJsonTree(src, typeOfSrc));
        }

        return json;
    }
    
}