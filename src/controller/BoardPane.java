package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.entity.Entity;
import model.entity.Position;

import java.io.IOException;
import java.util.HashMap;

public class BoardPane extends GridPane {

    @FXML private GridPane board;
    HashMap<Position, Entity> collections = new HashMap<>();

    public BoardPane(HashMap<Position, Entity> collections) {
        this.collections = collections;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/board.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                for (Position p : collections.keySet()) {
                    if (p.compareTo(new Position(j, i))) {
                        Label label = new Label(collections.get(p).getName());
                        board.add(label, j, 9-i);
                    }
                }
//                Position temp = new Position(j,i);
//                Label back = new Label(String.format("%d",temp.positionToInt()));
//                board.add(back, j, 9-i);
            }
        }
        board.setGridLinesVisible(true);
    }
}
