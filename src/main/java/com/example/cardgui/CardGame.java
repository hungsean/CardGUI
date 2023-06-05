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
    Scene startScene = new Scene(startBox);

    // set game scene

    int score = 0;

    GridPane gridPane = new GridPane();
    VBox gameBox = new VBox(gridPane);
    Scene gamScene = new Scene(gameBox);

    // set end scene

    Label endLabel = new Label("Game finished!");
    Button endButton = new Button("Restart");
    Button exitButton = new Button("Exit");
    HBox buttonBox = new HBox(endButton, exitButton);
    VBox endBox = new VBox(endLabel, buttonBox);
    Scene endScene = new Scene(endBox);


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

        // set end scene

        

        stage.setTitle("Hello!");
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
            secondCardIndex = index;
            if (cards[firstCardIndex].cardValue == cards[secondCardIndex].cardValue)
            {
                score ++;
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> 
                {
                    cards[firstCardIndex].button.setVisible(false);
                    cards[secondCardIndex].button.setVisible(false);
                    flippedCardNumber = 0;
                    firstCardIndex = -1;
                    secondCardIndex = -1;
                }));
                timeline.play();

                
            }
            else
            {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> 
                {
                    cards[firstCardIndex].flipCard();
                    cards[secondCardIndex].flipCard();
                    cards[firstCardIndex].button.setDisable(false);
                    cards[secondCardIndex].button.setDisable(false);
                    flippedCardNumber = 0;
                    firstCardIndex = -1;
                    secondCardIndex = -1;
                }));
                timeline.play();
            }
        }
    }

}