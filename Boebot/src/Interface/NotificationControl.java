package Interface;

import Hardware.BuildInRGBLed;
import Hardware.Speaker;

import java.awt.*;
import java.util.ArrayList;

public class NotificationControl
{
    private Speaker speaker;
    private ArrayList<BuildInRGBLed> buildInRGBLeds;

    public NotificationControl()
    {
        this.speaker = new Speaker(3, 1);

        this.buildInRGBLeds = new ArrayList<BuildInRGBLed>();

        for(int i = 0; i < 6; i++)
        {
            this.buildInRGBLeds.add(new BuildInRGBLed(i));
        }
    }

    public void update(Command command)
    {
        if(command != null)
        {
            excecuteCommand(command);
        }

        this.speaker.update();

        for(BuildInRGBLed buildInRGBLed : this.buildInRGBLeds)
        {
            buildInRGBLed.update();
        }
    }

    public void excecuteCommand(Command command)
    {
        switch(command.getCommand())
        {
            case PLAYSOUND:
            {
                this.speaker.setFrequentie((int)command.getParameters().get(0));
                this.speaker.turnOn();
                break;
            }
            case STOPSOUND:
            {
                this.speaker.setFrequentie(0);
                this.speaker.turnOff();
                break;
            }
            case CHANGESOUND:
            {
                this.speaker.setFrequentie((int)command.getParameters().get(0));
                break;
            }
            case STARTFLASHCOLOR:
            {
                for(BuildInRGBLed buildInRGBLed : this.buildInRGBLeds)
                {
                    buildInRGBLed.setColor((Color)command.getParameters().get(0));
                    buildInRGBLed.setIsFlashing(true);
                }

                break;
            }
            case STOPFLASHCOLOR:
            {
                Color color = Color.getHSBColor(0.0F, 0.0F, 0.0F);

                for(BuildInRGBLed buildInRGBLed : this.buildInRGBLeds)
                {
                    buildInRGBLed.setColor(color);
                    buildInRGBLed.setIsFlashing(false);
                }

                break;
            }
        }
    }
}