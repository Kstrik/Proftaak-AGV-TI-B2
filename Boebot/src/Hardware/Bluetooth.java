package Hardware;

import Interface.IConnection;
import TI.BoeBot;
import TI.SerialConnection;
import TI.Timer;

public class Bluetooth implements ICommunicationSensor
{
    private SerialConnection serialConnection;
    private IConnection bluetoothConnection;
    private Timer timer;

    public Bluetooth(int baudRate, IConnection bluetoothConnection)
    {
        this.serialConnection = new SerialConnection(baudRate);
        this.bluetoothConnection = bluetoothConnection;
        this.timer = new Timer(500);
    }

    public Bluetooth(int baudRate)
    {
        this.serialConnection = new SerialConnection(baudRate);
        this.bluetoothConnection = null;
        this.timer = null;
    }

    public Integer receive()
    {
        if(this.serialConnection.available() > 0)
        {
            int data = this.serialConnection.readByte();
            System.out.println(data);
            return data;
        }

        //BoeBot.wait(1);

        return -1;
    }

    public void transmit(Object data)
    {
        if(this.serialConnection.available() > 0)
        {
            int dataByte = (int)data;
            this.serialConnection.writeByte(dataByte);
        }
    }

    public void update()
    {
        if(this.timer.timeout())
        {
            int data = receive();

            if(data != -1)
            {
                this.bluetoothConnection.onSignalReceived(data);
            }
        }
    }
}