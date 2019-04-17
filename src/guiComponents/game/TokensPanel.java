package guiComponents.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TokensPanel extends JPanel {

	private JLabel tokensAmount = new JLabel("");
	private JLabel tokensImgLabel = new JLabel("");
	
	public static final Color backgroundColor = new Color(93, 183, 121);
	
	private static final int X_IMG = 0;
	private static final int Y_IMG = 0;
	private static final int WIDTH_IMG = 50;
	private static final int HEIGHT_IMG = 35;

	private static final int X_AMOUNT = 0;
	private static final int Y_AMOUNT = 35;
	private static final int WIDTH_AMOUNT = 90;
	private static final int HEIGHT_AMOUNT = 17;

	private static final int X_IMG_R = 0;
	private static final int Y_IMG_R = 20;
	private static final int X_AMOUNT_R = 0;
	private static final int Y_AMOUNT_R = 0;
	
	private static ImageIcon tokensImg;
	static {
        try {
        	tokensImg = new ImageIcon(ImageIO.read(new File("src/graphics/textures/tokensBg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public TokensPanel(boolean reflect) {
		this(false, "", reflect);
	}

	public TokensPanel(boolean visible, String txt, boolean reflect) {
		setLayout(null);
		setSize(90, 55);
		setBackground(backgroundColor);
		

		tokensImgLabel.setBounds(X_IMG, Y_IMG, WIDTH_IMG, HEIGHT_IMG);
		tokensAmount.setBounds(X_AMOUNT, Y_AMOUNT, WIDTH_AMOUNT, HEIGHT_AMOUNT);
		if(reflect) {
			tokensImgLabel.setBounds(X_IMG_R, Y_IMG_R, 50, 35);
			tokensAmount.setBounds(X_AMOUNT_R, Y_AMOUNT_R, 90, 17);
		}
		
		add(tokensImgLabel);
		
		tokensAmount.setForeground(Color.BLACK);
		tokensAmount.setBackground(backgroundColor);

		add(tokensAmount);

		tokensImgLabel.setIcon(tokensImg);
		setVisible(visible);
		setText(txt);
	}

	public void setVisible(boolean visible) {
		tokensImgLabel.setVisible(visible);
	}
	
	public void setText(String txt) {
		tokensAmount.setText(txt);
	}
	

}
