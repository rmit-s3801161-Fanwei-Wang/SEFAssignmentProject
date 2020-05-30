package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static controller.Util.changeScene;

public class MenuWindowViewController {
    @FXML
    public void loadGame(ActionEvent event) {

    }

    @FXML
    public void createGame(ActionEvent event) {

    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        String fileAddress = "/view/login_view.fxml";
        changeScene(event,fileAddress);
    }
}
