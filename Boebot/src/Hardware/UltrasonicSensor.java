package Hardware;


import Interface.IUltrasoneUpdate;
import TI.BoeBot;
import TI.Timer;

public class UltrasonicSensor implements ISensor
{
    private int pinInput;
    private int pinOutput;
    private IUltrasoneUpdate observer;
    private Timer timer;

    public UltrasonicSensor(int pinInput, int pinOutput, IUltrasoneUpdate observer)
    {
        this.pinInput = pinInput;
        this.pinOutput = pinOutput;
        this.observer = observer;
        this.timer = new Timer(50);
    }

    public void update()
    {
        if (this.timer.timeout())
        {
            BoeBot.digitalWrite(pinInput, true);
            BoeBot.wait(1);
            BoeBot.digitalWrite(pinInput, false);

            int pulse = BoeBot.pulseIn(pinOutput, true, 10000);

//            if(pulse != -2 && pulse != 16 && pulse != 17)
//            {
//                this.pulse = pulse;
//            }

            this.observer.onUltrasoneUpdate(pulse);

            this.timer.setInterval(50);
        }
    }
}