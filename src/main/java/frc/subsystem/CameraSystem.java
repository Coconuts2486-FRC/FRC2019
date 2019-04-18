package frc.subsystem;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * DEPRECATED: Ommitted due to encoders on the drive
 * Provides a system for managing which camera is currently active.
 */
@Deprecated
public class CameraSystem {
    private static CameraSystem instance = null;
    UsbCamera frontCamera;
    UsbCamera rearCamera;
    VideoSink server;

    boolean isFront;
    
    private CameraSystem() {
        frontCamera = CameraServer.getInstance().startAutomaticCapture(0);
        //rearCamera  = CameraServer.getInstance().startAutomaticCapture(1);
        server = CameraServer.getInstance().getServer();

        server.setSource(frontCamera);
        isFront = true;
    }

    public static CameraSystem getInstance() {
        if(instance == null) {
            instance = new CameraSystem();
        }

        return instance;
    }

    public void setFront() {
        server.setSource(frontCamera);
        isFront = true;
    }

    public void setRear() {
        server.setSource(rearCamera);
        isFront = false;
    }

    public void toggle() {
        if(isFront)
            setRear();
        else
            setFront();
    }

    /**
     * Returns whether the front or rear camera is active.
     * @return True if the front camera is active.
     */
    public boolean getStatus() {
        return isFront;
    }
}