package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import model.entity.*;
import model.exception.GridsBeingTakenException;
import model.exception.OutOfBoardException;

import java.io.IOException;
import java.util.HashMap;

public class BoardPane extends ListView {

    private Entity select = null;
    private boolean head = false;

    @FXML
    private ListView<GridPane> listview;
    private ObservableList<GridPane> lists;
    private GridPane board;
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
        listview.setFocusTraversable(false);
        listview.setPadding(new Insets(20, 0, 0, 0));
        reboard();
    }

    private void reboard() {
        board = new GridPane();
        board.setMinSize(0, 0);
        board.setPrefHeight(400);
        board.setPrefWidth(400);
        board.setAlignment(Pos.CENTER);
        ColumnConstraints c = new ColumnConstraints();
        c.setHalignment(HPos.CENTER);
        c.setMinWidth(10);
        c.setPrefWidth(100);
        RowConstraints r = new RowConstraints();
        r.setValignment(VPos.CENTER);
        r.setMinHeight(10);
        r.setPrefHeight(100);
        for (int i = 0; i < 10; i++) {
            board.getColumnConstraints().add(c);
            board.getRowConstraints().add(r);
        }
        board.setHgap(0);
        board.setVgap(0);
        board.setStyle("-fx-column-halignment: center");
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                Pane pane = new Pane();
                boolean exist = false;
                for (Entity e : collections.values()) {
                    if (e instanceof PGEntity) {
                        if (((PGEntity) e).getPosition().compareTo(new Position(j, i))) {
                            exist = true;
                            ImageView imageView = new ImageView();
                            imageView.setFitHeight(40);
                            imageView.setFitWidth(40);
                            ((PGEntity) e).draw(imageView);
                            imageView.setOnMouseClicked(mouseEvent -> {
                                select = e;
                            });
                            pane.getChildren().add(imageView);
                        }
                    } else if (((SLEntity) e).getEntry().compareTo(new Position(j, i)) || ((SLEntity) e).getExit().compareTo(new Position(j, i))) {
                        exist = true;
                        ImageView imageView = new ImageView();
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);
                        ((SLEntity) e).draw(imageView, new Position(j, i));
                        if (e instanceof Snake) {
                            if (((SLEntity) e).getEntry().compareTo(new Position(j, i))) {
                                imageView.setOnMouseClicked(mouseEvent -> {
                                    select = e;
                                    head = true;
                                });
                            }else{
                                imageView.setOnMouseClicked(mouseEvent -> {
                                    select = e;
                                    head = false;
                                });
                            }
                        }
                        pane.getChildren().add(imageView);
                    }
                }
                if (!exist) {
                    pane.setOnMouseClicked(mouseEvent -> {
                        Node source = (Node) mouseEvent.getSource();
                        Integer colIndex = GridPane.getColumnIndex(source);
                        Integer rowIndex = GridPane.getRowIndex(source);
                        if (select instanceof Piece) {
                            int position = new Position(colIndex, 9 - rowIndex).positionToInt();
                            ((Piece) select).move(collections, position - ((Piece) select).getPosition().positionToInt());
                            select = null;
                            reboard();
                        } else if (select instanceof Snake) {
                            boolean top = false, right = false;
                            if (head) {
                                // head
                                if (colIndex - ((Snake) select).getEntry().getX() == 1)
                                    right = true;
                                if (9 - rowIndex - ((Snake) select).getEntry().getY() == 1)
                                    top = true;
                            } else{
                                // tail
                                if (colIndex - ((Snake) select).getExit().getX() == 1)
                                    right = true;
                                if (9 - rowIndex - ((Snake) select).getExit().getY() == 1)
                                    top = true;
                            }
                            try {
                                StringBuilder s = new StringBuilder("");
                                if (top)
                                    s.append('T');
                                else
                                    s.append('B');
                                if (right)
                                    s.append('R');
                                else
                                    s.append('L');
                                ((Snake) select).move(collections, s.toString());
                            } catch (OutOfBoardException | GridsBeingTakenException e) {
                                e.printStackTrace();
                            }
                            select = null;
                            head = false;
                            reboard();
                        }
                    });
                }
                board.add(pane, j, 9 - i);
            }
        }
        board.setGridLinesVisible(true);
        lists = FXCollections.observableArrayList();
        lists.add(board);
        listview.setItems(lists);
        listview.refresh();
    }
}

