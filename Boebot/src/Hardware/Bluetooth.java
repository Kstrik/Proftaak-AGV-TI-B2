package Hardware;

import Interface.IConnection;
import TI.BoeBot;
import TI.SerialConnection;

public class Bluetooth implements ICommunicationSensor
{
    private SerialConnection serialConnection;
    private IConnection bluetoothConnection;

    public Bluetooth(int baudRate, IConnection bluetoothConnection)
    {
        this.serialConnection = new SerialConnection(baudRate);
        this.bluetoothConnection = bluetoothConnection;
    }

    public Integer receive()
    {
        if(this.serialConnection.available() > 0)
        {
            int data = this.serialConnection.readByte();

            return data;
        }

        BoeBot.wait(1);

        return 0;
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
        int data = receive();

        if(data != 0)
        {
            this.bluetoothConnection.onSignalReceived(data);
            System.out.println(data);
        }
    }
}