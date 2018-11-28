package Interface;

<<<<<<< HEAD
import java.lang.reflect.Array;
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

    public Commands getCommand()
    {
        return this.command;
    }

    public ArrayList<T> getParameters()
    {
        return this.parameters;
    }

    public enum Commands
    {
        GOTOSPEED,
        TURN,
        TURNDEGREES,
        STOP
=======
public class Command {
    private String cmd;

    public Command(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
>>>>>>> master
    }
}