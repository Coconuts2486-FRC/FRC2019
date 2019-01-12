package frc.auto.missions;

import frc.auto.commands.Drive;
import frc.auto.commands.Sleep;

/**
 * Center
 */
public class Center extends AutoMission {
    public Center() {
        super();
        super.commandList.add(new Drive(0.5, 0.5, 2500));
        super.commandList.add(new Sleep(4000));
    }
}