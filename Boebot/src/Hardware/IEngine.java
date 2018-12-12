package Hardware;

public interface IEngine {

	void update();

	void goToSpeed(int speed);

	void updateSpeed();

	void setSpeed(int speed);

	boolean isStationary();

	void increaseSpeed(int speed);

	void decreaseSpeed(int speed);

	void stop();
}