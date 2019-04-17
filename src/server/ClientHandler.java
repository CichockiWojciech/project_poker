package server;

import board.Board;
import database.Account;
import networkInterfaces.NetworkInterface;
import networkInterfaces.controlInterfaces.ClientRequest;
import networkInterfaces.controlInterfaces.GameRequest;
import networkInterfaces.controlInterfaces.ServerRespond;
import rmi.PlayerImp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Connection connection;
    private List<Board> boardList;
    private Registry reg;
    private PlayerImp player;

    public ClientHandler(Socket socket, Connection connection, List<Board> boardList, Registry reg) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.connection = connection;
        this.boardList = boardList;
        this.reg = reg;
    }

    @Override
    public void run() {
            try {
                while (true){
                    //for(Board board : boardList)
                        //System.out.println(board);

                    NetworkInterface data = (NetworkInterface) in.readObject();
                    if(data instanceof ClientRequest){
                        // handel control message
                        ClientRequest request = (ClientRequest) data;
                        ServerRespond respond = request.handel(connection, boardList);
                        out.writeObject(respond);
                        out.flush();

                    }else{
                        // handel game message
                        GameRequest request = (GameRequest) data;
                        joinBoard(request);

                    }
                }
            } catch (IOException e) {
                System.out.println("client left");
                if(player != null)
                    try {
                        releaseExportObject();
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    } catch (NotBoundException e1) {
                        e1.printStackTrace();
                    }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
    }

    private Board findBoard(String boardName){
        for(Board board : boardList){
            if(board.getName().equals(boardName))
                return board;
        }
        return null;
    }

    private void joinBoard(GameRequest request) throws RemoteException, SQLException, NotBoundException {
        Statement statement = connection.createStatement();
        String login = request.getLogin();
        Board board = findBoard(request.getBoardName());
        int tokens = Account.getTokensFormLogin(statement, login);
        player = new PlayerImp(login, request.getUsername(), board, out, tokens);
        System.out.println(login);
        reg.rebind(login, player);
        board.addPlayer(player);
    }

    public void releaseExportObject() throws RemoteException, NotBoundException {
        reg.unbind(player.getLogin());
        UnicastRemoteObject.unexportObject(player, true);
    }

}
