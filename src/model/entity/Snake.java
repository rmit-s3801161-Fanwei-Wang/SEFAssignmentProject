package model.entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.google.gson.JsonObject;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.exception.GridsBeingTakenException;
import model.exception.InitializeException;
import model.exception.OnlyOneSnakeGreaterEightyException;
import model.exception.OutOfBoardException;

public class Snake extends SLEntity {

    public Snake(Position head, Position tail, String name, ArrayList<Entity> collections) throws InitializeException, OnlyOneSnakeGreaterEightyException, GridsBeingTakenException {
        super(head, tail, name, collections);
        InitializeException ex = new InitializeException(
                "Head:" + head.positionToInt() + " ,Tail:" + tail.positionToInt() + " is not possible");
        if (super.getEntry().positionToInt() - super.getExit().positionToInt() > 30
                || super.getExit().positionToInt() > super.getEntry().positionToInt()
                || super.getEntry().positionToInt() == 100 || super.getEntry().getY() == super.getExit().getY()) {

            throw ex;
        }
        if(super.getEntry().positionToInt()>80) 
            	snakeBound(collections);

//        snakeBound(collections);
    }

    public boolean move(ArrayList<Entity> collections, String choice)
            throws OutOfBoardException, GridsBeingTakenException, OnlyOneSnakeGreaterEightyException {

        Position headDestination = new Position(super.getEntry().getX(), super.getEntry().getY());
        Position tailDestination = new Position(super.getExit().getX(), super.getExit().getY());
        if (choice.compareToIgnoreCase("TL") == 0) {
            headDestination.move("TL");
            tailDestination.move("TL");
        } else if (choice.compareToIgnoreCase("TR") == 0) {
            headDestination.move("TR");
            tailDestination.move("TR");
        } else if (choice.compareToIgnoreCase("BR") == 0) {
            headDestination.move("BR");
            tailDestination.move("BR");
        } else if (choice.compareToIgnoreCase("BL") == 0) {
            headDestination.move("BL");
            tailDestination.move("BL");
        }

        if (headDestination.positionToInt() == 100) {
            throw new OutOfBoardException("Snake head cannot reach 100");
        }

        for (Entity e : collections) {
            if (e instanceof Piece)
                continue;
            else if (e instanceof Guard) {
                Position p = ((Guard) e).getPosition();
                if (p.compareTo(headDestination) || p.compareTo(tailDestination)) {
                    throw new GridsBeingTakenException(super.getName() + " cannot move to " + p.positionToInt()
                            + " because of " + e.getName());
                }
            } else {
                if (e.equals(this))
                    continue;
                Position p1 = ((SLEntity) e).getEntry();
                Position p2 = ((SLEntity) e).getExit();
                if (p1.compareTo(headDestination) || p1.compareTo(tailDestination)) {
                    throw new GridsBeingTakenException(super.getName() + " cannot move to " + p1.positionToInt()
                            + " because of " + e.getName());
                } else if (p2.compareTo(headDestination) || p2.compareTo(tailDestination)) {
                    throw new GridsBeingTakenException(super.getName() + " cannot move to " + p2.positionToInt()
                            + " because of " + e.getName());
                }
            }
        }

        for (Entity e: collections) {
            if(e instanceof Snake) {
                Position p = ((Snake) e).getEntry();
                if (!e.equals(this)) {
                    if (p.positionToInt() > 80 && headDestination.positionToInt() > 80) {
                        System.out.println(((Snake) e).getEntry().positionToInt());
                        throw new OnlyOneSnakeGreaterEightyException(String.format("%s already been greater than 80", e.getName()));
                    }
                }
            }
        }

        super.getEntry().setXY(headDestination);
        super.getExit().setXY(tailDestination);

        for (Entity e:collections) {
            if(e instanceof Piece){
                if(((Piece) e).getPosition().compareTo(super.getEntry())) {
                    if (((Piece) e).getLevel() == 1)
                        adjustPosition((Piece) e);
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Snake win");
                        alert.showAndWait();
                        System.exit(0);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean adjustPosition(Piece piece) {
        if (super.adjustPosition(piece)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    piece.getName() + " is eaten by " + super.getName() + " to " + super.getExit().positionToInt());
            piece.setBuff();
            return true;
        }
        return false;
    }

    public void snakeBound(ArrayList<Entity> collections) throws OnlyOneSnakeGreaterEightyException {
        boolean exist = false;
        for (Entity e:collections) {
            if (e instanceof Snake) {
                if (((Snake) e).getEntry().positionToInt() > 80) {
                    exist = true;
                    break;
                }
            }
        }
        if (exist) {
            throw new OnlyOneSnakeGreaterEightyException();
        }
    }

    @Override
    public void draw(ImageView imageView, Position position) {
        Image image = null;
        String snakeName = "./src/model/icon/snake1H.png";

        if (position.compareTo(this.getExit())) {
            snakeName = snakeName.replace('H', 'T');
        }
        try {
            if (this.getName().compareToIgnoreCase("S1") != 0) {
                snakeName = snakeName.replace('1', this.getName().charAt(1));
            }
            image = new Image(new FileInputStream(snakeName.toString()));
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JsonObject toDbString() {
        JsonObject json = new JsonObject();
        json.addProperty("Type", "Snake");
        json.addProperty("Name", super.getName());
        json.addProperty("TailX", String.valueOf(super.getExit().getX()));
        json.addProperty("TailY", String.valueOf(super.getExit().getY()));
        json.addProperty("HeadX", String.valueOf(super.getEntry().getX()));
        json.addProperty("HeadY", String.valueOf(super.getEntry().getY()));
        return json;
//        return String.format("{\"Type\":\"Snake\",\"Name\":\"%s\",\"TailX\":\"%d\",\"TailY\":\"%d\",\"HeadX\":\"%d\",\"HeadY\":\"%d\"}",
//                super.getName(), super.getExit().getX(),super.getExit().getY(),super.getEntry().getX(),super.getEntry().getY());
    }

}
