package guiComponents.game;

import gameLogic.Card;

import javax.swing.*;
import java.awt.*;

import static guiComponents.GUISettings.backgroundColor;

public class UserPanel extends JPanel {
	private JTextField username = new JTextField();
	private JLabel firstCard = new CardLabel();
	private JLabel secondCard = new CardLabel();

	private static int FIRST_CARD_X = 70;
	private static int FIRST_CARD_Y = 0;
	
	private static int SECOND_CARD_X = 0;
	private static int SECOND_CARD_Y = 0;
	
	private static int CARD_WIDTH = 60;
	private static int CARD_HEIGHT = 75;
	
	private static int USER_X = 0;
	private static int USER_Y = 85;
	private static int USER_WIDTH = 130;
	private static int USER_HEIGHT = 19;
	
	private static int FIRST_CARD_X_R = FIRST_CARD_X;
	private static int FIRST_CARD_Y_R = 35;
	
	private static int SECOND_CARD_X_R = SECOND_CARD_X;
	private static int SECOND_CARD_Y_R = FIRST_CARD_Y_R;

	private static int USER_X_R = SECOND_CARD_X;
	private static int USER_Y_R = SECOND_CARD_Y;


	public UserPanel(boolean reflect) {
		setBackground(backgroundColor);
		setLayout(null);
		setSize(135, 110);
		
		if(!reflect) {
			firstCard.setBounds(FIRST_CARD_X, FIRST_CARD_Y, CARD_WIDTH, CARD_HEIGHT);
			secondCard.setBounds(SECOND_CARD_X, SECOND_CARD_Y, CARD_WIDTH, CARD_HEIGHT);
			username.setBounds(USER_X, USER_Y, USER_WIDTH, USER_HEIGHT);
		}else {
			firstCard.setBounds(FIRST_CARD_X_R, FIRST_CARD_Y_R, CARD_WIDTH, CARD_HEIGHT);
			secondCard.setBounds(SECOND_CARD_X_R, SECOND_CARD_Y_R, CARD_WIDTH, CARD_HEIGHT);
			username.setBounds(USER_X_R, USER_Y_R, USER_WIDTH, USER_HEIGHT);
		}

		username.setEnabled(false);
		username.setEditable(false);
		username.setColumns(20);
		username.setFont(new Font("Tahoma", Font.BOLD, 12));

		add(firstCard);
		add(secondCard);
		add(username);
		setVisible(false);
	}
	
	public void setVisible(boolean visible) {
		firstCard.setVisible(visible);
		secondCard.setVisible(visible);
	}
	
	public void setUser(String user) {
		username.setText(user);
	}
	
	public void setCards(Card firstCard, Card secondCard) {
		((CardLabel)this.firstCard).setCard(firstCard);
		((CardLabel)this.secondCard).setCard(secondCard);
	}
	
	public void setUsernameEnabled(boolean enable) {
		username.setEnabled(enable);
	}
	
	public void setColor(Color background, Color txt) {
		username.setBackground(background);
		username.setForeground(txt);
	}

	public void setColor(Color background) {
		username.setBackground(background);
	}

}
