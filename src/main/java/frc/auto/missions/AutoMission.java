package frc.auto.missions;

import java.util.ArrayList;

import frc.auto.commands.utils.ICommand;
import frc.opmode.OpMode;

public class AutoMission extends OpMode {
    
    protected ArrayList<ICommand> commandList = new ArrayList<ICommand>();

    /**
     * Creates a new instance of AutoMission.
     */
    public AutoMission() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        for(ICommand i : commandList) {
            i.run();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loop() {

    }
}