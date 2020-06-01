package model.player;

import java.io.IOException;
import java.sql.SQLException;

import controller.AdminGameViewController;
import controller.LoginViewController;
import controller.MainGameController;
import controller.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.entity.Board;
import model.exception.InitializeException;

public class Admin extends User {
//    static int uniqueID = 0;

    public static void main(String[] args) {
        Admin admin = new Admin();
        try {
            admin.CreateNewBoard();
        } catch (InitializeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Admin() {
    }

    public Admin(String username, String password, String userEmail) {
        super(username, password, userEmail);
    }

    public boolean CreateNewBoard() throws InitializeException, SQLException {
        Board board = Game.initBoard();
        String collection = Game.collectionConvetToStringJson(board.getCollections());
        DB db = new DB();
        String sql = "INSERT INTO boards (collections, createdBy) VALUES('" + collection + "', 'Admin')";
        long id = db.insert(sql);
        if (id == -1) {
            return false;
        }

        return true;
    }

    // ladder ,snake larger than 30    
    //TODO implement
    public Board setNewBoard() {
        return null;
    }
}
