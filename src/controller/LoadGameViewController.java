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
import models.Game;

public class LoadGameViewController {
    @FXML private TableView tableView;
    @FXML private Label usernameLabel;
    private ObservableList<Game> observableList;

    public void initialize(){
        usernameLabel.setText(LoginViewController.currentUser.getUsername());
        observableList = FXCollections.observableArrayList(Game.getGames(LoginViewController.currentUser));
        TableColumn<Game, String> gameIDColumn = new TableColumn<>("Game ID");
        gameIDColumn.setMinWidth(200);
        gameIDColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("game_id"));

        TableColumn<Game, String> playerIDColumn = new TableColumn<>("Player ID");
        playerIDColumn.setMinWidth(200);
        playerIDColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("player_id"));

        TableColumn<Game, String> boardIDColumn = new TableColumn<>("Board ID");
        boardIDColumn.setMinWidth(200);
        boardIDColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("board_id"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(gameIDColumn,playerIDColumn,boardIDColumn);


    }
    @FXML
    public void load(ActionEvent event) {

    }

    @FXML
    public void back(ActionEvent event) {
    }
}
