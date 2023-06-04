package com.example.cardgui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Card{
    Button button;
    boolean face = false;
    Image cardFront;
    Image cardBack;
    int cardValue;
    public Card()
    {
    }

    public void setCard(int cardValue, Image cardFront, Image cardBack)
    {
        this.cardValue = cardValue;
        this.cardFront = cardFront;
        this.cardBack = cardBack;
    }


    public void flipCard()
    {
        face = !face;
        checkFace();
    }

    public void checkFace()
    {
        if(face)
        {
            button.setGraphic(new ImageView(cardFront));
        }
        else
        {
            button.setGraphic(new ImageView(cardBack));
        }
    }
    
}
