package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.player.Game;

import java.io.IOException;

import static controller.Util.changeScene;

public class LoadGameViewController {
    @FXML
    private TableView tableView;
    @FXML
    private Label usernameLabel;
    private ObservableList<Game> observableList;

    public void initialize() {
        for (Game game : Game.getGames(LoginViewController.currentUser)) {
            System.out.println(game.getGameID() + " " + game.getPlayerID() + " " + game.getBoardID());
        }
        usernameLabel.setText(LoginViewController.currentUser.getUsername());
        observableList = FXCollections.observableArrayList(Game.getGames(LoginViewController.currentUser));

        System.out.println(Game.getGames(LoginViewController.currentUser));

        TableColumn<Game, Integer> gameIDColumn = new TableColumn<>("Game ID");
        gameIDColumn.setMinWidth(200);
        gameIDColumn.setCellValueFactory(new PropertyValueFactory<>("gameID"));

        TableColumn<Game, Integer> playerIDColumn = new TableColumn<>("Player ID");
        playerIDColumn.setMinWidth(200);
        playerIDColumn.setCellValueFactory(new PropertyValueFactory<>("playerID"));

        TableColumn<Game, Integer> boardIDColumn = new TableColumn<>("Board ID");
        boardIDColumn.setMinWidth(200);
        boardIDColumn.setCellValueFactory(new PropertyValueFactory<>("boardID"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(gameIDColumn, playerIDColumn, boardIDColumn);


    }

    @FXML
    public void load(ActionEvent event) {
        //TODO load game
        System.out.println("The Game ID you choose is: " + ((Game)tableView.getSelectionModel().getSelectedItem()).getGameID());
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        String fileAddress = "/view/menu_window_view.fxml";
        changeScene(event, fileAddress);
    }
}
