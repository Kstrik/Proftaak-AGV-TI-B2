package Interface;

import java.util.HashMap;

public class BluetoothSignal implements ISignal
{
    private int data;
    private HashMap<Integer, Command.Commands> commandBindings;

    public BluetoothSignal(int data)
    {
        this.data = data;
        this.commandBindings = new HashMap<Integer, Command.Commands>();

        initializeCommandBindings();
    }

    private void initializeCommandBindings()
    {
        this.commandBindings.put(119, Command.Commands.GOFORWARD);
        this.commandBindings.put(115, Command.Commands.GOBACKWARD);
        this.commandBindings.put(97, Command.Commands.TURNLEFT);
        this.commandBindings.put(100, Command.Commands.TURNRIGHT);
        //Stop knop X
        this.commandBindings.put(120, Command.Commands.STOP);
        //Slow down knop C
        this.commandBindings.put(99, Command.Commands.GOTOSPEED);
        //Knop T
        this.commandBindings.put(116, Command.Commands.MAKETRIANGLE);
        //Knop Y
        this.commandBindings.put(121, Command.Commands.MAKECIRCLE);
        //Knop U
        this.commandBindings.put(117, Command.Commands.MAKERECTANGLE);
        //Knop Toggle Line Tracing L
        this.commandBindings.put(108, Command.Commands.TOGGLELINETRACING);
    }

    public Command convertToCommand()
    {
        Command.Commands commandType = this.commandBindings.get(data);

        if(commandType == null)
        {
            commandType = Command.Commands.NONE;
        }

        return new Command(commandType, null);
    }
}