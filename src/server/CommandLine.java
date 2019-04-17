package server;

import board.Board;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class CommandLine implements Runnable {
    private List<Board> boardsList;
    private Connection connection;
    private Scanner scanner = new Scanner(System.in);

    public CommandLine(List<Board> boardsList, Connection connection) {
        this.boardsList = boardsList;
        this.connection = connection;
    }

    @Override
    public void run() {
        String command;
        String boardName;
        while (true){
            System.out.println("waiting for command[add/remove board]");
            command = scanner.nextLine();

            switch (command){
                case "a":
                case "add":{
                    System.out.println("enter board name");
                    boardName = scanner.nextLine();
                    boardsList.add(new Board(boardName, connection));
                    break;
                }
                case "r":
                case "d":
                case "del":
                case "delete":
                case "remove":{
                    System.out.println("enter board name");
                    boardName = scanner.nextLine();
                    for(Board b : boardsList){
                        if(b.getName().equals(boardName)){
                            boardsList.remove(b);
                            System.out.println("board remove");
                        }
                    }
                    break;
                }
                case "exit":{
                    System.exit(0);
                }
        }
    }



    }
}
