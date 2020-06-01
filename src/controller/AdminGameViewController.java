package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import model.entity.*;
import model.exception.InitializeException;
import model.exception.OnlyOneSnakeGreaterEightyException;
import model.player.DB;
import model.player.Game;

import java.util.HashMap;

import static controller.Util.changeScene;

public class AdminGameViewController {
    private Board board;
    private HashMap<Position, Entity> collections;

    @FXML
    private Pane boardP;

    public void setUp(Board board) {
        this.board = board;
        collections = board.getCollections();

        AdminBoard boardGUI = new AdminBoard(collections);
        boardP.getChildren().add(boardGUI);
//        hBox.getChildren().add(boardGUI);
//        ObservableList<Node> workingCollection = FXCollections.observableArrayList(hBox.getChildren());
//        Collections.swap(workingCollection, 0, 1);
//        hBox.getChildren().setAll(workingCollection);
    }

    // For development
    public void initialize() {
    }

    public void saveBoard(ActionEvent actionEvent) {
        try {
            for (Entity e : collections.values()) {
                if (e instanceof Snake) {
                    Snake snake = new Snake(((Snake) e).getEntry(), ((Snake) e).getExit(), "", collections);
                }
                if (e instanceof Ladder) {
                    Ladder ladder = new Ladder(((Ladder) e).getEntry(), ((Ladder) e).getExit(), "");
                }
            }

            String collection = Game.collectionConvetToStringJson(collections);
            DB db = new DB();
            String delSql = "Delete from boards where id = " + board.getId();
            long id = db.insert(delSql);
            String sql = "INSERT INTO boards (id, collections, createdBy) VALUES('" + board.getId()+"', '" + collection + "', 'Admin')";
            id = db.insert(sql);

            String fileAddress = "/view/admin_edit_view.fxml";
            changeScene(actionEvent, fileAddress);

        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR,exception.toString());
            alert.showAndWait();
        }
    }
}
