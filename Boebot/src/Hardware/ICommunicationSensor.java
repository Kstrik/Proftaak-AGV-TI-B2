package Hardware;

public interface ICommunicationSensor<T>
{
    void update();

    T receive();

    void transmit(T data);
}
