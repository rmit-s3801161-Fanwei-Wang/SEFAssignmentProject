package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import model.entity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminBoard extends ListView {

    private ArrayList<Entity>  collections;
    @FXML private ListView<GridPane> listview;
    private Position select;

    public AdminBoard(ArrayList<Entity> collections){
        this.collections = collections;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/adminBoard.fxml"));
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

    private void reboard(){
        select = null;
        GridPane board = new GridPane();
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

        for(Entity e:collections){
            if(e instanceof PGEntity)
                continue;
            SLEntity sl = (SLEntity)e;
            int rolIndexIn = 9 - sl.getEntry().getY();
            int colIndexIn = sl.getEntry().getX();
            ImageView entry = new ImageView();
            entry.setFitHeight(40);
            entry.setFitWidth(40);
            sl.draw(entry,sl.getEntry());
            entry.toFront();
            entry.setOnMouseClicked(mouseEvent -> {
                select = sl.getEntry();
            });
            board.add(entry,colIndexIn,rolIndexIn);

            int rolIndexOut = 9 - sl.getExit().getY();
            int colIndexOut = sl.getExit().getX();
            ImageView exit = new ImageView();
            exit.setFitHeight(40);
            exit.setFitWidth(40);
            sl.draw(exit,sl.getExit());
            exit.toFront();
            exit.setOnMouseClicked(mouseEvent -> {
                select = sl.getExit();
            });
            board.add(exit,colIndexOut,rolIndexOut);
        }

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                Position temp = new Position(j,i);
                boolean exist = false;
                for(Entity e: collections){
                    if(e instanceof Piece)
                        continue;
                    if(temp.compareTo(((SLEntity)e).getEntry())|| temp.compareTo(((SLEntity)e).getExit())) {
                        exist = true;
                        break;
                    }
                }
                if(!exist){
                    Pane pane = new Pane();
                    Label label = new Label();
                    label.setText(String.valueOf(temp.positionToInt()));
                    pane.getChildren().add(label);
                    pane.setOnMouseClicked(mouseEvent -> {
                        if(select!=null){
                            select.setXY(temp);
                            reboard();
                        }
                    });
                    board.add(pane,j,9-i);
                }
            }
        }

        board.setGridLinesVisible(true);
        ObservableList<GridPane> lists = FXCollections.observableArrayList();
        lists.add(board);
        listview.setItems(lists);
        listview.refresh();
    }
}
