package com.example.cardgui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class CardGame extends Application {

    private Map<Integer, Image> cardImageMap;
    // private int cardTypeNumber = 2;
    // private Image cardImage[0] = Image(getClass().getResourceAsStream("lion.jpg"));
    // private Image cardImage[1] = new Image(getClass().getResourceAsStream("apple.jpg"));
    
    

    private Image cardBack = new Image(getClass().getResourceAsStream("blue.jpg"));

    // set start scene

    Label welcome = new Label("Welcome to card game!");
    Button startButton = new Button("Start");
    VBox startBox = new VBox(welcome, startButton);

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


    @Override
    public void start(Stage stage) throws IOException {
        cardImageMap.put(0, new Image(getClass().getResourceAsStream("lion.jpg")));
        cardImageMap.put(1, new Image(getClass().getResourceAsStream("apple.jpg")));
        Card cards[] = new Card[cardImageMap.size() * 2];
        for (int i = 0; i < cards.length; i++) 
        {
            cards[i].setCard(i / 2, cardImageMap.get(i / 2), cardBack);
        }



        // set start scene

        startButton.setOnAction(e -> stage.setScene(gamScene));
        startBox.setSpacing(10);
        startBox.setStyle("-fx-alignment: center;");
        Scene startScene = new Scene(startBox);

        /*
         * 先定義好卡片 
         * 之後有需要時再洗牌本身
         */
        

        // set card image
        

        int cardNumber = cardMap.size() * 2;
        int cardValueArray[] = new int[cardNumber];
        for (int i = 0; i < cardNumber; i++) 
        {
            cardValueArray[i] = i / 2;
        }
        shuffleArray(cardValueArray);

        // debug ---
        for (int i = 0; i < cardValueArray.length; i++) 
        {
            System.out.println(cardValueArray[i]);
        }
        // debug end ---

        // set game scene
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        


        Card[] cards = new Card[cardNumber];
        for (int i = 0; i < cardNumber; i++) 
        {
            cards[i].setCard(i, cardMap.get(i), cardBack);
            gridPane.add(cards[i].button, i % 4, i / 4);
        }


        stage.setTitle("Hello!");
        stage.setScene(scene);
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
}