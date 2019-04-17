package gameLogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Card implements Serializable {
    private static final String startPath = "src/graphics/cards/";
    private static final String cardBack = "src/graphics/cards/card_back.png";

    private CardRank cardRank;
    private CardColor cardColor;

    public Card() {
    }

    public Card(CardRank cardRank, CardColor cardColor) {
        this.cardRank = cardRank;
        this.cardColor = cardColor;
    }

    public CardRank getCardRank() {
        return cardRank;
    }
    public CardColor getCardColor() {
        return cardColor;
    }

    public ImageIcon getImageIcon() {
        String rank = cardRank.name().toLowerCase();
        String color = cardColor.name().toLowerCase();
        String path = startPath + color + "/" + rank + "_" + color + ".png";
        try {
            return new ImageIcon(ImageIO.read(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("not found card texture");
        }
    }

    public static ImageIcon getBack() {
        try {
            return new ImageIcon(ImageIO.read(new File(cardBack)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("not found card texture");
        }
    }

    @Override
    public String toString() {
        return cardRank.name() + " " + cardColor.name();
    }

}
