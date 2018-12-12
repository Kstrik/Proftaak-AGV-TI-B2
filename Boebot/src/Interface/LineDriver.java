package Interface;

import Logic.PathFinder;
import TI.BoeBot;

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
        this.isInControl = false;

        this.driveControl.onCommandReceived(new Command(Command.Commands.STOP, null));
    }

    public void onRightLineDetected()
    {
        ArrayList<Object> parameters = new ArrayList<>();
        System.out.println("Right");
        parameters.add(this.speed);
        this.driveControl.onCommandReceived(new Command(Command.Commands.SETLEFTSPEED, parameters));
        this.driveControl.onCommandReceived(new Command(Command.Commands.STOPRIGHT, null));
    }

    public void onLeftLineDetected()
    {
        ArrayList<Object> parameters = new ArrayList<>();
        System.out.println("Left");
        parameters.add(this.speed);
        this.driveControl.onCommandReceived(new Command(Command.Commands.SETRIGHTPEED, parameters));
        this.driveControl.onCommandReceived(new Command(Command.Commands.STOPLEFT, null));
    }

    public void onMiddleLineDetected()
    {
        ArrayList<Object> parameters = new ArrayList<>();
        System.out.println("Middle");
        parameters.add(this.speed);
        this.driveControl.onCommandReceived(new Command(Command.Commands.SETSPEED, parameters));
    }

    public void onIntersectionDetected()
    {
        //this.isOnIntersection = true;
        //this.driveControl.onCommandReceived(new Command(Command.Commands.STOP, null));

        //BoeBot.wait(5);

        ArrayList<Object> parameters = new ArrayList<>();

        if(this.isFollowingPath)
        {
            switch(this.pathFinder.getNextAction())
            {
                case TURNLEFT:
                {
                    parameters.add(this.speed * -1);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.TURNDEGREES, parameters));
                    break;
                }
                case TURNRIGHT:
                {
                    parameters.add(this.speed);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.TURNDEGREES, parameters));
                    break;
                }
                case GOFORWARD:
                {
                    parameters.add(this.speed);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.SETSPEED, parameters));
                    BoeBot.wait(500);
                    this.isOnIntersection = false;
                    break;
                }
                case FINISHED:
                {
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
        this.lineDetector.update();
    }
}