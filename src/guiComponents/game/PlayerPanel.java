package guiComponents.game;

import javax.swing.*;

import static guiComponents.GUISettings.backgroundColor;

public class PlayerPanel extends JPanel {
	private UserPanel userPanel;
	private TokensPanel tokensPanel;

	public PlayerPanel(boolean reflect) {
		super();
		setLayout(null);
		setBackground(backgroundColor);
		setSize(165, 135);
		
		userPanel = new UserPanel(reflect);
		tokensPanel = new TokensPanel(reflect);
		
		userPanel.setBounds(0, 54, 135, 110);
		tokensPanel.setBounds(0, 0, 90, 55);
		if(reflect) {
			userPanel.setBounds(0, 0, 135, 110);
			tokensPanel.setBounds(0, 110, 90, 55);
		}
		
		add(userPanel);
		userPanel.setLayout(null);
		
		
		add(tokensPanel);
		tokensPanel.setLayout(null);
	}
	
	public UserPanel getUserPanel() {
		return userPanel;
	}
	
	public TokensPanel getTokensPanel() {
		return tokensPanel;
	}

}
