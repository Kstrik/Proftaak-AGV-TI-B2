package Interface;

public class Communication implements IContoller {

    private IContoller observer;

    public Communication(IContoller observer) {
        this.observer = observer;
    }

    @Override
    public void onCommandReceived(Command cmd) {
        observer.onCommandReceived(cmd);
    }
}