package Interface;

public class Command {
    private String cmd;

    public Command(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }
}