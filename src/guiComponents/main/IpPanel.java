package guiComponents.main;

import constants.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static guiComponents.GUISettings.PANEL_HEIGHT;
import static guiComponents.GUISettings.PANEL_WIDTH;

public class IpPanel extends JPanel {
	
	private JTextField IpTextField;
	private JButton connectButton;
	private JLabel connectResultLabel;
	private JButton exitButton;
	private JCheckBox localhostCheckBox;
	private static BufferedImage mainBackground;

	private MainPanel mainPanel;

	static {
		try {
			mainBackground = ImageIO.read(new File("src/graphics/textures/main3s.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public IpPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		setLayout(null);
		setSize(PANEL_WIDTH, PANEL_HEIGHT);

		IpTextField = new JTextField();
		IpTextField.setForeground(Color.BLACK);
		IpTextField.setBackground(SystemColor.activeCaption);
		IpTextField.setBounds(346, 213, 157, 38);
		add(IpTextField);
		IpTextField.setColumns(10);

		JLabel IpLabel = new JLabel("IP: ");
		IpLabel.setForeground(Color.WHITE);
		IpLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		IpLabel.setBounds(290, 216, 46, 35);
		add(IpLabel);

		connectButton = new JButton("connect");
		connectButton.setBackground(SystemColor.activeCaption);
		connectButton.setForeground(Color.BLACK);
		connectButton.setFont(new Font("Tahoma", Font.PLAIN, 20));

		connectButton.setBounds(290, 322, 213, 38);
		add(connectButton);

		connectResultLabel = new JLabel("");
		connectResultLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		connectResultLabel.setForeground(new Color(255, 0, 0));
		connectResultLabel.setBounds(290, 142, 213, 47);
		add(connectResultLabel);

		localhostCheckBox = new JCheckBox("use localhost");
		localhostCheckBox.setForeground(Color.BLACK);
		localhostCheckBox.setBackground(SystemColor.activeCaption);
		localhostCheckBox.setBounds(290, 265, 213, 38);
		add(localhostCheckBox);

		exitButton = new JButton("exit");
		exitButton.setBackground(Color.RED);
		exitButton.setForeground(Color.WHITE);
		exitButton.setBounds(701, 11, 89, 23);
		add(exitButton);

		mainPanel.setTile("choose ip");
		addActionListeners();
	}

	public void addActionListeners(){
		connectButton.addActionListener(e -> {
			try {
				InetAddress ip;
				if(localhostCheckBox.isSelected()){
					ip = InetAddress.getLocalHost();
				}else{
					String address = IpTextField.getText();
					ip = InetAddress.getByName(address);
				}
				Socket socket = new Socket(ip, Constants.SERVER_PORT);
				mainPanel.setSocket(socket);
				mainPanel.enterLogin();

			} catch (UnknownHostException e1) {
				connectResultLabel.setText("unknown address");
			} catch (IOException e1) {
				connectResultLabel.setText("connection failed");
			}
		});

		exitButton.addActionListener(e -> {
			System.exit(0);
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(mainBackground != null)
			g.drawImage(mainBackground, 0, 0, null);
		else
			this.setBackground(new Color(0,0,0));
	}
}
