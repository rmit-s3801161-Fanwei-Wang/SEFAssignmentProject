package controller;

import exception.DBException;
import exception.ExistException;
import exception.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DB;
import models.Player;

import java.io.IOException;
import java.util.HashMap;

public class SignInViewController {

    @FXML private TextField username;
    @FXML private TextField email;
    @FXML private TextField password;
    @FXML private TextField conPassword;
    @FXML private Label errorLabel;

    private DB db;
    @FXML
    public void CreateNewUser(ActionEvent event) throws DBException, IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

//        isNewUserValid()
        if(true){
            Player newPlayer = new Player(username.getText(), password.getText(), email.getText());
//            String sql = "insert into users (username, password, email, type) values(?, ?, ?, ?)";
//            HashMap<String, Object> resultHashMap = (HashMap<String, Object>) db.create(sql, newPlayer);
//            db.db_close();

            //change scene back to login window
            String fileAddress = "/view/login_view.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(fileAddress));
            Scene scene = new Scene(root);

            //get Window
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        }
    }

    private boolean isNewUserValid() throws DBException {
        StringBuilder errorMessage = new StringBuilder("");

        if (email.getText().isBlank() || password.getText().isBlank() || conPassword.getText().isBlank()) {
            errorMessage.append("Please enter right information!\n");
        }

        db = new DB();

        if (db.isUserExist(email.getText())) {
            errorMessage.append("Email exist!\n");
        }

        String emailPattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String passPattern = "^[a-zA-Z]\\w{5,17}$";
        if (!email.getText().matches(emailPattern)) {
            errorMessage.append("It is not an email!\n");
        }

        if (!password.getText().matches(passPattern)) {
            errorMessage.append("It is not a valid password!\n");
        } else if (!password.equals(conPassword)) {
            errorMessage.append("Password is not same!\n");
        }

        if (username.getText().isBlank()) {
            errorMessage.append("Email exist!\n");
            int playerCounts = db.count("select count(*) from users where type = 'player'");
            username.setText("Player" + (++playerCounts));
        }

        errorLabel.setText(errorMessage.toString());

        if (errorMessage.length() == 0)
            return true;
        else
            return false;
    }


    //Put logic here first TODO implement this with GUI part
    public static void signUp(String email, String userName, String password, String conPassword) throws ValidationException, DBException, ExistException {
        LoginViewController.currentUser = null;
        if (email.isBlank() || password.isBlank() || conPassword.isBlank()) {
            throw new ValidationException("Please enter right information!");
        }
        DB db = new DB();
        if (db.isUserExist(email)) {
            throw new ExistException("Email exist!");
        }

        String emailPattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String passPattern = "^[a-zA-Z]\\w{5,17}$";
        if (!email.matches(emailPattern)) {
            System.err.println("It is not an email!");
            throw new ValidationException("It is not an email!");
        }

        if (!password.matches(passPattern)) {
            System.err.println("It is not a valid passaword!");
            throw new ValidationException("It is not a valid passaword!");
        } else if (!password.equals(conPassword)) {
            System.err.println("Password is not same!");
            throw new ValidationException("Password is not same!");
        }

        if (userName.isBlank()) {
            int playerCounts = db.count("select count(*) from users where type = 'player'");
            userName = "Player" + (++playerCounts);
        }


        Player newPlayer = new Player(userName, password, email);
        String sql = "insert into users (username, password, email, type) values(?, ?, ?, ?)";
        HashMap<String, Object> resultHashMap = (HashMap<String, Object>) db.create(sql, newPlayer);
        db.db_close();

        if ((boolean)resultHashMap.get("status")) {
            newPlayer.setID((long) resultHashMap.get("id"));
            LoginViewController.currentUser = newPlayer;
        } else {
            throw new DBException((String) resultHashMap.get("message"));
        }
    }
}
