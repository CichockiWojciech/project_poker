package guiComponents.game;

import gameLogic.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static guiComponents.GUISettings.backgroundColor;

public class BoardCardPanel extends JPanel {
	private CardLabel card1 = new CardLabel();
	private CardLabel card2 = new CardLabel();
	private CardLabel card3= new CardLabel();
	private CardLabel card4 = new CardLabel();
	private CardLabel card5 = new CardLabel();
	private TokensPanel tokensPanel;
	private JLabel winner;

	private List<CardLabel> cards = new ArrayList<>();
	{
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		cards.add(card4);
		cards.add(card5);
	}


	public BoardCardPanel() {
		setLayout(null);
		setBackground(backgroundColor);
		setSize(340, 150);
		
		card2.setBounds(70, 20, 60, 75);
		add(card2);
		
		card1.setBounds(0, 20, 60, 75);
		add(card1);
		
		card3.setBounds(140, 20, 60, 75);
		add(card3);
		
		card4.setBounds(210, 20, 60, 75);
		add(card4);
		
		card5.setBounds(280, 20, 60, 75);
		add(card5);
		
		tokensPanel = new TokensPanel(false);
		tokensPanel.setBounds(129, 98, 90, 55);
		add(tokensPanel);
		
		winner = new JLabel();
		winner.setForeground(Color.BLACK);
		winner.setFont(new Font("Tahoma", Font.BOLD, 14));
		winner.setBounds(0, 0, 340, 13);
		add(winner);

		hideCards();
	}
	
	public void setCards(Card...cards) {
		int i = 0;
		for(Card c: cards){
			this.cards.get(i++).setCard(c);
		}

	}
	
	public void setCard(Card card, int index) {
		this.cards.get(index).setCard(card);
		setVisible(true, index);
	}
	
	public void setVisibleCard(int toIndex) {
		for(int i = 0 ; i < toIndex ; ++i)
			this.cards.get(i).setVisible(true);
	}

	public void hideCards(){
		for(CardLabel card : cards)
			card.setVisible(false);
	}
	
	public void setVisible(boolean visible, int index) {
		this.cards.get(index).setVisible(visible);
	}
	
	public TokensPanel getTokens() {
		return tokensPanel;
	}
	
	public void setWinner(String name) {
		winner.setText(name);
	}
	
	
}
