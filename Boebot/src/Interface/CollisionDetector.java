package Interface;

import Hardware.UltrasonicSensor;
import Hardware.Antenna;
import Hardware.ISensor;

import java.util.ArrayList;

public class CollisionDetector implements IUltrasoneUpdate, IAntennaUpdate, IUpdateable{

    private ISensor ultrasone;
    private ISensor antennaL;
    private ISensor antennaR;
    private ArrayList<ISensor> sensors = new ArrayList<>();

    private int     lastUltrasonePulse;
    private boolean lastAntennaStateR;
    private boolean lastAntennaStateL;


    public CollisionDetector(){
        this.ultrasone  = new UltrasonicSensor(this);
        this.antennaL   = new Antenna(this);
        this.antennaR   = new Antenna(this);

        this.sensors.add(this.ultrasone);
        this.sensors.add(this.antennaL);
        this.sensors.add(this.antennaR);
    }


    public boolean collisionDetected(){
        if(lastUltrasonePulse<600 || lastAntennaStateR || lastAntennaStateL){
            return true;
        }
        return false;
    }

    public int getLastUltrasonePulse(){
        return this.lastUltrasonePulse;
    }

    @Override
    public void update() {
        for (ISensor sensor : sensors) {
            sensor.update();
        }
    }

    @Override
    public void onUltrasoneUpdate(int pulse) {
        System.out.println(pulse);
        this.lastUltrasonePulse = pulse;
    }

    @Override
    public void onAntennaUpdate(boolean hit, Antenna antenna) {
        antenna.collision();
        if(antenna == this.antennaL){
            this.lastAntennaStateL = hit;
        }else{
            this.lastAntennaStateR = hit;
        }

    }
}