package networkInterfaces.controlInterfaces;

import board.Board;
import networkInterfaces.NetworkInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class ClientRequest extends NetworkInterface {
    abstract public ServerRespond handel(Connection con, List<Board> boards) throws SQLException;
}
