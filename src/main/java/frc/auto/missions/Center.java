package frc.auto.missions;

import frc.auto.commands.Drive;

/**
 * Center
 */
public class Center extends AutoMission {
    public Center() {
        super();
        super.commandList.add(new Drive(0.5, 0.5, 2500));
    }
}