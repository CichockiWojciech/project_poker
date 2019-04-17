package networkInterfaces.controlInterfaces;

import java.io.Serializable;

public enum ErrorCode implements Serializable {
    OK,
    WRONG_PASSWORD,
    UNKNOWN_LOGIN,
    LOGIN_OCCUPIED,
    USERNAME_OCCUPIED,
    DB_ERROR

}
