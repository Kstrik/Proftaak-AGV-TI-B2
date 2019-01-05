package Interface;

import Hardware.IEngine;
import Hardware.ServoEngine;
import TI.BoeBot;

import java.util.ArrayList;
import java.util.Arrays;

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
        Command command = new Command(Command.Commands.STOP, null);

        for(IEngine engine : this.engines)
        {
            engine.stop();
        }

        if(turnSpeed > 100)
        {
            turnSpeed = 100;
        }
        else if(turnSpeed < -100)
        {
            turnSpeed = -100;
        }

        this.engines.get(0).setTurnSpeed(turnSpeed);
        this.engines.get(1).setTurnSpeed(turnSpeed);

        BoeBot.wait(1);
    }

    public void turnDegrees(int degrees, int turnSpeed)
    {
        Command command = new Command(Command.Commands.STOP, null);

        for(IEngine engine : this.engines)
        {
            engine.stop();
        }

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
        if(interval < 0)
        {
            interval *= -1;
        }
        //System.out.println(interval);

        this.engines.get(0).setTurnSpeed(turnSpeed);
        this.engines.get(1).setTurnSpeed(turnSpeed);

        BoeBot.wait((int)interval);
        this.engines.get(0).stop();
        this.engines.get(1).stop();
    }

    public void driveInSquare()
    {
        for(int i = 0; i < 4; i++)
        {
            this.engines.get(0).setSpeed(-100);
            this.engines.get(1).setSpeed(100);
            BoeBot.wait(5000);
            this.engines.get(0).stop();
            this.engines.get(1).stop();
            BoeBot.wait(1000);
            turnDegrees(90, 100);
            BoeBot.wait(1000);
        }
    }

    public void driveInCircle()
    {
        this.engines.get(0).setSpeed(-40);
        this.engines.get(1).setSpeed(100);
    }

    public void driveInTriangle()
    {
        for(int i = 0; i < 3; i++)
        {
            this.engines.get(0).setSpeed(-100);
            this.engines.get(1).setSpeed(100);
            BoeBot.wait(5000);
            this.engines.get(0).stop();
            this.engines.get(1).stop();
            BoeBot.wait(1000);
            turnDegrees(135, 100);
            BoeBot.wait(1000);
        }
    }

    public void update(Command command)
    {
        if(command != null)
        {
            excecuteCommand(command);
        }
        else
        {
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
            case GOFORWARD:
            case GOBACKWARD:
            case GOTOSPEED:
            {
                for(IEngine engine : this.engines)
                {
                    engine.goToSpeed((int)command.getParameters().get(0));
                }

                break;
//                this.engines.get(0).goToSpeed((int)command.getParameters().get(0) * -1);
//                this.engines.get(1).goToSpeed((int)command.getParameters().get(0));

                //break;
            }
            case TURNLEFT:
            case TURNRIGHT:
            case TURN:
            {
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
                for(IEngine engine : this.engines)
                {
                    engine.stop();
                }

                break;
            }
            case SETLEFTSPEED:
            {
                this.engines.get(0).setSpeed((int)command.getParameters().get(0));
                break;
            }
            case SETRIGHTPEED:
            {
                this.engines.get(1).setSpeed((int)command.getParameters().get(0));
                break;
            }
            case SETSPEED:
            {
                for(IEngine engine : this.engines)
                {
                    engine.setSpeed((int)command.getParameters().get(0));
                }
                break;
            }
            case STOPLEFT:
            {
                this.engines.get(0).stop();
                break;
            }
            case STOPRIGHT:
            {
                this.engines.get(1).stop();
                break;
            }
            case MAKETRIANGLE:
            {
                driveInTriangle();
                break;
            }
            case MAKECIRCLE:
            {
                driveInCircle();
                break;
            }
            case MAKERECTANGLE:
            {
                driveInSquare();
                break;
            }
        }
    }

    public boolean isStationairy()
    {
        int stationairyCount = 0;

        for(IEngine engine : this.engines)
        {
            if(engine.isStationary())
            {
                stationairyCount++;
            }
        }

        if(stationairyCount == this.engines.size())
        {
            return true;
        }

        return false;
    }
}