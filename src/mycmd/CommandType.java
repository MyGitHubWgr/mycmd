package mycmd;

/**
 * Created by gongrui on 16-12-26.
 */
public enum CommandType {

    RUN_COMMAND("run"),
    ADD_COMMAND("add"),
    DEL_COMMAND("del"),
    EXIT_COMMAND("exit");

    private String type;

    CommandType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
