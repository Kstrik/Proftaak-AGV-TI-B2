package Hardware;

import TI.BoeBot;
import TI.Timer;

import java.awt.*;

public class BuildInRGBLed
{
    private int pin;
    private float brightness;
    private Color color;

    private Timer timer;
    private boolean isFlashing;
    private boolean isOn;

    public BuildInRGBLed(int pin)
    {
        this.pin = pin;
        this.brightness = 1.0F;
        this.color = Color.getHSBColor(1.0F, 1.0F, this.brightness);
        this.timer = new Timer(500);
        this.isFlashing = false;
        this.isOn = false;
    }

    public void setBrightness(float brightness)
    {
        this.brightness = clampBrigthness(brightness, 0.0F, 1.0F);
    }

    public void setColor(Color color)
    {
        Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue());
        this.color = newColor;
    }

    public void setIsFlashing(boolean isFlashing)
    {
        if(this.isFlashing)
        {
            BoeBot.rgbSet(pin, Color.getHSBColor(0.0f, 0.0f, 0.0f));
            BoeBot.rgbShow();
        }

        this.isFlashing = isFlashing;
        this.isOn = isFlashing;
    }

    private float clampBrigthness(float val, float min, float max)
    {
        return Math.max(min, Math.min(max, val));
    }

    public void update()
    {
        if(this.timer.timeout())
        {
            if(isFlashing)
            {
                if(this.isOn)
                {
                    BoeBot.rgbSet(this.pin, this.color);
                    BoeBot.rgbShow();
                    BoeBot.setStatusLed(true);
                    //BoeBot.wait(1);
                }
                else
                {
                    BoeBot.rgbSet(pin, Color.getHSBColor(0.0f, 0.0f, 0.0f));
                    BoeBot.rgbShow();
                    //BoeBot.setStatusLed(true);
                    //BoeBot.wait(1);
                }

                this.isOn = !this.isOn;
            }
        }
    }
}
