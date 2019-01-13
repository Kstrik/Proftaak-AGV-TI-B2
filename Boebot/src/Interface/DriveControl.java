package Interface;

import Hardware.ISensor;
import Hardware.InfraredSensor;
import Logic.Grid;
import Logic.Path;
import Logic.PathFinder;
import Logic.Vector2D;

import java.util.ArrayList;

public class DriveControl implements IContoller
{
    private Communication communication;
    private EngineControl engineControl;
    private LineDriver lineDriver;
    private CollisionDetector collisionDetector;
    private NotificationControl notificationControl;
    private Command lastCommand;

    public DriveControl()
    {
        Path path = new Path();
//        path.addPoint(new Vector2D(0, 0));
//        path.addPoint(new Vector2D(0, 1));
//        path.addPoint(new Vector2D(0, 2));
//        path.addPoint(new Vector2D(1, 2));
//        path.addPoint(new Vector2D(1, 3));

        path.addPoint(new Vector2D(0, 0));
        path.addPoint(new Vector2D(0, 1));
        path.addPoint(new Vector2D(1, 1));
        path.addPoint(new Vector2D(2, 1));
        path.addPoint(new Vector2D(2, 0));
//        path.addPoint(new Vector2D(3, 0));
//        path.addPoint(new Vector2D(3, 1));
//        path.addPoint(new Vector2D(3, 2));

//        path.addPoint(new Vector2D(0, 0));
//        path.addPoint(new Vector2D(0, 1));
//        path.addPoint(new Vector2D(1, 2));
//        path.addPoint(new Vector2D(0, 2));

//        path.addPoint(new Vector2D(0, 0));
////        path.addPoint(new Vector2D(0, 1));
//        path.addPoint(new Vector2D(0, 2));
//        path.addPoint(new Vector2D(1, 2));

//        path.addPoint(new Vector2D(0, 0));
//        path.addPoint(new Vector2D(1, 1));
//        path.addPoint(new Vector2D(1, 2));

        this.communication = new Communication(this);
        this.engineControl = new EngineControl();
        this.collisionDetector = new CollisionDetector(this);
        this.notificationControl = new NotificationControl();
        this.lineDriver = new LineDriver(this, 50, false, new PathFinder(Vector2D.Up(), new Grid(10, 10), path), true, this.notificationControl);
        this.lastCommand = new Command(Command.Commands.NONE, null);
    }

    public void onCommandReceived(Command command)
    {
        this.lastCommand = command;

        switch(command.getCommand())
        {
            case GOFORWARD:
            case GOBACKWARD:
            case GOTOSPEED:
            case TURNLEFT:
            case TURNRIGHT:
            case TURN:
            case TURNDEGREES:
            case MAKETRIANGLE:
            case MAKECIRCLE:
            case MAKERECTANGLE:
            case SETLEFTSPEED:
            case SETRIGHTPEED:
            case SETSPEED:
            case STOPLEFT:
            case STOPRIGHT:
            case STOP:
            {
                this.engineControl.update(command);
                break;
            }
            case PLAYSOUND:
            case STOPSOUND:
            case CHANGESOUND:
            case STARTFLASHCOLOR:
            case STOPFLASHCOLOR:
            {
                this.notificationControl.update(command);
                break;
            }
            case TOGGLELINETRACING:
            {
                if(!this.lineDriver.isInControl)
                {
                    this.lineDriver.activateLineTracing();
                }
                else
                {
                    this.lineDriver.deactivateLineTracing();
                }
                break;
            }
            case RECEIVEPATH:
            {
                this.lineDriver.receivePathSequence();
                break;
            }
        }
    }

    public void update()
    {
        //this.communication.transmitData(10, Communication.CommunicationTypes.BLUETOOTH);

        this.collisionDetector.update();

        if(!this.collisionDetector.collisionDetected())
        {
            this.communication.update();
            if(this.lineDriver.isInControl)
            {
                this.lineDriver.update();
            }
            else
            {
                this.engineControl.update(null);
            }
        }
//        else
//        {
//            //System.out.println(this.lastCommand.getCommand().toString());
//            if(this.lastCommand.getCommand() == Command.Commands.GOBACKWARD)
//            {
//                this.engineControl.update(this.lastCommand);
//            }
////            if(this.lastCommand.getCommand() == Command.Commands.GOBACKWARD && this.engineControl.isStationairy())
////            {
////
//////            }
//////            if(this.lastCommand.getCommand() != Command.Commands.GOBACKWARD && !this.engineControl.isStationairy())
//////            {
////                Command command = new Command(Command.Commands.STOP, null);
////                this.engineControl.update(command);
////            }
//        }
    }

    public Command.Commands getLastCommand()
    {
        return this.lastCommand.getCommand();
    }
}