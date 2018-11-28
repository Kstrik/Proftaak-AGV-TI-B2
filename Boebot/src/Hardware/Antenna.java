package Hardware;

import Interface.CollisionDetector;
import Interface.IAntennaUpdate;
import TI.BoeBot;

public class Antenna implements ISensor {

    boolean collision;
    IAntennaUpdate observer;


    public Antenna(){
        this.collision = false;
    }

    public Antenna(IAntennaUpdate observer){
        this();
        this.observer = observer;
    }

    @Override
    public void update() {
        //BoeBot.digitalRead(100  );// TODO: Pin to be Assigned
        //System.out.println("Test Antenna");
    }

    public boolean collision(){
        return this.collision;
    }



}