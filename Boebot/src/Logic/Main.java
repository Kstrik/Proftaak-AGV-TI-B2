package Logic;

import Interface.CollisionDetector;
import TI.BoeBot;

public class Main {

    public static void main(String[] args) {
        CollisionDetector collisionDetector = new CollisionDetector();


        while(true){
            collisionDetector.update();
            BoeBot.wait(1);
        }

    }
}