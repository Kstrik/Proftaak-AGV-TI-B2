package Interface;

import Hardware.Bluetooth;
import Logic.Path;
import Logic.PathFinder;
import Logic.Vector2D;
import TI.BoeBot;
import TI.Timer;

import java.awt.*;
import java.util.ArrayList;

public class LineDriver implements IContoller
{
    private IContoller driveControl;
    private LineDetector lineDetector;
    private Communication communicaton;
    private NotificationControl notificationControl;

    private int speed;
    public boolean isInControl;
    private boolean isLiningUp;
    private boolean isOnIntersection;

    private PathFinder pathFinder;
    private boolean isFollowingPath;

    private Bluetooth bluetoothModule;

    private Timer timer;

    public LineDriver(IContoller driveControl, int speed, boolean startsOnItersection, PathFinder pathFinder, boolean isFollowingPath, NotificationControl notificationControl)
    {
        this.driveControl = driveControl;
        this.lineDetector = new LineDetector(this);
        this.communicaton = new Communication(this);
        this.notificationControl = notificationControl;

        this.speed = speed;
        this.isInControl = false;
        this.isLiningUp = false;
        this.isOnIntersection = startsOnItersection;

        this.pathFinder = pathFinder;
        this.isFollowingPath = isFollowingPath;

        this.bluetoothModule = new Bluetooth(115200);

        this.timer = new Timer(100);
    }

    public void activateLineTracing()
    {
        if(this.lineDetector.isOnLine())
        {
            this.isInControl = true;
        }
        else
        {
            ArrayList<Object> parameters = new ArrayList<>();
            System.out.println("IsCalled");
            parameters.add(Color.getHSBColor(1.0F, 1.0F, 1.0F));
            //this.driveControl.onCommandReceived(new Command(Command.Commands.STARTFLASHCOLOR, parameters));
            this.notificationControl.excecuteCommand(new Command(Command.Commands.STARTFLASHCOLOR, parameters));
            this.isLiningUp = true;

            excecuteLineupSequence();
        }
    }

    public void deactivateLineTracing()
    {
        System.out.println("Deactivate line tracing");
        this.isInControl = false;

        this.driveControl.onCommandReceived(new Command(Command.Commands.STOP, null));
    }

    public void onRightLineDetected()
    {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(this.speed);
        this.driveControl.onCommandReceived(new Command(Command.Commands.SETLEFTSPEED, parameters));
        this.driveControl.onCommandReceived(new Command(Command.Commands.STOPRIGHT, null));
    }

    public void onLeftLineDetected()
    {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(this.speed);
        this.driveControl.onCommandReceived(new Command(Command.Commands.SETRIGHTPEED, parameters));
        this.driveControl.onCommandReceived(new Command(Command.Commands.STOPLEFT, null));
    }

    public void onMiddleLineDetected()
    {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(this.speed);
        this.driveControl.onCommandReceived(new Command(Command.Commands.SETSPEED, parameters));
    }

    public void onIntersectionDetected()
    {
        //System.out.println("Intersection");
        this.isOnIntersection = true;

        BoeBot.wait(5);

        ArrayList<Object> parameters = new ArrayList<>();

        if(this.isFollowingPath)
        {
            switch(this.pathFinder.getNextAction())
            {
                case TURNLEFT:
                {
                    BoeBot.wait(200);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.STOP, null));
//                    parameters.add(Color.getHSBColor(0.5F, 1.0F, 1.0F));
//                    this.notificationControl.excecuteCommand(new Command(Command.Commands.STARTFLASHCOLOR, parameters));
//                    parameters.clear();
                    parameters.add(90);
                    parameters.add(-50);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.TURNDEGREES, parameters));
                    //System.out.println("TURNLEFT");
                    break;
                }
                case TURNRIGHT:
                {
                    BoeBot.wait(200);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.STOP, null));
