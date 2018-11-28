package Hardware;

import Interface.EnumMovement;
import Interface.IConnection;
import TI.BoeBot;
import TI.Timer;

import java.util.Arrays;

public class InfraredSensor implements ISensor
{
    private int pin;
    private IConnection infraredConnection;

    public InfraredSensor(int pin, IConnection infraredConnection)
    {
        this.pin = pin;
        this.infraredConnection = infraredConnection;
    }

    public int[] getInput()
    {
        int pulseLen = BoeBot.pulseIn(this.pin, false, 6000);
        int lenghts[] = new int[12];

        if(pulseLen > 2000)
        {
            for(int i = 0; i < 12; i++)
            {
                lenghts[i] = BoeBot.pulseIn(this.pin, false, 20000);
            }

            BoeBot.wait(1);

            return lenghts;
        }
        else
        {
            return null;
        }
    }

    public void update()
    {
        int[] pulsLengths = getInput();

        if(pulsLengths != null)
        {
            this.infraredConnection.onSignalReceived(pulsLengths);
        }
    }
}