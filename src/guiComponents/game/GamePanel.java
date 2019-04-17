package guiComponents.game;

import client.AppFrame;
import gameLogic.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static guiComponents.GUISettings.*;

public class GamePanel extends JPanel {
	private AppFrame frame;
	private JTextField raiseAmount;

	private JTextField totalTokens;
	private JTextField actionResultMsg;
	private JTextField handType;

	private JButton foldButton;
	private JButton checkButton;
	private JButton raiseButton;
	private JButton leaveBoardButton;

	private List<PlayerPanel> players = new LinkedList<>();
	private BoardCardPanel boardCardPanel;

	private static BufferedImage boardImg;
	private static BufferedImage controlBg;
	private static BufferedImage cardTmp;
	private static ImageIcon cardIcon;
	private static ImageIcon tokenImg;
	private static BufferedImage decisionBg;

    static{
        try {
        	boardImg = ImageIO.read(new File("src/graphics/textures/planeboard.png"));
        	controlBg = ImageIO.read(new File("src/graphics/textures/background3.png"));
        	cardTmp = ImageIO.read(new File("src/graphics/cards/card_back.png"));
        	cardIcon = new ImageIcon(cardTmp);
        	tokenImg = new ImageIcon(ImageIO.read(new File("src/graphics/textures/tokensBg.png")));
        	decisionBg = ImageIO.read(new File("src/graphics/textures/background4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public GamePanel(AppFrame frame) {
		this.frame = frame;
		setLayout(null);
		
		JPanel boardPanel = new JPanel() {
			protected void paintComponent(Graphics g) {  
			  super.paintComponent(g);
			  if(boardImg != null)
				  g.drawImage(boardImg, 0, 0, null);
			}
		};
		boardPanel.setBounds(10, 10, 1004, 580);
		add(boardPanel);
		boardPanel.setLayout(null);
		
		PlayerPanel player5Panel = new PlayerPanel(true);
		player5Panel.setBounds(240, 55, 135, 165);
		boardPanel.add(player5Panel);
		
		boardCardPanel = new BoardCardPanel();
		boardCardPanel.setBounds(317, 228, 340, 150);
		boardPanel.add(boardCardPanel);
		
		PlayerPanel player1Panel = new PlayerPanel(false);
		player1Panel.setBounds(240, 353, 135, 165);
		boardPanel.add(player1Panel);
		
		PlayerPanel player6Panel = new PlayerPanel(false);
		player6Panel.setBounds(81, 202, 135, 165);
		boardPanel.add(player6Panel);
		
		PlayerPanel player2Panel = new PlayerPanel(false);
		player2Panel.setBounds(652, 352, 135, 165);
		boardPanel.add(player2Panel);
		
		PlayerPanel player3Panel = new PlayerPanel(false);
		player3Panel.setBounds(782, 202, 135, 165);
		boardPanel.add(player3Panel);
		
		PlayerPanel player4Panel = new PlayerPanel(true);
		player4Panel.setBounds(652, 55, 135, 165);
		boardPanel.add(player4Panel);
		
		// add palyer to list
		players.add(player1Panel);
		players.add(player2Panel);
		players.add(player3Panel);
		players.add(player4Panel);
		players.add(player5Panel);
		players.add(player6Panel);
		

		
		JPanel controlPanel = new JPanel() {
			protected void paintComponent(Graphics g) {  
				  super.paintComponent(g);
				  if(controlBg != null)
					  g.drawImage(controlBg, 0, 0, null);
				  else
					  this.setBackground(new Color(0,0,0));
				  
				}
		};
		controlPanel.setBounds(20, 588, 994, 180);
		add(controlPanel);
		controlPanel.setLayout(null);
		
		JPanel decisionPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {  
				  super.paintComponent(g);
				  if(decisionBg != null)
					  g.drawImage(decisionBg, 0, 0, null);
				  else
					  this.setBackground(new Color(0,0,0));
				  
				}
		};
		decisionPanel.setBounds(714, 18, 270, 126);
		controlPanel.add(decisionPanel);
		
		foldButton = new JButton("FOLD");
		foldButton.setFont(new Font("Tahoma", Font.BOLD, 14));

		checkButton = new JButton("CHECK");
		checkButton.setFont(new Font("Tahoma", Font.BOLD, 14));

		raiseButton = new JButton("RAISE");
		raiseButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		raiseAmount = new JTextField();
		raiseAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		raiseAmount.setColumns(10);
		
		JLabel raiseAmountLabel = new JLabel("amout");
		raiseAmountLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_decisionPanel = new GroupLayout(decisionPanel);
		gl_decisionPanel.setHorizontalGroup(
			gl_decisionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_decisionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_decisionPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(foldButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(raiseButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_decisionPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_decisionPanel.createSequentialGroup()
							.addComponent(raiseAmountLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(raiseAmount, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
						.addComponent(checkButton, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_decisionPanel.setVerticalGroup(
			gl_decisionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_decisionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_decisionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(foldButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_decisionPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_decisionPanel.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_decisionPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(raiseAmountLabel)
								.addComponent(raiseAmount, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(20, Short.MAX_VALUE))
						.addGroup(gl_decisionPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(raiseButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(20))))
		);
		decisionPanel.setLayout(gl_decisionPanel);
		
		leaveBoardButton = new JButton("leave board");
		leaveBoardButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		leaveBoardButton.setForeground(Color.WHITE);
		leaveBoardButton.setBackground(Color.RED);
		leaveBoardButton.setBounds(10, 10, 128, 50);
		controlPanel.add(leaveBoardButton);
		
		actionResultMsg = new JTextField();
		actionResultMsg.setFont(new Font("Tahoma", Font.PLAIN, 10));
		actionResultMsg.setText("You can not use this");
		actionResultMsg.setForeground(Color.RED);
		actionResultMsg.setBackground(Color.BLACK);
		actionResultMsg.setEnabled(false);
		actionResultMsg.setEditable(false);
		actionResultMsg.setBounds(426, 151, 558, 19);
		controlPanel.add(actionResultMsg);
		actionResultMsg.setColumns(10);
		
		JPanel infoPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {  
				  super.paintComponent(g);
				  if(decisionBg != null)
					  g.drawImage(decisionBg, 0, 0, null);
				  else
					  this.setBackground(new Color(0,0,0));
				  
				}
		};
		infoPanel.setForeground(Color.BLACK);
		infoPanel.setBounds(10, 70, 257, 91);
		controlPanel.add(infoPanel);
		
		JLabel totalToeknsLabel = new JLabel("total tokens");
		totalToeknsLabel.setForeground(Color.BLACK);
		totalToeknsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		totalTokens = new JTextField();
		totalTokens.setEditable(false);
		totalTokens.setForeground(new Color(0, 0, 0));
		totalTokens.setText("10000");
		totalTokens.setFont(new Font("Tahoma", Font.BOLD, 16));
		totalTokens.setColumns(10);
		totalToeknsLabel.setLabelFor(totalTokens);
		
		JLabel handTypeLabel = new JLabel("hand type");
		handTypeLabel.setForeground(Color.BLACK);
		handTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		handType = new JTextField();
		handType.setForeground(new Color(0, 0, 0));
		handType.setText("TWO PAIR");
		handType.setFont(new Font("Tahoma", Font.BOLD, 14));
		handType.setEditable(false);
		handType.setColumns(10);
		handTypeLabel.setLabelFor(handType);
		GroupLayout gl_infoPanel = new GroupLayout(infoPanel);
		gl_infoPanel.setHorizontalGroup(
			gl_infoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(totalToeknsLabel)
						.addComponent(handTypeLabel, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(totalTokens, 0, 0, Short.MAX_VALUE)
						.addComponent(handType, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_infoPanel.setVerticalGroup(
			gl_infoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(totalToeknsLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(totalTokens, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(handType, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(handTypeLabel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(18))
		);
		infoPanel.setLayout(gl_infoPanel);
	}
	
	/*  INTERFACE */
	public void setPlayer(String username, Card firstCard, Card secondCard, int index){
		PlayerPanel player = players.get(index);
		UserPanel user = player.getUserPanel();

		user.setUser(username);
		user.setUsernameEnabled(true);
		user.setColor(backgroundColor , playerNicknameColor);
		user.setCards(firstCard, secondCard);
		user.setVisible(true);
	}

	public void setPlayersCards(Card[] cards, int index){
		PlayerPanel player = players.get(index);
		UserPanel user = player.getUserPanel();

		user.setCards(cards[0], cards[1]);
		user.setVisible(true);
	}

	public void setFreeSeat(int index){
		PlayerPanel player = players.get(index);
		UserPanel user = player.getUserPanel();

		user.setUser("Free");
		user.setUsernameEnabled(true);
		user.setColor(backgroundColor , freeSeatColor);
		user.setVisible(false);
		user.setCards(null, null);

		TokensPanel tokens =  player.getTokensPanel();
		tokens.setText("");
		tokens.setVisible(false);
	}

	public void setPlayerTurn(int index){
		PlayerPanel player = players.get(index);
		UserPanel user = player.getUserPanel();
		for(PlayerPanel p : players)
			p.getUserPanel().setColor(backgroundColor);

		user.setColor(playerTurnColor);
	}

	public void setPlayerTokens(String txt, boolean showTokens, int index){
		PlayerPanel player = players.get(index);
		TokensPanel tokens =  player.getTokensPanel();
		tokens.setText(txt);
		tokens.setVisible(showTokens);
	}


	public void setBoardCards(Card[] cards){
		for(int i = 0 ; i < cards.length ; ++i){
			boardCardPanel.setCard(cards[i], i);
			boardCardPanel.setVisible(cards[i] != null, i);
		}
	}

	public void setWinner(String winner){
		if(winner.length() > 1)
			boardCardPanel.setWinner("Winner: " + winner);
		else
			boardCardPanel.setWinner("");
	}

	public void setJackpot(int jackpot){
		TokensPanel tokens = boardCardPanel.getTokens();

		tokens.setText("Jackpot: " + String.valueOf(jackpot));
		if(jackpot > 0)
			tokens.setVisible(true);
		else
			tokens.setVisible(false);
	}

	public void setTotalTokens(String totalTokens) {
		this.totalTokens.setText(totalTokens);
	}

	public void setActionResultMsg(String actionResultMsg) {
		this.actionResultMsg.setText(actionResultMsg);
	}

	public void setHandType(String handType) {
		this.handType.setText(handType);
	}

	public int getRaiseAmount() {
		return Integer.parseInt(raiseAmount.getText());
	}

	public void setFoldActionListener(ActionListener listener){
		foldButton.addActionListener(listener);
	}

	public void setCheckActionListener(ActionListener listener){
		checkButton.addActionListener(listener);
	}

	public void setRaiseActionListener(ActionListener listener){
		raiseButton.addActionListener(listener);
	}

	public void setLeaveBoardButtonActionListener(ActionListener listener){
		leaveBoardButton.addActionListener(listener);
	}
	
}
