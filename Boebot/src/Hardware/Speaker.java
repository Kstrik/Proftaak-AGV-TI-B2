package Hardware;

import TI.BoeBot;
import TI.Timer;

public class Speaker
{
    private int pin;
    private int frequentie;
    private boolean isOn;

    private Timer timer;

    //private int delay;

    public Speaker(int pin, int delay)
    {
        this.pin = pin;
        this.frequentie = 0;
        this.isOn = false;
        this.timer = new Timer(5);
        //this.delay = delay;
    }

    public void turnOn()
    {
        this.isOn = true;
    }

    public void turnOff()
    {
        this.isOn = false;
    }

    public void update()
    {
        if(this.timer.timeout())
        {
            if(this.isOn)
            {
                play();
            }
        }
    }

    public void setFrequentie(int frequentie)
    {
        this.frequentie = frequentie;
    }

    public void play()
    {
        BoeBot.freqOut(this.pin, this.frequentie, 50);
    }
}