//                    parameters.add(Color.getHSBColor(1.0F, 1.0F, 1.0F));
//                    this.notificationControl.excecuteCommand(new Command(Command.Commands.STARTFLASHCOLOR, parameters));
//                    parameters.clear();
                    parameters.add(90);
                    parameters.add(50);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.TURNDEGREES, parameters));
                    //System.out.println("TURNRIGHT");
                    break;
                }
                case GOFORWARD:
                {
//                    parameters.add(Color.getHSBColor(0.32F, 1.0F, 1.0F));
//                    this.notificationControl.excecuteCommand(new Command(Command.Commands.STARTFLASHCOLOR, parameters));
//                    parameters.clear();
                    parameters.add(this.speed);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.SETSPEED, parameters));
                    BoeBot.wait(200);
                    this.isOnIntersection = false;
                    //System.out.println("GO FORWARD");
                    break;
                }
                case FINISHED:
                {
                    parameters.add(Color.getHSBColor(0.32F, 1.0F, 1.0F));
                    this.notificationControl.excecuteCommand(new Command(Command.Commands.STARTFLASHCOLOR, parameters));
                    BoeBot.wait(10);
                    System.out.println("START FLASHING");
                    deactivateLineTracing();
                    //BoeBot.wait(2);
                    int delay = 0;
                    while(delay <= 100)
                    {
                        delay++;
                        BoeBot.wait(20);
                        this.notificationControl.update(null);
                    }
                    System.out.println("END FLASHING");
                    this.notificationControl.excecuteCommand(new Command(Command.Commands.STOPFLASHCOLOR, null));
                    //BoeBot.wait(2);
                    break;
                }
            }
        }
    }

    private void excecuteLineupSequence()
    {
        while(this.isLiningUp)
        {
            this.communicaton.update();
            this.notificationControl.update(null);

            if(this.lineDetector.isOnLine())
            {
                this.isInControl = true;
                break;
            }
        }

        this.notificationControl.excecuteCommand(new Command(Command.Commands.STOPFLASHCOLOR, null));
        //this.driveControl.onCommandReceived(new Command(Command.Commands.STOPFLASHCOLOR, null));
        this.isLiningUp = false;
    }

    public void receivePathSequence()
    {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(Color.getHSBColor(0.5F, 1.0F, 1.0F));
        this.notificationControl.excecuteCommand(new Command(Command.Commands.STARTFLASHCOLOR, parameters));

        boolean pathIsReceived = false;
        boolean isReceiving = false;
        Path path = new Path();

        int lastX = -1;
        int lastY = -1;

        while(!pathIsReceived)
        {
            this.notificationControl.update(null);
            int receivedData = this.bluetoothModule.receive();

            if(receivedData != -1)
            {
                if(receivedData == 254)
                {
                    isReceiving = true;
                }
                if(receivedData == 253)
                {
                    isReceiving = false;
                    pathIsReceived = true;
                    break;
                }

                if(isReceiving == true && receivedData != 254 && receivedData != 253)
                {
                    if(lastX == -1)
                    {
                        lastX = receivedData;
                    }
                    else if(lastY == -1)
                    {
                        lastY = receivedData;
                    }

                    if(lastX != -1 && lastY != -1)
                    {
                        path.addPoint(new Vector2D((float)lastX, (float)lastY));

                        lastX = -1;
                        lastY = -1;
                    }
                }
            }
        }

        this.pathFinder.setPath(path);
        this.pathFinder.reset();

        this.notificationControl.excecuteCommand(new Command(Command.Commands.STOPFLASHCOLOR, null));
    }

    public void onCommandReceived(Command command)
    {
        switch(command.getCommand())
        {
            case TOGGLELINETRACING:
            {
                this.isLiningUp = !this.isLiningUp;
                break;
            }
        }
    }

    public void update()
    {
        if(this.timer.timeout())
        {
            this.lineDetector.update();
        }
    }
}