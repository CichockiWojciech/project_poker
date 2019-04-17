package networkInterfaces.controlInterfaces;

import networkInterfaces.NetworkInterface;

public class ServerRespond extends NetworkInterface {
    private ErrorCode code;

    public ServerRespond(ErrorCode code) {
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
