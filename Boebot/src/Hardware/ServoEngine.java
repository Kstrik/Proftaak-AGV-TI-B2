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
            if(this.isGoingForward)
            {
                if(!this.isReversed)
                {
                    this.speed--;
                }
                else
                {
                    this.speed++;
                }
            }
            else
            {
                if(!this.isReversed)
                {
                    this.speed++;
                }
                else
                {
                    this.speed--;
                }
            }

//            if(!this.isReversed)
//            {
//                this.servo.update(1300 + (1700 - this.speed));
//                BoeBot.wait(1);
//            }
//            else
//            {
                this.servo.update(this.speed);
                BoeBot.wait(1);
//            }

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

        if(!this.isReversed)
        {
            newSpeed *= -1;
        }

        this.speed = this.servo.getPulseWidth();
        this.finalSpeed = 1500 + newSpeed;

        this.isGoingForward = false;

        if(this.isReversed)
        {
            if(this.speed < this.finalSpeed)
            {
                this.isGoingForward = true;
            }
        }
        else
        {
            if(this.speed > this.finalSpeed)
            {
                this.isGoingForward = true;
            }
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


        //New Code
        if(!this.isReversed)
        {
            this.speed = 1500 - newSpeed;
        }
        else
        {
            this.speed = 1500 + newSpeed;
        }

        //Old code
        //this.speed = 1500 + newSpeed;

        this.finalSpeed = this.speed;

        //System.out.println("Final Speed: " + finalSpeed);
        //System.out.println("Speed: " + speed);

        this.isGoingForward = false;
        this.hasConstantSpeed = true;
        this.servo.update(this.speed);
        BoeBot.wait(1);
    }

    public void setTurnSpeed(int newSpeed)
    {
        if(newSpeed > 200)
        {
            newSpeed = 200;
        }
        else if(newSpeed < -200)
        {
            newSpeed = -200;
        }

        this.speed = 1500 + newSpeed;

        this.finalSpeed = this.speed;

        //System.out.println("Final Speed: " + finalSpeed);
        //System.out.println("Speed: " + speed);

        this.isGoingForward = false;
        this.hasConstantSpeed = true;
        this.servo.update(this.speed);
        BoeBot.wait(1);
    }

    public boolean isStationary()
    {
        if(this.speed == 1500)
        {
            return true;
        }

        return false;
    }

    public void increaseSpeed(int speed)
    {
        if(speed > 0)
        {
            if(!this.isReversed)
            {
                if(this.speed + speed > 1700)
                {
                    speed = 1700;
                }
            }
            else
            {
                if(this.speed - speed < 1300)
                {
                    speed = 1300;
                }
            }
            this.setSpeed(this.speed + speed);
        }
    }

    public void decreaseSpeed(int speed)
    {
        if(speed > 0)
        {
            if(!this.isReversed)
            {
                if(this.speed - speed < 1300)
                {
                    speed = 1300;
                }
            }
            else
            {
                if(this.speed + speed > 1700)
                {
                    speed = 1700;
                }
            }
            this.setSpeed(this.speed + speed);
        }
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