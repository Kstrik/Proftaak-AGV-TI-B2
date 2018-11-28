package Interface;

public interface IConnection<T>
{
	void sendSignal();

	void onSignalReceived(T signal);

	void update();
}