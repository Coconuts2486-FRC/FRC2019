package frc.auto.commands.utils;

import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

/**
 * CommandClasses
 */
public class CommandClasses {
    private static CommandClasses instance = null;

    public Set<Class<? extends ICommand>> annotatedClasses;
    public HashMap<String, Class<? extends ICommand>> commands;

    private CommandClasses() {
        commands = new HashMap<>(13, 1);
        annotatedClasses = new Reflections("frc.auto.commands").getSubTypesOf(ICommand.class);
        for (Class<? extends ICommand> o : annotatedClasses) {
          Command c = o.getAnnotation(Command.class);  
          if(c != null)
            commands.put(c.name(), o);
        }
    }

    public static CommandClasses getInstance() {
        if(instance == null) {
            instance = new CommandClasses();
        }
        return instance;
    }
}