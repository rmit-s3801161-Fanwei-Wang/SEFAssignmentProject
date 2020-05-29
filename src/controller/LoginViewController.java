package controller;

import exception.DBException;
import exception.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Admin;
import models.DB;
import models.User;

import java.io.IOException;

import static controller.Util.changeScene;

public class LoginViewController {
    @FXML private Label errorLabel;
    @FXML private TextField username;
    @FXML private TextField password;

    public static User currentUser;


    @FXML
    public void signIn(ActionEvent event) throws IOException {
        String fileAddress = "/view/signIn_view.fxml";
        changeScene(event, fileAddress);

    }



    @FXML
    public void login(ActionEvent event) throws IOException {
//        try {
            //TODO change true as login(username.getText(),password.getText()) and add try&catch
            if(true){
                //login successful change scene to the menu window
                String fileAddress = "/view/menu_window_view.fxml";
                changeScene(event, fileAddress);

            }else{
                errorLabel.setText("Wrong password or email!");
            }

//        } catch (DBException e) {
//            e.printStackTrace();
//        } catch (ValidationException e) {
//            e.printStackTrace();
//        }
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
        if(!login(username.getText(),password.getText()));{
            errorLabel.setText("Wrong password or email!");
        }

        if(currentUser instanceof Admin){
            //TODO change scene to edit map window
        }
    }
}
