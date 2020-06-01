package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.entity.Board;
import model.exception.GameSLException;
import model.exception.InitializeException;
import model.player.Admin;
import model.player.Game;
import model.player.Player;

import java.io.IOException;
import java.sql.SQLException;

import static controller.Util.changeScene;

public class AdminEditViewController {
    @FXML
    private ListView listView;
    private ObservableList<Integer> observableList;


    public AdminEditViewController(){
        observableList = FXCollections.observableArrayList(Board.getBoards());

    }

    public void initialize(){
        listView.setItems(observableList);
    }
    @FXML
    public void generateBoard(ActionEvent event) throws InitializeException, SQLException {
        Admin admin = new Admin();
        admin.CreateNewBoard();
        observableList = FXCollections.observableArrayList(Board.getBoards());
        listView.setItems(observableList);
        listView.refresh();
    }

    @FXML
    public void editBoard(ActionEvent event) {
        int boardID = (int) listView.getSelectionModel().getSelectedItem();
        try {
            Board board = Board.findBoard(boardID);

            String fileAddress = "/view/adminGame.fxml";
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Util.class.getResource(fileAddress));
            Parent mainViewParent = loader.load();
            AdminGameViewController adminGameViewController = loader.getController();
            adminGameViewController.setUp(board);
            Scene scene = new Scene(mainViewParent);

            //get Window
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (GameSLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//    TODO use board id to edit specific board
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        String fileAddress = "/view/login_view.fxml";
        changeScene(event,fileAddress);
    }
}
