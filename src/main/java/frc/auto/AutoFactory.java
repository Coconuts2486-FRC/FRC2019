package frc.auto;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

import frc.auto.missions.AutoMission;
import frc.robot.RobotMap;
import frc.utils.FileHandler;

/**
 * Loads the auto modes from text.
 */
public class AutoFactory {
    private static AutoFactory _instance = null;

    private HashMap<String, HashMap<String, AutoMission>> autoMap = null;

    private AutoFactory() {
        RobotMap.logger.printStatus("Initializing the auto missions from file.");
        autoMap = new HashMap<>(12);
        
        String dir = "usb/auto/";
        // String dir = "/U/auto/";
        
        File file = new File(dir);
        String[] directories = file.list(new FilenameFilter() {
          @Override
          public boolean accept(File current, String name) {
            return new File(current, name).isDirectory();
          }
        });

        for(String s : directories) {
            HashMap<String, AutoMission> map = new HashMap<>();

            File   directory = new File(dir + s);
            File[] missions = directory.listFiles(new FilenameFilter(){
            
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".mission");
                }
            });

            for(File f : missions) {
                String missionData = FileHandler.ReadFromFile(f.getAbsolutePath());
                String key = f.getName().substring(0, f.getName().lastIndexOf('.'));
                map.put(key, AutoMission.deserialize(missionData));
                RobotMap.logger.printStatus(String.format("Added %s under %s.", key, s));
            }

            autoMap.put(s, map);
        }
    }

    public static AutoFactory getInstance() {
        if(_instance == null)
            _instance = new AutoFactory();
        return _instance;
    }

    public AutoMission getMission(String fileLocation) {
        String autoMission = FileHandler.ReadFromFile(fileLocation);
        return getMissionFromString(autoMission);
    }

    public AutoMission getMissionFromString(String autoMission) {
        return AutoMission.deserialize(autoMission);
    }
}