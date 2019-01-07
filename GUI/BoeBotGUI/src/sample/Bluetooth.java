package sample;

import jssc.SerialPort;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

public class Bluetooth {

    private SerialPort serialPort;

    public Bluetooth()
    {
        this.serialPort = new SerialPort("COM4");   //Set to default Serialport: COM4
    }

    public Bluetooth(String serialport)
    {
        this.serialPort = new SerialPort(serialport);
    }


    public boolean initialize()
    {
        try
        {
            this.serialPort.openPort();
            this.serialPort.setParams(this.serialPort.BAUDRATE_115200,
                                      this.serialPort.DATABITS_8,
                                      this.serialPort.STOPBITS_1,
                                      this.serialPort.PARITY_NONE);
            return true;

        }
        catch(SerialPortException e)
        {
            System.out.println(e);
        }
        return false;
    }

    public byte[] readBytes(int amount)
    {
        try
        {
            return this.serialPort.readBytes(amount, 10);
        }
        catch(SerialPortException | SerialPortTimeoutException e)
        {
            System.out.println(e);
        }
        System.out.println("readbytes");
        return new byte[0];
    }

    public void writeString(String data)
    {
        try
        {
            this.serialPort.writeString(data);
        }
        catch(SerialPortException e)
        {
            System.out.println(e);
        }
    }

    public void writeInt(int data)
    {
        try
        {
            this.serialPort.writeInt(data);
        }
        catch(SerialPortException e)
        {
            System.out.println(e);
        }
    }

    public void writeByte(byte data)
    {
        try
        {
            this.serialPort.writeByte(data);
        }
        catch (SerialPortException e)
        {
            System.out.println(e);
        }
    }

    public void writeIntArray(int[] data)
    {
        try
        {
            this.serialPort.writeIntArray(data);
        }
        catch(SerialPortException e)
        {
            System.out.println(e);
        }
    }

    public void writeBytes(byte[] data)
    {
        try
        {
            this.serialPort.writeBytes(data);
        }
        catch (SerialPortException e)
        {
            System.out.println(e);
        }
    }
}
