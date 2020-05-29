package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login_view.fxml"));

        stage.setTitle("Sneaks & Ladders Game");
        stage.setScene(new Scene(root,1000  ,1000));
        stage.show();

    }
}
