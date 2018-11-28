package Logic;

import Hardware.InfraredSensor;
import Interface.Command;
import Interface.DriveControl;
import Interface.EngineControl;
import Interface.InfraredSignal;
import TI.BoeBot;

import java.util.ArrayList;
import java.util.Arrays;

import TI.Timer;

public class Main {

    public static void main(String[] args)
    {
        DriveControl driveControl = new DriveControl();
        while(true)
        {
            driveControl.update();
        }


        //EngineControl engineControl = new EngineControl();

//        InfraredSensor infraredSensor = new InfraredSensor(5, null);
//        while(true)
//        {
//            System.out.println(Arrays.toString(infraredSensor.getInput()));
//        }

//        ServoEngine servo1 = new ServoEngine(12, false);
//        ServoEngine servo2 = new ServoEngine(13, true);
//
//        servo1.goToSpeed(-100);
//        servo2.goToSpeed(-100);

        //servo1.setSpeed(-200);
        //servo2.setSpeed(-200);
//        BoeBot.wait(5000);
//        EngineControl engineControl = new EngineControl();
//        //engineControl.turnDegrees(180, 100);
//
//        ArrayList<Integer> parameters = new ArrayList<>();
//        parameters.add(100);
//
//        Command command = new Command(1, parameters);
//        engineControl.update(command);
//
//        Timer timer = new Timer(5000);

//        BoeBot.wait(5000);
//        command = new Command(3, parameters);
//        engineControl.update(command);

//        while (true)
//        {
//            if(!timer.timeout())
//            {
//                engineControl.update(null);
//            }
//            else
//            {
//                Command newCommand = new Command(3, null);
//                engineControl.update(newCommand);
//            }
//        }

//        while(true)
//        {
//            servo1.update();
//            servo2.update();
//        }
    }
}
