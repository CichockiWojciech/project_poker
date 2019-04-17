package guiComponents.main;

import client.WaitingBoardTask;
import networkInterfaces.controlInterfaces.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.List;

import static guiComponents.GUISettings.*;
import static networkInterfaces.controlInterfaces.ErrorCode.OK;

public class AccountPanel extends JPanel {
	
	private String login;
	private List<String> boards;
	private List<Integer> players;
	private List<Integer> waiting;
	private String username;
	private int totalTokens;
	private int win;
	private int lose;
	private MainPanel mainPanel;
	private WaitingBoardTask waitingBoardTask;


	private JTextField totalTokensAmount;

	
	private JLabel usernameLabel;
	private JTextField playersAmount;
	private JTextField waitingAmount;
	private JTextField winAmount;
	private JTextField timeField;
	private JTextField loseAmount;
	private JComboBox<String> boardComboBox;
	private JButton refreshButton;
	private JButton logoutButton;
	private JButton joinButton;
	private JButton leaveButton;
	private JLabel msgLabel;


	public AccountPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		setBackground(BACKGROUND);
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		
		boardComboBox = new JComboBox();
		boardComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		boardComboBox.setForeground(Color.BLACK);
		boardComboBox.setSelectedItem(null);
		boardComboBox.setBackground(BRIGHT_BG);
		boardComboBox.setBounds(525, 80, 174, 44);
		add(boardComboBox);
		
		joinButton = new JButton("join");
	
		joinButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		joinButton.setForeground(Color.BLACK);
		joinButton.setBounds(395, 139, 107, 44);
		joinButton.setBackground(BRIGHT_BG);
		add(joinButton);

		refreshButton = new JButton("refresh");
		refreshButton.setForeground(Color.BLACK);
		refreshButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refreshButton.setBounds(594, 375, 107, 44);
		refreshButton.setBackground(BRIGHT_BG);
		add(refreshButton);
		
		leaveButton = new JButton("leave");
		leaveButton.setForeground(Color.BLACK);
		leaveButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		leaveButton.setBounds(395, 194, 107, 44);
		leaveButton.setBackground(BRIGHT_BG);
		add(leaveButton);
		
		JLabel waitingForBoard = new JLabel("Waiting for board");
		waitingForBoard.setForeground(Color.BLACK);
		waitingForBoard.setFont(new Font("Tahoma", Font.PLAIN, 20));
		waitingForBoard.setBounds(96, 199, 155, 30);
		add(waitingForBoard);
		
		JLabel playersInBoard = new JLabel("Players in board");
		playersInBoard.setForeground(Color.BLACK);
		playersInBoard.setFont(new Font("Tahoma", Font.PLAIN, 20));
		playersInBoard.setBounds(96, 146, 155, 30);
		add(playersInBoard);
		
		usernameLabel = new JLabel("username");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		usernameLabel.setForeground(Color.ORANGE);
		usernameLabel.setBackground(BRIGHT_BG);
		usernameLabel.setBounds(27, 18, 329, 70);
		add(usernameLabel);
		
		JLabel totalTokensLabel = new JLabel("Total tokens");
		totalTokensLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		totalTokensLabel.setForeground(Color.BLACK);
		totalTokensLabel.setBounds(366, 18, 136, 30);
		add(totalTokensLabel);
		
		msgLabel = new JLabel("");
		msgLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		msgLabel.setForeground(Color.BLACK);
		msgLabel.setBounds(302, 375, 244, 28);
		add(msgLabel);
		
		JLabel winLabel = new JLabel("win");
		winLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		winLabel.setForeground(Color.BLACK);
		winLabel.setBounds(96, 285, 52, 35);
		add(winLabel);
		
		JLabel loseLabel = new JLabel("lose");
		loseLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loseLabel.setForeground(Color.BLACK);
		loseLabel.setBounds(96, 330, 78, 35);
		add(loseLabel);

		JLabel timeLabel = new JLabel("game time");
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		timeLabel.setForeground(Color.BLACK);
		timeLabel.setBounds(96, 375, 78, 35);
		add(timeLabel);
		
		totalTokensAmount = new JTextField();
		totalTokensAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		totalTokensAmount.setForeground(Color.BLACK);
		totalTokensAmount.setEditable(false);
		totalTokensAmount.setBounds(525, 22, 176, 30);
		totalTokensAmount.setBackground(BRIGHT_BG);
		add(totalTokensAmount);
		totalTokensAmount.setColumns(10);
		
		JLabel chooseBoardLabel = new JLabel("choose board");
		chooseBoardLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		chooseBoardLabel.setForeground(Color.BLACK);
		chooseBoardLabel.setBounds(366, 85, 136, 30);
		add(chooseBoardLabel);
		
		JLabel statisticsLabel = new JLabel("statistics");
		statisticsLabel.setForeground(Color.BLACK);
		statisticsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		statisticsLabel.setBounds(96, 251, 107, 28);
		add(statisticsLabel);
		
		playersAmount = new JTextField();
		playersAmount.setEditable(false);
		playersAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		playersAmount.setForeground(Color.BLACK);
		playersAmount.setBounds(261, 146, 107, 30);
		playersAmount.setBackground(BRIGHT_BG);
		add(playersAmount);
		playersAmount.setColumns(10);
		
