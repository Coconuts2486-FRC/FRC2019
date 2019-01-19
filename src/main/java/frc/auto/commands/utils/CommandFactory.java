package frc.auto.commands.utils;

import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

/**
 * Provides reflections on all classes with the {@link Command} annotation.
 * Used in the {@link ICommandDeserializer} class to link serialized data with its corresponding class.
 * This is a Singleton class; call {@code}CommandFactory.getInstance(){@code} to retrieve the instance.
 */
public class CommandFactory {
    private static CommandFactory instance = null;

    /**
     * A set of {@link ICommand} classes.
     */
    public Set<Class<? extends ICommand>> annotatedClasses;
    /**
     * Correlates the ICommand class's name annotation with the class.
     * Used in data serialization.
     * Derived from the AnnotatedClasses in {@link CommandFactory}
     */
    public HashMap<String, Class<? extends ICommand>> commands;

    // Creates a new instance of CommandFactory and assigns the annotatedClasses and commands.
    private CommandFactory() {
        commands = new HashMap<>(13, 1);
        annotatedClasses = new Reflections("frc.auto.commands").getSubTypesOf(ICommand.class);
        for (Class<? extends ICommand> o : annotatedClasses) {
          Command c = o.getAnnotation(Command.class);  
          if(c != null)
            commands.put(c.name(), o);
        }
    }

    /**
     * Returns the instance of CommandFactory. If it has not previously been called, it will create
     * a new instance and run the reflections code.
     * @return The instance of CommandFactory.
     */
    public static CommandFactory getInstance() {
        if(instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }
}