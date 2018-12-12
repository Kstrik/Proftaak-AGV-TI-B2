package Interface;

import Hardware.ICommunicationSensor;
import Hardware.ISensor;
import Hardware.InfraredSensor;

import java.util.ArrayList;

public class InfraredConnection implements IConnection
{
    private IContoller observer;
    private ICommunicationSensor infraredSensor;

    public InfraredConnection(IContoller observer)
    {
        this.observer = observer;
        this.infraredSensor = new InfraredSensor(5, this);
    }

    public void sendSignal(Object signal)
    {
        System.out.println("Infrared signal could not be send. No transmitter available!");
    }

    public void onSignalReceived(Object signal)
    {
        InfraredSignal infraredSignal = new InfraredSignal((int[])signal);
        Command command = infraredSignal.convertToCommand();
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
        this.infraredSensor.update();
    }
}