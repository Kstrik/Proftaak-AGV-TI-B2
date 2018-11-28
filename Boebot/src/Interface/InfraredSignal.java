package Interface;

import java.util.ArrayList;
import java.util.HashMap;

public class InfraredSignal<T> implements ISignal
{
    private int[] pulsLengths;
    private HashMap<Integer, Command.Commands> commandBindings;

    public InfraredSignal(int[] pulsLengths)
    {
        this.pulsLengths = pulsLengths;
        this.commandBindings = new HashMap<Integer, Command.Commands>();

        initializeCommandBindings();
    }

    private void initializeCommandBindings()
    {
        this.commandBindings.put(144, Command.Commands.GOFORWARD);
        this.commandBindings.put(2192, Command.Commands.GOBACKWARD);
        this.commandBindings.put(3216, Command.Commands.TURNLEFT);
        this.commandBindings.put(1168, Command.Commands.TURNRIGHT);
        //Stop knop
        this.commandBindings.put(2872, Command.Commands.STOP);
        //Slow down knop
        this.commandBindings.put(2960, Command.Commands.GOTOSPEED);
        //Knop 1
        this.commandBindings.put(16, Command.Commands.MAKETRIANGLE);
        //Knop 2
        this.commandBindings.put(2064, Command.Commands.MAKECIRCLE);
        //Knop 3
        this.commandBindings.put(1040, Command.Commands.MAKERECTANGLE);
    }

    public int convertPulsesToInt()
    {
        int number = 0;

        for(int i = 0; i < this.pulsLengths.length; i++)
        {
            if(this.pulsLengths[i] >= 1000)
            {
                number++;
            }

            if(i != this.pulsLengths.length - 1)
            {
                number = number << 1;
            }
        }
        System.out.println(number);
        return number;
    }

    public Command convertToCommand()
    {
        Command.Commands commandType = this.commandBindings.get(convertPulsesToInt());

        if(commandType == null)
        {
            commandType = Command.Commands.NONE;
        }

        return new Command(commandType, null);
    }
}