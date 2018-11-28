package Interface;

public class Communication implements IContoller
{
    private IContoller observer;
    private InfraredConnection infraredConnection;
    private BluetoothConnection bluetoothConnection;
    private WifiConnection wifiConnection;

    public Communication(IContoller observer)
    {
        this.observer = observer;

        this.infraredConnection = new InfraredConnection(this);
        this.bluetoothConnection = new BluetoothConnection(this);
        this.wifiConnection = new WifiConnection(this);
    }

    public void onCommandReceived(Command command)
    {
        observer.onCommandReceived(command);
    }

    public void update()
    {
        this.infraredConnection.update();
        this.bluetoothConnection.update();
        this.wifiConnection.update();
    }
}