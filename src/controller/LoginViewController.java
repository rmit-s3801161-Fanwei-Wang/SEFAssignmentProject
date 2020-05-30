package controller;

import exception.DBException;
import exception.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.player.Admin;
import model.player.DB;
import model.player.User;

import java.io.IOException;

import static controller.Util.alertBox;
import static controller.Util.changeScene;

public class LoginViewController {
    @FXML private Label errorLabel;
    @FXML private TextField email;
    @FXML private TextField password;

    public static User currentUser;


    @FXML
    public void signIn(ActionEvent event) throws IOException {
        String fileAddress = "/view/signIn_view.fxml";
        changeScene(event, fileAddress);

    }



    @FXML
    public void login(ActionEvent event) throws IOException, DBException, ValidationException {
        try {
            //TODO change true as login(email.getText(),password.getText()) and add try&catch
            if(login(email.getText(),password.getText())){
                //login successful change scene to the menu window
                String fileAddress = "/view/menu_window_view.fxml";
                changeScene(event, fileAddress);

            }else{
                errorLabel.setText("Wrong password or email!");
            }

        } catch (DBException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    //TODO change login method: login by using the username and password
    public boolean login(String email, String password) throws DBException, ValidationException {
        DB db = new DB();

        currentUser = db.findPlayer(email, password);

        if(currentUser !=null)
            return true;

        db.db_close();
        return false;
    }

    public void loginAsAdmin(ActionEvent event) throws DBException, ValidationException {
        if(!login(email.getText(),password.getText()));{
            errorLabel.setText("Wrong password or email!");
        }

        if(currentUser instanceof Admin){
            alertBox("Alert", "YOU ARE AN ADMIN.");
        }
    }

    public void loadTestAccount(ActionEvent event) {
        email.setText("test@email.com");
        password.setText("asdf1234");
    }
}
