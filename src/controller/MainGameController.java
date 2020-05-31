package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.entity.*;
import model.exception.CannotMoveException;
import model.exception.InitializeException;
import model.exception.OnlyOneSnakeGreaterEightyException;
import model.player.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;

public class MainGameController {

    @FXML
    public Button Dice;
    @FXML
    private ImageView diceImage;
    @FXML
    private ImageView diceImage2;
    @FXML
    private Label Human;
    @FXML
    private Label Snake;
    @FXML
    private HBox hBox;
    @FXML
    private ImageView HUMAN;
    @FXML
    private ImageView SNAKE;
    @FXML
    private ImageView Guard1;
    @FXML
    private ImageView Guard2;
    @FXML
    private ImageView Guard3;
    @FXML
    public Button Guard;

    private HashMap<Position, Entity> collections;

    public void setUp(Game game) {
        collections = game.getBoard().getCollections();
        Piece[] pieces = new Piece[4];
        try {
            HUMAN.setImage(new Image(new FileInputStream(Piece.pieceShape)));
            SNAKE.setImage(new Image(new FileInputStream("./src/model/icon/SNAKE.png")));
            Guard temp = new Guard(null, "");
            temp.draw(Guard1);
            temp.draw(Guard2);
            temp.draw(Guard3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 4; i++) {
            pieces[i] = new Piece(null, Character.toString((char) (65 + i)));
        }
        try {
            for (int j = 0; j < 4; j++) {
                pieces[j].move(collections, 1);
            }
        } catch (CannotMoveException e) {
            e.printStackTrace();
        }

        BoardPane boardGUI = new BoardPane(collections, true, false, this);
        hBox.getChildren().add(boardGUI);
        ObservableList<Node> workingCollection = FXCollections.observableArrayList(hBox.getChildren());
        Collections.swap(workingCollection, 0, 1);
        hBox.getChildren().setAll(workingCollection);
    }

    // For development
    public void initialize() {
    }

    public void setDiceImage(Image image) {
        diceImage.setImage(image);
    }

    public void setDiceImage2(Image image2) {
        diceImage2.setImage(image2);
    }

    public void setGuard1Image() {
        Guard1.setImage(null);
    }

    public void setGuard2Image() {
        Guard2.setImage(null);
    }

    public void setGuard3Image() {
        Guard3.setImage(null);
    }
}
