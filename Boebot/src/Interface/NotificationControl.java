package Interface;

import Hardware.Speaker;

public class NotificationControl
{
    private Speaker speaker;

    public NotificationControl()
    {
        this.speaker = new Speaker(15, 1);
    }

    public void update(Command command)
    {
        if(true)
        {
            if(command != null)
            {
                excecuteCommand(command);
            }

            this.speaker.update();
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
        }
    }
}