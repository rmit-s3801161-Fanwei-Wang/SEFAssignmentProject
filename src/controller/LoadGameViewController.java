package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entity.Board;
import model.exception.GameSLException;
import model.player.Game;
import model.player.Player;

import java.io.IOException;
import java.sql.SQLException;

import static controller.Util.changeScene;

public class LoadGameViewController {
    @FXML
    private TableView tableView;
    @FXML
    private Label usernameLabel;
    private ObservableList<Game> observableList;

    public void initialize() {
//        for (Game game : Game.getGames(LoginViewController.currentUser)) {
//            System.out.println(game.getGameID() + " " + game.getPlayerID() + " " + game.getBoardID());
//        }
        usernameLabel.setText(LoginViewController.currentUser.getUsername());
        observableList = FXCollections.observableArrayList(Game.getGames(LoginViewController.currentUser));

//        System.out.println(Game.getGames(LoginViewController.currentUser));

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
    public void load(ActionEvent event) throws IOException, SQLException, GameSLException {
        Game game = (Game)tableView.getSelectionModel().getSelectedItem();
        game.setBoard(Board.findBoard(game.getBoardID(),LoginViewController.currentUser));
        String fileAddress = "/view/mainGame.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Util.class.getResource(fileAddress));
        Parent mainViewParent = loader.load();
        MainGameController mainGameController = loader.getController();
        mainGameController.setUp(game);
        Scene scene = new Scene(mainViewParent);

        //get Window
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
//        System.out.println("The Game ID you choose is: " + ((Game)tableView.getSelectionModel().getSelectedItem()).getGameID());
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        String fileAddress = "/view/menu_window_view.fxml";
        changeScene(event, fileAddress);
    }
}
