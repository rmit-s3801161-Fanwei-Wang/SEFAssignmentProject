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
        stage.setScene(new Scene(root,300  ,250));
        stage.show();

//import java.io.IOException;
//
//public class MainGUI extends Application {
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/view/mainGame.fxml"));
//            Scene scene = new Scene(root);
//            stage.setTitle("Snakes And Ladders");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
