package com.example.cardgui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
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
    int firstCardIndex = -1, secondCardIndex = -1;
    int speed = 1000;
    
    Image cardLion = new Image(getClass().getResourceAsStream("lion_200.jpg"));
    Image cardBack = new Image(getClass().getResourceAsStream("blue_200.jpg"));
    Image cardApple = new Image(getClass().getResourceAsStream("apple_200.jpg"));
    Image cardGoat = new Image(getClass().getResourceAsStream("goat_200.jpg"));
    

    // set start scene

    Label welcome = new Label("Welcome to card game!");

    TilePane speedPane = new TilePane();
    Label speedLabel = new Label("Please choose the speed:");
    RadioButton SlowRadioButton = new RadioButton("slow");
    RadioButton MediumRadioButton = new RadioButton("medium");
    RadioButton FastRadioButton = new RadioButton("fast");

    Button startButton = new Button("Start");
    VBox startBox = new VBox(welcome, speedLabel, speedPane, startButton);
    Scene startScene = new Scene(startBox, 700, 700);

    // set game scene

    int score = 0;
    int finishedCardNumber = 0;
    Label scoreLabel = new Label("Score: " + score);
    GridPane gridPane = new GridPane();
    VBox gameBox = new VBox(gridPane, scoreLabel);
    Scene gameScene = new Scene(gameBox, 700, 700);

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
        cardImageMap.put(2, cardGoat);
        cards = new Card[cardImageMap.size() * 2];
        for (int i = 0; i < cards.length; i++) 
        {
            cards[i] = new Card();
            cards[i].setCard(i / 2, cardImageMap.get(i / 2), cardBack);
        }



        // set start scene

        speedPane.getChildren().addAll(SlowRadioButton, MediumRadioButton, FastRadioButton);
        speedPane.setStyle("-fx-alignment: center;");
        startButton.setDisable(true);
        SlowRadioButton.setOnAction(e -> 
        {
            if (SlowRadioButton.isSelected())
            {
                System.out.println("set slow");
                speed = 2000;
                MediumRadioButton.setSelected(false);
                FastRadioButton.setSelected(false);
                startButton.setDisable(false);
            }
            else
            {
                SlowRadioButton.setSelected(true);
            }
        });
        MediumRadioButton.setOnAction(e -> 
        {
            if (MediumRadioButton.isSelected())
            {
                System.out.println("set medium");
                speed = 1000;
                SlowRadioButton.setSelected(false);
                FastRadioButton.setSelected(false);
                startButton.setDisable(false);
            }
            else
            {
                MediumRadioButton.setSelected(true);
            }
        });
        FastRadioButton.setOnAction(e -> 
        {
            if (FastRadioButton.isSelected())
            {
                System.out.println("set fast");
                speed = 500;
                SlowRadioButton.setSelected(false);
                MediumRadioButton.setSelected(false);
                startButton.setDisable(false);
            }
            else
            {
                FastRadioButton.setSelected(true);
            }
        });

        startButton.setOnAction(e -> 
        {
            score = 10;
            firstCardIndex = -1;
            secondCardIndex = -1;
            finishedCardNumber = 0;
            for(int i = 0; i < cards.length; i++)
            {
                final int index = i;
                cards[index].face = true;
                cards[index].checkFace();
                cards[index].button.setDisable(false);
                cards[index].button.setVisible(true);

            }
            shuffleCards();
            scoreLabel.setText("Score: " + score);
            stage.setScene(gameScene);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(speed * 1.5), ev -> 
            {
                for(int i = 0; i < cards.length; i++)
                {
                    final int index = i;
                    cards[index].face = false;
                    cards[index].checkFace();
                }
            }));
            System.out.println("start");
            timeline.play();
            System.out.println("end");
        });
        startBox.setSpacing(10);
        startBox.setStyle("-fx-alignment: center;");

        // set game scene
        scoreLabel.setText("Score: " + score);
        scoreLabel.setStyle("-fx-alignment: center;");
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
                checkCard(index);
                scoreLabel.setText("Score: " + score);
                if (finishedCardNumber == cards.length / 2)
                {
                    endLabel.setText("Game finished! Your score is " + score);
                    stage.setScene(endScene);
                }
                else if (score < 0)
                {
                    endLabel.setText("Game failure!");
                    stage.setScene(endScene);
                }
            });
            cards[index].button.setPrefSize(100, 200);
            gridPane.add(cards[index].button, index/2, index%2);
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
        int cardFaceOnNumber = 0;
        for(int i = 0; i < cards.length; i++)
        {
            if (cards[i].face == true)
            {
                cardFaceOnNumber ++;
            }
        }
        System.out.println(cardFaceOnNumber);
        if (cardFaceOnNumber >= 2) 
        {
            
            return;
        }
            
        else if (cardFaceOnNumber == 0)
        {
            cards[index].flipCard();
            firstCardIndex = index;
            return;
        }
        else if (cardFaceOnNumber == 1 && firstCardIndex == index)
        {
            cards[index].flipCard();
            firstCardIndex = -1;
            return;
        }
        else if (cardFaceOnNumber == 1 && firstCardIndex != index)
        {
            cards[index].flipCard();
            secondCardIndex = index;
            boolean isSame = cards[firstCardIndex].cardValue == cards[secondCardIndex].cardValue;
            if (isSame)
            {
                finishedCardNumber ++;
                score += 10;
                cards[firstCardIndex].button.setVisible(false);
                cards[secondCardIndex].button.setVisible(false);
                cards[firstCardIndex].flipCard();
                cards[secondCardIndex].flipCard();
                
                return;
            }
            score -= 2;
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> 
            {
                cards[firstCardIndex].flipCard();
                cards[secondCardIndex].flipCard();
                
                firstCardIndex = -1;
                secondCardIndex = -1;
            }));
            timeline.play();
        }





        // if (flippedCardNumber >= 2) return;
        // if (cards[index].face)
        // {
        //     flippedCardNumber --;
        // }
        // else
        // {
        //     flippedCardNumber ++;
        // }
        // if (flippedCardNumber == 1)
        // {
        //     firstCardIndex = index;
        //     cards[index].flipCard();
        //     return;
        // }
        // else if (flippedCardNumber == 2)
        // {
        //     cards[index].flipCard();
        //     for(int i = 0; i < cards.length; i++)
        //     {
        //         // cards[i].button.setDisable(true);
        //     }
        //     secondCardIndex = index;
        //     boolean isSame = cards[firstCardIndex].cardValue == cards[secondCardIndex].cardValue;
        //     if (isSame)
        //     {
        //         score ++;
        //     }

        //     Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> 
        //     {
        //         if (isSame)
        //         {
        //             cards[firstCardIndex].button.setVisible(false);
        //             cards[secondCardIndex].button.setVisible(false);
        //         }
        //         cards[firstCardIndex].flipCard();
        //         cards[secondCardIndex].flipCard();
        //         flippedCardNumber = 0;
        //         firstCardIndex = -1;
        //         secondCardIndex = -1;
        //         for(int i = 0; i < cards.length; i++)
        //         {
        //             if (cards[i].button.isVisible())
        //             {
        //                 cards[i].button.setDisable(false);
        //             }
        //         }
        //     }));

        //     timeline.play();
        // }
    }

}