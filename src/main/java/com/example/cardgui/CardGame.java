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

    GridPane gridPane = new GridPane();
    Label score = new Label(" / ");
    VBox gameBox = new VBox(gridPane, score);
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
            shuffleCards(cards);
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
            cards[index].button.setOnAction(e -> 
            {
                cards[index].flipCard();
                flippedCardNumber++;
                checkCard(index);
            });
            gridPane.add(cards[index].button, index%2, index/2);
        }


        stage.setTitle("Hello!");
        stage.setScene(startScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void shuffleCards(Card[] array)
    {
        Card temp;
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public void checkCard(int index)
    {
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
                cards[firstCardIndex].button.setDisable(true);
                cards[secondCardIndex].button.setDisable(true);
                flippedCardNumber = 0;
                firstCardIndex = -1;
                secondCardIndex = -1;
            }
            else
            {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> 
                {
                    cards[firstCardIndex].flipCard();
                    cards[secondCardIndex].flipCard();
                    flippedCardNumber = 0;
                    firstCardIndex = -1;
                    secondCardIndex = -1;
                }));
                timeline.play();
            }
        }
    }

}