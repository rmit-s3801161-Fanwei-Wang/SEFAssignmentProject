package model.entity;

import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.exception.GridsBeingTakenException;
import model.exception.InitializeException;
import model.exception.OutOfBoardException;

public class Snake extends SLEntity {

    public Snake(Position head, Position tail, String name) throws InitializeException {
        super(head, tail, name);
        InitializeException ex = new InitializeException(
                "Head:" + head.positionToInt() + " ,Tail:" + tail.positionToInt() + " is not possible");
        if (super.getEntry().positionToInt() - super.getExit().positionToInt() > 30
                || super.getExit().positionToInt() > super.getEntry().positionToInt()
                || super.getEntry().positionToInt() == 100) {
//        	|| super.getEntry().getY() == super.getExit().getY()
            throw ex;
        }
    }

    public boolean move(HashMap<Position, Entity> collections, String choice)
            throws OutOfBoardException, GridsBeingTakenException {

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

        for (Entity e: collections.values()) {
            if(e instanceof Piece)
                continue;
            else if(e instanceof Guard) {
                Position p = ((Guard)e).getPosition();
                if (p.compareTo(headDestination) || p.compareTo(tailDestination)) {
                        throw new GridsBeingTakenException(super.getName() + " cannot move to " + p.positionToInt()
                                + " because of " + collections.get(p).getName());
                }
            } else{
                if(e.equals(this))
                    continue;
                Position p1 = ((SLEntity)e).getEntry();
                Position p2 = ((SLEntity)e).getExit();
                if (p1.compareTo(headDestination) || p1.compareTo(tailDestination)) {
                    throw new GridsBeingTakenException(super.getName() + " cannot move to " + p1.positionToInt()
                            + " because of " + collections.get(p1).getName());
                } else if(p2.compareTo(headDestination) || p2.compareTo(tailDestination)){
                    throw new GridsBeingTakenException(super.getName() + " cannot move to " + p2.positionToInt()
                            + " because of " + collections.get(p1).getName());
                }
            }
        }

        System.out.print(super.getName() + " head moves from " + super.getEntry().positionToInt());
        super.getEntry().setXY(headDestination);
        System.out.println(" to " + super.getEntry().positionToInt());

        System.out.print(super.getName() + " tail moves from " + super.getExit().positionToInt());
        super.getExit().setXY(tailDestination);
        System.out.println(" to " + super.getExit().positionToInt());

        for (Position p : collections.keySet()) {
            if (p.compareTo(super.getEntry())) {
                if (collections.get(p) instanceof Piece) {
                    if (((Piece) collections.get(p)).getLevel() == 1) {
                        adjustPosition((Piece) collections.get(p));
                    } else if (((Piece) collections.get(p)).getLevel() == 3) {
                        System.out.println("Snake win");
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean adjustPosition(Piece piece) {
        if (super.adjustPosition(piece)) {
            System.out.println(
                    piece.getName() + " is eaten by " + super.getName() + " to " + super.getExit().positionToInt());
            piece.setBuff();
            return true;
        }
        return false;
    }

    @Override
    public void draw(ImageView imageView, Position position) {
        Image image = null;
        String snakeName = "./src/model/icon/snake1H.png";

        if(position.compareTo(this.getExit())) {
            snakeName = snakeName.replace('H','T');
        }
        try {
            if(this.getName().compareToIgnoreCase("S1")!=0){
                snakeName = snakeName.replace('1',this.getName().charAt(1));
            }
            image = new Image(new FileInputStream(snakeName.toString()));
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toDbString() {
        return String.format("{\"Type\":\"Snake\",\"Name\":\"%s\",\"TailX\":\"%d\",\"TailY\":\"%d\",\"HeadX\":\"%d\",\"HeadY\":\"%d\"}",
                super.getName(), super.getExit().getX(),super.getExit().getY(),super.getEntry().getX(),super.getEntry().getY());
    }

}
