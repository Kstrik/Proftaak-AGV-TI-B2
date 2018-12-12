package Interface;

import Hardware.UltrasonicSensor;
import Hardware.Antenna;
import Hardware.ISensor;

import javax.imageio.metadata.IIOMetadataController;
import java.util.ArrayList;
import java.util.Arrays;

public class CollisionDetector implements IUltrasoneUpdate, IAntennaUpdate
{
    private ISensor ultrasone;
    //private ISensor antennaL;
    //private ISensor antennaR;
    private ArrayList<ISensor> sensors = new ArrayList<>();

    private int lastUltrasonePulse;
    //private boolean lastAntennaStateR;
    //private boolean lastAntennaStateL;

    private IContoller driveControl;


    public CollisionDetector(IContoller driveControl)
    {
        this.ultrasone = new UltrasonicSensor(8, 9, this);
        //this.antennaL = new Antenna(this);
        //this.antennaR = new Antenna(this);

        this.sensors.add(this.ultrasone);
        //this.sensors.add(this.antennaL);
        //this.sensors.add(this.antennaR);

        this.driveControl = driveControl;
    }


    public boolean collisionDetected()
    {
        if(this.lastUltrasonePulse < 1200)//|| lastAntennaStateR || lastAntennaStateL
        {
            return true;
        }
        return false;
    }

    public int getLastUltrasonePulse()
    {
        return this.lastUltrasonePulse;
    }

    public void update()
    {
        for (ISensor sensor : sensors)
        {
            sensor.update();
        }
    }

    public void onUltrasoneUpdate(int pulse)
    {
        this.lastUltrasonePulse = pulse;

        if(this.lastUltrasonePulse < 1200)
        {
            Command speakerCommand = new Command(Command.Commands.PLAYSOUND, new ArrayList(Arrays.asList((10000 / 1200) * (1200 - pulse))));
            this.driveControl.onCommandReceived(speakerCommand);
        }
        else
        {
            Command speakerCommand = new Command(Command.Commands.STOPSOUND, null);
            this.driveControl.onCommandReceived(speakerCommand);
        }

        if(collisionDetected())
        {
            Command command = new Command(Command.Commands.STOP, null);
            this.driveControl.onCommandReceived(command);
        }
    }

    public void onAntennaUpdate(boolean hit, Antenna antenna)
    {
//        antenna.collision();
//        if(antenna == this.antennaL)
//        {
//            this.lastAntennaStateL = hit;
//        }
//        else
//        {
//            this.lastAntennaStateR = hit;
//        }

    }
}