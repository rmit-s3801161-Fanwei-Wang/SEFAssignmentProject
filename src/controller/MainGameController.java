package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import model.entity.*;
import model.exception.InitializeException;

import java.util.HashMap;

public class MainGameController {

    @FXML
    private HBox hBox;

    public void initialize() {
        HashMap<Position, Entity> collections = new HashMap<>();
        Piece[] pieces = new Piece[4];

        try {
            Board board = new Board();
            collections = board.getCollections();

            board.addCollection(new Ladder(new Position(1, 0), new Position(2, 2), "L1"));
            board.addCollection(new Ladder(new Position(7, 0), new Position(1, 2), "L2"));
            board.addCollection(new Ladder(new Position(3, 1), new Position(3, 3), "L3"));
            board.addCollection(new Ladder(new Position(4, 3), new Position(0, 5), "L4"));
            board.addCollection(new Ladder(new Position(9, 4), new Position(0, 6), "L5"));

            board.addCollection(new Snake(new Position(1, 9), new Position(1, 7), "S1"));
            board.addCollection(new Snake(new Position(3, 7), new Position(7, 5), "S2"));
            board.addCollection(new Snake(new Position(5, 5), new Position(6, 3), "S3"));
            board.addCollection(new Snake(new Position(8, 8), new Position(1, 6), "S4"));
            board.addCollection(new Snake(new Position(5, 3), new Position(9, 1), "S5"));

            for (int i = 0; i < 4; i++) {
                pieces[i] = new Piece(null, Character.toString((char) (65 + i)));
            }
            for (int j = 0; j < 4; j++) {
                pieces[j].move(collections, 1);
            }
            pieces[0].move(collections, 17);
            pieces[1].move(collections, 35);
            pieces[2].move(collections, 53);
            pieces[3].move(collections, 71);
        } catch (InitializeException e) {
            e.printStackTrace();
        }

        BoardPane boardGUI = new BoardPane(collections);
        hBox.getChildren().add(boardGUI);
    }
}
