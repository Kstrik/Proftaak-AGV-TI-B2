package Hardware;

public interface IEngine {

	void update();

	void goToSpeed(int speed);

	void updateSpeed();

	void setSpeed(int speed);

	void stop();
}