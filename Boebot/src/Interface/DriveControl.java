package Interface;

import Hardware.ISensor;
import Hardware.InfraredSensor;

import java.util.ArrayList;

public class DriveControl implements IContoller, IUpdateable
{
    private Communication communication;
    private EngineControl engineControl;
    private CollisionDetector collisionDetector;
    private NotificationControl notificationControl;

    public DriveControl()
    {
        this.communication = new Communication(this);
        this.engineControl = new EngineControl();
        this.collisionDetector = new CollisionDetector(this);
        this.notificationControl = new NotificationControl();
    }

    public void onCommandReceived(Command command)
    {
        switch(command.getCommand())
        {
            case GOFORWARD:
            case GOBACKWARD:
            case GOTOSPEED:
            case TURNLEFT:
            case TURNRIGHT:
            case TURN:
            case TURNDEGREES:
            case STOP:
            {
                this.engineControl.update(command);
                break;
            }
            case PLAYSOUND:
            case STOPSOUND:
            case CHANGESOUND:
            {
                this.notificationControl.update(command);
                break;
            }
        }
    }

    public void update()
    {
        this.collisionDetector.update();

        if(!this.collisionDetector.collisionDetected())
        {
            this.communication.update();
            this.engineControl.update(null);
        }
        else
        {
            Command command = new Command(Command.Commands.STOP, null);
            this.engineControl.update(command);
        }
    }
}