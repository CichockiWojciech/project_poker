package guiComponents.main;

import networkInterfaces.controlInterfaces.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import static guiComponents.GUISettings.*;
import static networkInterfaces.controlInterfaces.ErrorCode.OK;

public class LoginPanel extends JPanel {
	private MainPanel mainPanel;
	private JButton exitButton;

	private JTextField usernameField;
	private JTextField loginRegisterField;
	private JTextField loginField;
	private JPasswordField passwordLogin;
	private JPasswordField passwordRegisterConfirm;
	private JPasswordField passwordRegister;
	private JButton registerButton;
	private JButton loginButton;
	private JTextField messageField;
	

	public LoginPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(BACKGROUND);
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLogin.setBounds(405, 145, 170, 30);
		add(lblLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(405, 185, 170, 30);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(405, 225, 170, 30);
		add(lblPassword);
		
		JLabel lblRepeatPassword = new JLabel("Confirm Password");
		lblRepeatPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRepeatPassword.setBounds(405, 265, 170, 30);
		add(lblRepeatPassword);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameField.setColumns(10);
		usernameField.setBackground(BRIGHT_BG);
		usernameField.setBounds(584, 185, 170, 30);
		add(usernameField);
		
		loginRegisterField = new JTextField();
		loginRegisterField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loginRegisterField.setColumns(10);
		loginRegisterField.setBackground(BRIGHT_BG);
		loginRegisterField.setBounds(584, 145, 170, 30);
		add(loginRegisterField);
		
		registerButton = new JButton("Register");
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		registerButton.setBackground(BRIGHT_BG);
		registerButton.setBounds(405, 315, 349, 30);
		add(registerButton);
		
		JLabel label = new JLabel("Login");
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label.setBounds(28, 145, 170, 30);
		add(label);
		
		JLabel label_1 = new JLabel("Password");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_1.setBounds(28, 185, 170, 30);
		add(label_1);
		
		loginField = new JTextField();
		loginField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loginField.setColumns(10);
		loginField.setBackground(BRIGHT_BG);
		loginField.setBounds(130, 145, 170, 30);
		add(loginField);
		
		loginButton = new JButton("Log in");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loginButton.setBounds(28, 229, 272, 30);
		loginButton.setBackground(BRIGHT_BG);
		add(loginButton);
		
		passwordLogin = new JPasswordField();
		passwordLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordLogin.setBounds(130, 185, 170, 30);
		passwordLogin.setBackground(BRIGHT_BG);
		add(passwordLogin);
		
		passwordRegisterConfirm = new JPasswordField();
		passwordRegisterConfirm.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordRegisterConfirm.setBounds(584, 265, 170, 30);
		passwordRegisterConfirm.setBackground(BRIGHT_BG);
		add(passwordRegisterConfirm);
		
		passwordRegister = new JPasswordField();
		passwordRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordRegister.setBounds(584, 225, 170, 30);
		passwordRegister.setBackground(BRIGHT_BG);
		add(passwordRegister);
		
		exitButton = new JButton("exit");
		exitButton.setBackground(Color.RED);
		exitButton.setForeground(Color.WHITE);
		exitButton.setBounds(665, 11, 89, 23);
		add(exitButton);

		messageField = new JTextField();
		messageField.setForeground(Color.RED);
		messageField.setEditable(false);
		messageField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		messageField.setBounds(28, 408, 726, 30);
		messageField.setBackground(BRIGHT_BG);
		add(messageField);
		messageField.setColumns(10);

		addActionListeners();
	}

	public void addActionListeners(){

		exitButton.addActionListener(e -> {

			System.exit(0);
		});

		registerButton.addActionListener(e ->{
			ObjectInputStream in = mainPanel.getIn();
			ObjectOutputStream out = mainPanel.getOut();

			String login = loginRegisterField.getText();
			String username = usernameField.getText();
			char[] password = passwordRegister.getPassword();
			char[] confirmPassword = passwordRegisterConfirm.getPassword();
			if(login.length() == 0){
				messageField.setText("login can not be empty");
				return;
			}
			if(username.length() == 0){
				messageField.setText("username can not be empty");
				return;
			}
			if(!Arrays.equals(password, confirmPassword)){
				messageField.setText("passwords are not equal");
				return;
			}
			ClientRequest request = new RegisterRequest(username, login, password);
			sendRequest(request, out);

			try {
				// wait for respond
				ServerRespond respond = (ServerRespond) in.readObject();

				if(respond.getCode() == OK){
					clearPanel();
					AccountInfoRespond accountInfoRespond = (AccountInfoRespond) respond;
					mainPanel.enterAccount(login, accountInfoRespond);
					mainPanel.setTile("account: " + login);
				}else{
					messageField.setText("error: " + respond.getCode().toString());
				}
			} catch (IOException | ClassNotFoundException  e1) {
				messageField.setText("can not connect with server");
				e1.printStackTrace();
			}

		});

		loginButton.addActionListener(e -> {
			ObjectInputStream in = mainPanel.getIn();
			ObjectOutputStream out = mainPanel.getOut();

			String login = loginField.getText();
			char[] password = passwordLogin.getPassword();

			ClientRequest request = new LoginRequest(login, password);
			sendRequest(request, out);

			try {
				ServerRespond respond = (ServerRespond) in.readObject();

				if(respond.getCode() == OK){
					clearPanel();
					AccountInfoRespond accountInfoRespond = (AccountInfoRespond) respond;
					mainPanel.enterAccount(login, accountInfoRespond);
					mainPanel.setTile("account: " + login);
				}else{
					messageField.setText("error: " + respond.getCode().toString());
				}

			} catch (IOException | ClassNotFoundException e1) {
				messageField.setText("can not connect with server");
				e1.printStackTrace();
			}
		});
	}

	private void clearPanel(){
		usernameField.setText("");
		loginRegisterField.setText("");
		loginField.setText("");
		messageField.setText("");
		passwordLogin.setText("");
		passwordRegisterConfirm.setText("");
		passwordRegister.setText("");

	}

	private void sendRequest(ClientRequest request, ObjectOutputStream out){
		try {
			out.writeObject(request);
		} catch (IOException e) {
			messageField.setText("can not connect with server");
		}
	}

}
