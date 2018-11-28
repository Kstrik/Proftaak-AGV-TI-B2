package Hardware;

import Interface.IReceiver;

public class InfraredSensor implements ISensor{

    private IReceiver callbackClass;

    public InfraredSensor(IReceiver callbackClass) {
        this.callbackClass = callbackClass;
    }

    @Override
    public void update() {
        callbackClass.onSignalReceived();
    }
}