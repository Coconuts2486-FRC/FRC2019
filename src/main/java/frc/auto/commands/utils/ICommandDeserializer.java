package frc.auto.commands.utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Set;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.reflections.Reflections;

import frc.auto.commands.Drive;
import frc.auto.commands.Sleep;
import frc.robot.RobotMap;

/**
 * ICommandDeserializer
 */
public class ICommandDeserializer implements JsonDeserializer<ICommand> {

	@Override
	public ICommand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ICommand command;
        JsonObject jObject = json.getAsJsonObject();

        String type = jObject.get("Type").getAsString();
        switch(type) {
            case "Drive":
                command = new Drive();
                break;
            case "Sleep":
                command = new Sleep();
                break;
            default:
                command = new ICommand();
                break;
        }

        RobotMap.logger.printDebug(String.format("Deserialized command of type %s", command.getClass().getTypeName()));

        Type token = new TypeToken<HashMap<String, Double>>(){}.getType();
        Gson gson = new Gson();
        command.parameters = gson.fromJson(jObject.get("Data").getAsJsonObject().get("parameters"), token);
    
        return command;

        // TODO: Broken. Don't uncomment unless its fixed. Temporary solution above.
        // Set<Class<? extends ICommand>> annotatedClasses = new Reflections("frc.auto.commands").getSubTypesOf(ICommand.class);
        // for (Class<? extends ICommand> o : annotatedClasses) {
        //     Command[] annotations = o.getAnnotationsByType(Command.class);
        //     if(annotations.length != 0)
        //     {
        //         if(annotations[0].name() == type)
        //         {
        //             return ICommand.class.cast(o);
        //         }
        //     }       
        // }

        // RobotMap.logger.printError(String.format("Could not deserialize a command. %s in JSON file not found.", type));
        // throw new JsonParseException(String.format("Could not find %s.", type));
    }
}