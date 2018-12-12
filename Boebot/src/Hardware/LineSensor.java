package Hardware;

import TI.BoeBot;

public class LineSensor implements ISensor
{
    private int pin;
    private int lastValue;

    public LineSensor(int pin)
    {
        this.pin = pin;
        this.lastValue = 0;
    }

    public int getLastValue()
    {
        return this.lastValue;
    }

    public void update()
    {
        this.lastValue = BoeBot.analogRead(this.pin);
    }
}