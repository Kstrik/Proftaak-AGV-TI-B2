package Hardware;

import Interface.EnumMovement;
import Interface.IConnection;
import TI.BoeBot;
import TI.Timer;

public class InfraredSensor implements ICommunicationSensor
{
    private int pin;
    private IConnection infraredConnection;

    public InfraredSensor(int pin, IConnection infraredConnection)
    {
        this.pin = pin;
        this.infraredConnection = infraredConnection;
    }

    public int[] receive()
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

    public void transmit(Object data)
    {
        System.out.println("Infrared signal could not be send. No transmitter available!");
    }

    public void update()
    {
        int[] pulsLengths = receive();

        if(pulsLengths != null)
        {
            this.infraredConnection.onSignalReceived(pulsLengths);
        }
    }
}