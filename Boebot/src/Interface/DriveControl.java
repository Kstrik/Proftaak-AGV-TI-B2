package Interface;

import Hardware.ISensor;
import Hardware.InfraredSensor;

import java.util.ArrayList;

public class DriveControl implements IContoller {

    public DriveControl() {
        ArrayList<ISensor> sensors = new ArrayList<>();
        Communication communication = new Communication(this);
        InfraredConnection infraredConnection = new InfraredConnection(communication);
        sensors.add(new InfraredSensor(infraredConnection));

        while(true) {
            for(ISensor s : sensors) {
                s.update();
            }
        }
    }

    @Override
    public void onCommandReceived(Command cmd) {
        System.out.println(cmd);
        /*switch(getInput())
        {
            case GOFORWARD:
            {
                System.out.println("Forward");
                break;
            }
            case GOBACKWARD:
            {
                System.out.println("Backward");
                break;
            }
            case TURNLEFT:
            {
                System.out.println("Left");
                break;
            }
            case TURNRIGHT:
            {
                System.out.println("Right");
                break;
            }
            case STOP: {
                System.out.println("Stop");
                break;
            }
            case MAKE_TRIANGLE:
            {
                System.out.println("Make triangle");
                break;
            }
            case MAKE_CIRCLE:
            {
                System.out.println("Make circle");
                break;
            }
            case MAKE_RECTANGLE:
            {
                System.out.println("Make rectangle");
                break;
            }
        }*/
    }
}