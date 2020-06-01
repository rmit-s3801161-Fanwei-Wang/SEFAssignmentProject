package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.entity.Board;

import java.io.IOException;

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
    public void generateBoard(ActionEvent event) {


    }

    @FXML
    public void editBoard(ActionEvent event) {
        int boardID = (int) listView.getSelectionModel().getSelectedItem();
//    TODO use board id to edit specific board
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        String fileAddress = "/view/login_view.fxml";
        changeScene(event,fileAddress);
    }
}
