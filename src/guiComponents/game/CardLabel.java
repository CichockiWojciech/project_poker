package guiComponents.game;

import gameLogic.Card;

import javax.swing.*;

public class CardLabel extends JLabel {
	private Card card;
	private static final int WIDTH = 60;
	private static final int HEIGHT = 75;

	
	public CardLabel() {
		super();
		setSize(WIDTH, HEIGHT); 
		paintCard();
	}
	
	public void setCard(Card card) {
		this.card = card;
		paintCard();
	}
	
	
	public void paintCard() {
		if(this.card != null) {
			setIcon(this.card.getImageIcon());
		}
		else {
			setIcon(Card.getBack());
		}
	}

}
