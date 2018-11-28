package Interface;

import Hardware.IEngine;
import Hardware.ServoEngine;
import TI.BoeBot;

import java.util.ArrayList;

public class EngineControl
{
    private ArrayList<IEngine> engines;

    public EngineControl()
    {
        this.engines = new ArrayList<IEngine>();
        this.engines.add(new ServoEngine(12, false));
        this.engines.add(new ServoEngine(13, true));
    }

    public void turn(int turnSpeed)
    {
        if(turnSpeed > 100)
        {
            turnSpeed = 100;
        }
        else if(turnSpeed < -100)
        {
            turnSpeed = -100;
        }

        this.engines.get(0).setSpeed(turnSpeed);
        this.engines.get(1).setSpeed(turnSpeed * -1);

        BoeBot.wait(1);
    }

    public void turnDegrees(int degrees, int turnSpeed)
    {
        if(turnSpeed > 100)
        {
            turnSpeed = 100;
        }
        else if(turnSpeed < -100)
        {
            turnSpeed = -100;
        }

        if(degrees > 360)
        {
            degrees = 360;
        }
        else if(degrees < -360)
        {
            degrees = -360;
        }

        double interval = ((((double)2150 / (double)360) / (double)100) * (double)turnSpeed) * (double)degrees;

        System.out.println(interval);

        this.engines.get(0).setSpeed(turnSpeed);
        this.engines.get(1).setSpeed(turnSpeed * -1);

        BoeBot.wait((int)interval);
        this.engines.get(0).stop();
        this.engines.get(1).stop();
    }

    public void update(Command command)
    {
        if(true)
        {
            if(command != null)
            {
                excecuteCommand(command);
            }

            for(IEngine engine : this.engines)
            {
                engine.update();
            }
        }
    }

    public void excecuteCommand(Command command)
    {
        switch(command.getCommand())
        {
            case GOTOSPEED:
            {
                for(IEngine engine : this.engines)
                {
                    engine.goToSpeed((int)command.getParameters().get(0));
                }

                break;
            }
            case TURN:
            {
                System.out.println("Turn");
                turn((int)command.getParameters().get(0));

                break;
            }
            case TURNDEGREES:
            {
                turnDegrees((int)command.getParameters().get(0), (int)command.getParameters().get(1));

                break;
            }
            case STOP:
            {
                System.out.println("Stop");
                for(IEngine engine : this.engines)
                {
                    engine.stop();
                }

                break;
            }
        }
    }
}