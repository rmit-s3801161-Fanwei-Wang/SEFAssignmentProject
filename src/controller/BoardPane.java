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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import model.entity.*;
import model.exception.CannotMoveException;
import model.exception.GridsBeingTakenException;
import model.exception.OnlyOneSnakeGreaterEightyException;
import model.exception.OutOfBoardException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class BoardPane extends ListView {
    @FXML
    private ListView<GridPane> listview;
    private HashMap<Position, Entity> collections;
    private MainGameController controller;

    private Entity select = null;
    private int round;
    private boolean head = false;
    private boolean human;
    private boolean level;
    private int levelStartRound;
    private int guards = 0;

    public BoardPane(HashMap<Position, Entity> collections, boolean human, boolean level, MainGameController controller) {
        this.collections = collections;
        this.controller = controller;
        round = 0;
        this.human = human;
        this.level = level;
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
        if (level && round - levelStartRound == 20) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Out of Rounds, Snake win!");
            alert.showAndWait();
            System.exit(0);
        }
        if (level && round - levelStartRound < 20) {
            boolean check = true;
            for (Entity e : collections.values()) {
                if (e instanceof Snake)
                    check = false;
            }
            if (check) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Human win!");
                alert.showAndWait();
                System.exit(0);
            }
        }
        if (!level && round >= 50) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Out of Rounds, Snake win!");
            alert.showAndWait();
            System.exit(0);
        }

        if (human) {
            controller.Human.setVisible(true);
            controller.Snake.setVisible(false);
        } else {
            controller.Human.setVisible(false);
            controller.Snake.setVisible(true);
        }

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
        if (human && !level) {
            boolean allBuff = true;
            for (Entity e : collections.values()) {
                if (e instanceof Piece) {
                    if (((Piece) e).getBuff() == 0) {
                        allBuff = false;
                        break;
                    }
                }
            }
            if (allBuff)
                human = false;
        }
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                Pane pane = new Pane();
                Label label = new Label();
                label.setText(String.format("%d", new Position(j, i).positionToInt()));
                pane.getChildren().add(label);
                boolean exist = false;
                for (Entity e : collections.values()) {
                    if (e instanceof PGEntity) {
                        if (((PGEntity) e).getPosition().compareTo(new Position(j, i))) {
                            ImageView imageView = new ImageView();
                            imageView.setFitHeight(40);
                            imageView.setFitWidth(40);
                            if (level && e instanceof Piece && ((Piece) e).getPosition().positionToInt() != 100) {
                                ((Piece) e).draw(imageView, level);
                            } else {
                                ((PGEntity) e).draw(imageView);
                            }
                            if (human) {
                                if (e instanceof Piece) {
                                    exist = true;
                                    imageView.setOnMouseClicked(mouseEvent -> {
                                        select = e;
                                    });
                                    if (!level) {
                                        controller.Dice.setOnAction(actionEvent -> {
                                            if (human && !level && select != null) {
                                                Dice dice = new Dice();
                                                int value = dice.rollDice();
                                                try {
                                                    ((Piece) select).move(collections, value);
                                                    String imagePosition = String.format("./src/model/icon/dice%d.gif_scaling", value);
                                                    Image image = null;
                                                    try {
                                                        image = new Image(new FileInputStream(imagePosition));
                                                    } catch (FileNotFoundException ex) {
                                                        ex.printStackTrace();
                                                    }
                                                    controller.setDiceImage(image);
                                                    if (((Piece) select).getPosition().positionToInt() == 100 && ((Piece) select).getClimbNumber() >= 3) {
                                                        level = true;
                                                        levelStartRound = round;
                                                        for (Entity e1 : collections.values()) {
                                                            if (e1 instanceof Piece) {
                                                                ((Piece) e1).addLevel();
                                                            }
                                                        }
                                                    } else {
                                                        if (value == 6) {
                                                            value = dice.rollDice();
                                                            ((Piece) select).move(collections, value);
                                                            String imagePosition2 = String.format("./src/model/icon/dice%d.gif_scaling", value);
                                                            Image image2 = null;
                                                            try {
                                                                image2 = new Image(new FileInputStream(imagePosition2));
                                                            } catch (FileNotFoundException ex) {
                                                                ex.printStackTrace();
                                                            }
                                                            controller.setDiceImage2(image2);
                                                            if (((Piece) select).getPosition().positionToInt() == 100 && ((Piece) select).getClimbNumber() >= 3) {
                                                                level = true;
                                                                for (Entity e1 : collections.values()) {
                                                                    if (e1 instanceof Piece) {
                                                                        ((Piece) e1).addLevel();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    select = null;
                                                    human = false;
                                                    reboard();
                                                } catch (CannotMoveException ex) {
                                                    Alert alert = new Alert(Alert.AlertType.ERROR, ex.toString());
                                                    alert.showAndWait();
                                                    human = true;
                                                    select = null;
                                                    reboard();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                            imageView.toFront();
                            pane.getChildren().add(imageView);
                        }
                    } else if (((SLEntity) e).getEntry().compareTo(new Position(j, i)) || ((SLEntity) e).getExit().compareTo(new Position(j, i))) {
                        ImageView imageView = new ImageView();
                        imageView.setFitHeight(30);
                        imageView.setFitWidth(30);
                        imageView.toBack();
                        if (human && level)
                            exist = false;
                        else
                            exist = true;
                        ((SLEntity) e).draw(imageView, new Position(j, i));
                        if (!human) {
                            if (e instanceof Snake) {
                                if (((SLEntity) e).getEntry().compareTo(new Position(j, i))) {
                                    imageView.setOnMouseClicked(mouseEvent -> {
                                        select = e;
                                        head = true;
                                    });
                                } else {
                                    imageView.setOnMouseClicked(mouseEvent -> {
                                        select = e;
                                        head = false;
                                    });
                                }
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
                        if (human) {
                            if (level) {
                                if (select != null) {
                                    // TO-DO implements super move
                                    Position position = new Position(colIndex, 9 - rowIndex);
                                    boolean okay = false;
                                    switch ((position.getY() - ((Piece) select).getPosition().getY()) * 10 + position.getX() - ((Piece) select).getPosition().getX()) {
                                        case 21:
                                            try {
                                                ((Piece) select).move(collections, "T2R1");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case 19:
                                            try {
                                                ((Piece) select).move(collections, "T2L1");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case -19:
                                            try {
                                                ((Piece) select).move(collections, "B2R1");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case -21:
                                            try {
                                                ((Piece) select).move(collections, "B2L1");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case 12:
                                            try {
                                                ((Piece) select).move(collections, "T1R2");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case 8:
                                            try {
                                                ((Piece) select).move(collections, "T1L2");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case -8:
                                            try {
                                                ((Piece) select).move(collections, "B1R2");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case -12:
                                            try {
                                                ((Piece) select).move(collections, "B1L2");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case 11:
                                            try {
                                                ((Piece) select).move(collections, "TR");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case 9:
                                            try {
                                                ((Piece) select).move(collections, "TL");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case -9:
                                            try {
                                                ((Piece) select).move(collections, "BR");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        case -11:
                                            try {
                                                ((Piece) select).move(collections, "BL");
                                            } catch (OutOfBoardException e) {
                                                e.printStackTrace();
                                            }
                                            okay = true;
                                            break;
                                        default:
                                            okay = false;
                                    }
                                    if (okay) {
                                        level = true;
                                        select = null;
                                        human = false;
                                        reboard();
                                    }
                                }
                            } else {
                                level = false;
                                select = null;
                                human = true;
                                controller.Guard.setOnAction(actionEvent -> {
                                    if (human) {
                                        if (guards < 3) {
                                            Position position = new Position(colIndex, 9 - rowIndex);
                                            boolean check = false;
                                            for (Entity e : collections.values()) {
                                                if (e instanceof SLEntity) {
                                                    if (((SLEntity) e).getEntry().compareTo(position) || ((SLEntity) e).getExit().compareTo(position)) {
                                                        check = true;
                                                        break;
                                                    }
                                                } else if (e instanceof Guard) {
                                                    if (((Guard) e).getPosition().compareTo(position)) {
                                                        check = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (!check) {
                                                guards++;
                                                Guard guard = new Guard(position, String.format("G%d", guards));
                                                collections.put(position, guard);
                                                human = false;
                                                select = null;
                                                if (guards == 1)
                                                    controller.setGuard3Image();
                                                else if (guards == 2)
                                                    controller.setGuard2Image();
                                                else if (guards == 3)
                                                    controller.setGuard1Image();
                                                reboard();
                                            } else {
                                                Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot place guard on a snake or ladder");
                                                alert.showAndWait();
                                                human = true;
                                                select = null;
                                                reboard();
                                            }
                                        }
                                    }
                                });
                                reboard();
                            }
                        } else {
                            if (select instanceof Snake) {
                                StringBuilder s = new StringBuilder("");
                                boolean okay;
                                if (head) {
                                    switch ((9 - rowIndex - ((Snake) select).getEntry().getY()) * 10 + colIndex - ((Snake) select).getEntry().getX()) {
                                        case 11:
                                            s.append("TR");
                                            okay = true;
                                            break;
                                        case 9:
                                            s.append("TL");
                                            okay = true;
                                            break;
                                        case -9:
                                            s.append("BR");
                                            okay = true;
                                            break;
                                        case -11:
                                            s.append("BL");
                                            okay = true;
                                            break;
                                        default:
                                            okay = false;
                                    }
                                } else {
                                    switch ((9 - rowIndex - ((Snake) select).getExit().getY()) * 10 + colIndex - ((Snake) select).getExit().getX()) {
                                        case 11:
                                            s.append("TR");
                                            okay = true;
                                            break;
                                        case 9:
                                            s.append("TL");
                                            okay = true;
                                            break;
                                        case -9:
                                            s.append("BR");
                                            okay = true;
                                            break;
                                        case -11:
                                            s.append("BL");
                                            okay = true;
                                            break;
                                        default:
                                            okay = false;
                                    }
                                }
                                try {
                                    if (okay) {
                                        ((Snake) select).move(collections, s.toString());
                                        select = null;
                                        head = false;
                                        human = true;
                                        controller.setDiceImage(null);
                                        controller.setDiceImage2(null);
                                        round++;
                                        for (Entity e : collections.values()) {
                                            if (e instanceof Piece) {
                                                if (((Piece) e).getBuff() != 0)
                                                    ((Piece) e).roundBuff();
                                            }
                                        }
                                        reboard();
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.ERROR, "Can only move diagonally one unit");
                                        alert.showAndWait();
                                        select = null;
                                        head = false;
                                        human = false;
                                        reboard();
                                    }
                                } catch (OutOfBoardException | GridsBeingTakenException | OnlyOneSnakeGreaterEightyException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR, e.toString());
                                    alert.showAndWait();
                                    select = null;
                                    head = false;
                                    human = false;
                                    reboard();
                                }
                            }
                        }
                    });
                }
                board.add(pane, j, 9 - i);
            }
        }
        board.setGridLinesVisible(true);
        ObservableList<GridPane> lists = FXCollections.observableArrayList();
        lists.add(board);
        listview.setItems(lists);
        listview.refresh();
    }
}

