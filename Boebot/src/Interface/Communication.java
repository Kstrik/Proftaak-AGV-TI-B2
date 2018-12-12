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

    public void transmitData(int data, CommunicationTypes communicationType)
    {
        switch(communicationType)
        {
            case INFRARED:
            {
                System.out.println("Infrared signal could not be send. No transmitter available!");
                break;
            }
            case BLUETOOTH:
            {
                this.bluetoothConnection.sendSignal(data);
                break;
            }
            case WIFI:
            {
                this.wifiConnection.sendSignal(data);
                break;
            }
        }
    }

    public void update()
    {
        this.infraredConnection.update();
        this.bluetoothConnection.update();
        this.wifiConnection.update();
    }

    public enum CommunicationTypes
    {
        INFRARED,
        BLUETOOTH,
        WIFI
    }
}