package Interface;

import java.util.ArrayList;

public class Command<T>
{
    private Commands command;
    private ArrayList<T> parameters;

    public Command(int commandIndex, ArrayList<T> parameters)
    {
        this.command = Commands.values()[commandIndex];
        this.parameters = parameters;
    }

    public Command(Commands command, ArrayList<T> parameters)
    {
        this.command = command;
        this.parameters = parameters;
    }

    public Commands getCommand()
    {
        return this.command;
    }

    public ArrayList<T> getParameters()
    {
        return this.parameters;
    }

    public void setParameters(ArrayList<T> parameters)
    {
        this.parameters = parameters;
    }

    public enum Commands
    {
        GOTOSPEED,
        TURN,
        TURNDEGREES,
        GOFORWARD,
        GOBACKWARD,
        TURNLEFT,
        TURNRIGHT,
        INCREASELEFTSPEED,
        INCREASERIGHTSPEED,
        SETLEFTSPEED,
        SETRIGHTPEED,
        SETSPEED,
        STOPLEFT,
        STOPRIGHT,
        STOP,
        MAKERECTANGLE,
        MAKECIRCLE,
        MAKETRIANGLE,
        PLAYSOUND,
        STOPSOUND,
        CHANGESOUND,
        STARTFLASHCOLOR,
        STOPFLASHCOLOR,
        TOGGLELINETRACING,
        RECEIVEPATH,
        NONE
    }
}