		waitingAmount = new JTextField();
		waitingAmount.setEditable(false);
		waitingAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		waitingAmount.setForeground(Color.BLACK);
		waitingAmount.setColumns(10);
		waitingAmount.setBackground(BRIGHT_BG);
		waitingAmount.setBounds(261, 201, 107, 30);
		add(waitingAmount);
		
		winAmount = new JTextField();
		winAmount.setEditable(false);
		winAmount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		winAmount.setForeground(Color.BLACK);
		winAmount.setColumns(10);
		winAmount.setBackground(BRIGHT_BG);
		winAmount.setBounds(173, 292, 107, 20);
		add(winAmount);
		
		timeField = new JTextField();
		timeField.setEditable(false);
		timeField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		timeField.setForeground(Color.BLACK);
		timeField.setBackground(BRIGHT_BG);
		timeField.setBounds(173, 384, 107, 20);
		timeField.setColumns(10);
		add(timeField);

		loseAmount = new JTextField();
		loseAmount.setEditable(false);
		loseAmount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loseAmount.setForeground(Color.BLACK);
		loseAmount.setColumns(10);
		loseAmount.setBackground(BRIGHT_BG);
		loseAmount.setBounds(173, 339, 107, 20);

		add(loseAmount);
		
		logoutButton = new JButton("Log out");
		logoutButton.setForeground(Color.WHITE);
		logoutButton.setBackground(EXIT_COLOR);
		logoutButton.setBounds(10, 466, 89, 23);
		add(logoutButton);

		addActionListeners();
	}

	private void addActionListeners(){
		boardComboBox.addActionListener(e -> {
			if(waiting != null && players != null && waiting.size() > 0 && players.size() > 0){
				if(boardComboBox.getItemCount() > 0){
					playersAmount.setText(String.valueOf(players.get(boardComboBox.getSelectedIndex())));
					waitingAmount.setText(String.valueOf(waiting.get(boardComboBox.getSelectedIndex())));
				}

			}
		});

		refreshButton.addActionListener(e -> {
			ObjectInputStream in = mainPanel.getIn();
			ObjectOutputStream out = mainPanel.getOut();

			try {
				out.writeObject(new BoardListRequest());
				ServerRespond respond = (ServerRespond) in.readObject();
				if(respond.getCode() == OK){
					BoardListRespond boardRespond = (BoardListRespond) respond;
					refreshBoardList(boardRespond);
				}
			} catch (IOException | ClassNotFoundException e1) {
				msgLabel.setText("server failed");
			}
		});

		logoutButton.addActionListener(e -> {
			leaveBoard();
			mainPanel.enterLogin();
		});

		joinButton.addActionListener(e -> {
			if(waitingBoardTask != null) {
				msgLabel.setText("you are already waiting for a board");
				return;
			}
			ObjectInputStream in = mainPanel.getIn();
			ObjectOutputStream out = mainPanel.getOut();

			String boardName = (String) boardComboBox.getSelectedItem();
			GameRequest request = new GameRequest(boardName, login, username);

			try {
				out.writeObject(request);
				waitingBoardTask = new WaitingBoardTask(login, in, mainPanel, timeField);
				new Thread(waitingBoardTask).start();
				if(players.size() > 2)
					msgLabel.setText("waiting for the end of turn");
				else
					msgLabel.setText("waiting for other players");


			} catch (IOException e1) {
				msgLabel.setText("server failed");
			}
		});

		leaveButton.addActionListener(e -> {
			leaveBoard();
			msgLabel.setText("left the board");
		});

	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setBoards(List<String> boards) {
		this.boards = boards;
		boardComboBox.removeAllItems();
		for(String boardName : boards){
			boardComboBox.addItem(boardName);
		}
	}

	public void setPlayers(List<Integer> players) {
		this.players = players;
	}

	public void setWaiting(List<Integer> waiting) {
		this.waiting = waiting;
	}

	public void setUsername(String username) {
		this.username = username;
		usernameLabel.setText(username);
	}

	public void setTotalTokens(int totalTokens) {
		this.totalTokens = totalTokens;
		totalTokensAmount.setText(String.valueOf(totalTokens));
	}

	public void setWin(int win) {
		this.win = win;
		winAmount.setText(String.valueOf(win));
	}

	public void setLose(int lose) {
		this.lose = lose;
		loseAmount.setText(String.valueOf(lose));
	}

	private void refreshBoardList(BoardListRespond boardRespond){
		setBoards(boardRespond.getBoards());
		setPlayers(boardRespond.getPlayers());
		setWaiting(boardRespond.getWaiting());
	}

	private void leaveBoard(){
		if(waitingBoardTask != null){
			try {
				waitingBoardTask.leaveBoard();
				waitingBoardTask = null;
				msgLabel.setText("left the board");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}else
			msgLabel.setText("you do not wait for any table");
	}

	public void refreshAccountPanel(){

		try {
			AccountInfoRequest request = new AccountInfoRequest(login);
			ObjectInputStream in = mainPanel.getIn();
			ObjectOutputStream out = mainPanel.getOut();
			out.writeObject(request);
			ServerRespond respond = (ServerRespond) in.readObject();
			if(respond instanceof AccountInfoRespond){
				AccountInfoRespond accountInfo = (AccountInfoRespond) respond;
				mainPanel.enterAccount(login, accountInfo);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
