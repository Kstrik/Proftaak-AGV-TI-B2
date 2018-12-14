package Interface;

import Logic.PathFinder;
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
        System.out.println("Deactivate line sensor");
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
        this.isOnIntersection = true;
        this.driveControl.onCommandReceived(new Command(Command.Commands.STOP, null));

        BoeBot.wait(5);
;
        ArrayList<Object> parameters = new ArrayList<>();

        if(this.isFollowingPath)
        {
            switch(this.pathFinder.getNextAction())
            {
                case TURNLEFT:
                {
//                    parameters.add(Color.getHSBColor(0.5F, 1.0F, 1.0F));
//                    this.notificationControl.excecuteCommand(new Command(Command.Commands.STARTFLASHCOLOR, parameters));
//                    parameters.clear();
                    parameters.add(90);
                    parameters.add(-200);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.TURNDEGREES, parameters));
                    System.out.println("TURNLEFT");
                    break;
                }
                case TURNRIGHT:
                {
//                    parameters.add(Color.getHSBColor(1.0F, 1.0F, 1.0F));
//                    this.notificationControl.excecuteCommand(new Command(Command.Commands.STARTFLASHCOLOR, parameters));
//                    parameters.clear();
                    parameters.add(90);
                    parameters.add(200);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.TURNDEGREES, parameters));
                    System.out.println("TURNRIGHT");
                    break;
                }
                case GOFORWARD:
                {
//                    parameters.add(Color.getHSBColor(0.32F, 1.0F, 1.0F));
//                    this.notificationControl.excecuteCommand(new Command(Command.Commands.STARTFLASHCOLOR, parameters));
//                    parameters.clear();
                    parameters.add(this.speed);
                    this.driveControl.onCommandReceived(new Command(Command.Commands.SETSPEED, parameters));
                    BoeBot.wait(500);
                    this.isOnIntersection = false;
                    System.out.println("GO FORWARD");
                    break;
                }
                case FINISHED:
                {
                    parameters.add(Color.getHSBColor(0.32F, 1.0F, 1.0F));
                    this.notificationControl.excecuteCommand(new Command(Command.Commands.STARTFLASHCOLOR, parameters));
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
        if(this.timer.timeout())
        {
            this.lineDetector.update();
        }
    }
}