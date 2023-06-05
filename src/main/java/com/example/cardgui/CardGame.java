package com.example.cardgui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class CardGame extends Application {

    // define card
    
    HashMap<Integer, Image> cardImageMap = new HashMap<Integer,Image>();
    Card cards[];
    int flippedCardNumber = 0;
    int firstCardIndex = -1, secondCardIndex = -1;
    
    Image cardLion = new Image(getClass().getResourceAsStream("lion.jpg"));
    Image cardBack = new Image(getClass().getResourceAsStream("blue.jpg"));
    Image cardApple = new Image(getClass().getResourceAsStream("apple.jpg"));
    

    // set start scene

    Label welcome = new Label("Welcome to card game!");
    Button startButton = new Button("Start");
    VBox startBox = new VBox(welcome, startButton);
    Scene startScene = new Scene(startBox, 700, 700);

    // set game scene

    int score = 0;

    GridPane gridPane = new GridPane();
    VBox gameBox = new VBox(gridPane);
    Scene gamScene = new Scene(gameBox, 700, 700);

    // set end scene

    Label endLabel = new Label("Game finished!");
    Button endButton = new Button("Restart");
    Button exitButton = new Button("Exit");
    HBox buttonBox = new HBox(endButton, exitButton);
    VBox endBox = new VBox(endLabel, buttonBox);
    Scene endScene = new Scene(endBox, 700, 700);


    @Override
    public void start(Stage stage) throws IOException {
        cardImageMap.put(0, cardLion);
        cardImageMap.put(1, cardApple);
        cards = new Card[cardImageMap.size() * 2];
        for (int i = 0; i < cards.length; i++) 
        {
            cards[i] = new Card();
            cards[i].setCard(i / 2, cardImageMap.get(i / 2), cardBack);
        }



        // set start scene

        startButton.setOnAction(e -> 
        {
            score = 0;
            flippedCardNumber = 0;
            firstCardIndex = -1;
            secondCardIndex = -1;
            for(int i = 0; i < cards.length; i++)
            {
                final int index = i;
                cards[index].face = false;
                cards[index].checkFace();
                cards[index].button.setDisable(false);
                cards[index].button.setVisible(true);

            }
            shuffleCards();
            stage.setScene(gamScene);
        });
        startBox.setSpacing(10);
        startBox.setStyle("-fx-alignment: center;");

        // set game scene
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        int scoreNumber = 0;

        for (int i = 0; i < cards.length; i++) 
        {
            final int index = i;
            cards[index].cardBack = cardBack;
            cards[index].checkFace();
            cards[index].button.setOnAction(e -> 
            {
                flippedCardNumber++;
                checkCard(index);
                if (score == cards.length / 2)
                {
                    stage.setScene(endScene);
                }
            });
            gridPane.add(cards[index].button, index%2, index/2);
        }
        gameBox.setSpacing(10);
        gameBox.setStyle("-fx-alignment: center;");

        // set end scene

        endButton.setOnAction(e -> 
        {
            stage.setScene(startScene);
        });
        exitButton.setOnAction(e -> 
        {
            stage.close();
        });
        buttonBox.setSpacing(10);
        buttonBox.setStyle("-fx-alignment: center;");
        endBox.setSpacing(10);
        endBox.setStyle("-fx-alignment: center;");


        stage.setTitle("Card Game");
        stage.setScene(startScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void shuffleCards()
    {
        int tempInt;
        Image tempImage;
        int index;
        Random random = new Random();
        for (int i = cards.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            tempInt = cards[index].cardValue;
            tempImage = cards[index].cardFront;
            cards[index].cardValue = cards[i].cardValue;
            cards[index].cardFront = cards[i].cardFront;
            cards[i].cardValue = tempInt;
            cards[i].cardFront = tempImage;

        }
    }

    public void checkCard(int index)
    {
        cards[index].flipCard();
        cards[index].button.setDisable(true);
        if (flippedCardNumber == 1)
        {
            firstCardIndex = index;
            return;
        }
        else if (flippedCardNumber == 2)
        {
            for(int i = 0; i < cards.length; i++)
            {
                cards[i].button.setDisable(true);
            }
            secondCardIndex = index;
            boolean isSame = cards[firstCardIndex].cardValue == cards[secondCardIndex].cardValue;
            if (isSame)
            {
                score ++;
            }

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> 
            {
                if (isSame)
                {
                    cards[firstCardIndex].button.setVisible(false);
                    cards[secondCardIndex].button.setVisible(false);
                }
                cards[firstCardIndex].flipCard();
                cards[secondCardIndex].flipCard();
                flippedCardNumber = 0;
                firstCardIndex = -1;
                secondCardIndex = -1;
                for(int i = 0; i < cards.length; i++)
                {
                    if (cards[i].button.isVisible())
                    {
                        cards[i].button.setDisable(false);
                    }
                }
            }));

            timeline.play();
        }
    }

}