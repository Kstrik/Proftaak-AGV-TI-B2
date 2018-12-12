package Interface;

import Hardware.Bluetooth;
import Hardware.ICommunicationSensor;
import Hardware.ISensor;

import java.util.ArrayList;

public class BluetoothConnection implements IConnection
{
    private IContoller observer;
    private ICommunicationSensor bluetooth;

    public BluetoothConnection(IContoller observer)
    {
        this.observer = observer;
        this.bluetooth = new Bluetooth(115200, this);
    }

    public void sendSignal(Object signal)
    {
        this.bluetooth.transmit(signal);
    }

    public void onSignalReceived(Object signal)
    {
        BluetoothSignal bluetoothSignal = new BluetoothSignal((int)signal);
        Command command = bluetoothSignal.convertToCommand();
        ArrayList<Object> parameters = new ArrayList<>();

        switch(command.getCommand())
        {
            case GOTOSPEED:
            {
                parameters.add(0);
                System.out.println("Stop slowly");
                break;
            }
            case GOFORWARD:
            {
                parameters.add(100);
                System.out.println("Go forward");
                break;
            }
            case GOBACKWARD:
            {
                parameters.add(-100);
                System.out.println("Go backward");
                break;
            }
            case TURNLEFT:
            {
                parameters.add(-100);
                System.out.println("Turn left");
                break;
            }
            case TURNRIGHT:
            {
                parameters.add(100);
                System.out.println("Turn right");
                break;
            }
            case STOP: {
                parameters = null;
                System.out.println("Stop");
                break;
            }
            case MAKETRIANGLE:
            {
                parameters = null;
                System.out.println("Make triangle");
                break;
            }
            case MAKECIRCLE:
            {
                parameters = null;
                System.out.println("Make circle");
                break;
            }
            case MAKERECTANGLE:
            {
                parameters = null;
                System.out.println("Make rectangle");
                break;
            }
            case TOGGLELINETRACING:
            {
                parameters = null;
                System.out.println("Toggle Line tracing");
                break;
            }
        }

        command.setParameters(parameters);

        observer.onCommandReceived(command);
    }

    public void update()
    {
        this.bluetooth.update();
    }
}