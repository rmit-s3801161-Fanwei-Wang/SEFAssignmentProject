package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.entity.*;
import model.exception.CannotMoveException;
import model.exception.GameSLException;
import model.exception.InitializeException;
import model.exception.OnlyOneSnakeGreaterEightyException;
import model.player.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static controller.Util.changeScene;

public class MainGameController {

    @FXML
    public Button Dice;
    @FXML
    public Label ROUND;
    @FXML
    public Label round;
    @FXML
    public Label LEVELROUND;
    @FXML
    public Label levelRound;
    @FXML
    private ImageView diceImage;
    @FXML
    private ImageView diceImage2;
    @FXML
    public Label Human;
    @FXML
    public Label Snake;
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

    private ArrayList<Entity> collections;
    private Game game;
    private BoardPane boardGUI;

    public boolean human, level;

    public void setUp(Game game) {
        this.game = game;
        collections = game.getBoard().getCollections();

        int count = 3;
        for (Entity e : collections) {
            if (e instanceof Guard)
                count--;
        }
        Guard temp = new Guard(null, "");
        switch (count) {
            case 3:
                temp.draw(Guard3);
            case 2:
                temp.draw(Guard2);
            case 1:
                temp.draw(Guard1);
            default:
        }

        try {
            HUMAN.setImage(new Image(new FileInputStream(Piece.pieceShape)));
            SNAKE.setImage(new Image(new FileInputStream("./src/model/icon/SNAKE.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Human.setText(String.format("%s, it's your turn", LoginViewController.currentUser.getUsername()));
        Snake.setText(String.format("%s, it's your turn", LoginViewController.currentUser.getUsername()));
        round.setText(game.getRound());
        levelRound.setText(game.getLevelRound());
        if(!level){
            LEVELROUND.setVisible(false);
            levelRound.setVisible(false);
        }
        /* // To delete!!!!!!
        Piece[] pieces = new Piece[4];
        for (int i = 0; i < 4; i++) {
            pieces[i] = new Piece(null,"P+"+(i+2));
        }
        for (int j=0;j<4;j++) {
            try {
                pieces[j].move(collections, 1);
            } catch (CannotMoveException e) {
                e.printStackTrace();
            }
        }*/

        boardGUI = new BoardPane(collections, game.getHuman(), game.getLevel(),game.getRound(),game.getLevelRound(), this);
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

    public void BackToLogInView(ActionEvent actionEvent) throws IOException {
        String fileAddress = "/view/menu_window_view.fxml";
        changeScene(actionEvent, fileAddress);
    }

    public void SaveGame(ActionEvent actionEvent) {
        game.setHuman(boardGUI.getHuman());
        game.setLevel(boardGUI.getLevel());
        game.setRound(boardGUI.getRound());
        game.setLevelRound(boardGUI.getLevelRound());
        try {
            game.saveGame();
            String fileAddress = "/view/menu_window_view.fxml";
            changeScene(actionEvent, fileAddress);
        }catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR,exception.toString());
            alert.showAndWait();
        }
    }
}
