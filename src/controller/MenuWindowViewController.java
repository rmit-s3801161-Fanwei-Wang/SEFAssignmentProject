package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.player.Game;
import model.player.Player;

import java.io.IOException;
import java.sql.SQLException;

import static controller.Util.changeScene;

public class MenuWindowViewController {
    @FXML
    public void loadGame(ActionEvent event) throws IOException {
        String fileAddress = "/view/loadGame_view.fxml";
        changeScene(event,fileAddress);

    }

    @FXML
    public void createGame(ActionEvent event) throws IOException, SQLException {
        if(LoginViewController.currentUser instanceof Player) {
            Game game = Game.createGame((Player) LoginViewController.currentUser);
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
        }
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        String fileAddress = "/view/login_view.fxml";
        changeScene(event,fileAddress);
    }
}
