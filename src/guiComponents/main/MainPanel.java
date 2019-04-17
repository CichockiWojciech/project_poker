package guiComponents.main;

import client.AppFrame;
import guiComponents.game.GamePanel;
import networkInterfaces.controlInterfaces.AccountInfoRespond;
import networkInterfaces.controlInterfaces.BoardListRespond;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import static guiComponents.GUISettings.*;

public class MainPanel extends JPanel {
	private static BufferedImage mainBackground;
	private JPanel currentPanel;
	private IpPanel ipPanel;
	private LoginPanel loginPanel;
	private AccountPanel accountPanel;

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	private AppFrame frame;
	
	/*public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;*/
	
	/*public static final int PANEL_X = 112;
	public static final int PANEL_Y = 134;*/
	
	static {
		try {
			mainBackground = ImageIO.read(new File("src/graphics/textures/main3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MainPanel(AppFrame frame) {
		this.frame = frame;
		setLayout(null);

		ipPanel = new IpPanel(this);
		loginPanel = new LoginPanel(this);
		accountPanel = new AccountPanel(this);

		setPanel(ipPanel);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(mainBackground != null)
			  g.drawImage(mainBackground, 0, 0, null);
		  else
			  this.setBackground(new Color(0,0,0));
	}

	private void setPanel(JPanel panel){
		currentPanel = panel;
		currentPanel.setBounds(PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT);
		add(panel);
		repaint();
	}

	private void removePanel(){
		remove(currentPanel);
		repaint();
	}

	private void switchPanel(JPanel panel){
		removePanel();
		setPanel(panel);
	}


	public void setSocket(Socket socket) throws IOException {
		this.socket = socket;
		this.in = new ObjectInputStream(socket.getInputStream());
		this.out = new ObjectOutputStream(socket.getOutputStream());
	}

	public ObjectInputStream getIn(){
		return in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void enterLogin(){
		this.switchPanel(loginPanel);
		setTile("login");
	}

	public void enterAccount(String login, AccountInfoRespond respond){
		accountPanel.setLogin(login);
		accountPanel.setTotalTokens(respond.getTotalTokens());
		accountPanel.setUsername(respond.getUsername());
		accountPanel.setLose(respond.getLoseAmount());
		accountPanel.setWin(respond.getWinAmount());
		BoardListRespond boardRespond = respond.getBoardList();
		accountPanel.setBoards(boardRespond.getBoards());
		accountPanel.setPlayers(boardRespond.getPlayers());
		accountPanel.setWaiting(boardRespond.getWaiting());

		switchPanel(accountPanel);
	}

	public void enterGame(){
		frame.enterGame();
	}

	public void backToAccount(){
		accountPanel.refreshAccountPanel();
		frame.backToAccount();
	}

	public GamePanel getGamePanel(){
		return frame.getGamePanel();
	}

	public void setTile(String tile){
		frame.setTitle(tile);
	}

	public InetAddress getServerIp(){
		return socket.getInetAddress();
	}

}
