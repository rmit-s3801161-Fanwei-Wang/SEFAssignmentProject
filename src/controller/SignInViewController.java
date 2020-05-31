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
import model.player.DB;
import model.player.Player;

import java.io.IOException;
import java.util.HashMap;

import static controller.Util.alertBox;
import static controller.Util.changeScene;

public class SignInViewController {

    @FXML private TextField username;
    @FXML private TextField email;
    @FXML private TextField password;
    @FXML private TextField conPassword;
    @FXML private Label errorLabel;

    private DB db;

    @FXML
    public void createNewUser(ActionEvent event) throws DBException, IOException {
//        isNewUserValid() set this in if()
        if(isNewUserValid()){
            Player newPlayer = new Player(username.getText(), password.getText(), email.getText());
            String sql = "insert into users (username, password, email, type) values(?, ?, ?, ?)";
            HashMap<String, Object> resultHashMap = (HashMap<String, Object>) db.create(sql, newPlayer);
            db.db_close();

            alertBox("Congratulation","\nSign in successful.\nID: " + newPlayer.getID() + "\nUsername: " + newPlayer.getUsername());
            //change scene back to login window
            String fileAddress = "/view/login_view.fxml";
            changeScene(event,fileAddress);
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
        }

        if (!password.getText().equals(conPassword.getText())) {
            errorMessage.append("Password is not same!\n");
        }

        if (username.getText().isBlank()) {
            int playerCounts = db.count("select count(*) from users where type = 'player'");
            username.setText("Player" + (++playerCounts));
        }

        errorLabel.setText(errorMessage.toString());

        if (errorMessage.length() == 0)
            return true;
        else
            return false;
    }


    public void cancel(ActionEvent event) throws IOException {
        //change scene back to login window
        String fileAddress = "/view/login_view.fxml";
        changeScene(event,fileAddress);
    }
}
