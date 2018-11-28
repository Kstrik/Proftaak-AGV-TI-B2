package Interface;

public class BluetoothConnection implements IConnection
{
    private IContoller observer;

    public BluetoothConnection(IContoller observer)
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