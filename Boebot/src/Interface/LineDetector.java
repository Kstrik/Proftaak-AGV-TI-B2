package Interface;

import Hardware.ISensor;
import Hardware.LineSensor;
import Hardware.ServoEngine;
import TI.BoeBot;

import java.util.ArrayList;

public class LineDetector
{
    private LineDriver lineDriver;
    private LineSensor[] lineSensors;

    private int speed;

    public LineDetector(LineDriver linedriver)
    {
        this.lineDriver = linedriver;
        this.lineSensors = new LineSensor[] {new LineSensor(1), new LineSensor(2), new LineSensor(3)};
    }

    public boolean isOnLine()
    {
        for(LineSensor lineSensor : this.lineSensors)
        {
            lineSensor.update();
            System.out.print(lineSensor.getLastValue());
            System.out.print(" : ");
        }
        System.out.println("");
        if(this.lineSensors[1].getLastValue() > 1400)
        {
            return true;
        }

        return false;
    }

    public void update()
    {
        System.out.println("Linedriver Update");
        for(ISensor lineSensor : this.lineSensors)
        {
            lineSensor.update();
        }

        //Intersection
        //        else if(this.lineSensors[0].getLastValue() < 1400 && this.lineSensors[1].getLastValue() < 1400 && this.lineSensors[2].getLastValue() < 1400)
//        {
//            System.out.println("No line");
//            this.lineDriver.deactivateLineTracing();
//        }
        if(this.lineSensors[0].getLastValue() > 1400 && this.lineSensors[1].getLastValue() > 1400 && this.lineSensors[2].getLastValue() > 1400)
        {
            System.out.println("Intersection");
            this.lineDriver.onIntersectionDetected();
        }
        else
        {
            if(this.lineSensors[2].getLastValue() > 1400)
            {
                //Right
                System.out.println("Right");
                this.lineDriver.onRightLineDetected();
            }
            else if(this.lineSensors[0].getLastValue() > 1400)
            {
                //Left
                System.out.println("Left");
                this.lineDriver.onLeftLineDetected();
            }
            else
            {
                //Going forward
                System.out.println("Middle");
                this.lineDriver.onMiddleLineDetected();
            }
        }
    }
}