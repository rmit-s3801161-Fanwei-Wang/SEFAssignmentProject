package model.entity;


import javafx.scene.image.ImageView;
import model.exception.GridsBeingTakenException;

import java.util.HashMap;

abstract public class SLEntity extends Entity{
    private Position entry;
    private Position exit;

//    public SLEntity(Position entry, Position exit, String name){
//        super(name);
//        this.entry = entry;
//        this.exit = exit;
//    }

    public SLEntity(Position entry, Position exit, String name, HashMap<Position,Entity> collections) throws GridsBeingTakenException {
        super(name);
        this.entry = entry;
        this.exit = exit;

        for(Entity e:collections.values()){
            if(e instanceof SLEntity){
                if(((SLEntity) e).getEntry().compareTo(this.entry) || ((SLEntity) e).getEntry().compareTo(this.exit) || ((SLEntity) e).getExit().compareTo(this.entry) || ((SLEntity) e).getExit().compareTo(this.exit))
                    throw new GridsBeingTakenException("Grids already been taken by " + e.getName());
            }
        }
    }

    public Position getEntry() {
        return entry;
    }

    public Position getExit() {
        return exit;
    }

    public boolean adjustPosition(Piece piece) {
        if (exit == null || entry == null)
            return false;
        if (piece.getPosition() != null)
            if (piece.getPosition().compareTo(entry)) {
                piece.getPosition().setXY(exit);
                return true;
            }
        return false;
    }

    abstract public void draw(ImageView imageView,Position position);

}
