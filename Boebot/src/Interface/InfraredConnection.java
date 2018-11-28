package Interface;

import TI.BoeBot;

import java.util.HashMap;

public class InfraredConnection implements IReceiver {

    private HashMap<Integer, MovementType> movementOptions;

    public InfraredConnection()
    {
        intitializeOptions();
    }

    public void intitializeOptions()
    {
        this.movementOptions = new HashMap<Integer, MovementType>();
        this.movementOptions.put(144, MovementType.GOFORWARD);
        this.movementOptions.put(2192, MovementType.GOBACKWARD);
        this.movementOptions.put(3216, MovementType.TURNLEFT);
        this.movementOptions.put(1168, MovementType.TURNRIGHT);
        //Stop knop
        this.movementOptions.put(2872, MovementType.STOP);
        //Knop 1
        this.movementOptions.put(16, MovementType.MAKE_TRIANGLE);
        //Knop 2
        this.movementOptions.put(2064, MovementType.MAKE_CIRCLE);
        //Knop 3
        this.movementOptions.put(1040, MovementType.MAKE_RECTANGLE);

    }

    public MovementType getInput()
    {
        int pulseLen = BoeBot.pulseIn(0, false, 6000);
        int lengtes[] = new int[12];

        if(pulseLen > 2000)
        {
            for(int i = 0; i < 12; i++)
            {
                lengtes[i] = BoeBot.pulseIn(0, false, 20000);
            }
        }

        BoeBot.wait(10);

        MovementType result = this.movementOptions.get(convertPulsesToInt(lengtes));

        if(result != null)
        {
            return result;
        }
        else
        {
            return MovementType.NONE;
        }
    }

    public int convertPulsesToInt(int[] pulsLengths)
    {
        int number = 0;

        for(int i = 0; i < pulsLengths.length; i++)
        {
            if(pulsLengths[i] >= 1000)
            {
                number++;
            }

            if(i != pulsLengths.length - 1)
            {
                number = number << 1;
            }
        }

        System.out.println(number);

        return number;
    }

    @Override
    public void onSignalReceived() {
        switch(getInput())
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
        }
    }

    private enum MovementType
    {
        GOFORWARD,
        GOBACKWARD,
        TURNLEFT,
        TURNRIGHT,
        STOP,
        MAKE_RECTANGLE,
        MAKE_CIRCLE,
        MAKE_TRIANGLE,
        NONE;
    }
}