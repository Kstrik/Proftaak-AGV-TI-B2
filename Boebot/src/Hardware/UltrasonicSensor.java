package Hardware;


import Interface.IUltrasoneUpdate;
import Interface.IUpdateable;
import TI.BoeBot;
import TI.Timer;

import java.awt.*;

public class UltrasonicSensor implements ISensor{


    private int pulse;
    private IUltrasoneUpdate observer = null;
    private Timer timer = new Timer(50);


    public UltrasonicSensor() {
    this.pulse = 0;
    }

    public UltrasonicSensor(IUltrasoneUpdate observer){
        this();
        this.observer = observer;
    }

    @Override
    public void update() {

        if (timer.timeout()) {
            BoeBot.digitalWrite(8, true);
            BoeBot.wait(1);
            BoeBot.digitalWrite(8, false);

            int pulse = BoeBot.pulseIn(9, true, 10000);

            if(pulse != -2 && pulse != 16 && pulse != 17){
                this.pulse = pulse;
            }

            this.observer.onUltrasoneUpdate(this.pulse);

            timer.setInterval(50);
        }



    }


    public int getPulse(){
        return this.pulse;
    }
}