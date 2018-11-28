package Hardware;

import TI.BoeBot;
import TI.Servo;
import TI.Timer;

public class ServoEngine implements IEngine
{
    private Servo servo;
    private Timer timer;

    private int speed;
    private int finalSpeed;

    private boolean isReversed;
    private boolean hasConstantSpeed;
    private boolean isGoingForward;

    private final int Delay = 20;

    public ServoEngine(int pin, boolean isReversed)
    {
        this.servo = new Servo(pin);
        this.timer = new Timer(this.Delay);

        this.speed = 1500;
        this.finalSpeed = 1500;

        this.isReversed = isReversed;
        this.hasConstantSpeed = true;
        this.isGoingForward = false;
    }

    public void update()
    {
        System.out.println(this.speed);
        if(this.timer.timeout())
        {
            if(!hasConstantSpeed)
            {
                updateSpeed();
            }
        }
    }

    public void updateSpeed()
    {
        if(this.speed != this.finalSpeed)
        {
            if(!this.isGoingForward)
            {
                this.speed--;
            }
            else
            {
                this.speed++;
            }

            if(!this.isReversed)
            {
                this.servo.update(1300 + (1700 - this.speed));
                BoeBot.wait(1);
            }
            else
            {
                this.servo.update(this.speed);
                BoeBot.wait(1);
            }

            if(this.speed == this.finalSpeed)
            {
                this.hasConstantSpeed = true;
            }
        }
    }

    public void goToSpeed(int newSpeed)
    {
        if(newSpeed > 200)
        {
            newSpeed = 200;
        }
        else if(newSpeed < -200)
        {
            newSpeed = -200;
        }

        this.speed = this.servo.getPulseWidth();
        this.finalSpeed = 1500 + newSpeed;

        this.isGoingForward = false;

        if(this.speed < finalSpeed)
        {
            this.isGoingForward = true;
        }

        if(this.speed != this.finalSpeed)
        {
            this.hasConstantSpeed = false;
        }
    }

    public void setSpeed(int newSpeed)
    {
        if(newSpeed > 200)
        {
            newSpeed = 200;
        }
        else if(newSpeed < -200)
        {
            newSpeed = -200;
        }

        int servoSpeed = this.servo.getPulseWidth();

        if(!this.isReversed)
        {
            newSpeed *= -1;
        }

        this.speed = 1500 + newSpeed;

        this.finalSpeed = this.speed;
        this.isGoingForward = false;
        this.hasConstantSpeed = true;
        this.servo.update(this.speed);
        BoeBot.wait(1);
    }

    public void stop()
    {
        this.speed = 1500;
        this.finalSpeed = 1500;
        this.isGoingForward = false;
        this.hasConstantSpeed = true;
        this.servo.update(1500);
        BoeBot.wait(1);
    }
}