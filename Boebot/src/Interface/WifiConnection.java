package Interface;

public class WifiConnection implements IConnection
{
    private IContoller observer;

    public WifiConnection(IContoller observer)
    {
        this.observer = observer;
    }

    public void sendSignal()
    {

    }

    public void onSignalReceived(Object signal)
    {

    }

    public void update()
    {

    }
}