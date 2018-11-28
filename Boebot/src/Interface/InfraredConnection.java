package Interface;

import TI.BoeBot;

import java.util.HashMap;

public class InfraredConnection implements IReceiver {

    private HashMap<Integer, EnumMovement.MovementType> movementOptions;
    private IContoller observer;

    public InfraredConnection(IContoller observer)
    {
        this.observer = observer;
        intitializeOptions();
    }

    public void intitializeOptions()
    {
        this.movementOptions = new HashMap<Integer, EnumMovement.MovementType>();
        this.movementOptions.put(144, EnumMovement.MovementType.GOFORWARD);
        this.movementOptions.put(2192, EnumMovement.MovementType.GOBACKWARD);
        this.movementOptions.put(3216, EnumMovement.MovementType.TURNLEFT);
        this.movementOptions.put(1168, EnumMovement.MovementType.TURNRIGHT);
        //Stop knop
        this.movementOptions.put(2872, EnumMovement.MovementType.STOP);
        //Knop 1
        this.movementOptions.put(16, EnumMovement.MovementType.MAKE_TRIANGLE);
        //Knop 2
        this.movementOptions.put(2064, EnumMovement.MovementType.MAKE_CIRCLE);
        //Knop 3
        this.movementOptions.put(1040, EnumMovement.MovementType.MAKE_RECTANGLE);

    }

    public EnumMovement.MovementType getInput()
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

        EnumMovement.MovementType result = this.movementOptions.get(convertPulsesToInt(lengtes));

        if(result != null)
        {
            return result;
        }
        else
        {
            return EnumMovement.MovementType.NONE;
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
        Command cmd = new Command("Test");
        observer.onCommandReceived(cmd);

    }
}