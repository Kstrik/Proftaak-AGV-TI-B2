package Interface;

public interface IConnection<T>
{
	void sendSignal(T signal);

	void onSignalReceived(T signal);

	void update();
}