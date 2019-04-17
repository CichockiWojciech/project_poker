package server;

import board.Board;
import constants.Constants;
import database.DatabaseSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        List<Board> boardList = new LinkedList<>();
        try {
            serverSocket = new ServerSocket(Constants.SERVER_PORT);
            Connection connection = DatabaseSource.getConnection();

            new Thread(new CommandLine(boardList, connection)).start();
            Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);


            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("new client");
                new Thread(new ClientHandler(socket, connection, boardList, reg)).start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("sql failed");
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
